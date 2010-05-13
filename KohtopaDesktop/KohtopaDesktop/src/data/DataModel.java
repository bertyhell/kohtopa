package data;

import Exceptions.ContractNotValidException;
import Language.Language;
import gui.Logger;
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
import gui.Main;
import java.util.Vector;

public class DataModel {

	//this class is used to seperate betwean database and gui in case of change
	//and it will also be used to cach certain data (like images and the global list of buildings
	//TODO add caching + options on timeout in settings
	private Integer ownerId;

	public DataModel() {
		Logger.logger.info("initializing DataModel");
	}

	public Vector<Rentable> getRentablePreviews(int buildingId) throws SQLException, IOException {
		Logger.logger.info("getting Rentable Previews");
		return DataConnector.getRentablesFromBuilding(buildingId);
	}

	public void addDummyPictures() {
		Logger.logger.info("adding Dummy Pictures");
		DataConnector.addDummyPictures();
	}

	public Building getBuilding(int buildingId) throws SQLException {
		Logger.logger.info("getting Building by id");
		return DataConnector.getBuilding(buildingId);
	}

	public Vector<Rentable> getRentablesFromBuilding(int buildingId) throws SQLException {
		Logger.logger.info("getting Rentables by BuildingId");
		return DataConnector.getRentablesFromBuilding(buildingId);
	}

	public Rentable getRentable(int rentableId) throws SQLException {
		Logger.logger.info("getting Rentable by id");
		return DataConnector.getRentable(rentableId);
	}

	public Vector<Rentable> getRentablesFromOwner() {
		Logger.logger.info("getting Rentables From Owner");
		return DataConnector.getRentableFromOwner();
	}

	public Vector<Person> getRenters() {
		Logger.logger.info("getting Renters");
		return DataConnector.getRenters();
	}

	public Vector<Invoice> getInvoices(int RenterId) {
		Logger.logger.info("getting Invoices");
		return DataConnector.getInvoices(RenterId);
	}

	public Vector<Contract> getPreviewContractsFromRenter(int renterId) {
		Logger.logger.info("getting Preview contracts from renters");
		return DataConnector.getPreviewContractsFromRenter(renterId);
	}

	public Contract getContract( int contractId) {
		Logger.logger.info("getting contract with id: " + contractId);
		return DataConnector.getContract(contractId);
	}

	public Vector<Contract> getContracts() {
		Logger.logger.info("getting contracts");
		return DataConnector.getContracts();
	}

	public Person getPerson(int id) {
		Logger.logger.info("getting person by id");
		return DataConnector.getPerson(id);
	}

	public Person getOwner() {
		Logger.logger.info("getting owner");
		return DataConnector.getPerson(ProgramSettings.getOwnerId());
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int userId) {
		Logger.logger.info("setting OwnerId");
		this.ownerId = userId;
	}

	public boolean isLoggedIn() {
		return ownerId != null;
	}

	public void addBuildingImage(int buildingId, BufferedImage img) throws SQLException, IOException {
		Logger.logger.info("add Building Image");
		DataConnector.addBuildingPicture(buildingId, img);
	}

	public void addRentableImage(int rentableId, BufferedImage img) throws SQLException, IOException {
		Logger.logger.info("adding Rentable Image");
		DataConnector.addRentablePicture(rentableId, img);
	}

	public void deleteSelectedPictures(Vector<Integer> ids) throws SQLException {
		Logger.logger.info("deleting Selected Pictures");
		for (Integer id : ids) {
			Logger.logger.info("\t" + id);
			DataConnector.removePicture(id); //TODO more efficient in dataconnector?
		}
	}

	public void deleteSelectedBuildings(Vector<Integer> ids) {
		Logger.logger.info("deleting Selected Buildings");
		boolean continueWithouthConfirm = false;
		for (Integer id : ids) {
			try {
				//delete building
				DataConnector.deleteBuilding(id);
			} catch (Exception ex) {//TODO 010 fix this exception use > check if there are any rentables in building yourself
				Logger.logger.info("expected exception during deletion building (contains rentables): \n" + ex.getMessage());
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
					Logger.logger.error("Failed to delete building: \n" + ex1.getMessage());
					JOptionPane.showMessageDialog(Main.getInstance(), "Failed to delete building: \n" + ex1.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public void addBuildingPreviewPicture(int id, BufferedImage img) throws SQLException {
		Logger.logger.info("adding Building PreviewPicture");
		DataConnector.addBuildingPreviewPicture(id, img);
	}

	public void addRentablePreviewPicture(int id, BufferedImage img) throws SQLException {

		Logger.logger.info("adding Rentable PreviewPicture");
		DataConnector.addRentablePreviewPicture(id, img);
	}

	public void addBuilding(String street, String streetNumber, String zip, String city, String country) throws SQLException {

		Logger.logger.info("add building");
		DataConnector.addBuilding(street, streetNumber, zip, city, country);
	}

	public void updateBuilding(int id, String street, String streetNumber, String zip, String city, String country) throws SQLException {

		Logger.logger.info("update building");
		DataConnector.updateBuilding(id, street, streetNumber, zip, city, country);
	}

	public Vector<Integer> getFloors(int buildingId) throws SQLException {
		Logger.logger.info("getting floors");
		return DataConnector.getFloors(buildingId);
	}

	public Vector<InvoiceItem> getInvoiceItems(int renterId, boolean newInvoice, boolean utilities, boolean guarantee, int months) throws ContractNotValidException {

		Logger.logger.info("getInvoiceItems");
		Vector<InvoiceItem> items = new Vector<InvoiceItem>();

		//monthly cost
		try {
			items.add(new InvoiceItem(Language.getString("invoiceMonthPrice"), DataConnector.getRentPriceOrGuarantee(renterId, false) * months));
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "error while getting month price: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}

		//utilities cost
		if (utilities) {
			Logger.logger.info("getting utilities");
			try {
				DataConnector.getUtilitiesInvoiceItems(renterId, items);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(Main.getInstance(), "error while getting utilities: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		}

		//guarantee
		if (guarantee) {
			Logger.logger.info("getting garantee");
			try {
				items.add(new InvoiceItem(Language.getString("invoiceGuarantee"), DataConnector.getRentPriceOrGuarantee(renterId, true)));
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(Main.getInstance(), "error while getting guarantee: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		return items;
	}

	public void addInvoice(int renterId, Date sendDate, String xmlString) {
		Logger.logger.info("addInvoice");
		try {
			DataConnector.insertInvoice(renterId, sendDate, xmlString);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to store invoice in database: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public Vector<Building> getBuildingPreviews() throws SQLException, IOException {
		Logger.logger.info("getBuildingPreviews");
		return DataConnector.selectBuildingPreviews();
	}

	public void deleteSelectedRentables(Vector<Integer> selected) throws SQLException {
		Logger.logger.info("deleteSelectedRentables");
		for (Integer id : selected) {
			DataConnector.deleteRentable(id);
		}
	}

	public Vector<Picture> getPicturesFromBuilding(int buildingId) throws SQLException {
		Logger.logger.info("getPicturesFromBuilding");
		return DataConnector.getBuildingPictures(buildingId);
	}

	public Vector<Picture> getPicturesFromRentable(int rentableId) throws SQLException {
		Logger.logger.info("getPicturesFromRentable");
		return DataConnector.getRentablePictures(rentableId);
	}
}
