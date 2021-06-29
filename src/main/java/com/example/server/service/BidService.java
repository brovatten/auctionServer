package com.example.server.service;


import com.example.server.model.Bid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Service
public class BidService {

    //ArrayList<Bid> bids = new ArrayList<>();
    RaftService raftService = new RaftService();
    int currentHighestBid = 0;

    void addNewPerson(){

    }

    public ArrayList<Bid> getAllBids2() throws ExecutionException, InterruptedException {
        return raftService.getAllBids();
    }

   /* public List<Bid> getAllBids(){return bids;}

    public String addFirstBid(Bid bid) {
        if(bid.getAmount()>currentHighestBid) {
            bids.add(bid);
            currentHighestBid = bid.getAmount();
            String succes = "ok bid";
            return succes;
        }
        String error = "too low bid";
        return error;
    }*/

    public void addFirstBid2(Bid bid) throws ExecutionException, InterruptedException {
        if(bid.getAmount()>currentHighestBid) {
            raftService.put(bid.getPerson(),Integer.toString(bid.getAmount()));
            currentHighestBid = bid.getAmount();
        }
    }
}
