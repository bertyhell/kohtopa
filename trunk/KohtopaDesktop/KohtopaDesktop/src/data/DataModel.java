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
import javax.swing.JOptionPane;
import data.entities.Rentable;
import java.util.Vector;

public class DataModel {

	//this class is used to seperate betwean database and gui in case of change
	//and it will also be used to cach certain data (like images and the global list of buildings
	//TODO add caching + options on timeout in settings
	private Integer ownerId;

	public DataModel() {
		DataConnector.init();
		Main.logger.info("initializing DataModel");
	}

	public Vector<Rentable> getRentablePreviews(int buildingId) throws SQLException, IOException {
		Main.logger.info("getting Rentable Previews");
		return DataConnector.getRentablesFromBuilding(buildingId);
	}

	public void addDummyPictures() {
		Main.logger.info("adding Dummy Pictures");
		DataConnector.addDummyPictures();
	}

	public Building getBuilding(int buildingId) throws SQLException {
		Main.logger.info("getting Building by id");
		return DataConnector.getBuilding(buildingId);
	}

	public Vector<Rentable> getRentablesFromBuilding(int buildingId) throws SQLException {
		Main.logger.info("getting Rentables by BuildingId");
		return DataConnector.getRentablesFromBuilding(buildingId);
	}

	public Rentable getRentable(int rentableId) throws SQLException {
		Main.logger.info("getting Rentable by id");
		return DataConnector.getRentable(rentableId);
	}

	public Vector<Rentable> getRentablesFromOwner(int ownerID) {
		Main.logger.info("getting Rentables From Owner");
		return DataConnector.getRentableFromOwner(ownerID);
	}

	public Vector<Person> getRenters() {
		Main.logger.info("getting Renters");
		return DataConnector.getRenters(ownerId);
	}

	public Vector<Invoice> getInvoices(int RenterId) {
		Main.logger.info("getting Invoices");
		return DataConnector.getInvoices(RenterId);
	}

	public Vector<Contract> getContracts() {
		Main.logger.info("getting contracts");
		return DataConnector.getContracts(ownerId);
	}

	public Person getPerson(int id) {
		Main.logger.info("getting person by id");
		return DataConnector.getPerson(id);
	}

	public Person getOwner() {
		Main.logger.info("getting owner");
		return DataConnector.getPerson(ownerId);
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int userId) {
		Main.logger.info("setting OwnerId");
		this.ownerId = userId;
	}

	public boolean checkLogin(String username, String password) {
		Main.logger.info("checking login");
		try {
			ownerId = DataConnector.checkLogin(username, password);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "login attempt failed: \n" + ex.getMessage(), "login fail", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (ownerId != null) {
			ProgramSettings.setOwnerID(ownerId);
		}
		return ownerId != null;
	}

	public boolean isLoggedIn() {
		return ownerId != null;
	}

	public void addBuildingImage(int buildingId, BufferedImage img) throws SQLException, IOException {
		Main.logger.info("add Building Image");
		DataConnector.addBuildingPicture(buildingId, img);
	}

	public void addRentableImage(int rentableId, BufferedImage img) throws SQLException, IOException {
		Main.logger.info("adding Rentable Image");
		DataConnector.addRentablePicture(rentableId, img);
	}

	public void deleteSelectedPictures(Vector<Integer> ids) throws SQLException {
		Main.logger.info("deleting Selected Pictures");
		for (Integer id : ids) {
			DataConnector.removePicture(id); //TODO more efficient in dataconnector?
		}
	}

	public void deleteSelectedBuildings(Vector<Integer> ids) {
		Main.logger.info("deleting Selected Buildings");
		boolean continueWithouthConfirm = false;
		for (Integer id : ids) {
			try {
				//delete building
				DataConnector.deleteBuilding(id);
			} catch (Exception ex) {//TODO 010 fix this exception use > check if there are any rentables in building yourself
				Main.logger.info("expected exception during deletion building (contains rentables): \n" + ex.getMessage());
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
					for (Rentable rentable : DataConnector.getRentablesFromBuilding(id)) {
						DataConnector.deleteRentable(rentable.getId());
					}
					//deleting building (should be empty now
					DataConnector.deleteBuilding(id);
				} catch (SQLException ex1) {
					Main.logger.error("Failed to delete building: \n" + ex1.getMessage());
					JOptionPane.showMessageDialog(Main.getInstance(), "Failed to delete building: \n" + ex1.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public void addBuildingPreviewPicture(int id, BufferedImage img) throws SQLException {
		Main.logger.info("adding Building PreviewPicture");
		DataConnector.addBuildingPreviewPicture(id, img);
	}

	public void addRentablePreviewPicture(int id, BufferedImage img) throws SQLException {
		
			Main.logger.info("adding Rentable PreviewPicture");
		DataConnector.addRentablePreviewPicture(id, img);
	}

	public void addBuilding(String street, String streetNumber, String zip, String city, String country) throws SQLException {
		
			Main.logger.info("add building");
		DataConnector.addBuilding(street, streetNumber, zip, city, country);
	}

	public void updateBuilding(int id, String street, String streetNumber, String zip, String city, String country) throws SQLException {
		
			Main.logger.info("update building");
		DataConnector.updateBuilding(id, street, streetNumber, zip, city, country);
	}

	public Vector<Integer> getFloors(int buildingId) throws SQLException {
			Main.logger.info("getting floors");
		return DataConnector.getFloors(buildingId);
	}

	public Vector<InvoiceItem> getInvoiceItems(int renterId, boolean newInvoice, boolean utilities, boolean guarantee, int months) throws ContractNotValidException {
		
		Main.logger.info("getInvoiceItems");
		Vector<InvoiceItem> items = new Vector<InvoiceItem>();

		//monthly cost
		try {
			items.add(new InvoiceItem(Language.getString("invoiceMonthPrice"), DataConnector.getRentPriceOrGuarantee(renterId, false) * months));
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "error while getting month price: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}

		//utilities cost
		if (utilities) {
			Main.logger.info("getting utilities");
			try {
				DataConnector.getUtilitiesInvoiceItems(renterId, items);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(Main.getInstance(), "error while getting utilities: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		}

		//guarantee
		if (guarantee) {
			Main.logger.info("getting garantee");
			try {
				items.add(new InvoiceItem(Language.getString("invoiceGuarantee"), DataConnector.getRentPriceOrGuarantee(renterId, true)));
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(Main.getInstance(), "error while getting guarantee: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		return items;
	}

	public void addInvoice(int renterId, Date sendDate, String xmlString) {
		Main.logger.info("addInvoice");
		try {
			DataConnector.insertInvoice(renterId, sendDate, xmlString);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to store invoice in database: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public Vector<Building> getBuildingPreviews() throws SQLException, IOException {
		Main.logger.info("getBuildingPreviews");
		return DataConnector.selectBuildingPreviews();
	}

	public void deleteSelectedRentables(Vector<Integer> selected) throws SQLException {
		Main.logger.info("deleteSelectedRentables");
		for (Integer id : selected) {
			DataConnector.deleteRentable(id);
		}
	}

	public Vector<Picture> getPicturesFromBuilding(int buildingId) throws SQLException {
		Main.logger.info("getPicturesFromBuilding");
		return DataConnector.getBuildingPictures(buildingId);
	}

	public Vector<Picture> getPicturesFromRentable(int rentableId) throws SQLException {
		Main.logger.info("getPicturesFromRentable");
		return DataConnector.getRentablePictures(rentableId);
	}
}
