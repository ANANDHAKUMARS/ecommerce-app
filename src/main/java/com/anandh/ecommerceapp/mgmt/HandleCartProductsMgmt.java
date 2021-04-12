package com.anandh.ecommerceapp.mgmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anandh.ecommerceapp.bean.CartProduct;
import com.anandh.ecommerceapp.bean.Product;
import com.anandh.ecommerceapp.bean.Promotion;
import com.anandh.ecommerceapp.bean.PromotionType;
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
public class HandleCartProductsMgmt {
	private static final Logger LOGGER = LoggerFactory.getLogger(HandleCartProductsMgmt.class);
	private static final String DISCOUNTED_AMOUNT = "discountedAmount";
	private static final String DISCOUNT = "discount";
	private static final String TOTAL_AMOUNT = "totalAmount";
	@Autowired
	private PromotionsRepository promotionsRepository;
	@Autowired
	private ProductInventoryRepository productInventoryRepository;
	private static ScriptEngine engine = null;
	private static ScriptContext context = null;

	static {
		init();
	}

	public static void init() {
		LOGGER.info("script engine init started");
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("nashorn");

		context = new SimpleScriptContext();
		context.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
		LOGGER.info("script engine init completed");
	}

	public ListCartProductsResponse calculateCartProductAmounts(List<CartProduct> cartProducts) throws Exception {
		LOGGER.info("calculateCartProductAmounts method started");
		Map<String, CartProduct> cartProductIdAndProductMapping = cartProducts.stream()
				.collect(Collectors.toMap(CartProduct::getProductId, product -> product));

		Map<String, Product> productIdAndProductMapping = getCartAddedProducts(cartProductIdAndProductMapping);

		List<CartProduct> cartProductsWithActualAmounts = getCartProductsWithActualAmounts(cartProducts, productIdAndProductMapping);

		Map<String, Promotion> productIdPromotionsMapping = new HashMap<>();
		List<Promotion> totalAmountPromotions = new ArrayList<>();
		getApplicablePromotions(cartProductIdAndProductMapping, productIdPromotionsMapping, totalAmountPromotions);

		List<CartProduct> promotionAppliedCart = applyProductPromotions(cartProducts, cartProductIdAndProductMapping,
				productIdAndProductMapping, cartProductsWithActualAmounts, productIdPromotionsMapping);

		ListCartProductsResponse response = applyTotalAmountPromotions(cartProducts, totalAmountPromotions,
				promotionAppliedCart);
		LOGGER.info("calculateCartProductAmounts method completed");
		return response;
	}

	private Map<String, Product> getCartAddedProducts(Map<String, CartProduct> cartProductIdAndProductMapping) {
		List<Product> products = productInventoryRepository
				.listProducts(new ListProductsRequest(cartProductIdAndProductMapping.keySet()));
		return products.stream().collect(Collectors.toMap(Product::getId, product -> product));
	}

	private ListCartProductsResponse applyTotalAmountPromotions(List<CartProduct> cartProducts,
			List<Promotion> totalAmountPromotions, List<CartProduct> promotionAppliedCart) throws Exception {
		double totalAmount = promotionAppliedCart.stream()
				.collect(Collectors.summingDouble(CartProduct::getDiscountedAmount));
		ListCartProductsResponse response = new ListCartProductsResponse(cartProducts, totalAmount);
		for (Promotion promotion : totalAmountPromotions) {
			applyTotalAmountPromotion(promotion, response);
		}
		return response;
	}

	private List<CartProduct> applyProductPromotions(List<CartProduct> cartProducts,
			Map<String, CartProduct> cartProductIdAndProductMapping, Map<String, Product> productIdAndProductMapping,
			List<CartProduct> cartProductsWithActualAmounts, Map<String, Promotion> productIdPromotionsMapping)
			throws Exception {
		List<CartProduct> promotionAppliedCart = new ArrayList<>(cartProducts.size());
		for (Entry<String, Promotion> promotionEntry : productIdPromotionsMapping.entrySet()) {
			CartProduct discountedCartProduct = applyProductPromotion(promotionEntry.getValue(),
					cartProductIdAndProductMapping.get(promotionEntry.getKey()),
					productIdAndProductMapping.get(promotionEntry.getKey()));
			promotionAppliedCart.add(discountedCartProduct);
			cartProductsWithActualAmounts.remove(discountedCartProduct);
		}
		promotionAppliedCart.addAll(cartProductsWithActualAmounts);
		return promotionAppliedCart;
	}

	private void getApplicablePromotions(Map<String, CartProduct> cartProductIdAndProductMapping,
			Map<String, Promotion> productIdPromotionsMapping, List<Promotion> totalAmountPromotions) {
		List<Promotion> promotions = promotionsRepository.listPromotions(cartProductIdAndProductMapping.keySet());
		for (Promotion promotion : promotions) {
			if (PromotionType.PRODUCT.equals(promotion.getType())) {
				productIdPromotionsMapping.put(promotion.getProductId(), promotion);
			} else if (PromotionType.TOTAL_AMOUNT.equals(promotion.getType())) {
				totalAmountPromotions.add(promotion);
			}
		}
	}

	private List<CartProduct> getCartProductsWithActualAmounts(List<CartProduct> cartProducts,
			Map<String, Product> productIdAndProductMapping) {
		return cartProducts.stream().map(cartProduct -> {
			Product product = productIdAndProductMapping.get(cartProduct.getProductId());
			cartProduct.setTotalAmount(product.getRatePerUnit() * cartProduct.getTotalGoods());
			cartProduct.setDiscountedAmount(cartProduct.getTotalAmount());
			return cartProduct;
		}).collect(Collectors.toList());
	}

	public ListCartProductsResponse applyTotalAmountPromotion(Promotion promotion, ListCartProductsResponse response)
			throws Exception {
		context.setAttribute(TOTAL_AMOUNT, response.getTotalAmount(), ScriptContext.ENGINE_SCOPE);

		try {
			engine.eval(String.join("", promotion.getRules()), context);
			response.setTotalDiscountedAmount((double) context.getAttribute(DISCOUNTED_AMOUNT));
			response.setTotalAmount((double) context.getAttribute(TOTAL_AMOUNT));
			response.addDiscount((double) context.getAttribute(DISCOUNT));
			return response;
		} catch (ScriptException e) {
			throw new Exception(e);
		}
	}

	public CartProduct applyProductPromotion(Promotion promotion, CartProduct cartProduct, Product product)
			throws Exception {
		context.setAttribute("cartProduct", cartProduct, ScriptContext.ENGINE_SCOPE);
		context.setAttribute("product", product, ScriptContext.ENGINE_SCOPE);

		try {
			engine.eval(String.join("", promotion.getRules()), context);
			cartProduct.setDiscountedAmount((double) context.getAttribute(DISCOUNTED_AMOUNT));
			cartProduct.setTotalAmount((double) context.getAttribute(TOTAL_AMOUNT));
			cartProduct.setDiscount((double) context.getAttribute(DISCOUNT));
			return cartProduct;
		} catch (ScriptException e) {
			throw new Exception(e);
		}
	}

}
