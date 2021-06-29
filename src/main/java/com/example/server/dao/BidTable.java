
package com.example.server.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BidTable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer bidId;


    private String personId;
    private int amount;



    public BidTable(@JsonProperty("personId") String personId,
                    @JsonProperty("amount") int amount) {
        this.personId = personId;
        this.amount = amount;
    }

    public BidTable() {

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

