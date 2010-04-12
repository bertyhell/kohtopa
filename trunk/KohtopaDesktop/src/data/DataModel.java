package data;

import data.addremove.BuildingListModel;
import data.addremove.RentableListModel;
import Language.Language;
import data.addremove.PictureListModel;
import gui.Main;
import data.entities.Building;
import data.entities.Picture;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import data.entities.Rentable;
import gui.SplashConnect;

public class DataModel {

	//this class is used to seperate betwean database and gui in case of change
	//and it will also be used to cach certain data (like images and the global list of buildings
	//TODO add caching + options on timeout in settings
	private Integer userId;
	private int lastPictureDialogId;
	private int lastPictureDialogType;
	private BuildingListModel lmBuilding;
	private RentableListModel lmRentable;
	private PictureListModel lmPicture;
	private static int buildingIndex;
	private static int rentableIndex;
	private static int pictureIndex;

	public DataModel() {
		buildingIndex = -1; //none selected
		rentableIndex = -1; //none selected
		pictureIndex = -1;  //none selected

		lmBuilding = new BuildingListModel();
		lmRentable = new RentableListModel();
		lmPicture = new PictureListModel();
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

	public static int getRentableIndex() {
		return rentableIndex;
	}

	public static int getPictureIndex() {
		return pictureIndex;
	}

	public static void setBuildingIndex(int buildingIndex) {
		DataModel.buildingIndex = buildingIndex;
	}

	public static void setRentableIndex(int index) {
		DataModel.rentableIndex = index;
	}

	public static void setPictureIndex(int pictureIndex) {
		DataModel.pictureIndex = pictureIndex;
	}

	public BuildingListModel getLmBuilding() {
		return lmBuilding;
	}

	public RentableListModel getLmRentable() {
		return lmRentable;
	}

	public PictureListModel getLmPicture() {
		return lmPicture;
	}

	public RentableListModel updateRentables(int buildingIndex) throws IOException, SQLException {
		lmRentable.updateItems(lmBuilding.getId(buildingIndex));
		return lmRentable;
	}

	public void updatePictures() throws IOException, SQLException {
		System.out.println("updating pictures");
		lmPicture.updateItems(lastPictureDialogId, lastPictureDialogType == 0);
	}

	public PictureListModel updateBuildingPictures(int buildingId) throws IOException, SQLException {
		lastPictureDialogId = buildingId;
		lastPictureDialogType = 0; //building  //TODO change ints by enums
		lmPicture.updateItems(buildingId, true);
		return lmPicture;
	}

	public PictureListModel updateRentablePictures(int rentableId) throws IOException, SQLException {
		lastPictureDialogId = rentableId;
		lastPictureDialogId = 1; //rentable
		lmPicture.updateItems(rentableId, false);
		return lmPicture;
	}

	public int getSelectedBuildingId() {
		return lmBuilding.getElementAt(buildingIndex).getId();
	}

	public int getSelectedRentableId() {
		return lmRentable.getRentableAt(rentableIndex).getId();
	}

	public int getSelectedRentableId(int index) {
		rentableIndex = index;
		return lmRentable.getRentableAt(rentableIndex).getId();
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
			lmBuilding.updateItems();
			return true;
		} catch (SQLException ex) {
			SplashConnect.hideSplash();
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			//TODO add connection string settings

			return false;
		} catch (IOException ex) {
			SplashConnect.hideSplash();
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errImagesFetchFail") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			//TODO add connection string settings

			return false;
		}
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean checkLogin(String username, String password) {
		try{
		userId = DataConnector.checkLogin(username, password);
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(Main.getInstance(), "login attempt failed: \n" + ex.getMessage(), "login fail", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return userId != null;
	}

	public boolean isLoggedIn() {
		return userId != null;
	}

	public void addImage(int id, String type, BufferedImage img) throws SQLException, IOException {
		if (type.equals("BuildingDialog")) {
			DataConnector.addBuildingPicture(id, img);
		} else {
			DataConnector.addRentablePicture(id, img);
		}
		//lists verwittigen
		lmPicture.addElement(new Picture(id, img));
		Main.updatePictureLists();//TODO observer pattern toepassen
	}

	public void deleteSelectedPictures(int[] indexs) throws SQLException {
		lmPicture.removeSelectedPictures(indexs);
	}

	public void addBuildingPreviewPicture(int id, int index) throws SQLException {
		DataConnector.addBuildingPreviewPicture(id, lmPicture.getElementAt(index).getPicture());
	}

	public void addRentablePreviewPicture(int id, int index) throws SQLException {
		DataConnector.addRentablePreviewPicture(id, lmPicture.getElementAt(index).getPicture());
	}

	public void addBuilding(String street, String streetNumber, String zip, String city) throws SQLException {
		DataConnector.addBuilding(street, streetNumber, zip, city);
	}

	public void updateBuilding(int id,String street, String streetNumber, String zip, String city) throws SQLException {
		DataConnector.updateBuilding(id, street, streetNumber, zip, city);
	}

	public ArrayList<Integer> getFloors(){
		return lmRentable.getFloors();
	}
}