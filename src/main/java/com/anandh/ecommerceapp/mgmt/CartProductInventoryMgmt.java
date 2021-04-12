package com.anandh.ecommerceapp.mgmt;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anandh.ecommerceapp.bean.CartProduct;
import com.anandh.ecommerceapp.bean.Product;
import com.anandh.ecommerceapp.repo.CartProductInventoryRepository;
import com.anandh.ecommerceapp.service.bean.ListCartProductsRequest;
import com.anandh.ecommerceapp.service.bean.ListCartProductsResponse;

/**
 * 
 * @author anandhakumar.s
 *
 */
@Service
public class CartProductInventoryMgmt {
	private static final Logger LOGGER = LoggerFactory.getLogger(CartProductInventoryMgmt.class);
	@Autowired
	private CartProductInventoryRepository cartProductInventoryRepository;
	@Autowired
	private HandleCartProductsMgmt handleCartMgmt;

	public ListCartProductsResponse listProducts(ListCartProductsRequest request) throws Exception {
		LOGGER.info("listProducts method started. request: {}", request);
		List<CartProduct> products = cartProductInventoryRepository.listProducts(request);
		ListCartProductsResponse response = handleCartMgmt.calculateCartProductAmounts(products);
		LOGGER.info("listProducts method completed. response: {}", response);
		return response;
	}

	public void save(CartProduct product) {
		LOGGER.info("save method started. request: {}", product);
		cartProductInventoryRepository.save(product);
		LOGGER.info("save method completed");
	}

	public void deleteById(String id) {
		LOGGER.info("delete method started id : {}", id);
		cartProductInventoryRepository.deleteById(id);
		LOGGER.info("delete method completed");
	}

	public Product getProductById(String productId) {
		return cartProductInventoryRepository.findProductById(productId);
	}

}
