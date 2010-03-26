package data.entities;

import Language.Language;

public class Rentable extends AbstractPlace {

    private String type;
    private int area;
    private String windowsDirection;
    private int windowArea;
    private boolean internet;
    private boolean cable;
    private int outletCount;
    private int floor;
    private boolean rented;
    private double price;
    private String description;

    public Rentable(int id, int type, int area, String windowsDirection, int windowArea, boolean internet, boolean cable, int outletCount, int floor, boolean rented, double price, String description) {
	this(id, type, floor, description);
	this.area = area;
	this.windowsDirection = windowsDirection;
	this.windowArea = windowArea;
	this.internet = internet;
	this.cable = cable;
	this.outletCount = outletCount;
	this.rented = rented;
	this.price = price;
    }

    public Rentable(int id, int type, int floor, String description) {
	super(id);
	this.type = Language.getString("rentableType" + type);
	this.floor = floor;
	this.description = description;
    }

    public int getArea() {
	return area;
    }

    public boolean isCable() {
	return cable;
    }

    public int getFloor() {
	return floor;
    }

    public boolean isInternet() {
	return internet;
    }

    public int getOutletCount() {
	return outletCount;
    }

    public double getPrice() {
	return price;
    }

    public boolean isRented() {
	return rented;
    }

    public String getType() {
	return type;
    }

    public int getWindowArea() {
	return windowArea;
    }

    public String getWindowsDirection() {
	return windowsDirection;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Override
    public String toString() {
	return Integer.toString(id); //TODO add name for rentable, return it here
    }
}
