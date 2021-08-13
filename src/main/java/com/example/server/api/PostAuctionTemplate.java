package com.example.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;

class PostAuctionTemplate {
    private final Integer auctionId;
    private final Integer auctioneer;

    PostAuctionTemplate(@JsonProperty("auctionId") Integer auctionId,
                        @JsonProperty("auctioneer") Integer auctioneer) {
        this.auctionId = auctionId;
        this.auctioneer = auctioneer;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public Integer getAuctioneer() {
        return auctioneer;
    }
}