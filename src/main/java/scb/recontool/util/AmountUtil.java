package scb.recontool.util;

import java.math.BigDecimal;

public class AmountUtil {

	public static int getScale(String attribute) throws NumberFormatException{
		BigDecimal a = new BigDecimal(attribute);
		return a.scale();
	}
	
	
	public static BigDecimal resetScale(String attribute) throws NumberFormatException{
		BigDecimal a = new BigDecimal(attribute);
		BigDecimal roundOff = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return roundOff;
	}


}
