package com.addressbook;

public class AddressBookException extends Exception {
	public enum ExceptionType { NO_SUCH_DATA, CONNECTION_FAILED, QUERY_EXECUTION_FAILED;

	}
	public AddressBookException exceptionType;

	public AddressBookException(String message, AddressBookException exceptionType ) {
		super(message);
		this.exceptionType = exceptionType;
	}
}
