package com.assignment.parkspace.domain;

import org.junit.Assert;
import org.junit.Test;

public class CarTest {

	@Test
	public void validTest() {
		Car c = new Car("KA-01-HH-3141", "White");
		Assert.assertEquals("White", c.getColor());
		Assert.assertEquals("KA-01-HH-3141", c.getRegistrationNumber());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testWithNullRegistrationNumber() {
		new Car(null, "WHITE");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWithNullColor() {
		new Car("KA-01-HH-3141", null);
	}
}
