package com.anandh.ecommerceapp.bean;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author anandhakumar.s
 *
 */
@Document("cart")
public class CartProduct {
	private String productId;
	private String userId;
	private long totalGoods;
	private double discountedAmount;
	private double discount;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public long getTotalGoods() {
		return totalGoods;
	}

	public void setTotalGoods(long totalGoods) {
		this.totalGoods = totalGoods;
	}

	public double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
}
