package com.addressbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.Scanner;

public class AddressBookService {

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<PersonData> addressBookList;

	private AddressBookDBService addressBookDBService;

	public AddressBookService() {
		addressBookDBService = AddressBookDBService.getInstance();
	}

	public AddressBookService(List<PersonData> addressBookList) {
		this();
		this.addressBookList = addressBookList;
	}

	private void readPersonData(Scanner consoleInputReader) {
		System.out.println("Enter Id: ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter FirstName: ");
		String firstName = consoleInputReader.next();
		System.out.println("Enter LastName: ");
		String lastName = consoleInputReader.next();
		System.out.println("Enter Address: ");
		String address = consoleInputReader.next();
		System.out.println("Enter City: ");
		String city = consoleInputReader.next();
		System.out.println("Enter State: ");
		String state = consoleInputReader.next();
		System.out.println("Enter Zip: ");
		int zip = consoleInputReader.nextInt();
		System.out.println("Enter PhoneNumber: ");
		long phoneNumber = consoleInputReader.nextLong();
		System.out.println("Enter EmailId: ");
		String emailId = consoleInputReader.next();
		addressBookList.add(new PersonData(id, firstName, lastName, address, city, state, zip, phoneNumber, emailId));
	}

	// uc2
	public List<PersonData> readPersonData(IOService ioService) {
		if (ioService.equals(IOService.DB_IO))
			this.addressBookList = addressBookDBService.readData();
		return this.addressBookList;
	}
}
