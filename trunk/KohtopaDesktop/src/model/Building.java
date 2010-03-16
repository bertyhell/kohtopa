package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Building {

	private int id;
	private String street;
	private String number;
	private String zipcode;
	private String city;
	private ImageIcon previewImage;
	private ArrayList<BufferedImage> floors;
	private ArrayList<BufferedImage> images;

	public Building(int id, ImageIcon previewImage, String street, String number, String zipcode, String city) {
		this.id = id;
		if (previewImage == null) {
			this.previewImage = new ImageIcon(getClass().getResource("/images/dummy_building_preview.png"));
		} else {
			this.previewImage = previewImage;
		}
		this.street = street;
		this.number = number;
		this.zipcode = zipcode;
		this.city = city;
	}

	public ArrayList<BufferedImage> getFloors() {
		return floors;
	}

	public ImageIcon getPreviewImage() {
		return previewImage;
	}

	public int getId() {
		return id;
	}

	public String getCity() {
		return city;
	}

	public ArrayList<BufferedImage> getImages() {
		return images;
	}

	public String getNumber() {
		return number;
	}

	public String getStreet() {
		return street;
	}

	public String getZipcode() {
		return zipcode;
	}
}
