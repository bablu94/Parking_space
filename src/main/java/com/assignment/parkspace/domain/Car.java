package com.assignment.parkspace.domain;

public class Car implements Vehicle {

	private String registrationNumber;
	private String color;


	public Car(String registrationNumber, String color) {
		if(registrationNumber == null || color == null) {
			throw new IllegalArgumentException("Both registrationNumber & Color should not be null");
		}
		this.registrationNumber = registrationNumber;
		this.color = color;
	}

	public String getColor() {
		return this.color;
	}

	public String getRegistrationNumber() {
		return this.registrationNumber;
	}

}
