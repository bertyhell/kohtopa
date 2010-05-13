package data.entities;

import Language.Language;
import javax.swing.ImageIcon;

public class Rentable extends AbstractPlace {

	private int type;
	private int area;
	private String windowDirection;
	private int windowArea;
	private boolean internet;
	private boolean cable;
	private int outletCount;
	private int floor;
	private boolean rented;
	private double price;
	private String description;
	private ImageIcon previewImage;
    private int buildingID;

    public Rentable(int id, ImageIcon previewImage, int type, int area, String windowDirection, int windowArea, boolean internet, boolean cable, int outletCount, int floor, boolean rented, double price, String description, int buildingID) {
        this(id, previewImage, type, area, windowDirection, windowArea, internet, cable, outletCount, floor, rented, price, description);
        this.buildingID = buildingID;
    }

	public Rentable(int id, ImageIcon previewImage, int type, int area, String windowDirection, int windowArea, boolean internet, boolean cable, int outletCount, int floor, boolean rented, double price, String description) {
		this(id, previewImage, type, floor, description);
		this.area = area;
		this.windowDirection = windowDirection;
		this.windowArea = windowArea;
		this.internet = internet;
		this.cable = cable;
		this.outletCount = outletCount;
		this.rented = rented;
		this.price = price;
	}

	public Rentable(int id, ImageIcon previewImage, int type, int floor, String description) {
		super(id);
		if (previewImage == null) {
			this.previewImage = new ImageIcon(getClass().getResource("/images/dummy_rentable_preview.png"));
		} else {
			this.previewImage = previewImage;
		}
		this.type = type;
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

    public int getBuildingID() {
        return buildingID;
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

	public int getType() {
		return type;
	}

	public int getWindowArea() {
		return windowArea;
	}

	public String getWindowsDirection() {
		return windowDirection;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ImageIcon getPreviewImage() {
		return previewImage;
	}

	//description
	@Override
	public String toString() {
		return Integer.toString(id); //TODO add name for rentable, return it here
	}
}
