package data.entities;

import gui.Logger;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.swing.ImageIcon;

public class Building extends AbstractPlace {

	private Address address;
	private BufferedImage previewImage;
	private double latitude;
	private double longitude;

	public Building(int id, BufferedImage previewImage, String street, String number, String zipcode, String city) {
		this(id, previewImage, street, number, zipcode, city, "BE", 0, 0);
		geocode(street + " " + number + ", " + zipcode + " " + city);
	}

	public Building(int id, BufferedImage previewImage, String street, String number, String zipcode, String city, String country, double latitude, double longitude) {
		super(id);
		if (previewImage == null) {
			Image image = new ImageIcon(getClass().getResource("/images/dummy_building_preview.png")).getImage();
			BufferedImage img = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			img.getGraphics().drawImage(image, 0, 0, null);
			this.previewImage = img;
		} else {
			this.previewImage = previewImage;
		}
		address = new Address(street, number, zipcode, city, country);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	private void geocode(String address) {
		try {
			URL lookup = new URL("http://maps.google.com/maps/geo?output=csv&oe=utf8&sensor=false&key=ABQIAAAAzKGmk-jPDLGRcG8fwxkGYBT2yXp_ZAY8_ufC3CFXhHIE1NvwkxTesRHHZ4bWtr9jHa4CooA-t6-eIg%20&q=" + URLEncoder.encode(address, "UTF-8"));
			URLConnection conn = lookup.openConnection();
			conn.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String response = br.readLine();

			String[] parts = response.split(",");
			if (parts[0].equals("200")) {
				latitude = Double.parseDouble(parts[2]);
				longitude = Double.parseDouble(parts[3]);
			}
			// wait 200 ms to get next value
			long t0, t1;

			t0 = System.currentTimeMillis();

			do {
				t1 = System.currentTimeMillis();
			} while ((t1 - t0) < 200);
			Logger.logger.info(response);
		} catch (IOException ex) {
			Logger.logger.error("Exception in geocode " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}

	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public BufferedImage getPreviewImage() {
		return previewImage;
	}

	@Override
	public int getId() {
		return id;
	}

	public Address getAddress() {
		return address;
	}

	@Override
	public String toString() {
		try {
			return address.getStreetLine() + ", " + address.getCityLine();
		} catch (Exception ex) {
			System.out.println("exception in tostring of building: " + ex.getMessage());
			return null;
		}
	}
}
