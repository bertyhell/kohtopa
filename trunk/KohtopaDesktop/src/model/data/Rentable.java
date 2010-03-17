package model.data;

public class Rentable {

    private int rantableId;
    private int buildingId;
    private int ownerId;
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

    public Rentable(int rantableId, int buildingId, int ownerId, String type, int area, String windowsDirection, int windowArea, boolean internet, boolean cable, int outletCount, int floor, boolean rented, double price) {
	this.rantableId = rantableId;
	this.buildingId = buildingId;
	this.ownerId = ownerId;
	this.type = type;
	this.area = area;
	this.windowsDirection = windowsDirection;
	this.windowArea = windowArea;
	this.internet = internet;
	this.cable = cable;
	this.outletCount = outletCount;
	this.floor = floor;
	this.rented = rented;
	this.price = price;
    }

    public int getArea() {
	return area;
    }

    public int getBuildingId() {
	return buildingId;
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

    public int getOwnerId() {
	return ownerId;
    }

    public double getPrice() {
	return price;
    }

    public int getRantableId() {
	return rantableId;
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
}
