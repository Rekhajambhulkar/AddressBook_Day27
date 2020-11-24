package com.addressbook;

import java.time.LocalDate;
import java.util.Objects;

public class PersonData { 
	int Id;
	String FirstName;
	String LastName;
	String Address;
	String City;
	String State;
	int Zip;
	long PhoneNumber;
	String EmailId;

	public PersonData(int id, String firstName, String lastName, String address, String city, String state, int zip,
			long phoneNumber, String emailId) {
		this.Id = id;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.Address = address;
		this.City = city;
		this.State = state;
		this.Zip = zip; 
		this.PhoneNumber = phoneNumber;
		this.EmailId = emailId;

	}

	@Override
	public String toString() {
		return "PersonData [FirstName=" + FirstName + ", LastName=" + LastName + ", Address=" + Address + ", City="
				+ City + ", State=" + State + ", Zip=" + Zip + ", MobileNo=" + PhoneNumber + ", EmailId=" + EmailId + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		PersonData other = (PersonData) obj;
		if (Address == null) {
			if (other.Address != null)
				return false;
		} else if (!Address.equals(other.Address))
			return false;
		if (City == null) {
			if (other.City != null)
				return false;
		} else if (!City.equals(other.City))
			return false;
		if (EmailId == null) {
			if (other.EmailId != null)
				return false;
		} else if (!EmailId.equals(other.EmailId))
			return false;
		if (FirstName == null) {
			if (other.FirstName != null)
				return false;
		} else if (!FirstName.equals(other.FirstName))
			return false;
		if (LastName == null) {
			if (other.LastName != null)
				return false;
		} else if (!LastName.equals(other.LastName))
			return false;
		if (PhoneNumber != other.PhoneNumber)
			return false;
		if (State == null) {
			if (other.State != null)
				return false;
		} else if (!State.equals(other.State))
			return false;
		if (Zip != other.Zip)
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
}
