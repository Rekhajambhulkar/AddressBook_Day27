package com.addressbook;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

	private PreparedStatement addressBookDataStatement;
	private static AddressBookDBService addressBookDBService;

	private AddressBookDBService() {}

	public static AddressBookDBService getInstance() {
		if (addressBookDBService == null)
			addressBookDBService = new AddressBookDBService();
		return addressBookDBService;
	}

	//Create connection
	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/addressBook_service?useSSL=false";
		String userName = "root";
		String password = "root";
		Connection con;
		System.out.println("Connecting to database:" + jdbcURL);
		con = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is successful!!!!" + con);
		return con;
	}

	//Read Peson Data
	public List<PersonData> readData() {
		String sql = "SELECT * FROM addressbook";
		List<PersonData> addressBookList = new ArrayList<>();
		try (Connection con = this.getConnection()){
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String FirstName = resultSet.getString("FirstName");
				String LastName = resultSet.getString("LastName");
				String Address = resultSet.getString("Address");
				String City = resultSet.getString("City");
				String State = resultSet.getString("State");
				int Zip = resultSet.getInt("Zip");
				long PhoneNumber = resultSet.getLong("PhoneNumber");
				String EmailId = resultSet.getString("EmailId");
				LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
				addressBookList.add(new PersonData(FirstName, LastName, Address, City, State, Zip, PhoneNumber, EmailId,
						startDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	// uc3
	public List<PersonData> getPersonData(String firstname) {
		List<PersonData> addressBookList = null;
		if (this.addressBookDataStatement == null)
			this.preparedStatementForAddressBook();
		try {
			addressBookDataStatement.setString(1, firstname);
			ResultSet resultSet = addressBookDataStatement.executeQuery();
			addressBookList = this.getPersonData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	private List<PersonData> getPersonData(ResultSet resultSet) {
		List<PersonData> addressBookList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("Id");
				String firstName = resultSet.getString("FirstName");
				String lastName = resultSet.getString("LastName");
				String address = resultSet.getString("Address");
				String city = resultSet.getString("City");
				String state = resultSet.getString("State");
				int zip = resultSet.getInt("Zip");
				long phoneNumber = resultSet.getLong("PhoneNumber");
				String emailId = resultSet.getString("EmailId");
				LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
				addressBookList.add(new PersonData(id,firstName, lastName, address, city, state, zip, phoneNumber, emailId,startDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	public int updatePersonData(String firstname, String address) {
		return this.updatePersonDataUsingStatement(firstname, address);
	}

	private int updatePersonDataUsingStatement(String firstname, String address) {
		String sql = String.format("update addressbook set address = '%s' where firstname = '%s';", address, firstname);
		try (Connection con = this.getConnection()) {
			Statement statement = con.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private void preparedStatementForAddressBook() {
		try {
			Connection con = this.getConnection();
			String sql = "SELECT * FROM addressbook WHERE firstname= ? ";
			addressBookDataStatement = con.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<PersonData> getAddressBookForDateRange(LocalDate startDate, LocalDate endDate) {
		String sql = String.format("SELECT * FROM addressbook WHERE STARTDATE BETWEEN '%s' AND '%s';",
				Date.valueOf(startDate), Date.valueOf(endDate));
		return this.getAddressBookDataUsingDB(sql);
	}

	private List<PersonData> addressBookList = new ArrayList<>();
		try (Connection con = this.getConnection()) {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			employeepayrollList = this.getPersonData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	public List<PersonData> searchByCity(String city) {
		String sql = String.format("SELECT * FROM ADDRESSBOOK WHERE city = '%s';", city);
		return this.getAddressBookDataUsingDB(sql);
	}
}
