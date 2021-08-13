package com.example.server.api;

import com.example.server.dao.AuctionTable;
import com.example.server.dao.BidTable;
import com.example.server.dao.DBService;
import com.example.server.service.AuctionService;
import com.example.server.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

@RequestMapping(path = "api")
@RestController
public class BidController {

    private AuctionService auctionService;
    private BidService bidService;
    private DBService dbService;

    private int auctionId = 0;
    private String CONTROLLER_URI = "http://localhost:8081/api";




    @Autowired
    public BidController(AuctionService auctionService, BidService bidService, DBService dbService) {
        this.auctionService = auctionService;
        this.bidService = bidService;
        this.dbService = dbService;

        //RestTemplate restTemplate = new RestTemplate();
        //restTemplate.postForObject(CONTROLLER_URI + "/server/started/" + this.auctionId,this.auctionId, Integer.class);
        try {
            serverStartedMessage();
        }
        catch(Exception e){
            System.out.println("Sending confirmation didnt work");
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/bid")
    public Iterable<BidTable> getBidsForThisAuction() throws ExecutionException, InterruptedException {
        return bidService.getBidsForThisAuction(this.auctionId);
    }


    public void serverStartedMessage(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(CONTROLLER_URI + "/server/started", auctionId, Integer.class);
    }


/*    @PostMapping(path = "/bid")
    public void addBid(@RequestBody Bid bid) throws ExecutionException, InterruptedException {
         //bidService.addFirstBid(bid);
    }*/

    @GetMapping(path = "/auction")
    public @ResponseBody
    Iterable<AuctionTable> getAllAuctions() throws ExecutionException, InterruptedException {
        // This returns a JSON or XML with the users
        return auctionService.getAllAuctions();
    }

    @PostMapping(path = "/auction")
    public void addNewAuction(@RequestBody PostAuctionTemplate postAuctionTemplate) {
        //dbService.addNewAuction(auctionTable);
        int auctionId = postAuctionTemplate.getAuctionId();
        int auctioneer = postAuctionTemplate.getAuctioneer();
        auctionService.addNewAuction(auctioneer, auctionId);
        System.out.println("controllerauctiondone");
    }

    @PostMapping(path = "/auction/setting")
    public void setAuctionId(@RequestBody Integer auctionId) {
        this.auctionId = auctionId;
        System.out.println("controllerauctionsettingdone");
    }

    @PostMapping(path = "/bid")
    public void addNewBid(@RequestBody PostBidTemplate postBidTemplate) throws ExecutionException, InterruptedException {
        //dbService.addNewAuction(auctionTable);
        System.out.println("nufunkardet");
        int bidId = postBidTemplate.getBidId();
        int auctionId = postBidTemplate.getAuctionId();
        int amount = postBidTemplate.getAmount();
        auctionService.addNewBid(auctionId,bidId, amount);
        System.out.println("controllerbiddone");
    }

   /* @PostMapping(path = "/server")
    public void setServersAuctionId(@RequestBody Integer serversAuctionId) {
        auctionService.setAuctionServerId(serversAuctionId);
        System.out.println("controllerserverdone");
    }*/
}
