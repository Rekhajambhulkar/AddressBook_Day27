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

	//Function for Read Person Data
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

	public List<PersonData> readPersonData(IOService ioService) {
		if (ioService.equals(IOService.DB_IO))
			this.addressBookList = addressBookDBService.readData();
		return this.addressBookList;
	}

	//Function for Update person contacts
	public void updatePersonAddress(String firstname, String address) {
		int result = addressBookDBService.updatePersonData(firstname, address);
		if (result == 0)
			return;
		PersonData addressBookData = this.getPersonData(firstname);
		if (addressBookData != null)
			addressBookData.Address = address;
	}

	private PersonData getPersonData(String firstname) {
		PersonData addressBookData;
		addressBookData = this.addressBookList.stream()
				.filter(employeePayrollDataItem -> employeePayrollDataItem.FirstName.equals(firstname)).findFirst()
				.orElse(null);
		return addressBookData;
	}

	public boolean checkAddressBookInSyncWithDB(String firstname) {
		List<PersonData> addressBookDataList = addressBookDBService.getPersonData(firstname);
		return addressBookDataList.get(0).equals(getPersonData(firstname));
	}

	public List<PersonData> readAddressBookForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
		if (ioService.equals(IOService.DB_IO))
			return addressBookDBService.getAddressBookForDateRange(startDate, endDate);
		return null;
	}

	//Function for Retrieve city from DB
	public List<PersonData> searchByCity(IOService ioService, String state) {
		return addressBookDBService.searchByCity(state);
	}

	//Function for add Contacts in DB
	public void addToAddressBook(String firstname, String lastname, String address, String city, String state, int zip,
			int phoneNumber, String emailId, LocalDate startDate) {
		addressBookList.add(addressBookDBService.addEmployeeToPayroll(firstname, lastname, address, city, state, zip,
				phoneNumber, emailId, startDate));
	}

	//Function for adding multiple contacts in DB
	public void addToAddressBook(List<PersonData> personDataList) {
		personDataList.forEach(addressBookData -> {
			this.addEmployeeToPayroll(addressBookData.FirstName, addressBookData.LastName, addressBookData.Address,
					addressBookData.City, addressBookData.State, addressBookData.Zip, addressBookData.PhoneNumber,
					addressBookData.EmailId, addressBookData.startDate);
		});
	}

	//Add multiple contacts using Thread
	public void addEmployeePayrollWithThread(List<PersonData> personDataList) {
		Map<Integer, Boolean> personAdditionStatus = new HashMap<Integer, Boolean>();
		personDataList.forEach(addressBookData -> {
			Runnable task = () -> {
				personAdditionStatus.put(addressBookData.hashCode(), false);
				System.out.println("person bieng added:" + Thread.currentThread().getName());
				this.addEmployeeToPayroll(addressBookData.FirstName, addressBookData.LastName, addressBookData.Address,
						addressBookData.City, addressBookData.State, addressBookData.Zip, addressBookData.PhoneNumber,
						addressBookData.EmailId, addressBookData.startDate);
				personAdditionStatus.put(addressBookData.hashCode(), true);
				System.out.println("Person added: " + Thread.currentThread().getName());
			};
			Thread thread = new Thread(task, addressBookData.FirstName);
			thread.start();
		});
		while (personAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println(personDataList);
			}
		}
	}

	public long countEntries(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO))
			return new AddressBookFileIOService().countEntries();
		return addressBookList.size();
	}

	public void printData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO))
			new AddressBookFileIOService().printData();
		else
			System.out.println(addressBookList);
	}

	public void writeEmployeePayrollData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO))
			System.out.println("\n Writing Employee payroll roaster to console\n" + addressBookList);
		else if (ioService.equals(IOService.FILE_IO))
			new AddressBookFileIOService().writeData(addressBookList);
	}
}
