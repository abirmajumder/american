package com.aig.object;

public class AddressCapsule implements Cloneable {
	
	private String location, account, addressLine, city, state, country;

	public AddressCapsule() {
	}
	
	public AddressCapsule(String location, String account, String addressLine,
			String city, String state, String country) {
		this.location = location;
		this.account = account;
		this.addressLine = addressLine;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "AddressCapsule [location=" + location + ", account=" + account
				+ ", addressLine=" + addressLine + ", city=" + city
				+ ", state=" + state + ", country=" + country + "]";
	}
	
}
