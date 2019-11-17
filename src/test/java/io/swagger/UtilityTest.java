package io.swagger;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

public class UtilityTest {

//	@Test
//	public void testConvertStringToDateCatchParseException() {
//		assertEquals(Utility.convertStringToCalendar("anmanm"), null);
//	}
//
//	@Test
//	public void testConvertStringToDateThrowsParseException() {
//		assertEquals(Utility.convertStringToCalendar("2019-02-01 00:00:00"), new Date(119, 01, 01, 00, 00, 00));
//	}

	@Test
	public void testConvertObjectToInteger() {
		assertEquals(Utility.convertObjectToInteger("2"), (Integer) 2);
	}

	@Test
	public void testConvertObjectToIntegerNullException() {
		assertEquals(Utility.convertObjectToInteger(null), null);
	}

	@Test
	public void testConvertObjectToLong() {
		assertEquals(Utility.convertObjectToLong((Object) "2"), (Long) (long) 2);
	}

	@Test
	public void testConvertObjectToLongException() {
		assertEquals(Utility.convertObjectToLong(null), null);
	}

	@Test
	public void testConvertObjectToBigDecimalException() {
		assertEquals(Utility.convertObjectToBigDecimal(null), null);
	}
	
	@Test
	public void testConvertObjectToBigDecimal() {
		assertEquals(Utility.convertObjectToBigDecimal("20"), BigDecimal.valueOf((Double)(double)20));
	}
}
