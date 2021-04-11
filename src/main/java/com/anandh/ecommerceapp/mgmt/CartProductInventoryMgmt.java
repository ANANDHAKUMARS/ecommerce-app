package com.anandh.ecommerceapp.mgmt;

import java.util.List;

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
	@Autowired
	private CartProductInventoryRepository cartProductInventoryRepository;
	@Autowired
	private ApplyPromotionsMgmt promotionsMgmt;

	public ListCartProductsResponse listProducts(ListCartProductsRequest request) throws Exception {
		List<CartProduct> products = cartProductInventoryRepository.listProducts(request);
		return promotionsMgmt.applyPromotions(products);
	}

	public void save(CartProduct product) {
		cartProductInventoryRepository.save(product);
	}

	public void deleteById(String id) {
		cartProductInventoryRepository.deleteById(id);
	}

	public Product getProductById(String productId) {
		return cartProductInventoryRepository.findProductById(productId);
	}

}
