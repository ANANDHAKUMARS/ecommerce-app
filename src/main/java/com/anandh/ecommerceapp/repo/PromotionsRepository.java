package com.anandh.ecommerceapp.repo;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.anandh.ecommerceapp.bean.Promotion;
import com.anandh.ecommerceapp.bean.PromotionType;

/**
 * 
 * @author anandhakumar.s
 *
 */
@Repository
public class PromotionsRepository {
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Promotion> listPromotions(Set<String> productIds) {
		Query query = Query.query(new Criteria().andOperator(
						Criteria.where("type").in(PromotionType.PRODUCT.name(), PromotionType.TOTAL_AMOUNT.name()),
						Criteria.where("productId").in(productIds)));
		return mongoTemplate.find(query, Promotion.class);
	}

	public Promotion save(Promotion promotion) {
		mongoTemplate.save(promotion);
		return promotion;
	}

	public void deleteById(String id) {
		mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Promotion.class);
	}

	public Promotion findPromotionById(String id) {
		return mongoTemplate.findById(id, Promotion.class);
	}
}
