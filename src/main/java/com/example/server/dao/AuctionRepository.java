
package com.example.server.dao;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.server.dao.BidTable;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AuctionRepository extends CrudRepository<AuctionTable, Integer> {

    @Transactional
    @Modifying
    @Query("update AuctionTable a set a.highestBidId = :newHighestBidId where a.auctionId = :auctionId")
    public void updateHighestBid(@Param("newHighestBidId") int newHighestBidId,
                                 @Param("auctionId") int auctionId);
}