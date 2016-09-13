package scb.recontool.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class AmountUtilTest {

	@Test
	public void testGetScale() {
		assertEquals(2, AmountUtil.getScale("100.01"));
		assertEquals(1, AmountUtil.getScale("100.0"));
		assertEquals(2, AmountUtil.getScale("0.02"));
	}

	@Test
	public void testResetScale() {
		assertEquals(new BigDecimal("100.01"), AmountUtil.resetScale("100.01"));
		assertEquals(new BigDecimal("100.00"), AmountUtil.resetScale("100.0"));
		assertEquals(new BigDecimal("0.02"), AmountUtil.resetScale("0.02"));
	}

}
