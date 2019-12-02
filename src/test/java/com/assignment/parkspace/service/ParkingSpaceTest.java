package com.assignment.parkspace.service;

import org.junit.Assert;
import org.junit.Test;

public class ParkingSpaceTest {

	@Test
	public void fillAvailableSlotWhenSlotIsAvailable() {
		ParkingSpace parkingSpace = new ParkingSpace(2);

		int slot1 = parkingSpace.fillAvailableSlot();
		Assert.assertEquals(1, slot1);

		int slot2 = parkingSpace.fillAvailableSlot();
		Assert.assertEquals(2, slot2);
	}

	@Test
	public void fillAvailableSlotWhenNoSlotIsAvailable() {
		ParkingSpace parkingSpace = new ParkingSpace(1);

		int slot = parkingSpace.fillAvailableSlot();
		Assert.assertEquals(1, slot);

		try {
			parkingSpace.fillAvailableSlot();
			Assert.assertTrue("should throw parking lot is full", false);
		} catch (Exception e) {
			String message = e.getMessage();
			Assert.assertEquals("Sorry, parking lot is full", message);
		}
	}

	@Test
	public void emptySlotWithValidSlotNumber() {
		ParkingSpace parkingSpace = new ParkingSpace(3);

		int slot = parkingSpace.fillAvailableSlot();
		Assert.assertEquals(1, slot);

		int slot2 = parkingSpace.fillAvailableSlot();
		Assert.assertEquals(2, slot2);

		int slot3 = parkingSpace.fillAvailableSlot();
		Assert.assertEquals(3, slot3);

		parkingSpace.emptySlot(2);
		int slot4 = parkingSpace.fillAvailableSlot();
		Assert.assertEquals(2, slot4);

		parkingSpace.emptySlot(1);
		int slot5 = parkingSpace.fillAvailableSlot();
		Assert.assertEquals(1, slot5);
	}

	@Test
	public void emptySlotWithInvalidSlotNumber() {
		ParkingSpace parkingSpace = new ParkingSpace(2);

		int slot = parkingSpace.fillAvailableSlot();
		Assert.assertEquals(1, slot);

		int slot2 = parkingSpace.fillAvailableSlot();
		Assert.assertEquals(2, slot2);

		try {
			parkingSpace.emptySlot(3);
			Assert.assertTrue("should throw slot number is invalid exception", false);
		} catch (Exception e) {
			String message = e.getMessage();
			Assert.assertEquals("The slot number is invalid", message);
		}
	}

	@Test
	public void emptySlotWhichIsAlreadyEmpty() {
		ParkingSpace parkingSpace = new ParkingSpace(2);

		int slot = parkingSpace.fillAvailableSlot();
		Assert.assertEquals(1, slot);

		try {
			parkingSpace.emptySlot(2);
			Assert.assertTrue("should throw slot already empty exception", false);
		} catch (Exception e) {
			String message = e.getMessage();
			Assert.assertEquals("The slot is already empty", message);
		}
	}

}
