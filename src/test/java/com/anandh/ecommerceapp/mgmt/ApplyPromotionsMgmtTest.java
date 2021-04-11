package com.anandh.ecommerceapp.mgmt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.anandh.ecommerceapp.bean.CartProduct;
import com.anandh.ecommerceapp.bean.Product;
import com.anandh.ecommerceapp.bean.Promotion;

/**
 * 
 * @author anandhakumar.s
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplyPromotionsMgmtTest {
	private static final String RULE_ONE = "totalAmount = product.ratePerUnit * cartProduct.totalGoods;\n"
			+ "totalThrees = Math.floor(cartProduct.totalGoods / 3);\n"
			+ "discount = totalThrees >= 1 ? 15*totalThrees : 0;\n" + "discountedAmount = totalAmount - discount;";
	@InjectMocks
	private ApplyPromotionsMgmt mgmt;

	@Test
	public void shouldApplyPromotionForProductATest1() throws Exception {
		int totalGoods = 3;
		int ratePerUnit = 30;
		Promotion promotion = new Promotion();
		promotion.setRule(RULE_ONE);
		CartProduct cartProduct = new CartProduct();
		cartProduct.setTotalGoods(totalGoods);
		Product product = new Product();
		product.setRatePerUnit(ratePerUnit);
		mgmt.applyPromotion(promotion, cartProduct, product);

		assertEquals(75.0, cartProduct.getDiscountedAmount());
		assertEquals(15.0, cartProduct.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForProductATest2() throws Exception {
		int totalGoods = 2;
		int ratePerUnit = 30;
		Promotion promotion = new Promotion();
		promotion.setRule(RULE_ONE);
		CartProduct cartProduct = new CartProduct();
		cartProduct.setTotalGoods(totalGoods);
		Product product = new Product();
		product.setRatePerUnit(ratePerUnit);
		mgmt.applyPromotion(promotion, cartProduct, product);

		assertEquals(60.0, cartProduct.getDiscountedAmount());
		assertEquals(0.0, cartProduct.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForProductATest3() throws Exception {
		int totalGoods = 4;
		int ratePerUnit = 30;
		Promotion promotion = new Promotion();
		promotion.setRule(RULE_ONE);
		CartProduct cartProduct = new CartProduct();
		cartProduct.setTotalGoods(totalGoods);
		Product product = new Product();
		product.setRatePerUnit(ratePerUnit);
		mgmt.applyPromotion(promotion, cartProduct, product);

		assertEquals(105.0, cartProduct.getDiscountedAmount());
		assertEquals(15.0, cartProduct.getDiscount());
	}

	@Test
	public void shouldApplyPromotionForProductATest4() throws Exception {
		int totalGoods = 6;
		int ratePerUnit = 30;
		Promotion promotion = new Promotion();
		promotion.setRule(RULE_ONE);
		CartProduct cartProduct = new CartProduct();
		cartProduct.setTotalGoods(totalGoods);
		Product product = new Product();
		product.setRatePerUnit(ratePerUnit);
		mgmt.applyPromotion(promotion, cartProduct, product);

		assertEquals(150.0, cartProduct.getDiscountedAmount());
		assertEquals(30.0, cartProduct.getDiscount());
	}
}
