package com.anandh.ecommerceapp.service.bean;

import java.util.Set;

/**
 * 
 * @author anandhakumar.s
 *
 */
public class ListProductsRequest {
	private Set<String> productIds;
	private String search;
	private int skip;
	private int limit;

	public ListProductsRequest() {
		this.setSkip(0);
		this.setLimit(25);
	}

	public ListProductsRequest(Set<String> productIds) {
		this.setProductIds(productIds);
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Set<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(Set<String> productIds) {
		this.productIds = productIds;
	}

	@Override
	public String toString() {
		return "ListProductsRequest [productIds=" + productIds + ", search=" + search + ", skip=" + skip + ", limit="
				+ limit + "]";
	}

}
