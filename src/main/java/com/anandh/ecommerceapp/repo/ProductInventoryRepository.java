package com.anandh.ecommerceapp.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.anandh.ecommerceapp.bean.Product;
import com.anandh.ecommerceapp.service.bean.ListProductsRequest;

/**
 * 
 * @author anandhakumar.s
 *
 */
@Repository
public class ProductInventoryRepository {
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Product> listProducts(ListProductsRequest request) {
		Query query = new Query();
		if (request.getSearch() != null && !request.getSearch().isEmpty()) {
			query.addCriteria(Criteria.where("name").regex(request.getSearch()));
		}
		if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
			query.addCriteria(Criteria.where("id").in(request.getProductIds()));
		}
		return mongoTemplate.find(query.skip(request.getSkip()).limit(request.getLimit()), Product.class);
	}

	public Product save(Product product) {
		mongoTemplate.save(product);
		return product;
	}

	public void deleteById(String id) {
		mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Product.class);
	}

	public Product findProductById(String id) {
		return mongoTemplate.findById(id, Product.class);
	}
}
