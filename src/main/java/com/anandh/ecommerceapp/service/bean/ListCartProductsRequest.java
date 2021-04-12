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

	@Override
	public String toString() {
		return "ListCartProductsRequest [userId=" + userId + ", ListProductsRequest=" + super.toString() + "]";
	}
}
