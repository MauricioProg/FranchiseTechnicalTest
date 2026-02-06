package com.tecnical_test.franchise_management.franchise_core.infrastructure.repository;


import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FranchiseRepository extends MongoRepository<FranchiseEntity,String> {
}
