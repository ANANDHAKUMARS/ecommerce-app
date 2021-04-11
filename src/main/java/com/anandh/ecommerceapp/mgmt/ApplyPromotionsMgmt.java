package com.anandh.ecommerceapp.mgmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anandh.ecommerceapp.bean.CartProduct;
import com.anandh.ecommerceapp.bean.Product;
import com.anandh.ecommerceapp.bean.Promotion;
import com.anandh.ecommerceapp.repo.ProductInventoryRepository;
import com.anandh.ecommerceapp.repo.PromotionsRepository;
import com.anandh.ecommerceapp.service.bean.ListCartProductsResponse;
import com.anandh.ecommerceapp.service.bean.ListProductsRequest;

/**
 * 
 * @author anandhakumar.s
 *
 */
@Service
public class ApplyPromotionsMgmt {
	@Autowired
	private PromotionsRepository promotionsRepository;
	@Autowired
	private ProductInventoryRepository productInventoryRepository;

	public ListCartProductsResponse applyPromotions(List<CartProduct> cartProducts) throws Exception {
		Map<String, CartProduct> cartProductIdAndProductMapping = cartProducts.stream()
				.collect(Collectors.toMap(CartProduct::getProductId, product -> product));

		List<Product> products = productInventoryRepository
				.listProducts(new ListProductsRequest(cartProductIdAndProductMapping.keySet()));
		Map<String, Product> productIdAndProductMapping = products.stream()
				.collect(Collectors.toMap(Product::getId, product -> product));

		List<Promotion> promotions = promotionsRepository.listPromotions(cartProductIdAndProductMapping.keySet());
		Map<String, Promotion> productIdPromotionsMapping = promotions.stream()
				.collect(Collectors.toMap(Promotion::getProductId, promotion -> promotion));

		List<CartProduct> promotionAppliedCart = new ArrayList<>(cartProducts.size());
		for (Entry<String, Promotion> promotionEntry : productIdPromotionsMapping.entrySet()) {
			CartProduct discountedCartProduct = applyPromotion(promotionEntry.getValue(),
					cartProductIdAndProductMapping.get(promotionEntry.getKey()),
					productIdAndProductMapping.get(promotionEntry.getKey()));
			promotionAppliedCart.add(discountedCartProduct);
		}

		double totalAmount = promotionAppliedCart.stream()
				.collect(Collectors.summingDouble(CartProduct::getDiscountedAmount));
		return new ListCartProductsResponse(cartProducts, totalAmount);
	}

	public CartProduct applyPromotion(Promotion promotion, CartProduct cartProduct, Product product) throws Exception {
		final ScriptEngineManager factory = new ScriptEngineManager();
		final ScriptEngine engine = factory.getEngineByName("nashorn");
		final ScriptContext context = new SimpleScriptContext();

		context.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
		context.setAttribute("cartProduct", cartProduct, ScriptContext.ENGINE_SCOPE);
		context.setAttribute("product", product, ScriptContext.ENGINE_SCOPE);

		try {
			engine.eval(promotion.getRule(), context);
			cartProduct.setDiscountedAmount((double) context.getAttribute("discountedAmount"));
			cartProduct.setDiscount((double) context.getAttribute("discount"));
			return cartProduct;
		} catch (ScriptException e) {
			throw new Exception(e);
		}
	}
	
}
