package com.anandh.ecommerceapp.service.bean;

import java.util.List;

import com.anandh.ecommerceapp.bean.CartProduct;

/**
 * 
 * @author anandhakumar.s
 *
 */
public class ListCartProductsResponse {
	private List<CartProduct> products;
	private double totalAmount;

	public ListCartProductsResponse(List<CartProduct> products, double totalAmount) {
		super();
		this.products = products;
		this.totalAmount = totalAmount;
	}

	public List<CartProduct> getProducts() {
		return products;
	}

	public void setProducts(List<CartProduct> products) {
		this.products = products;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
