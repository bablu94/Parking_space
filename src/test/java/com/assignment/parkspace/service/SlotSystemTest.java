package com.assignment.parkspace.service;

import java.util.List;

import com.assignment.parkspace.domain.ParkingStatus;
import org.junit.Assert;
import org.junit.Test;

import com.assignment.parkspace.domain.Car;

public class SlotSystemTest {

	@Test
	public void integrationTest() {
		SlotSystem slotSystem = SlotSystem.createInstance(6);

		int slot1 = slotSystem.issueParkingTicket(new Car("KA-01-HH-1234", "White"));
		Assert.assertEquals(1, slot1);

		int slot2 = slotSystem.issueParkingTicket(new Car("KA-01-HH-9999", "White"));
		Assert.assertEquals(2, slot2);

		int slot3 = slotSystem.issueParkingTicket(new Car("KA-01-BB-0001", "Black"));
		Assert.assertEquals(3, slot3);

		int slot4 = slotSystem.issueParkingTicket(new Car("KA-01-HH-7777", "Red"));
		Assert.assertEquals(4, slot4);

		int slot5 = slotSystem.issueParkingTicket(new Car("KA-01-HH-2701", "Blue"));
		Assert.assertEquals(5, slot5);

		int slot6 = slotSystem.issueParkingTicket(new Car("KA-01-HH-3141", "Black"));
		Assert.assertEquals(6, slot6);

		slotSystem.exitVehicle(4);

		int slot7 = slotSystem.issueParkingTicket(new Car("KA-01-P-333", "White"));
		Assert.assertEquals(4, slot7);

		try {
			slotSystem.issueParkingTicket(new Car("DL-12-AA-9999", "White"));
			Assert.assertTrue("parking lot is full", false);
		} catch (Exception e) {
			Assert.assertEquals("", "Sorry, parking lot is full", e.getMessage());
		}

		List<String> registrationNumbers = slotSystem.getRegistrationNumbersFromColor("White");
		Assert.assertEquals(3, registrationNumbers.size());

		List<Integer> slotNumbers = slotSystem.getSlotNumbersFromColor("White");
		Assert.assertEquals(3, slotNumbers.size());

		int slotNumber = slotSystem.getSlotNumberFromRegistrationNumber("KA-01-HH-3141");
		Assert.assertEquals(6, slotNumber);

		try {
			int slotNumber2 = slotSystem.getSlotNumberFromRegistrationNumber("MH-04-AY-1111");
			Assert.assertTrue("should throw not found exception", true);
		} catch (Exception e) {
			Assert.assertEquals("", "Not found", e.getMessage());
		}
	}

	@Test
	public void issueParkingTicketWithValidVehicle() {
		SlotSystem slotSystem = new SlotSystem(new FakeParkingSpace(3));

		int slot1 = slotSystem.issueParkingTicket(new Car("", ""));
		Assert.assertEquals(3, slot1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void issueParkingTicketWithNullVehicle() {
		SlotSystem slotSystem = new SlotSystem(new FakeParkingSpace(3));
		int slot1 = slotSystem.issueParkingTicket(null);
	}

	@Test
	public void exitVehicleWithValidRegistrationNumber() {
		FakeParkingSpace fakeParkingLot = new FakeParkingSpace(3);
		SlotSystem slotSystem = new SlotSystem(fakeParkingLot);
		slotSystem.issueParkingTicket(new Car("KA-01-HH-3141", "White"));

		slotSystem.exitVehicle(3);
		Assert.assertEquals(3, fakeParkingLot.emptySlotNumber);
	}

	@Test
	public void exitVehicleWithInvalidSlotNumber() {
		FakeParkingSpace fakeParkingLot = new FakeParkingSpace(3);
		SlotSystem slotSystem = new SlotSystem(fakeParkingLot);

		try {
			slotSystem.exitVehicle(3);
			Assert.assertTrue("should throw ticket not found exception", false);
		} catch (Exception e) {
			Assert.assertEquals("", "No vehicle found at given slot. Incorrect input", e.getMessage());
		}
	}

	@Test
	public void getRegistrationNumbersFromColor() {
		SlotSystem slotSystem = new SlotSystem(new ParkingSpace(5));

		slotSystem.issueParkingTicket(new Car("KA-01-HH-1234", "White"));
		slotSystem.issueParkingTicket(new Car("KA-01-HH-9999", "White"));
		slotSystem.issueParkingTicket(new Car("KA-01-BB-0001", "Black"));
		slotSystem.issueParkingTicket(new Car("KA-01-HH-7777", "Blue"));
		slotSystem.issueParkingTicket(new Car("KA-01-HH-2701", "Blue"));

		// TODO: Assert Values
		List<String> registrationNumbers = slotSystem.getRegistrationNumbersFromColor("White");
		Assert.assertEquals(2, registrationNumbers.size());

		List<String> registrationNumbers2 = slotSystem.getRegistrationNumbersFromColor("Black");
		Assert.assertEquals(1, registrationNumbers2.size());

		List<String> registrationNumbers3 = slotSystem.getRegistrationNumbersFromColor("Blue");
		Assert.assertEquals(2, registrationNumbers3.size());

		List<String> registrationNumbers4 = slotSystem.getRegistrationNumbersFromColor("Green");
		Assert.assertEquals(0, registrationNumbers4.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRegistrationNumbersFromNullColor() {
		SlotSystem slotSystem = new SlotSystem(new ParkingSpace(5));
		slotSystem.getRegistrationNumbersFromColor(null);
	}

	@Test
	public void getSlotNumberFromRegistrationNumberWithValidRegistrationNumber() {
		SlotSystem slotSystem = new SlotSystem(new ParkingSpace(5));

		slotSystem.issueParkingTicket(new Car("KA-01-HH-1234", "White"));
		slotSystem.issueParkingTicket(new Car("KA-01-HH-9999", "White"));

		int slot1 = slotSystem.getSlotNumberFromRegistrationNumber("KA-01-HH-1234");
		Assert.assertEquals(1, slot1);

		int slot2 = slotSystem.getSlotNumberFromRegistrationNumber("KA-01-HH-9999");
		Assert.assertEquals(2, slot2);
	}

	@Test
	public void getSlotNumberFromRegistrationNumberWithInvalidRegistrationNumber() {
		SlotSystem slotSystem = new SlotSystem(new ParkingSpace(5));

		try {
			slotSystem.getSlotNumberFromRegistrationNumber("KA-01-HH-9999");
			Assert.assertTrue("should throw not found exception", false);
		} catch (Exception e) {
			Assert.assertEquals("", "Not found", e.getMessage());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void getSlotNumberFromRegistrationNumberWithNullRegistrationNumber() {
		SlotSystem slotSystem = new SlotSystem(new ParkingSpace(5));
		slotSystem.getSlotNumberFromRegistrationNumber(null);
	}

	@Test
	public void getSlotNumbersFromColor() {
		SlotSystem slotSystem = new SlotSystem(new ParkingSpace(5));

		slotSystem.issueParkingTicket(new Car("KA-01-HH-1234", "White"));
		slotSystem.issueParkingTicket(new Car("KA-01-HH-9999", "White"));
		slotSystem.issueParkingTicket(new Car("KA-01-BB-0001", "Black"));
		slotSystem.issueParkingTicket(new Car("KA-01-HH-7777", "Blue"));
		slotSystem.issueParkingTicket(new Car("KA-01-HH-2701", "Blue"));

		// TODO: Assert Values
		List<Integer> slotNumbers = slotSystem.getSlotNumbersFromColor("White");
		Assert.assertEquals(2, slotNumbers.size());

		List<Integer> slotNumbers2 = slotSystem.getSlotNumbersFromColor("Black");
		Assert.assertEquals(1, slotNumbers2.size());

		List<Integer> slotNumbers3 = slotSystem.getSlotNumbersFromColor("Blue");
		Assert.assertEquals(2, slotNumbers3.size());

		List<Integer> slotNumbers4 = slotSystem.getSlotNumbersFromColor("Green");
		Assert.assertEquals(0, slotNumbers4.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getSlotNumbersFromNullColor() {
		SlotSystem slotSystem = new SlotSystem(new ParkingSpace(5));
		slotSystem.getSlotNumbersFromColor(null);
	}

	@Test
	public void getStatus() {
		SlotSystem slotSystem = new SlotSystem(new ParkingSpace(1));
		slotSystem.issueParkingTicket(new Car("KA-01-HH-2701", "Blue"));

		List<ParkingStatus> parkingStatusList = slotSystem.getStatus();
		Assert.assertEquals(1, parkingStatusList.size());
		Assert.assertEquals("KA-01-HH-2701", parkingStatusList.get(0).getRegistrationNumber());
		Assert.assertEquals("Blue", parkingStatusList.get(0).getColor());
		Assert.assertEquals(1, parkingStatusList.get(0).getSlotNumber());

	}

	/**
	 * FakeParkingSpace to emulate/mock ParkingSpace class and override some functions
	 * for testing
	 *
	 */
	private class FakeParkingSpace extends ParkingSpace {

		private int nextAvailableSlotNumber;
		private int emptySlotNumber;

		FakeParkingSpace(int slotNumber) {
			super(1);
			this.nextAvailableSlotNumber = slotNumber;
			this.emptySlotNumber = slotNumber;
		}

		@Override
		int fillAvailableSlot() {
			return nextAvailableSlotNumber;
		}

		@Override
		void emptySlot(int slotNumber) {
			this.emptySlotNumber = slotNumber;
		}
	}
}
