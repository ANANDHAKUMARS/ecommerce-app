package com.anandh.ecommerceapp.service.bean;

import com.anandh.ecommerceapp.bean.CartProduct;

/**
 * 
 * @author anandhakumar.s
 *
 */
public class SaveCartProductRequest {
	private CartProduct product;

	public CartProduct getProduct() {
		return product;
	}

	public void setProduct(CartProduct product) {
		this.product = product;
	}
}
