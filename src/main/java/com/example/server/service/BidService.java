package com.example.server.service;


import com.example.server.dao.BidTable;
import com.example.server.dao.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {

    RaftService raftService;
    DBService dbService;


    @Autowired
    public BidService(DBService dbService, RaftService raftService) {
        this.dbService = dbService;
        this.raftService = raftService;
    }

    //ArrayList<Bid> bids = new ArrayList<>();
    //int currentHighestBid = 0;
    void addNewPerson(){

    }

    /*public String addBid(Bid bid, int auctionId) throws ExecutionException, InterruptedException {
        //Bid(key,value) = (auctionId, bidAmount-hash)
        if (raftService.highestBid() > bid.getAmount())
                return "Too low bid";
        raftService.put(String.valueOf(auctionId), bid.getAmount() + "-" + String.valueOf(bid.hashCode()));
        return "Saved";
    }*/

    public Iterable<BidTable> getBidsForThisAuction(int auctionId){
        return dbService.getBidsForThisAuction(auctionId);
    }



    /*public ArrayList<Bid> getAllBids2() throws ExecutionException, InterruptedException {
        return raftService.getAllBids();
    }

   *//* public List<Bid> getAllBids(){return bids;}

    public String addFirstBid(Bid bid) {
        if(bid.getAmount()>currentHighestBid) {
            bids.add(bid);
            currentHighestBid = bid.getAmount();
            String succes = "ok bid";
            return succes;
        }
        String error = "too low bid";
        return error;
    }*//*

    public void addFirstBid2(Bid bid) throws ExecutionException, InterruptedException {
        if(bid.getAmount()>currentHighestBid) {
            raftService.put(bid.getPerson(),Integer.toString(bid.getAmount()));
            currentHighestBid = bid.getAmount();
        }
    }*/
}
