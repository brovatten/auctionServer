package com.example.server.api;

import com.example.server.dao.AuctionTable;
import com.example.server.dao.DBService;
import com.example.server.model.Bid;
import com.example.server.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping(path="api")
@RestController
public class BidController {


    private BidService bidService;
    private DBService dbService;

    @Autowired
    public BidController(BidService bidService, DBService dbService) {
        this.bidService = bidService;
        this.dbService = dbService;
    }

    @GetMapping(path = "/bid")
    public List<Bid> getAllBids() throws ExecutionException, InterruptedException {
        return bidService.getAllBids2();
    }


    @PostMapping(path = "/bid")
    public void addBid(@RequestBody Bid bid) throws ExecutionException, InterruptedException {
         //bidService.addFirstBid(bid);
         bidService.addFirstBid2(bid);
    }

    @GetMapping(path = "/auction")
    public @ResponseBody Iterable<AuctionTable> getAllAuctions() {
        // This returns a JSON or XML with the users
        return dbService.getAllAuctions();
    }

    @PostMapping(path = "/auction")
    public String addNewAuction (@RequestBody AuctionTable auctionTable){
        dbService.addNewAuction(auctionTable);
        return "Saved";
    }
}
