
package com.example.server.dao;


import org.springframework.data.repository.CrudRepository;
import com.example.server.dao.BidTable;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BidRepository extends CrudRepository<BidTable, Integer> {

}
