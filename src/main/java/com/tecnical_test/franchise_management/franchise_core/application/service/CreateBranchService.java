package com.tecnical_test.franchise_management.franchise_core.application.service;


import com.tecnical_test.franchise_management.franchise_core.application.dto.request.BranchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tools.jackson.databind.JsonNode;

public class CreateBranchService {

    public ResponseEntity<JsonNode> executeCreateBranch(BranchRequest branchRequest) {



        return new ResponseEntity<>(HttpStatus.OK);
    }

}
