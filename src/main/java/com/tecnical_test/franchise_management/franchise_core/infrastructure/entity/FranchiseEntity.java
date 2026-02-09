package com.tecnical_test.franchise_management.franchise_core.infrastructure.entity;


import com.tecnical_test.franchise_management.franchise_core.domain.model.BranchModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.json.JsonObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "franchises")
public class FranchiseEntity {

    @Id
    private int id;

    private String name;

    private List<BranchEntity> branchList;

}
