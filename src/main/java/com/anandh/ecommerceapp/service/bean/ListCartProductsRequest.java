package com.anandh.ecommerceapp.service.bean;

/**
 * 
 * @author anandhakumar.s
 *
 */
public class ListCartProductsRequest extends ListProductsRequest {
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
