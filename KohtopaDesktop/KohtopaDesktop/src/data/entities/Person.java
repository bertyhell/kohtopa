package data.entities;

import javax.swing.ImageIcon;

public class Person {

	private int id;
	private Address address;
	private ImageIcon preview;
	private String name;
	private String firstName;
	private String email;
	private String telephone;
	private String cellphone;

	public Person(
			int id,
			String street,
			String streetNumber,
			String zipCode,
			String city,
			String country,
			String name,
			String firstName,
			String email,
			String telephone,
			String cellphone) {
		this.id = id;
		this.address = new Address(street, streetNumber, zipCode, city, country);
		preview = new ImageIcon(getClass().getResource("/images/dummy_person_preview.png"));
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.telephone = telephone;
		this.cellphone = cellphone;
	}

	public Person(int id, ImageIcon preview, String name, String firstName) {
		this.id = id;
		this.address = null;
		if(preview == null){
			this.preview = new ImageIcon(getClass().getResource("/images/dummy_person_preview.png"));
		}else{
			this.preview = preview;
		}
		this.name = name;
		this.firstName = firstName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTelephone() {
		return telephone;
	}

	public ImageIcon getPreview() {
		return preview;
	}

	public Address getAddress() {
		return address;
	}

	

	@Override
	public String toString() {
		return firstName + " " + name;
	}
}