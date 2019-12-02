package com.assignment.parkspace.service;

import java.util.HashMap;
import java.util.Map;

import com.assignment.parkspace.service.exceptions.ParkingSpaceException;

class ParkingSpace {
	private static ParkingSpace parkingSpace;
	private Map<Integer, Slot> slots;

	/**
	 * VisibleForTesting(otherwise = PRIVATE)
	 */
	protected ParkingSpace(int numberOfSlots) {
		slots = new HashMap<Integer, Slot>();
		for (int i = 1; i <= numberOfSlots; i++) {
			slots.put(i, new Slot(i));
		}
	}

	/**
	 * Singleton Class => Returns a single instance of the class
	 * 
	 * @param numberOfSlots => number of slots in the parking lot
	 * @return ParkingSpace instance
	 */
	static ParkingSpace getInstance(int numberOfSlots) {
		if (parkingSpace == null) {
			parkingSpace = new ParkingSpace(numberOfSlots);
		}
		return parkingSpace;
	}

	/**
	 * Finds the next available slot and marks it unavailable
	 * 
	 * @return slot number which was marked unavailable
	 */
	int fillAvailableSlot() {
		int nextAvailableSlotNumber = -1;
		for (int i = 1; i <= slots.size(); i++) {
			Slot s = slots.get(i);
			if (s.status) {
				nextAvailableSlotNumber = s.slotNumber;
				s.status = false;
				break;
			}
		}
		if (nextAvailableSlotNumber != -1) {
			return nextAvailableSlotNumber;
		} else {
			throw new ParkingSpaceException("Sorry, parking lot is full");
		}
	}

	/**
	 * Empties the Slot => marks the slot available
	 * 
	 * @param slotNumber => the slot number to be made empty
	 */
	void emptySlot(int slotNumber) {
		if (slots.containsKey(slotNumber)) {
			if (slots.get(slotNumber).status) {
				throw new IllegalStateException("The slot is already empty");
			} else {
				slots.get(slotNumber).status = true;
			}
		} else {
			throw new IllegalStateException("The slot number is invalid");
		}
	}

	/**
	 * private Class => Slot is an entity known only to parking lot.
	 *
	 */
	private class Slot {
		// unique slot identifier
		private int slotNumber;
		// boolean status to maintain isAvailable => true=available, false=not available
		private boolean status;

		Slot(int slotNumber) {
			this.slotNumber = slotNumber;
			this.status = true;
		}
	}
}