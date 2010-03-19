package model;

import model.data.Building;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.data.Rentable;

public class Model {

	//this class is used to seperate betwean database and gui in case of change
	//and it will also be used to cach certain data (like images and the global list of buildings

	private static Model instance = new Model();

	public static Model getInstance(){
		return instance;
	}

	private Model() {
	}
	
	public ArrayList<Building> getBuildingPreviews(Component requesterFrame) throws SQLException, IOException{
		return DataConnector.selectBuildingPreviews();
	}

	public void addDummyPictures(){
		DataConnector.addDummyPictures();
	}

	public HashMap<Integer, BufferedImage> getPictures(int buildingId, boolean isBuilding) throws SQLException {
		return DataConnector.getPictures(buildingId, isBuilding);
	}

	public Building getBuilding(int buildingId) throws SQLException{
	    return DataConnector.getBuilding(buildingId);
	}

	public ArrayList<Rentable> getRentablesFromBuilding(int buildingId) throws SQLException{
		return DataConnector.getRentablesFromBuilding(buildingId);
	}

	public Rentable getRentable(int rentableId) throws SQLException {
		return DataConnector.getRentable(rentableId);
	}
}
