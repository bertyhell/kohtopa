package model;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Model {

	//this class is used to seperate betwean database and gui in case of change
	//and it will also be used to cach certain data (like images and the global list of buildings

	private static Model instance = new Model();

	public static Model getInstance(){
		return instance;
	}

	private Model() {
	}
	
	public ArrayList<Building> getBuildingPreviews(Component requesterFrame) throws SQLException{
		return DataConnector.selectBuildingPreviews();
	}

	public void addDummyPictures(){
		DataConnector.addDummyPictures();
	}

	public HashMap<Integer, BufferedImage> getPictures(int buildingId, boolean isBuilding) throws SQLException {
		return DataConnector.getPictures(buildingId, isBuilding);
	}
}
