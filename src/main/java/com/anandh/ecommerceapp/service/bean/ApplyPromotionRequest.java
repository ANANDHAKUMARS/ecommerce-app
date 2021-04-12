package com.anandh.ecommerceapp.service.bean;

import com.anandh.ecommerceapp.bean.CartProduct;
import com.anandh.ecommerceapp.bean.Product;
import com.anandh.ecommerceapp.bean.Promotion;

/**
 * 
 * @author anandhakumar.s
 *
 */
public class ApplyPromotionRequest {
	private Promotion promotion;
	private CartProduct cartProduct;
	private Product product;

	public ApplyPromotionRequest(Promotion promotion, CartProduct cartProduct, Product product) {
		super();
		this.promotion = promotion;
		this.cartProduct = cartProduct;
		this.product = product;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public CartProduct getCartProduct() {
		return cartProduct;
	}

	public void setCartProduct(CartProduct cartProduct) {
		this.cartProduct = cartProduct;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
