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
}
