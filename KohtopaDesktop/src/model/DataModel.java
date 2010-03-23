package model;

import Language.Language;
import gui.AddRemoveTab.BuildingListModel;
import gui.AddRemoveTab.RentableListModel;
import gui.AddRemoveTab.RentableListPanel;
import gui.Main;
import model.data.Building;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import model.data.Rentable;

public class DataModel {

	//this class is used to seperate betwean database and gui in case of change
	//and it will also be used to cach certain data (like images and the global list of buildings
	//TODO add caching + options on timeout in settings
	private ArrayList<Building> buildingPreviews;
	private ArrayList<Rentable> rentablePreviews;
	private BuildingListModel lmBuilding;
	private RentableListModel lmRentable;
	private static int buildingIndex;
	private static int rentableIndex;

	public DataModel() {
		buildingIndex = -1; //none selected
		rentableIndex = -1; //none selected

		lmBuilding = new BuildingListModel();
		lmRentable = new RentableListModel();
		buildingPreviews = new ArrayList<Building>();
		rentablePreviews = new ArrayList<Rentable>();
	}

	public ArrayList<Building> getBuildingPreviews(Component requesterFrame) throws SQLException, IOException {
		return DataConnector.selectBuildingPreviews();
	}

	public ArrayList<Rentable> getRentablePreviews(int buildingId) throws SQLException, IOException {
		return DataConnector.getRentablesFromBuilding(buildingId);
	}

	public void addDummyPictures() {
		DataConnector.addDummyPictures();
	}

	public HashMap<Integer, BufferedImage> getPictures(int buildingId, boolean isBuilding) throws SQLException {
		return DataConnector.getPictures(buildingId, isBuilding);
	}

	public Building getBuilding(int buildingId) throws SQLException {
		return DataConnector.getBuilding(buildingId);
	}

	public ArrayList<Rentable> getRentablesFromBuilding(int buildingId) throws SQLException {
		return DataConnector.getRentablesFromBuilding(buildingId);
	}

	public Rentable getRentable(int rentableId) throws SQLException {
		return DataConnector.getRentable(rentableId);
	}

	public static int getBuildingIndex() {
		return buildingIndex;
	}

	public static void setBuildingIndex(int buildingIndex) {
		DataModel.buildingIndex = buildingIndex;
	}

	public void setBuildingPreviews(ArrayList<Building> buildingPreviews) {
		this.buildingPreviews = buildingPreviews;
	}

	public BuildingListModel getLmBuilding() {
		return lmBuilding;
	}

	public RentableListModel getLmRentable() {
		return lmRentable;
	}

	public ArrayList<Rentable> getRentablePreviews() {
		return rentablePreviews;
	}

	public void setRentablePreviews(ArrayList<Rentable> rentablePreviews) {
		this.rentablePreviews = rentablePreviews;
	}

	public RentableListModel updateRentables(int buildingIndex) throws IOException, SQLException {
		rentablePreviews = getRentablePreviews(buildingPreviews.get(buildingIndex).getId());
		lmRentable.setData(rentablePreviews);
		return lmRentable;
	}

	public int getSelectedBuildingId() {
		return buildingPreviews.get(buildingIndex).getId();
	}

	public int getSelectedRentableId() {
		return rentablePreviews.get(rentableIndex).getId();
	}

	public boolean mouseOver(int index, boolean isBuilding) {
		if (isBuilding) {
			if (index == buildingIndex) {
				return false;
			} else {
				buildingIndex = index;
				return true;
			}
		} else {
			if (index == rentableIndex) {
				return false;
			} else {
				rentableIndex = index;
				return true;
			}
		}
	}

	public boolean fetchAddRemove() {
		try {
			buildingPreviews = DataConnector.selectBuildingPreviews();
			//lmBuilding.setData(buildingPreviews);

			return true;
		} catch (SQLException ex) {
			System.out.println("fetch failed(listener)");
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), Language.getString("errConnectDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
			//TODO add connection string settings

			return false;
		} catch (IOException ex) {
			System.out.println("fetch failed io (listener)");
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errImagesFetchFail") + "\n" + ex.getMessage(), Language.getString("errImagesFetchFailTitle"), JOptionPane.ERROR_MESSAGE);
			//TODO add connection string settings

			return false;
		}
	}
}
