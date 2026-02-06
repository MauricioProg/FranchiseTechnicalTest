package com.tecnical_test.franchise_management.franchise_core.application.handler;


import com.tecnical_test.franchise_management.franchise_core.application.dto.request.BranchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@Component
public class CreateBranchHandler {

    public ResponseEntity<JsonNode> executeCreateFranchise(BranchRequest branchRequest) {

        if (branchRequest.getFranchiseId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (branchRequest.getBranchName() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return null;
    }

}
