package com.assignment.parkspace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import com.assignment.parkspace.service.CommandLine;

public class App {
	public static void main(String[] args) throws Exception {

		CommandLine commandLine = CommandLine.getInstance();
		BufferedReader bufferedReader;
		
		if (args.length == 0) {
			// Input Command Line Reader
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		} else {
			// Input File Reader
			String filePath = args[0];
			File inputFile = new File(filePath);
			bufferedReader = new BufferedReader(new FileReader(inputFile));
		}
		
		while (true) {
			String commandText = bufferedReader.readLine();
			if (commandText == null || "exit".equalsIgnoreCase(commandText)) {
				break;
			} else {
				// if execution is not success then exit as there can be dependencies with commands
				boolean executionSuccess = commandLine.execute(commandText);
				if(!executionSuccess) {
					break;
				}
			}
		}
	}
}
