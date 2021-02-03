package com.srikanthmadhira.portfolio.pointsservice.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mongodb.client.MongoClients;
import com.srikanthmadhira.portfolio.pointsservice.model.Points;
import com.srikanthmadhira.portfolio.pointsservice.model.RedeemPointsRequest;
import com.srikanthmadhira.portfolio.pointsservice.model.response.PointsResponse;
import com.srikanthmadhira.portfolio.pointsservice.model.response.RedeemResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Dao implementation class that implemenst the interface
 * {@link IPointsAccountingDao}
 * 
 * @author SM053453
 *
 */
@Slf4j
@Service
public class PointsAccountingDaoImpl implements IPointsAccountingDao {

	MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "UserPointsDatabase");

	@Autowired
	public PointsAccountingDaoImpl(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
	}
	@Override
	public void earnPoints(Points request) {
		log.debug("Entered earnPoints method in the DAO layer.");
		if (request.getPoints() < 0) {
			log.debug("User requesting to add negative points.");
			long total = getTotalPointsResponseAvailableForAccount(request.getAccountId(), request.getUserName());
			Assert.isTrue(total + request.getPoints() >= 0, "Users can not accumulate nevative points!!");
			if (total == -1 * request.getPoints()) {
				log.info("User [{}] has exactly enough points get a refund, processing the refund.",
						request.getUserName());
				Criteria deleteCriteria = Criteria.where("userName").is(request.getUserName());
				Query deleteQuery = Query.query(deleteCriteria);
				mongoOps.remove(deleteQuery, Points.class, "UserPointsMetrics");
			} else if (total > -1 * request.getPoints()) {
				log.info("User [{}] has more than enough points get a refund, processing the refund.",
						request.getUserName());
				Criteria updateCriteria = Criteria.where("userName").is(request.getUserName());
				long currentPoints = getCurrentPoints(updateCriteria);
				Update update = new Update();
				Query updateQuery = Query.query(updateCriteria);
				update.set("points", currentPoints - request.getPoints());
				mongoOps.updateFirst(updateQuery, update, "UserPointsMetrics");
			}

		} else {
			log.debug("Creating new document in Mongo for account [{}], and user [{}] with [{}] points.",
					request.getAccountId(), request.getUserName(), request.getPoints());
			mongoOps.insert(new Points(request.getAccountId(), new Date(System.currentTimeMillis()),
					request.getUserName(), request.getPoints()), "UserPointsMetrics");
		}

	}

	@Override
	public List<RedeemResponse> redeemPoints(RedeemPointsRequest request) {
		log.debug("Entered redeemPoints method in the DAO layer.");
		int points = request.getPoints();
		long total = getTotalPointsResponseAvailableForAccount(request.getAccountId(), null);
		log.info("Checking if there is enough points in the account.");
		Assert.isTrue(total >= points, "You cannot redeem more points than you have!");
		Criteria criteria = Criteria.where("accountId").is(request.getAccountId());
		AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
		MatchOperation match = new MatchOperation(criteria);
		SortOperation sort = Aggregation.sort(Sort.by("createDate"));
		Aggregation aggretaion = Aggregation.newAggregation(match, sort).withOptions(options);
		log.debug(
				"Executing the query aggretaion for redeemPoints to get all the points associated with account [{}] ::: []",
				request.getAccountId(), aggretaion.toString());
		List<PointsResponse> result = mongoOps.aggregate(aggretaion, "UserPointsMetrics", PointsResponse.class)
				.getMappedResults();
		List<RedeemResponse> redeemResponseObject = new ArrayList<>();
		List<String> uniqueIds = new ArrayList<String>();
		// this loop iterates over all the points items and picks as many entries from
		// the database as needed for successful redemption
		for (PointsResponse item : result) {
			if (points - item.getPoints() >= 0) {
				redeemResponseObject.add(new RedeemResponse("now", item.getUserName(), -1 * item.getPoints()));
				uniqueIds.add(item.getId());
				points -= item.getPoints();
			} else if (points - item.getPoints() < 0) {
				redeemResponseObject.add(new RedeemResponse("now", item.getUserName(), -1 * points));
				uniqueIds.add(item.getId());
				break;
			}
		}

		// update all the uniqueIds to corresponding values,
		// keep track of who is giving how much in the redeemResponseObject for using in
		// response
		// delete all records/documents in the DB that currently have ZERO points
		// balance
		// remove all items from users that contributed all their points
		Criteria deleteCriteria = Criteria.where("_id").in(uniqueIds.subList(0, uniqueIds.size() - 1));
		Query deleteQuery = Query.query(deleteCriteria);
		log.debug(
				"Executing delete operation over the picked records in the previous stage, query being executed is : [{}]",
				deleteQuery.toString());
		mongoOps.remove(deleteQuery, "UserPointsMetrics");
		// update the entry for the last user that contributed a part of their points
		Criteria updateCriteria = Criteria.where("_id").is(uniqueIds.get(uniqueIds.size() - 1));
		// get current value and update
		// update last candidate with new points.
		Query updateQuery = Query.query(updateCriteria);
		RedeemResponse currentItem = redeemResponseObject.get(redeemResponseObject.size() - 1);
		Update update = new Update();
		update.set("points", getCurrentPoints(updateCriteria) + currentItem.getPoints());
		log.debug("Executing update operation over the record that contributed a part of its points,"
				+ " query being executed is : [{}]", deleteQuery.toString());
		mongoOps.updateFirst(updateQuery, update, "UserPointsMetrics");
		return redeemResponseObject;
	}

	/**
	 * 
	 * private method inside the {@link PointsAccountingDaoImpl} class that is used
	 * by both {@link PointsAccountingDaoImpl#earnPoints(Points)} and
	 * {@link PointsAccountingDaoImpl#redeemPoints(RedeemPointsRequest)} methods to
	 * calculate the total points available in an account.
	 * 
	 * @param accountId - accountId for the account from which the points are being
	 *                  redeemed.
	 * @param userName  - nullable field that contains the userName for which
	 *                  balance is being refunded.
	 * @return a {@link Long} variable containing the total number of points
	 *         available.
	 */
	private long getTotalPointsResponseAvailableForAccount(long accountId, String userName) {

		log.debug(
				"Entered getTotalPointsResponseAvailableForAccount method for calculating the total number of points in an account.");
		Criteria criteria = Criteria.where("accountId").is(accountId);
		if (userName != null) {
			criteria.and("userName").is(userName);
		}
		AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
		MatchOperation match = new MatchOperation(criteria);
		SortOperation sort = Aggregation.sort(Sort.by("createDate"));
		Aggregation aggretaion = Aggregation.newAggregation(match, sort).withOptions(options);
		long total = mongoOps.aggregate(aggretaion, "UserPointsMetrics", Points.class).getMappedResults().stream()
				.map(item -> item.getPoints()).collect(Collectors.summingInt(Integer::intValue));
		return total;

	}

	/**
	 * 
	 * private method inside the {@link PointsAccountingDaoImpl} class that is used
	 * by both {@link PointsAccountingDaoImpl#earnPoints(Points)} and
	 * {@link PointsAccountingDaoImpl#redeemPoints(RedeemPointsRequest)} methods to
	 * calculate the total points available in an account.
	 * 
	 * @param updateCriteria - {@link Criteria} for querying the DB for extracting
	 *                       the number of points in a specific record.
	 * 
	 * @return - a {@link Long} variable containing the points for the specific
	 *         record to be updated.
	 */
	private long getCurrentPoints(Criteria updateCriteria) {
		log.debug("Entered getCurrentPoints method for calculating the number of points in a specific record by id.");
		MatchOperation match = new MatchOperation(updateCriteria);
		SortOperation sort = Aggregation.sort(Sort.by("createDate"));
		AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
		Aggregation aggretaion = Aggregation.newAggregation(match, sort).withOptions(options);

		return mongoOps.aggregate(aggretaion, "UserPointsMetrics", Points.class).getMappedResults().get(0).getPoints();

	}
}
