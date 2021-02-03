package com.srikanthmadhira.portfolio.pointsservice.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClients;
import com.srikanthmadhira.portfolio.pointsservice.model.response.PointsResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GetPointsDaoImpl implements IGetPointsDao{

	MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "UserPointsDatabase");
	
	@Override
	public List<PointsResponse> getAll(Pageable pagination) {

		AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
	    SortOperation sort = Aggregation
	            .sort(Sort.by("createDate"));

	    //setting upper limit for number of pages that can be requested to 50
        int pageSizeLimit = Math.min(pagination.getPageSize(), 50);
        LimitOperation limit = Aggregation.limit(pageSizeLimit);
        SkipOperation skip = Aggregation
                .skip((long) pagination.getPageNumber() * pagination.getPageSize());
        
		Aggregation aggretaion = Aggregation.newAggregation(sort, limit, skip).withOptions(options);
		log.debug("Inside getAll(), Executing mongo aggregation [{}]", aggretaion.toString());
		List<PointsResponse> result = mongoOps.aggregate(aggretaion, "UserPointsMetrics", PointsResponse.class)
				.getMappedResults();

		return result;
	}

}
