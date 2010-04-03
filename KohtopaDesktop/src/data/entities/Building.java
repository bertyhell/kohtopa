package data.entities;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class Building extends AbstractPlace {

	private String street;
	private String number;
	private String zipcode;
	private String city;
	private String country;
	private ImageIcon previewImage;
        private double latitude;
        private double longitude;
	private ArrayList<BufferedImage> floors;
	private ArrayList<BufferedImage> images;

	public Building(int id, ImageIcon previewImage, String street, String number, String zipcode, String city) {
            this(id, previewImage, street, number, zipcode, city,"BE",0,0);
            geocode(street+" "+number+", "+zipcode + " "+city);
	}

	public Building(int id, ImageIcon previewImage, String street, String number, String zipcode, String city, String country,double latitude,double longitude) {
                super(id);
		if (previewImage == null) {
			this.previewImage = new ImageIcon(getClass().getResource("/images/dummy_building_preview.png"));
		} else {
			this.previewImage = previewImage;
		}
		this.street = street;
		this.number = number;
		this.zipcode = zipcode;
		this.city = city;
		this.country = country;
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
                if(parts[0].equals("200")) {
                    latitude = Double.parseDouble(parts[2]);
                    longitude = Double.parseDouble(parts[3]);
                }
                // wait 200 ms to get next value
                long t0, t1;

                t0 = System.currentTimeMillis();

                do {
                    t1 = System.currentTimeMillis();
                } while ((t1 - t0) < 200);
                System.out.println(response);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
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

	public String getCountry() {
		return country;
	}

	public String getStreetLine() {
		return street + " " + number;
	}
}
