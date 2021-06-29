
package com.example.server.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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
        return "Saved";
    }

    public @ResponseBody String addNewBid (BidTable bidTable) {
        bidRepository.save(bidTable);
        return "Saved";
    }

    public @ResponseBody Iterable<AuctionTable> getAllAuctions() {
        // This returns a JSON or XML with the users
        return auctionRepository.findAll();
    }

    public @ResponseBody Iterable<BidTable> getAllBids() {
        // This returns a JSON or XML with the users
        return bidRepository.findAll();
    }
}