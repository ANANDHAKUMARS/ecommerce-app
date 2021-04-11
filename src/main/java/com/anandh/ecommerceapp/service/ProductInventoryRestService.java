package com.anandh.ecommerceapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anandh.ecommerceapp.bean.Product;
import com.anandh.ecommerceapp.mgmt.ProductInventoryMgmt;
import com.anandh.ecommerceapp.service.bean.ListProductsRequest;
import com.anandh.ecommerceapp.service.bean.ListProductsResponse;
import com.anandh.ecommerceapp.service.bean.SaveProductRequest;

/**
 * 
 * @author anandhakumar.s
 *
 */
@RestController
@RequestMapping(path = "/product")
public class ProductInventoryRestService {
	@Autowired
	private ProductInventoryMgmt productInventoryMgmt;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ListProductsResponse listProducts(ListProductsRequest request) {
		return productInventoryMgmt.listProducts(request);
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public boolean saveProduct(@RequestBody SaveProductRequest product) {
		productInventoryMgmt.save(product);
		return true;
	}

	@DeleteMapping("/{productId}")
	public String deleteProduct(@PathVariable String productId) {
		productInventoryMgmt.deleteById(productId);
		return productId;
	}

	@GetMapping("/{productId}")
	public Product getProductById(@PathVariable String productId) {
		return productInventoryMgmt.getProductById(productId);
	}

}
