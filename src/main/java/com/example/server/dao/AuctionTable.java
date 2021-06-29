
package com.example.server.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AuctionTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int auctionId;

    private String highestBidId;

    private int auctioneer;


    public AuctionTable(@JsonProperty("auctioneer") int auctioneer) {
        this.auctioneer = auctioneer;
    }

    public AuctionTable() {

    }

    public int getAuctioneer() {
        return auctioneer;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public String getHighestBidId() {
        return highestBidId;
    }

    public void setHighestBidId(String highestBidId) {
        this.highestBidId = highestBidId;
    }
}
