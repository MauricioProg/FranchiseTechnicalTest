package com.tecnical_test.franchise_management.franchise_core.infrastructure.repository;


import com.tecnical_test.franchise_management.franchise_core.domain.model.FranchiseModel;
import com.tecnical_test.franchise_management.franchise_core.infrastructure.entity.FranchiseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface FranchiseRepository extends ReactiveMongoRepository<FranchiseEntity, Integer> {

    Mono<FranchiseEntity> findFirstByOrderByIdDesc();
}
