
package com.example.server.dao;

import org.springframework.data.repository.CrudRepository;
import com.example.server.dao.BidTable;
import org.springframework.data.repository.query.Param;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BidRepository extends CrudRepository<BidTable, Integer> {

    public Iterable<BidTable> findByAuctionId(@Param("auctionId") Integer auctionId);

}
