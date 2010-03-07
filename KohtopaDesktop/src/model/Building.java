package model;

import java.awt.Image;
import java.util.ArrayList;

public class Building {

	private int id;
	private String name;
	private String street;
	private String number;
	private String zipcode;
	private String city;
	private int previewIndex;
	private ArrayList<Image> images;

	public Building(int id, String name, String street, String number, String zipcode, String city) {
		this.id = id;
		this.name = name;
		this.street = street;
		this.number = number;
		this.zipcode = zipcode;
		this.city = city;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public String getNumber() {
		return number;
	}

	public int getPreviewIndex() {
		return previewIndex;
	}

	public String getStreet() {
		return street;
	}

	public String getZipcode() {
		return zipcode;
	}
}
