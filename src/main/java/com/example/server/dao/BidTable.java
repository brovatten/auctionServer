
package com.example.server.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;

@Entity
public class BidTable {
    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    private Integer bidId;


    private String personId;
    private int amount;
    private int auctionId;


    public BidTable(@JsonProperty("bidId") int bidId,
                    @JsonProperty("amount") int amount,
                    @JsonProperty("personId") String personId,
                    @JsonProperty("auctionId") Integer auctionId
    ) {
        this.bidId = bidId;
        this.personId = personId;
        this.amount = amount;
    }

    public BidTable() {

    }


    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public String getPerson() {
        return personId;
    }

    public void setPerson(String personId) {
        this.personId = personId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

