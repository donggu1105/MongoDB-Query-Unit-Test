package com.example.mongodb;

import com.example.mongodb.domain.ArtWork;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.FacetOperation;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MongodbApplication.class})
class MongodbApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName(value = "$facet")
    @Test
    public void facet_test() {

        FacetOperation facet = new FacetOperation();

        List<AggregationOperation> agg = new ArrayList<>();

        agg.add(
                facet
                        .and(
                                unwind("tags"),
                                sortByCount("tags")

                ).as("categorizedByTags")
                        .and(
                                bucketAuto(
                                        "year", 4
                                )
                ).as("categorizedByYears(Auto)")

        );


        agg.add(facet);

        Map<String, Object> result = mongoTemplate.aggregate(newAggregation(ArtWork.class, agg), Map.class).getUniqueMappedResult();

        System.out.println("result : " + result);

    }
}
