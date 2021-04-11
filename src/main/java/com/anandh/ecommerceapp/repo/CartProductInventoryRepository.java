package com.anandh.ecommerceapp.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.anandh.ecommerceapp.bean.CartProduct;
import com.anandh.ecommerceapp.bean.Product;
import com.anandh.ecommerceapp.service.bean.ListCartProductsRequest;

/**
 * 
 * @author anandhakumar.s
 *
 */
@Repository
public class CartProductInventoryRepository {
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<CartProduct> listProducts(ListCartProductsRequest request) {
		Query query = new Query();
		if (request.getUserId() != null && request.getUserId().isEmpty()) {
			query.addCriteria(Criteria.where("userId").is(request.getUserId()));
		}
		if (request.getSearch() != null && request.getSearch().isEmpty()) {
			query.addCriteria(Criteria.where("name").regex(request.getSearch()));
		}
		return mongoTemplate.find(query.skip(request.getSkip()).limit(request.getLimit()), CartProduct.class);
	}

	public CartProduct save(CartProduct product) {
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
