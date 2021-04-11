package com.anandh.ecommerceapp.mgmt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anandh.ecommerceapp.bean.Product;
import com.anandh.ecommerceapp.repo.ProductInventoryRepository;
import com.anandh.ecommerceapp.service.bean.ListProductsRequest;
import com.anandh.ecommerceapp.service.bean.ListProductsResponse;

/**
 * 
 * @author anandhakumar.s
 *
 */
@Service
public class ProductInventoryMgmt {
	@Autowired
	private ProductInventoryRepository productInventoryRepository;

	public ListProductsResponse listProducts(ListProductsRequest request) {
		List<Product> products = productInventoryRepository.listProducts(request);
		return new ListProductsResponse(products, request);
	}

	public void save(Product product) {
		productInventoryRepository.save(product);
	}

	public void deleteById(String id) {
		productInventoryRepository.deleteById(id);
	}

	public Product getProductById(String productId) {
		return productInventoryRepository.findProductById(productId);
	}

}
