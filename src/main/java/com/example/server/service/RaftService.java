package com.example.server.service;

import com.example.server.model.Bid;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.GetOption;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RaftService {

    private Client client = Client.builder().endpoints("http://localhost:2379").build();
    private KV kvClient = client.getKVClient();

    void put(String keyParam, String valueParam) throws ExecutionException, InterruptedException {
        ByteSequence key = ByteSequence.from(keyParam.getBytes());
        ByteSequence value = ByteSequence.from(valueParam.getBytes());
        kvClient.put(key, value).get();

        CompletableFuture<GetResponse> getFuture = kvClient.get(key);
        GetResponse response = getFuture.get();
        System.out.println(response);

    }

    //public String deleteAllBids(){

    //}

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
