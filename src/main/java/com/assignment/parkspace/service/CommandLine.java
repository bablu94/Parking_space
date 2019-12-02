package com.assignment.parkspace.service;

import java.util.List;

import com.assignment.parkspace.service.exceptions.ParkingSpaceException;
import com.assignment.parkspace.domain.Car;
import com.assignment.parkspace.domain.ParkingStatus;

public class CommandLine {
	private static CommandLine commandLine;

	private CommandLine() {

	}

	/**
	 * Singleton Class => Returns a single instance of the class
	 * 
	 * @return CommandLine instance
	 */
	public static CommandLine getInstance() {
		if (commandLine == null) {
			commandLine = new CommandLine();
		}
		return commandLine;
	}

	private CommandName getCommandName(String commandString) {

		CommandName commandName = null;

		if (commandString == null) {
			System.out.println("Not a valid input");
		} else {
			String[] commandStringArray = commandString.split(" ");
			if ("".equals(commandStringArray[0])) {
				System.out.println("Not a valid input");
			} else {
				try {
					commandName = CommandName.valueOf(commandStringArray[0]);
				} catch (Exception e) {
					System.out.println("Unknown Command");
				}
			}
		}
		return commandName;

	}
	
	/**
	 * the main function to execute the commands 
	 * @param commandString
	 * @return boolean if the execution is success or not
	 */
	public boolean execute(String commandString) {

		CommandName commandName = getCommandName(commandString);

		if (commandName == null) {
			return false;
		}
		String[] commandStringArray = commandString.split(" ");
		Command command;

		switch (commandName) {
		case create_parking_lot:
			command = new CreateParkingLotCommand(commandStringArray);
			break;
		case park:
			command = new ParkCommand(commandStringArray);
			break;
		case leave:
			command = new LeaveCommand(commandStringArray);
			break;
		case status:
			command = new StatusCommand(commandStringArray);
			break;
		case registration_numbers_for_cars_with_colour:
			command = new RegistrationNumbersForColorCommand(commandStringArray);
			break;
		case slot_numbers_for_cars_with_colour:
			command = new SlotNumbersForColorCommand(commandStringArray);
			break;
		case slot_number_for_registration_number:
			command = new SlotNumberCommand(commandStringArray);
			break;
		default:
			System.out.println("Unknown Command");
			return false;
		}

		try {
			command.validate();
		} catch (IllegalArgumentException e) {
			System.out.println("Please provide a valid argument");
			return false;
		}

		String output = "";
		try {
			output = command.execute();
		} catch (ParkingSpaceException e) {
			System.out.print(e.getMessage());
		} catch(Exception e) {
			System.out.println("Unknown System Issue");
			e.printStackTrace();
			return false;
		}
		System.out.println(output);
		return true;
	}

	/**
	 * All CommandNames
	 *
	 */
	private enum CommandName {
		create_parking_lot, park, leave, status, registration_numbers_for_cars_with_colour,
		slot_numbers_for_cars_with_colour, slot_number_for_registration_number
	}

	/**
	 * Command Interface which validates & executes the command
	 *
	 */
	private interface Command {
		public void validate();

		public String execute();
	}

	/**
	 * Command Implementing create_parking_lot
	 *
	 */
	private class CreateParkingLotCommand implements Command {
		private String[] commandStringArray;

		CreateParkingLotCommand(String[] s) {
			commandStringArray = s;
		}

		public void validate() {
			if (commandStringArray.length != 2) {
				throw new IllegalArgumentException("create_parking_lot command should have exactly 1 argument");
			}
		}

		public String execute() {
			int numberOfSlots = Integer.parseInt(commandStringArray[1]);
			SlotSystem.createInstance(numberOfSlots);
			return "Created a parking lot with " + commandStringArray[1] + " slots";
		}
	}

	/**
	 * holds the responsibility of implementing park command
	 *
	 */
	private class ParkCommand implements Command {
		private String[] commandStringArray;

		ParkCommand(String[] s) {
			commandStringArray = s;
		}

		public void validate() {
			if (commandStringArray.length != 3) {
				throw new IllegalArgumentException("park command should have exactly 2 arguments");
			}
		}

		public String execute() {
			SlotSystem slotSystem = SlotSystem.getInstance();
			int allocatedSlotNumber = slotSystem
					.issueParkingTicket(new Car(commandStringArray[1], commandStringArray[2]));
			return "Allocated slot number: " + allocatedSlotNumber;
		}
	}

	/**
	 * holds the responsibility of implementing leave command
	 *
	 */
	private class LeaveCommand implements Command {
		private String[] commandStringArray;

		LeaveCommand(String[] s) {
			commandStringArray = s;
		}

		public void validate() {
			if (commandStringArray.length != 2) {
				throw new IllegalArgumentException("leave command should have exactly 1 argument");
			}
		}

		public String execute() {
			SlotSystem slotSystem = SlotSystem.getInstance();
			slotSystem.exitVehicle(Integer.parseInt(commandStringArray[1]));
			return "Slot number " + commandStringArray[1] + " is free";
		}
	}

	/**
	 * holds the responsibility of implementing status command
	 *
	 */
	private class StatusCommand implements Command {
		private String[] commandStringArray;

		StatusCommand(String[] s) {
			commandStringArray = s;
		}

		public void validate() {
			if (commandStringArray.length != 1) {
				throw new IllegalArgumentException("status command should have no arguments");
			}
		}

		public String execute() {
			SlotSystem slotSystem = SlotSystem.getInstance();
			List<ParkingStatus> parkingStatusList = slotSystem.getStatus();

			StringBuilder outputStringBuilder = new StringBuilder("Slot No.    Registration No    Colour");
			for (ParkingStatus parkingStatus : parkingStatusList) {
				outputStringBuilder.append("\n").append(parkingStatus);
			}
			return outputStringBuilder.toString();
		}
	}

	/**
	 * holds the responsibility of implementing
	 * registration_numbers_for_cars_with_colour command
	 *
	 */
	private class RegistrationNumbersForColorCommand implements Command {
		private String[] commandStringArray;

		RegistrationNumbersForColorCommand(String[] s) {
			commandStringArray = s;
		}

		public void validate() {
			if (commandStringArray.length != 2) {
				throw new IllegalArgumentException(
						"registration_numbers_for_cars_with_colour command should have exactly 1 argument");
			}
		}

		public String execute() {
			SlotSystem slotSystem = SlotSystem.getInstance();
			List<String> registrationNumbersList = slotSystem
					.getRegistrationNumbersFromColor(commandStringArray[1]);
			StringBuilder outputStringBuilder = new StringBuilder();
			for (String registrationNumber : registrationNumbersList) {
				if (outputStringBuilder.length() > 0) {
					outputStringBuilder.append(", ");
				}
				outputStringBuilder.append(registrationNumber);
			}
			return outputStringBuilder.toString();
		}
	}

	/**
	 * holds the responsibility of implementing slot_numbers_for_cars_with_colour
	 * command
	 *
	 */
	private class SlotNumbersForColorCommand implements Command {
		private String[] commandStringArray;

		SlotNumbersForColorCommand(String[] s) {
			commandStringArray = s;
		}

		public void validate() {
			if (commandStringArray.length != 2) {
				throw new IllegalArgumentException(
						"slot_numbers_for_cars_with_colour command should have exactly 1 argument");
			}
		}

		public String execute() {
			SlotSystem slotSystem = SlotSystem.getInstance();
			List<Integer> slotNumbersList = slotSystem.getSlotNumbersFromColor(commandStringArray[1]);
			StringBuilder outputStringBuilder = new StringBuilder();
			for (int slotNumber : slotNumbersList) {
				if (outputStringBuilder.length() > 0) {
					outputStringBuilder.append(", ");
				}
				outputStringBuilder.append(slotNumber);
			}
			return outputStringBuilder.toString();
		}
	}

	/**
	 * holds the responsibility of implementing slot_number_for_registration_number
	 * command
	 *
	 */
	private class SlotNumberCommand implements Command {
		private String[] commandStringArray;

		SlotNumberCommand(String[] s) {
			commandStringArray = s;
		}

		public void validate() {
			if (commandStringArray.length != 2) {
				throw new IllegalArgumentException(
						"slot_number_for_registration_number command should have exactly 1 argument");
			}
		}

		public String execute() {
			SlotSystem slotSystem = SlotSystem.getInstance();
			int slotNumber = slotSystem.getSlotNumberFromRegistrationNumber(commandStringArray[1]);
			return "" + slotNumber;
		}
	}

}
