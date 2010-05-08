package data;

import Exceptions.ContractNotValidException;
import Language.Language;
import gui.Main;
import data.entities.Building;
import data.entities.Contract;
import data.entities.Invoice;
import data.entities.InvoiceItem;
import data.entities.Person;
import data.entities.Picture;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import data.entities.Rentable;
import java.util.Vector;

public class DataModel {

	//this class is used to seperate betwean database and gui in case of change
	//and it will also be used to cach certain data (like images and the global list of buildings
	//TODO add caching + options on timeout in settings
	private Integer ownerId;
//	private int lastPictureDialogId;
//	private int lastPictureDialogType;
//	private BuildingListModel lmBuilding;
//	private RentableListModel lmRentable;
//	private PictureListModel lmPicture;
//	private static int buildingIndex;
//	private static int rentableIndex;
//	private static int pictureIndex;

	public DataModel() {
		DataConnector.init();
//		buildingIndex = -1; //none selected
//		rentableIndex = -1; //none selected
//		pictureIndex = -1;  //none selected
//
//		lmBuilding = new BuildingListModel();
//		lmRentable = new RentableListModel();
//		lmPicture = new PictureListModel();

	}

	public Vector<Rentable> getRentablePreviews(int buildingId) throws SQLException, IOException {
		return DataConnector.getRentablesFromBuilding(buildingId);
	}

	public void addDummyPictures() {
		DataConnector.addDummyPictures();
	}

	public Building getBuilding(int buildingId) throws SQLException {
		return DataConnector.getBuilding(buildingId);
	}

	public Vector<Rentable> getRentablesFromBuilding(int buildingId) throws SQLException {
		return DataConnector.getRentablesFromBuilding(buildingId);
	}

	public Rentable getRentable(int rentableId) throws SQLException {
		return DataConnector.getRentable(rentableId);
	}

	public Vector<Rentable> getRentablesFromOwner(int ownerID) {
		return DataConnector.getRentableFromOwner(ownerID);
	}
//
//	public static int getBuildingIndex() {
//		return buildingIndex;
//	}
//
//	public static int getRentableIndex() {
//		return rentableIndex;
//	}
//
//	public static int getPictureIndex() {
//		return pictureIndex;
//	}
//
//	public static void setBuildingIndex(int index) {
//		DataModel.buildingIndex = index;
//	}
//
//	public static void setRentableIndex(int index) {
//		DataModel.rentableIndex = index;
//	}
//
//	public static void setPictureIndex(int index) {
//		DataModel.pictureIndex = index;
//	}
//
//	public BuildingListModel getLmBuilding() {
//		return lmBuilding;
//	}
//
//	public RentableListModel getLmRentable() {
//		return lmRentable;
//	}
//
//	public PictureListModel getLmPicture() {
//		return lmPicture;
//	}

//	public RentableListModel updateRentables(int buildingIndex) throws IOException, SQLException {
//		lmRentable.updateItems(lmBuilding.getId(buildingIndex));
//		return lmRentable;
//	}
	public Vector<Person> getRenters() {
		return DataConnector.getRenters(ownerId);
	}

	public Vector<Invoice> getInvoices(int RenterId) {
		return DataConnector.getInvoices(RenterId);
	}

	public Vector<Contract> getContracts(int ownerID) {
		return DataConnector.getContracts(ownerID);
	}

	public Vector<Contract> getContracts() {
		return DataConnector.getContracts(ownerId);
	}

	public Person getPerson(int id) {
		return DataConnector.getPerson(id);
	}

	public Person getOwner() {

		return DataConnector.getPerson(ownerId);
	}
//
//	public void updatePictures() throws IOException, SQLException {
//		System.out.println("updating pictures");
//		lmPicture.updateItems(lastPictureDialogId, lastPictureDialogType == 0);
//	}
//
//	public PictureListModel updateBuildingPictures(int buildingId) throws IOException, SQLException {
//		lastPictureDialogId = buildingId;
//		lastPictureDialogType = 0; //building  //TODO change ints by enums
//		lmPicture.updateItems(buildingId, true);
//		return lmPicture;
//	}
//
//	public PictureListModel updateRentablePictures(int rentableId) throws IOException, SQLException {
//		lastPictureDialogId = rentableId;
//		lastPictureDialogId = 1; //rentable
//		lmPicture.updateItems(rentableId, false);
//		return lmPicture;
//	}

//	public int getSelectedBuildingId() {
//		return lmBuilding.getElementAt(buildingIndex).getId();
//	}
//
//	public boolean isBuildingSelected() {
//		return buildingIndex != -1;
//	}
//
//	public boolean isRentableSelected() {
//		return rentableIndex != -1;
//	}
//	public int getSelectedRentableId() {
//		return lmRentable.getRentableAt(rentableIndex).getId();
//	}
//
//	public int getSelectedRentableId(int index) {
//		rentableIndex = index;
//		return lmRentable.getRentableAt(rentableIndex).getId();
//	}
//
//	public boolean mouseOver(int index, boolean isBuilding) {
//		if (isBuilding) {
//			if (index == buildingIndex) {
//				return false;
//			} else {
//				buildingIndex = index;
//				return true;
//			}
//		} else {
//			if (index == rentableIndex) {
//				return false;
//			} else {
//				rentableIndex = index;
//				return true;
//			}
//		}
//	}
//
//	public boolean fetchAddRemove() {
//		try {
//			lmBuilding.updateItems();
//			return true;
//		} catch (SQLException ex) {
//			SplashConnect.hideSplash();
//			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//			//TODO add connection string settings
//
//			return false;
//		} catch (IOException ex) {
//			SplashConnect.hideSplash();
//			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errImagesFetchFail") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//			//TODO add connection string settings
//
//			return false;
//		}
//	}
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int userId) {
		this.ownerId = userId;
	}

	public boolean checkLogin(String username, String password) {
		try {
			ownerId = DataConnector.checkLogin(username, password);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "login attempt failed: \n" + ex.getMessage(), "login fail", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (ownerId != null) {
			ProgramSettings.setUserID(ownerId);
		}
		return ownerId != null;
	}

	public boolean isLoggedIn() {
		return ownerId != null;
	}

	public void addBuildingImage(int buildingId, BufferedImage img) throws SQLException, IOException {
		DataConnector.addBuildingPicture(buildingId, img);
	}

	public void addRentableImage(int rentableId, BufferedImage img) throws SQLException, IOException {
		DataConnector.addRentablePicture(rentableId, img);
	}

	public void deleteSelectedPictures(Vector<Integer> ids) throws SQLException {
		for (Integer id : ids) {
			DataConnector.removePicture(id); //TODO more efficient in dataconnector?
		}
	}


	public void deleteSelectedBuildings(Vector<Integer> ids) {
		boolean continueWithouthConfirm = false;
		for (Integer id : ids) {
			try {
				//delete building
				DataConnector.deleteBuilding(id);
			} catch (Exception ex) {//TODO 010 fix this exception use > check if there are any rentables in building yourself
				System.out.println("exception during deletion building: \n" + ex.getMessage());
				if (!continueWithouthConfirm) {
					Object[] options = {"Always", "Once", "Abort"};
					int choise = JOptionPane.showOptionDialog(
							Main.getInstance(),
							"One of the selected buildings still contain rentables. \nContinue with deletion?",
							Language.getString("confirm"),
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[2]);
					if (choise == 0) {
						continueWithouthConfirm = true;
					} else if (choise == 2) {
						return; //abort delete
					}
				}
				try {
					//first deleting rentables
					for(Rentable rentable : DataConnector.getRentablesFromBuilding(id)){
						DataConnector.deleteRentable(rentable.getId());
					}
					//deleting building (should be empty now
					DataConnector.deleteBuilding(id);
				} catch (SQLException ex1) {
					JOptionPane.showMessageDialog(Main.getInstance(), "Failed to delete building: \n" + ex1.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public void addBuildingPreviewPicture(int id, BufferedImage img) throws SQLException {
		DataConnector.addBuildingPreviewPicture(id, img);
	}

	public void addRentablePreviewPicture(int id, BufferedImage img) throws SQLException {
		DataConnector.addRentablePreviewPicture(id, img);
	}

	public void addBuilding(String street, String streetNumber, String zip, String city, String country) throws SQLException {
		DataConnector.addBuilding(street, streetNumber, zip, city, country);
	}

	public void updateBuilding(int id, String street, String streetNumber, String zip, String city, String country) throws SQLException {
		DataConnector.updateBuilding(id, street, streetNumber, zip, city, country);
	}

	public Vector<Integer> getFloors(int buildingId) throws SQLException {
		return DataConnector.getFloors(buildingId);
	}

	public Vector<InvoiceItem> getInvoiceItems(int renterId, boolean newInvoice, boolean utilities, boolean guarantee, int months) throws ContractNotValidException {
		Vector<InvoiceItem> items = new Vector<InvoiceItem>();

		//monthly cost
		try {
			items.add(new InvoiceItem(Language.getString("invoiceMonthPrice"), DataConnector.getRentPriceOrGuarantee(renterId, false) * months));
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "error while getting month price: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}

		//utilities cost
		if (utilities) {
			try {
				DataConnector.getUtilitiesInvoiceItems(renterId, items);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(Main.getInstance(), "error while getting utilities: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		}

		//guarantee
		if (guarantee) {
			System.out.println("getting garantee");
			try {
				items.add(new InvoiceItem(Language.getString("invoiceGuarantee"), DataConnector.getRentPriceOrGuarantee(renterId, true)));
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(Main.getInstance(), "error while getting guarantee: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		return items;
	}

	public void addInvoice(int renterId, Date sendDate, String xmlString) {
		try {
			DataConnector.insertInvoice(renterId, sendDate, xmlString);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to store invoice in database: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public Vector<Building> getBuildingPreviews() throws SQLException, IOException {
		return DataConnector.selectBuildingPreviews();
	}

	public void deleteSelectedRentables(Vector<Integer> selected) throws SQLException {
		for (Integer id : selected) {
			DataConnector.deleteRentable(id);
		}
	}

	public Vector<Picture> getPicturesFromBuilding(int buildingId) throws SQLException {
		return DataConnector.getBuildingPictures(buildingId);
	}

	public Vector<Picture> getPicturesFromRentable(int rentableId) throws SQLException {
		return DataConnector.getRentablePictures(rentableId);
	}
}
