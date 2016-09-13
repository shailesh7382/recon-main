package scb.recontool.util;

import static org.junit.Assert.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.Test;

public class DateUtilTest {

	@Test
	public void testConvertDate() {
		assertEquals(LocalDate.of(2016, 6, 25), DateUtil.convertDate("25-Jun-2016"));
		assertEquals(LocalDate.of(2016, 6, 5), DateUtil.convertDate("5-Jun-2016"));
		assertEquals(LocalDate.of(2016, 6, 5), DateUtil.convertDate("05-Jun-2016"));
	}
	
	@Test
	public void testConvertDateError() {
		LocalDate date = null;
		try {
			date = DateUtil.convertDate("25-Jun-16");
		} catch (Exception e) {
			assertTrue(e instanceof DateTimeParseException);
		}
		assertNull(date);
	}
	
	@Test
	public void testConvertDateError2() {
		LocalDate date = null;
		try {
			date = DateUtil.convertDate("31-Feb-2016");
			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(e instanceof DateTimeException);
		}
		assertNull(date);
	}

	@Test
	public void testIsWithinADay() {
		assertTrue(DateUtil.isWithinADay("1-Jun-2016", "2-Jun-2016"));
		assertTrue(DateUtil.isWithinADay("3-Jun-2016", "2-Jun-2016"));
		assertFalse(DateUtil.isWithinADay("1-Jun-2016", "3-Jun-2016"));
		assertFalse(DateUtil.isWithinADay("3-Jun-2016", "1-Jun-2016"));
		assertTrue(DateUtil.isWithinADay("3-Jun-2016", "4-Jun-2016"));
		assertTrue(DateUtil.isWithinADay("3-Jun-2016", "6-Jun-2016"));
		assertTrue(DateUtil.isWithinADay("4-Jun-2016", "6-Jun-2016"));
	}

}
