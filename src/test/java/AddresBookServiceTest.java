package com.addressbook;

import org.junit.Test;
import com.addressbook.PersonData;
import static com.addressbook.AddressBookService.IOService.DB_IO;
import static com.addressbook.AddressBookService.IOService.FILE_IO;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;

public class AddressBookServiceTest {

	@Test
	public void givenAddressBookInDB_WhenRetrieved_ShouldMatchAddressBookCount() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService();
		List<PersonData> addressBookData = addressBookService.readPersonData(DB_IO);
		Assert.assertEquals(3, addressBookData.size());
	}

	@Test
	public void givenNewAddressforPerson_WhenUpdated_ShouldSyncWithDB() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService();
		List<PersonData> addressBookData = addressBookService.readPersonData(DB_IO);
		addressBookService.updatePersonAddress("REKHA", "katraj");
		boolean result = addressBookService.checkAddressBookInSyncWithDB("REKHA");
		Assert.assertTrue(result);
	}

	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readPersonData(DB_IO);
		LocalDate startDate = LocalDate.of(2018, 01, 01);
		LocalDate endDate = LocalDate.now();
		List<PersonData> addressBookData = addressBookService.readAddressBookForDateRange(DB_IO, startDate,
				endDate);
		Assert.assertEquals(1, addressBookData.size());
	}

	@Test
	public void givenNewAddressforPerson_WhensearchByState_ShouldSyncWithDB() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService();
		List<PersonData> addressBookData = addressBookService.searchByCity(AddressBookService.IOService.DB_IO, "PUNE");
		Assert.assertEquals(2, addressBookData.size());
	}

	@Test
	public void givenNewPerson_WhenAdded_ShouldSyncWithDB() {
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readPersonData(DB_IO);
		addressBookService.addToAddressBook("MAHI", "KHURANA", "LONAVALA", "PUNE", "MAHARASHTRA",432415, 98226256,"mahi.khuran@gmail.com",LocalDate.now());
		boolean result = addressBookService.checkAddressBookInSyncWithDB("MAHI");
		Assert.assertTrue(result);
	}

	@Test
	public void givenPerson_WhenAddedToDB_ShouldMatchPersonEntries() {
	PersonData[] arrayOfEmps = {
			new PersonData (0,"shweta","sane","chinchwad","pune","maharahstra",435678,98226265,"saneshweta@gmail.com",LocalDate.now()),
			new PersonData (0,"nupoor","khan","pimapri","pune","maharahstra",234532,98778789,"nuppor123@gmail.com",LocalDate.now()),
			new PersonData (0,"ankita","bhat","Aundh","pune","maharahstra",675432,98665566,"ankita.bhat@gmail.com",LocalDate.now()),
			new PersonData (0,"Ash","khurana","kothrud","pune","maharahstra",412432,98342189,"ash.khurana@gmail.com",LocalDate.now()),
			new PersonData (0,"piyu","vaste","chinchwad","pune","maharahstra",213434,98765678,"piyu12.vaste@gmail.com",LocalDate.now()),
	};
	AddressBookService addressBookService = new AddressBookService();
	addressBookService.readPersonData(DB_IO);
	Instant start = Instant.now();
	addressBookService.addToAddressBook(Arrays.asList(arrayOfEmps));
	Instant end = Instant.now();
	System.out.println("Duration without thread:"+ Duration.between(start,end));
	Instant threadStart = Instant.now();
	addressBookService.addEmployeePayrollWithThread(Arrays.asList(arrayOfEmps));
	Instant threadEnd = Instant.now();
	System.out.println("Duration with thread"+Duration.between(threadStart,threadEnd));
	addressBookService.printData(DB_IO);
	Assert.assertEquals(12, addressBookService.countEntries(DB_IO));
	}
}
