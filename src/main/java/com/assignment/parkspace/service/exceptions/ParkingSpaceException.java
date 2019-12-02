package com.assignment.parkspace.service.exceptions;


public class ParkingSpaceException extends RuntimeException {

	private static final long serialVersionUID = 9026290603804836446L;
	private String message;

	public ParkingSpaceException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
