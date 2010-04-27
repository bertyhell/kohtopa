package data.entities;

/**
 *
 * @author Bert Verhelst
 */
public class Address {

	private String street;
	private String streetNumber;
	private String zipcode;
	private String city;
	private String country;

	public Address(String street, String streetNumber, String zipcode, String city, String country) {
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipcode = zipcode;
		this.city = city;
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getStreet() {
		return street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getStreetLine(){
		return street + " " + streetNumber;
	}

	public String getCityLine(){
		return zipcode + " " + city;
	}
}
