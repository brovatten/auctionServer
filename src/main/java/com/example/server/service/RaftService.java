package com.example.server.service;

import com.example.server.dao.AuctionTable;
import com.example.server.dao.BidTable;
import com.example.server.dao.DBService;
import com.example.server.model.Auction;
import com.example.server.model.Bid;
import io.etcd.jetcd.*;
import io.etcd.jetcd.api.Compare;
import io.etcd.jetcd.api.WatchResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.kv.TxnResponse;
import io.etcd.jetcd.op.Cmp;
import io.etcd.jetcd.op.CmpTarget;
import io.etcd.jetcd.op.Op;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.options.WatchOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class RaftService {

    private Client client = Client.builder().endpoints("http://localhost:2379").build();
    private KV kvClient = client.getKVClient();
    private DBService dbService;


    @Autowired
    public RaftService(DBService dbService) {
        this.dbService = dbService;
    }


    public int highestBid() {
        return 5;
    }

    //Lägger till när auktionen skapas i raft. Den kollar efter när den läggs till, och sparar sedan auktionen i databasen
    //och slutar lyssna på keyn.
    public void putAuction(int auctioneer, int auctionId) {
        AuctionTable auctionTableSendRaft = new AuctionTable(auctionId, auctioneer);
        System.out.println(auctionTableSendRaft);
        System.out.println(auctionId);

        String rawKey = String.valueOf(auctionId);

        String initialOffer = "null";
        String valueString = String.valueOf(auctioneer) + "-" + initialOffer;

        ByteSequence key = ByteSequence.from(rawKey.getBytes());
        ByteSequence value = ByteSequence.from(valueString.getBytes());
        kvClient.put(key, value);

        Logger log = LoggerFactory.getLogger("watcher");

        Watch watch = client.getWatchClient();
        ByteSequence watchKey = ByteSequence.from(String.valueOf(auctionId).getBytes());
        WatchOption watchOption = WatchOption.newBuilder().build();
        watch.watch(watchKey, watchOption,
                new Watch.Listener() {

                    @Override
                    public void onNext(io.etcd.jetcd.watch.WatchResponse watchResponse) {

                        ByteSequence keyBytes = watchResponse.getEvents().get(0).getKeyValue().getKey();
                        int auctionId = Integer.parseInt(new String(keyBytes.getBytes(), StandardCharsets.UTF_8));

                        ByteSequence valueBytes = watchResponse.getEvents().get(0).getKeyValue().getValue();
                        String valueString = new String(valueBytes.getBytes(), StandardCharsets.UTF_8);

                        String[] extractedValueString = extractDataFromValue(valueString);
                        String highestBidAmount = extractedValueString[1];
                        System.out.println(highestBidAmount);
                        if (highestBidAmount.equals("null")) {
                            int auctioneer = Integer.parseInt(extractedValueString[0]);
                            AuctionTable auctionTableSendDB = new AuctionTable(auctionId, auctioneer);
                            dbService.addNewAuction(auctionTableSendDB);
                        } else {
                            int highestBidderId = Integer.parseInt(extractedValueString[0]);
                            int highestBidAmountInt = Integer.parseInt(highestBidAmount);
                            BidTable bidTable = new BidTable(highestBidderId,highestBidAmountInt,"alex",auctionId);
                            dbService.addNewBid(bidTable,auctionId);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onCompleted() {

                    }
                }
        );
    }

    public String[] extractDataFromValue(String valueString) {
        String[] dataString = valueString.split("-");
        String[] data = {dataString[0], dataString[1]};
        return data;
    }

    public String createValueString(int bidId, int amount){
        return bidId + "-" + amount;
    }

    public String getAuctionBid(int auctionId) throws ExecutionException, InterruptedException {
        ByteSequence key = ByteSequence.from(String.valueOf(auctionId).getBytes());
        CompletableFuture<GetResponse> futureResponse = client.getKVClient().get(key);
        GetResponse response = futureResponse.get();
        System.out.println(response.toString());
        return "hej";
    }

    public ArrayList<Integer> getAllAuctions() throws ExecutionException, InterruptedException {
        ByteSequence key = ByteSequence.from("\0".getBytes());
        GetOption option = GetOption.newBuilder().withRange(key).build();
        CompletableFuture<GetResponse> futureResponse = client.getKVClient().get(key, option);
        GetResponse response = futureResponse.get();

        ArrayList<Integer> auctions = new ArrayList<>();
        for (KeyValue kv : response.getKvs()) {
            /*System.out.println(kv.getValue());
            System.out.println(kv.getKey());
            System.out.println(kv.getValue().toString(StandardCharsets.UTF_8));
            System.out.println(kv.getKey().toString(StandardCharsets.UTF_8));*/
            String auctionIdStr = kv.getKey().toString(StandardCharsets.UTF_8);
            int auctionId = Integer.parseInt(auctionIdStr);
            auctions.add(auctionId);
        }
        return auctions;
    }

    public void putBid(int auctionId, int bidId, int amount) throws ExecutionException, InterruptedException {

        ByteSequence key = ByteSequence.from(String.valueOf(auctionId).getBytes());

        CompletableFuture<GetResponse> futureResponse = kvClient.get(key,GetOption.DEFAULT);
        GetResponse response = futureResponse.get();
        ByteSequence valueBytes = response.getKvs().get(0).getValue();
        String valueString = new String(valueBytes.getBytes(), StandardCharsets.UTF_8);
        String[] extractedValueString = extractDataFromValue(valueString);


        try {
            int highestBidAmount = Integer.parseInt(extractedValueString[1]);
            if(highestBidAmount>amount)
                return;
        }
        catch (NumberFormatException e){
            System.out.println(e);
        }

        ByteSequence value = ByteSequence.from(createValueString(bidId,amount).getBytes());

        CompletableFuture<PutResponse> futureResponse2 = kvClient.put(key,value);
        System.out.println(futureResponse2);

    }

   /* public void put(String keyParam, String valueParam) throws ExecutionException, InterruptedException {
        keyParam = "10";
        valueParam = "15";
        String key2Param = "pow123";
        //if pow > value(pow) put (pow,pow)
        ByteSequence key = ByteSequence.from(keyParam.getBytes());
        ByteSequence value = ByteSequence.from(valueParam.getBytes());
        ByteSequence key2 = ByteSequence.from(key2Param.getBytes());

        kvClient.txn().If(new Cmp(value, Cmp.Op.EQUAL, CmpTarget.value(key))).Then(Op.put(key2, value, PutOption.DEFAULT)).commit();
        System.out.println("här" + CmpTarget.value(value));
        //kvClient.put(key,value).get();
        System.out.println("nu" + new Cmp(value, Cmp.Op.GREATER, CmpTarget.value(key)));

        // construct txn operation
        System.out.println("hej2123");
        System.out.println(key);
        System.out.println(key2);
        Txn txn = kvClient.txn();
        Cmp cmp = new Cmp(key, Cmp.Op.EQUAL, CmpTarget.value(key));
        CompletableFuture<io.etcd.jetcd.kv.TxnResponse> txnResp = txn.If(cmp)
                .Then(Op.put(key, key2, PutOption.DEFAULT)).Else(Op.put(key, value, PutOption.DEFAULT))
                .commit();
        txnResp.get();
        // get the value
        GetResponse getResp = kvClient.get(key).get();


        //kvClient.put(key, value).get();
        //Op.put(key,value, PutOption.)

        CompletableFuture<GetResponse> getFuture = kvClient.get(key);
        GetResponse response = getFuture.get();
        System.out.println(response);

    }*/

    //public String deleteAllBids(){

    //}

   /* void test(String bidAmount, String hash,String auctionId){
        ByteSequence key = ByteSequence.from(keyParam.getBytes());
        ByteSequence value = ByteSequence.from(valueParam.getBytes());
        kvClient.txn().If(Compare((
                auctionId + bidAmount, ">",
                )))
    }*/

    ArrayList<Bid> getAllBids() throws ExecutionException, InterruptedException {
        ByteSequence key = ByteSequence.from("\0".getBytes());
        GetOption option = GetOption.newBuilder().withRange(key).build();
        CompletableFuture<GetResponse> futureResponse = client.getKVClient().get(key, option);
        GetResponse response = futureResponse.get();

        System.out.println("count: " + response.getCount());
        ArrayList<Bid> bids = new ArrayList<>();
        for (KeyValue kv : response.getKvs()) {
            /*System.out.println(kv.getValue());
            System.out.println(kv.getKey());
            System.out.println(kv.getValue().toString(StandardCharsets.UTF_8));
            System.out.println(kv.getKey().toString(StandardCharsets.UTF_8));*/
            bids.add(new Bid(kv.getKey().toString(StandardCharsets.UTF_8), Integer.parseInt(kv.getValue().toString(StandardCharsets.UTF_8))));
        }
        return bids;
    }
}
