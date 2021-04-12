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
	private double discount;
	private double totalDiscountedAmount;

	public ListCartProductsResponse(List<CartProduct> products, double totalAmount) {
		super();
		this.discount = 0;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void addDiscount(double discount) {
		this.discount += discount;
	}

	public double getTotalDiscountedAmount() {
		return totalDiscountedAmount == 0 ? totalAmount : totalDiscountedAmount;
	}

	public void setTotalDiscountedAmount(double totalDiscountedAmount) {
		this.totalDiscountedAmount = totalDiscountedAmount;
	}

	@Override
	public String toString() {
		return "ListCartProductsResponse [products=" + products + ", totalAmount=" + totalAmount + ", discount="
				+ discount + ", totalDiscountedAmount=" + totalDiscountedAmount + "]";
	}

}
