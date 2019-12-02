package com.assignment.parkspace.domain;

public class ParkingStatus {
	private int slotNumber;
	private String registrationNumber;
	private String color;

	public ParkingStatus(int slotNumber, String registrationNumber, String color) {
		this.slotNumber = slotNumber;
		this.registrationNumber = registrationNumber;
		this.color = color;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return slotNumber + "           " + registrationNumber + "      " + color;
	}

}
