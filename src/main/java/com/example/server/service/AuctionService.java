package com.example.server.service;

import com.example.server.dao.AuctionTable;
import com.example.server.dao.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Service
public class AuctionService {

    RaftService raftService;
    DBService dbService;

   private int serversAuctionId = 2;


    @Autowired
    public AuctionService(DBService dbService, RaftService raftService) {
        this.dbService = dbService;
        this.raftService = raftService;
    }


    public @ResponseBody
    Iterable<AuctionTable> getAllAuctions() {
        return dbService.getAllAuctions();
    }

    public String addNewAuction(@RequestBody int auctioneer, int auctionId) {
        raftService.putAuction(auctioneer, auctionId);
        //dbService.addNewAuction(auctionTable);
        return "Saved";
    }

    public String addNewBid(@RequestBody int auctionId, int bidId, int amount) throws ExecutionException, InterruptedException {
        raftService.putBid(auctionId, bidId,amount);
        //dbService.addNewAuction(auctionTable);
        return "Saved";
    }

    public String getAuctionBid() throws ExecutionException, InterruptedException {
        raftService.getAuctionBid(this.serversAuctionId);
        dbService.getBidsForThisAuction(this.serversAuctionId);
        return "Saved";
    }

    public ArrayList<Integer> getAllAuctionsIds() throws ExecutionException, InterruptedException {
        System.out.println(dbService.getAllAuctions());
        return raftService.getAllAuctions();
    }

    public String setAuctionServerId(int serversAuctionId){
        this.serversAuctionId = serversAuctionId;
        return "serversAuctionId set";
    }

}
