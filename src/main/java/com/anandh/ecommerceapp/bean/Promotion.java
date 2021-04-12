package com.anandh.ecommerceapp.bean;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("promotion")
public class Promotion {
	@Id
	private String id;
	private String name;
	private PromotionType type;
	private String productId;
	private List<String> rules;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public PromotionType getType() {
		return type;
	}

	public void setType(PromotionType type) {
		this.type = type;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	public List<String> getRules() {
		return rules;
	}
	public void setRules(List<String> rules) {
		this.rules = rules;
	}

	@Override
	public String toString() {
		return "Promotion [id=" + id + ", name=" + name + ", type=" + type + ", productId=" + productId + "]";
	}
}
