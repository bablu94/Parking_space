package com.assignment.parkspace.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.assignment.parkspace.domain.ParkingStatus;
import com.assignment.parkspace.service.exceptions.ParkingSpaceException;
import com.assignment.parkspace.domain.Vehicle;

class SlotSystem {
	private static SlotSystem slotSystem;
	private ParkingSpace parkingSpace;
	private Map<Integer, Ticket> tickets;

	/**
	 * VisibleForTesting(otherwise = PRIVATE)
	 */
	SlotSystem(ParkingSpace parkingSpace) {
		this.parkingSpace = parkingSpace;
		tickets = new HashMap<Integer, Ticket>();
	}

	static SlotSystem createInstance(int numberOfSlots) {
		if(numberOfSlots < 1) {
			throw new ParkingSpaceException("Number of slots cannot be less than 1");
		}
		if (slotSystem == null) {
			ParkingSpace parkingSpace = ParkingSpace.getInstance(numberOfSlots);
			slotSystem = new SlotSystem(parkingSpace);
		}
		return slotSystem;
	}

	/**
	 * 
	 * @return SlotSystem instance
	 */
	static SlotSystem getInstance() {
		if(slotSystem == null) {
			throw new IllegalStateException("Parking Lot is not initialized");
		}
		return slotSystem;
	}

	/**
	 * Parks a vehicle
	 * 
	 * @return slotNumber => slot number at which the vehicle needs to be parked
	 */
	int issueParkingTicket(Vehicle vehicle) {
		if (vehicle == null) {
			throw new IllegalArgumentException("Vehicle cannot be null");
		}
		int assignedSlotNumber = parkingSpace.fillAvailableSlot();
		Ticket ticket = new Ticket(assignedSlotNumber, vehicle);
		tickets.put(assignedSlotNumber, ticket);
		return assignedSlotNumber;
	}

	/**
	 * Exits a vehicle from the parking lot
	 * 
	 * @param registrationNumber
	 * @return slotNumber => the slot from the car has exited.
	 */
	void exitVehicle(int slotNumber) {
		if (tickets.containsKey(slotNumber)) {
			parkingSpace.emptySlot(slotNumber);
			tickets.remove(slotNumber);
			return;
		} else {
			throw new ParkingSpaceException("No vehicle found at given slot. Incorrect input");
		}
	}

	/**
	 * returns all the registration numbers of the vehicles with the given color
	 * 
	 * @param color => Color of the Vehicle
	 * @return List of all the registration numbers of the vehicles with the given
	 *         color
	 */
	List<String> getRegistrationNumbersFromColor(String color) {
		if (color == null) {
			throw new IllegalArgumentException("color cannot be null");
		}
		List<String> registrationNumbers = new ArrayList<String>();
		for (Ticket ticket : tickets.values()) {
			if (color.equals(ticket.vehicle.getColor())) {
				registrationNumbers.add(ticket.vehicle.getRegistrationNumber());
			}
		}
		return registrationNumbers;
	}

	/**
	 * returns the slot number at which the Vehicle with given registrationNumber is
	 * parked
	 * 
	 * @param registrationNumber => Registration Number of the Vehicle
	 * @return slot number at which the Vehicle with given registrationNumber is
	 *         parked
	 */
	int getSlotNumberFromRegistrationNumber(String registrationNumber) {
		if (registrationNumber == null) {
			throw new IllegalArgumentException("registrationNumber cannot be null");
		}
		for (Ticket ticket : tickets.values()) {
			if (registrationNumber.equals(ticket.vehicle.getRegistrationNumber())) {
				return ticket.slotNumber;
			}
		}

		throw new ParkingSpaceException("Not found");
	}

	/**
	 * returns all the slot numbers of the vehicles with the given color
	 * 
	 * @param color => Color of the Vehicle
	 * @return List of all the slot numbers of the vehicles with the given color
	 */
	List<Integer> getSlotNumbersFromColor(String color) {
		if (color == null) {
			throw new IllegalArgumentException("color cannot be null");
		}
		List<Integer> registrationNumbers = new ArrayList<Integer>();
		for (Ticket ticket : tickets.values()) {
			if (color.equals(ticket.vehicle.getColor())) {
				registrationNumbers.add(ticket.slotNumber);
			}
		}
		return registrationNumbers;
	}

	/**
	 * returns the status of the ticketing system, a list of all the tickets
	 * converted to status objects
	 * 
	 * @return List of ParkingStatus => List of (slotNumber, registrationNumber,
	 *         color)
	 */
	List<ParkingStatus> getStatus() {
		List<ParkingStatus> parkingStatusList = new ArrayList<ParkingStatus>();
		for (Ticket ticket : tickets.values()) {
			parkingStatusList.add(new ParkingStatus(ticket.slotNumber, ticket.vehicle.getRegistrationNumber(),
					ticket.vehicle.getColor()));
		}
		return parkingStatusList;
	}
	
	/**
	 * Ticketing System issues a ticket => an object known only to Ticketing System
	 *
	 */
	private class Ticket {
		int slotNumber;
		Vehicle vehicle;

		Ticket(int slotNumber, Vehicle vehicle) {
			this.slotNumber = slotNumber;
			this.vehicle = vehicle;
		}
	}
}
