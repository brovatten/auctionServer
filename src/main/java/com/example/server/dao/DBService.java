
package com.example.server.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Service
public class DBService {
     // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private BidRepository bidRepository;
    private AuctionRepository auctionRepository;


    @Autowired
    public DBService(BidRepository bidRepository, AuctionRepository auctionRepository){
        this.bidRepository = bidRepository;
        this.auctionRepository = auctionRepository;
    }

    public @ResponseBody String addNewAuction (AuctionTable auctionTable){
        auctionRepository.save(auctionTable);
        //System.out.println("wtf" + auctionRepository.findAll());
        System.out.println("AuctionAdded");
        return "Saved";
    }

    public @ResponseBody String addNewBid (BidTable bidTable,int auctionId) {
        bidRepository.save(bidTable);
        auctionRepository.updateHighestBid(bidTable.getBidId(),auctionId);
        return "Saved";
    }

    public @ResponseBody Iterable<AuctionTable> getAllAuctions() {
        // This returns a JSON or XML with the users
        return auctionRepository.findAll();
    }

    public @ResponseBody Iterable<BidTable> getBidsForThisAuction(int auctionId) {
        // This returns a JSON or XML with the users
        System.out.println(bidRepository.findByAuctionId(auctionId));
        return bidRepository.findByAuctionId(auctionId);

    }
}
