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
import com.anandh.ecommerceapp.mgmt.CartProductInventoryMgmt;
import com.anandh.ecommerceapp.service.bean.ListCartProductsRequest;
import com.anandh.ecommerceapp.service.bean.ListCartProductsResponse;
import com.anandh.ecommerceapp.service.bean.SaveCartProductRequest;

/**
 * 
 * @author anandhakumar.s
 *
 */
@RestController
@RequestMapping(path = "/cart")
public class CartProductInventoryRestService {

	@Autowired
	private CartProductInventoryMgmt productInventoryMgmt;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ListCartProductsResponse listProducts(ListCartProductsRequest request) throws Exception {
		return productInventoryMgmt.listProducts(request);
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public boolean saveProduct(@RequestBody SaveCartProductRequest product) {
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