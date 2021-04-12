package com.anandh.ecommerceapp.service.bean;

import java.util.List;

import com.anandh.ecommerceapp.bean.Product;

/**
 * 
 * @author anandhakumar.s
 *
 */
public class ListProductsResponse {
	private List<Product> products;
	private ListProductsRequest request;

	public ListProductsResponse(List<Product> products, ListProductsRequest request) {
		super();
		this.products = products;
		this.request = request;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public ListProductsRequest getRequest() {
		return request;
	}

	public void setRequest(ListProductsRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "ListProductsResponse [products=" + products + ", request=" + request + "]";
	}

}
