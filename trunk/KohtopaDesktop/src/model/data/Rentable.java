package model.data;

public class Rentable {

	private int Id;
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

	public Rentable(int rantableId, String type, int area, String windowsDirection, int windowArea, boolean internet, boolean cable, int outletCount, int floor, boolean rented, double price) {
		this.Id = rantableId;
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

	public Rentable(int rentableId, int floor) {
		this.Id = rentableId;
		this.floor = floor;
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

	public int getId() {
		return Id;
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

	@Override
	public String toString() {
		return Integer.toString(Id); //TODO add name for rentable, return it here
	}
}
