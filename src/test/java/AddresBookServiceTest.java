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
}
