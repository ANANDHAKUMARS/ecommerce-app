package com.anandh.ecommerceapp.mgmt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.anandh.ecommerceapp.bean.CartProduct;
import com.anandh.ecommerceapp.bean.Product;
import com.anandh.ecommerceapp.bean.Promotion;
import com.anandh.ecommerceapp.bean.PromotionType;
import com.anandh.ecommerceapp.repo.ProductInventoryRepository;
import com.anandh.ecommerceapp.repo.PromotionsRepository;
import com.anandh.ecommerceapp.service.bean.ApplyPromotionRequest;
import com.anandh.ecommerceapp.service.bean.ListCartProductsResponse;

/**
 * 
 * @author anandhakumar.s
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HandleCartProductsMgmtTest {
	private static final List<String> RULE_ONE = listOf("totalAmount = product.ratePerUnit * cartProduct.totalGoods;",
			"totalThrees = Math.floor(cartProduct.totalGoods / 3);",
			"discount = totalThrees >= 1 ? 15*totalThrees : 0;", "discountedAmount = totalAmount - discount;");
	private static final List<String> RULE_TWO = listOf("totalAmount = product.ratePerUnit * cartProduct.totalGoods;",
			"totalTwos = Math.floor(cartProduct.totalGoods / 2);", "discount = totalTwos >= 1 ? 5*totalTwos : 0;",
			"discountedAmount = totalAmount - discount;");
	private static final List<String> RULE_THREE = listOf("discount = totalAmount >= 150 ? 20.0 : 0;",
			"discountedAmount = totalAmount - discount;");
	@InjectMocks
	private HandleCartProductsMgmt mgmt;
	@Mock
	private PromotionsRepository promotionsRepository;
	@Mock
	private ProductInventoryRepository productInventoryRepository;

	@Before
	public void init() {
		mgmt.init();
	}

	private static <T> List<T> listOf(T... scripts) {
		List<T> scriptList = new ArrayList<>(scripts.length);
		for (T script : scripts) {
			scriptList.add(script);
		}
		return scriptList;
	}
	
	private ApplyPromotionRequest buildMockRequest(long totalGoods, double ratePerUnit, List<String> rules) {
		Promotion promotion = new Promotion();
		promotion.setRules(rules);
		CartProduct cartProduct = new CartProduct();
		cartProduct.setTotalGoods(totalGoods);
		Product product = new Product();
		product.setRatePerUnit(ratePerUnit);
		return new ApplyPromotionRequest(promotion, cartProduct, product);
	}

	private CartProduct mockCartProduct(String productId, long totalGoods) {
		CartProduct product = new CartProduct();
		product.setProductId(productId);
		product.setTotalGoods(totalGoods);
		return product;
	}

	private Product mockProduct(String productId, double ratePerUnit) {
		Product product = new Product();
		product.setId(productId);
		product.setName(productId);
		product.setRatePerUnit(ratePerUnit);
		return product;
	}

	private Promotion mockPromotion(String productId, PromotionType type, List<String> rules) {
		Promotion promotion = new Promotion();
		promotion.setProductId(productId);
		promotion.setType(type);
		promotion.setRules(rules);
		return promotion;
	}

	@Test
	public void shouldApplyPromotionForProductATest1() throws Exception {
		ApplyPromotionRequest request = buildMockRequest(3, 30, RULE_ONE);
		CartProduct cartProduct = mgmt.applyProductPromotion(request.getPromotion(), request.getCartProduct(),
				request.getProduct());

		assertEquals(75.0, cartProduct.getDiscountedAmount());
		assertEquals(15.0, cartProduct.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForProductATest2() throws Exception {
		ApplyPromotionRequest request = buildMockRequest(2, 30, RULE_ONE);
		CartProduct cartProduct = mgmt.applyProductPromotion(request.getPromotion(), request.getCartProduct(),
				request.getProduct());

		assertEquals(60.0, cartProduct.getDiscountedAmount());
		assertEquals(0.0, cartProduct.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForProductATest3() throws Exception {
		ApplyPromotionRequest request = buildMockRequest(4, 30, RULE_ONE);
		CartProduct cartProduct = mgmt.applyProductPromotion(request.getPromotion(), request.getCartProduct(),
				request.getProduct());

		assertEquals(105.0, cartProduct.getDiscountedAmount());
		assertEquals(15.0, cartProduct.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForProductATest4() throws Exception {
		ApplyPromotionRequest request = buildMockRequest(6, 30, RULE_ONE);
		CartProduct cartProduct = mgmt.applyProductPromotion(request.getPromotion(), request.getCartProduct(),
				request.getProduct());

		assertEquals(150.0, cartProduct.getDiscountedAmount());
		assertEquals(30.0, cartProduct.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForProductBTest1() throws Exception {
		ApplyPromotionRequest request = buildMockRequest(2, 20, RULE_TWO);
		CartProduct cartProduct = mgmt.applyProductPromotion(request.getPromotion(), request.getCartProduct(),
				request.getProduct());

		assertEquals(35.0, cartProduct.getDiscountedAmount());
		assertEquals(5.0, cartProduct.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForProductBTest2() throws Exception {
		ApplyPromotionRequest request = buildMockRequest(1, 20, RULE_TWO);
		CartProduct cartProduct = mgmt.applyProductPromotion(request.getPromotion(), request.getCartProduct(),
				request.getProduct());

		assertEquals(20.0, cartProduct.getDiscountedAmount());
		assertEquals(0, cartProduct.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForProductBTest3() throws Exception {
		ApplyPromotionRequest request = buildMockRequest(3, 20, RULE_TWO);
		CartProduct cartProduct = mgmt.applyProductPromotion(request.getPromotion(), request.getCartProduct(),
				request.getProduct());

		assertEquals(55.0, cartProduct.getDiscountedAmount());
		assertEquals(5.0, cartProduct.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForProductBTest4() throws Exception {
		ApplyPromotionRequest request = buildMockRequest(4, 20, RULE_TWO);
		CartProduct cartProduct = mgmt.applyProductPromotion(request.getPromotion(), request.getCartProduct(),
				request.getProduct());

		assertEquals(70.0, cartProduct.getDiscountedAmount());
		assertEquals(10.0, cartProduct.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForTotalAmountTest1() throws Exception {
		Promotion promotion = new Promotion();
		promotion.setRules(RULE_THREE);
		ListCartProductsResponse response = new ListCartProductsResponse(Collections.emptyList(), 150.0);
		response = mgmt.applyTotalAmountPromotion(promotion, response);

		assertEquals(150.0, response.getTotalAmount());
		assertEquals(130.0, response.getTotalDiscountedAmount());
		assertEquals(20.0, response.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForTotalAmountTest2() throws Exception {
		Promotion promotion = new Promotion();
		promotion.setRules(RULE_THREE);
		ListCartProductsResponse response = new ListCartProductsResponse(Collections.emptyList(), 149.0);
		response = mgmt.applyTotalAmountPromotion(promotion, response);

		assertEquals(149.0, response.getTotalAmount());
		assertEquals(149.0, response.getTotalDiscountedAmount());
		assertEquals(0, response.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForTotalAmountTest3() throws Exception {
		Promotion promotion = new Promotion();
		promotion.setRules(RULE_THREE);
		ListCartProductsResponse response = new ListCartProductsResponse(Collections.emptyList(), 170.0);
		response = mgmt.applyTotalAmountPromotion(promotion, response);

		assertEquals(170.0, response.getTotalAmount());
		assertEquals(150.0, response.getTotalDiscountedAmount());
		assertEquals(20.0, response.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForSelectedProductsAndReturnResultTest1() throws Exception {
		List<CartProduct> cartProducts = listOf(mockCartProduct("A", 1), mockCartProduct("B", 1), mockCartProduct("C", 1));
		List<Product> products = listOf(mockProduct("A", 30), mockProduct("B", 20), mockProduct("C", 50),
				mockProduct("D", 15));
		when(productInventoryRepository.listProducts(any())).thenReturn(products);
		List<Promotion> rules = listOf(mockPromotion("A", PromotionType.PRODUCT, RULE_ONE),
				mockPromotion("B", PromotionType.PRODUCT, RULE_TWO),
				mockPromotion(null, PromotionType.TOTAL_AMOUNT, RULE_THREE));
		when(promotionsRepository.listPromotions(any())).thenReturn(rules);

		ListCartProductsResponse response = mgmt.calculateCartProductAmounts(cartProducts);

		assertEquals(100, response.getTotalDiscountedAmount());
	}

	@Test
	public void shouldApplyPromotionForSelectedProductsAndReturnResultTest2() throws Exception {
		List<CartProduct> cartProducts = listOf(mockCartProduct("A", 3), mockCartProduct("B", 2));
		List<Product> products = listOf(mockProduct("A", 30), mockProduct("B", 20), mockProduct("C", 50),
				mockProduct("D", 15));
		when(productInventoryRepository.listProducts(any())).thenReturn(products);
		List<Promotion> rules = listOf(mockPromotion("A", PromotionType.PRODUCT, RULE_ONE),
				mockPromotion("B", PromotionType.PRODUCT, RULE_TWO),
				mockPromotion(null, PromotionType.TOTAL_AMOUNT, RULE_THREE));
		when(promotionsRepository.listPromotions(any())).thenReturn(rules);

		ListCartProductsResponse response = mgmt.calculateCartProductAmounts(cartProducts);

		assertEquals(110, response.getTotalDiscountedAmount());
	}

	@Test
	public void shouldApplyPromotionForSelectedProductsAndReturnResultTest3() throws Exception {
		List<CartProduct> cartProducts = listOf(mockCartProduct("C", 1), mockCartProduct("B", 2),
				mockCartProduct("A", 3), mockCartProduct("D", 1));
		List<Product> products = listOf(mockProduct("A", 30), mockProduct("B", 20), mockProduct("C", 50),
				mockProduct("D", 15));
		when(productInventoryRepository.listProducts(any())).thenReturn(products);
		List<Promotion> rules = listOf(mockPromotion("A", PromotionType.PRODUCT, RULE_ONE),
				mockPromotion("B", PromotionType.PRODUCT, RULE_TWO),
				mockPromotion(null, PromotionType.TOTAL_AMOUNT, RULE_THREE));
		when(promotionsRepository.listPromotions(any())).thenReturn(rules);

		ListCartProductsResponse response = mgmt.calculateCartProductAmounts(cartProducts);

		assertEquals(155, response.getTotalDiscountedAmount());
	}

	@Test
	public void shouldApplyPromotionForSelectedProductsAndReturnResultTest4() throws Exception {
		List<CartProduct> cartProducts = listOf(mockCartProduct("C", 1), mockCartProduct("A", 3),
				mockCartProduct("D", 1));
		List<Product> products = listOf(mockProduct("A", 30), mockProduct("C", 50), mockProduct("D", 15));
		when(productInventoryRepository.listProducts(any())).thenReturn(products);
		List<Promotion> rules = listOf(mockPromotion("A", PromotionType.PRODUCT, RULE_ONE),
				mockPromotion(null, PromotionType.TOTAL_AMOUNT, RULE_THREE));
		when(promotionsRepository.listPromotions(any())).thenReturn(rules);

		ListCartProductsResponse response = mgmt.calculateCartProductAmounts(cartProducts);

		assertEquals(140, response.getTotalDiscountedAmount());
	}
}
