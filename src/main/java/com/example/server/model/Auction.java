package com.example.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Auction implements Serializable {

    private int auctionId;

    private int highestBidId;

    private int auctioneer;


    public Auction(@JsonProperty("auctionId") int auctionId, @JsonProperty("highestBidId") int highestBidId, @JsonProperty("auctioneer") int auctioneer) {
        this.auctionId = auctionId;
        this.highestBidId = highestBidId;
        this.auctioneer = auctioneer;
    }

    public Auction() {

    }
}

