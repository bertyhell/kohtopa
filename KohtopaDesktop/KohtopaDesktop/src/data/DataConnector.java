package data;

import Exceptions.ContractNotValidException;
import Exceptions.PersonNotFoundException;
import Language.Language;
import data.entities.Building;
import data.entities.Invoice;
import gui.Main;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import data.entities.Message;
import data.entities.Person;
import data.entities.Picture;
import data.entities.Rentable;
import data.entities.Task;
import data.entities.Contract;
import data.entities.InvoiceItem;
import gui.calendartab.CalendarModel;
import java.util.Date;
import java.util.HashMap;

/**
 * this class gets/puts data from/to database + caches information (rentables, buildings).
 */
public class DataConnector {

	//TODO add aspectJ for dataconnection to avoid duplicate code

	/**
	 * initiazlize, registers and starts the JDBC oracle-driver
	 */
	public static void init() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Class.forName(DataBaseConstants.driver);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (ClassNotFoundException ex) {
			System.out.println("failed to open driver: \n" + ex.getMessage());
		}
	}

	/**
	 * Makes a connection
	 * @return the connection
	 * @throws SQLException thrown if something goes wrong creating the connection
	 */
	private static Connection geefVerbinding() throws SQLException {
		return DriverManager.getConnection(DataBaseConstants.connectiestring, DataBaseConstants.un, DataBaseConstants.pw);
	}
	
	/**
	 * Makes a connection
	 * @return the connection
	 * @throws SQLException thrown if something goes wrong creating the connection
	 */
	private static Connection geefVerbindingOwner() throws SQLException {
		return DriverManager.getConnection(DataBaseConstants.connectiestring, ProgramSettings.getUsername(), ProgramSettings.getPassword());
	}

	/**
	 * Fetches buildingPreviews from the database
	 * @return an ArrayList of buildings
	 * @throws SQLException thrown if something goes wrong with the select statements
	 * @throws IOException thrown if there is a problem fetching the images
	 */
	public static ArrayList<Building> selectBuildingPreviews() throws SQLException, IOException {
		ArrayList<Building> buildings = new ArrayList<Building>();
		Connection conn = geefVerbindingOwner();
		try {
			Statement selectBuildings = conn.createStatement();
			ResultSet rsBuildings = selectBuildings.executeQuery(DataBaseConstants.selectBuildingPreviews);
			while (rsBuildings.next()) {
				ImageIcon img = null;
				byte[] imgData = rsBuildings.getBytes(DataBaseConstants.pictureData);
				if (imgData != null) {
					img = new ImageIcon(ImageIO.read(new ByteArrayInputStream(imgData)));
				}
				buildings.add(new Building(
						rsBuildings.getInt(DataBaseConstants.buildingID),
						img,
						rsBuildings.getString(DataBaseConstants.street),
						rsBuildings.getString(DataBaseConstants.streetNumber),
						rsBuildings.getString(DataBaseConstants.zipCode),
						rsBuildings.getString(DataBaseConstants.city),
						rsBuildings.getString(DataBaseConstants.country),
						rsBuildings.getDouble(DataBaseConstants.latitude),
						rsBuildings.getDouble(DataBaseConstants.longitude)));
			}
		} finally {
			conn.close();
		}
		return buildings;
	}

//	/**
//	 * Fetches buildingPreviews from the database
//	 * @return an ArrayList of buildings
//	 * @throws SQLException thrown if something goes wrong with the select statements
//	 * @throws IOException thrown if there is a problem fetching the images
//	 */
//	public static ArrayList<Building> selectBuildingPreviews() throws SQLException, IOException {
//		ArrayList<Building> buildings = new ArrayList<Building>();
//		Connection conn = geefVerbinding();
//		try {
//			Statement selectBuildings = conn.createStatement();
//			ResultSet rsBuildings = selectBuildings.executeQuery(DataBaseConstants.selectBuildingPreviews);
//			while (rsBuildings.next()) {
//				ImageIcon img = null;
//				byte[] imgData = rsBuildings.getBytes(DataBaseConstants.pictureData);
//				if (imgData != null) {
//					img = new ImageIcon(ImageIO.read(new ByteArrayInputStream(imgData)));
//				}
//				buildings.add(new Building(
//						rsBuildings.getInt(DataBaseConstants.buildingID),
//						img,
//						rsBuildings.getString(DataBaseConstants.street),
//						rsBuildings.getString(DataBaseConstants.streetNumber),
//						rsBuildings.getString(DataBaseConstants.zipCode),
//						rsBuildings.getString(DataBaseConstants.city),
//						rsBuildings.getString(DataBaseConstants.country),
//						rsBuildings.getDouble(DataBaseConstants.latitude),
//						rsBuildings.getDouble(DataBaseConstants.longitude)));
//			}
//		} finally {
//			conn.close();
//		}
//		return buildings;
//	}
	/**
	 * Fetches renter previews from the database
	 * @return an ArrayList of renters
	 * @throws SQLException thrown if something goes wrong with the select statements
	 * @throws IOException thrown if there is a problem fetching the images
	 */
	public static Vector<Person> selectRenterPreviews(int ownerId) throws SQLException, IOException {
		Vector<Person> renters = new Vector<Person>();
		Connection conn = geefVerbindingOwner();
		try {
			PreparedStatement psSelectRenters = conn.prepareStatement(DataBaseConstants.selectRenterPreviews);
			psSelectRenters.setInt(1, ownerId);
			ResultSet rsRenters = psSelectRenters.executeQuery();
			while (rsRenters.next()) {
				renters.add(new Person(
						rsRenters.getInt(DataBaseConstants.personID),
						null,
						rsRenters.getString(DataBaseConstants.firstName),
						rsRenters.getString(DataBaseConstants.personName)));
			}
		} finally {
			conn.close();
		}
		return renters;
	}

	/**
	 * Returns an optimal login, having the best combination of username/password length,
	 * rentables owned, messages received and contracts owned.
	 * @return a string containing the username and password of the optimal login
	 * returns "no owners found" if there are no usefull entries in the database,
	 * if this is the case, please check your connection or database version
	 */
	public static String getOptimalLogin() {
		try {
			Connection conn = geefVerbinding();
			try {
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(
						"select distinct p.personid,max(p.username),max(p.password),count(1) ,count(1)/length(max(p.password) || max(p.username)) "
						+ "from messages m "
						+ "join persons p on m.recipientid = p.personid "
						+ "join rentables r on r.ownerid = p.personid "
						+ "join contracts c on c.rentableid = r.rentableid "
						+ "group by p.personid "
						+ "order by 5 desc");
				if (rs.next()) {
					return "Username: " + rs.getString(2) + " , Password: " + rs.getString(3);
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return "no owners found";

	}

	/**
	 * Updates the latitude and longitude of a building in the database
	 * @param b the building containing the new data
	 * @throws SQLException thrown if something goes wrong with the update command
	 */
	public static void updateBuildings(Building b) throws SQLException {
		try {
			Connection conn = geefVerbinding();
			try {
				Statement s = conn.createStatement();
				if (b.getLatitude() != 0) {
					s.executeUpdate("update buildings"
							+ " set latitude = " + b.getLatitude()
							+ " , longitude = " + b.getLongitude()
							+ " where buildingid = " + b.getId());
				}
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			throw new SQLException("update buildings"
					+ " set latitude = " + b.getLatitude()
					+ " set longitude = " + b.getLongitude()
					+ " where buildingid = " + b.getId() + "/" + ex.getMessage());
		}
	}

	/**
	 * Adds a building to the database
	 * @param street the street of the building
	 * @param streetNumber the streetnumber of the building
	 * @param zip zip code of the city of the buidling
	 * @param city the city of the building
	 * @throws SQLException thrown if insert fails
	 */
	static void addBuilding(String street, String streetNumber, String zip, String city, String country) throws SQLException {
		Connection conn = geefVerbindingOwner();
		try {
			Integer addressID = addAddress(conn, street, streetNumber, zip, city, country);
			//adding building with correct address id
			PreparedStatement psAddBuilding = conn.prepareStatement(DataBaseConstants.insertBuilding);
			if(addressID == null) System.out.println("addressid is null *********************");
			psAddBuilding.setInt(1, addressID);
			psAddBuilding.execute();
			psAddBuilding.close();
		} catch (SQLException ex) {
			throw new SQLException("error in addBuilding: " + ex);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(Main.getInstance(), "error in addbuilding: unknown error \n" + ex.getMessage(), "Failed to store image", JOptionPane.ERROR_MESSAGE);
		} finally {
			conn.close();
		}
	}

	/**
	 * Adds an address tot the database
	 * @param conn the connection to use
	 * @param street the street
	 * @param streetNumber the streetnumber
	 * @param zip zipcode of the address
	 * @param city city of the address
	 * @return the id of the address that is used in the database
	 * @throws SQLException thrown if there is a problem in the commands used
	 */
	static Integer addAddress(Connection conn, String street, String streetNumber, String zip, String city, String country) throws SQLException {

		Integer addressID = getAddressID(conn, street, streetNumber, zip, city, country, true);
		try {
			if (addressID == null) {
				System.out.println("id is null");
				//address does not exists > make it and then get id
				PreparedStatement psAddAddress = conn.prepareStatement(DataBaseConstants.addAddress);
				psAddAddress.setString(1, streetNumber);
				psAddAddress.setString(2, street);
				psAddAddress.setString(3, zip);
				psAddAddress.setString(4, city);
				psAddAddress.setString(5, country);
				psAddAddress.execute();
				addressID = getAddressID(conn, street, streetNumber, zip, city, country, false);
				if(addressID == null) System.out.println("addressid is null *********************");
			}
		} catch (SQLException ex) {
			System.out.println("sql exception in addadress: " + ex.getMessage());
			throw new SQLException(ex.getMessage());
		}
		return addressID;
	}

	/**
	 * Getter for the id of an address
	 * @param conn the connection to use
	 * @param street the street of the address
	 * @param streetNumber the streetnumber of the address
	 * @param zip the zipcode
	 * @param city the city of the address
	 * @return the ID used in the database
	 * @throws SQLException thrown if the select statement fails
	 */
	private static Integer getAddressID(Connection conn, String street, String streetNumber, String zip, String city, String country, boolean connected) throws SQLException {
		Integer id = null;
		try {
			PreparedStatement psCheckAddress = null;
			if(connected){
				psCheckAddress = conn.prepareStatement(DataBaseConstants.checkAddressConnected);
			}else{
				psCheckAddress = conn.prepareStatement(DataBaseConstants.checkAddressNotConnected);
			}
			System.out.println("command: " + DataBaseConstants.checkAddressNotConnected);
			psCheckAddress.setString(1, street);
			psCheckAddress.setString(2, streetNumber);
			psCheckAddress.setString(3, zip);
			psCheckAddress.setString(4, city);
			psCheckAddress.setString(5, country);
			ResultSet rsCheck = psCheckAddress.executeQuery();
			if (rsCheck.next()) {
				//address already exists
				id = rsCheck.getInt(DataBaseConstants.addressID);
				System.out.println("id: " + id);
			}else{
				System.out.println("address nog niet gevonden");
			}
			rsCheck.close();
			psCheckAddress.close();
		} catch (SQLException ex) {
			System.out.println("sql exception in check address: " + ex.getMessage());
			throw new SQLException(ex.getMessage());
		}
		return id;
	}

	/**
	 * Updates a building in the database
	 * @param id the id of the building
	 * @param street the new street of the building
	 * @param streetNumber the new streetnumber of the building
	 * @param zip the new zipcode of the building
	 * @param city the new city of the building
	 * @throws SQLException thrown when the update fails
	 */
	public static void updateBuilding(int id, String street, String streetNumber, String zip, String city, String country) throws SQLException {
		Connection conn = geefVerbinding();
		try {
			//TODO: adding new address with new data (old address: has to be cleaned up by garbagecollect if it is not used anymore)
			Integer addressID = addAddress(conn, street, streetNumber, zip, city, country);
			//adding building with correct address id
			PreparedStatement psAddBuilding = conn.prepareStatement(DataBaseConstants.updateBuilding);
			psAddBuilding.setInt(1, addressID);
			psAddBuilding.setInt(2, id);
			psAddBuilding.execute();
			psAddBuilding.close();
		} catch (SQLException ex) {
			throw new SQLException("error in updateBuilding, ***: " + ex);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(Main.getInstance(), "error in addPicture: encoding bufferedImage failed \n" + ex.getMessage(), "Failed to store image", JOptionPane.ERROR_MESSAGE);
		} finally {
			conn.close();
		}
	}

	/**
	 * Adds picture to the database
	 * @param id the id
	 * @param bufferedImage the image to add
	 * @throws SQLException thrown if insert fails
	 */
	public static void addRentablePicture(int id, BufferedImage bufferedImage) throws SQLException {
		addFloorPlan(id, bufferedImage, -1);
	}

	/**
	 * Adds a rentablePreviewPicture to the database
	 * @param id the id
	 * @param img the image to add
	 * @throws SQLException thrown if insert fails
	 */
	public static void addRentablePreviewPicture(int id, BufferedImage img) throws SQLException {
		addFloorPlan(id, img, -2);
	}

	/**
	 * Adds a building picture to the database
	 * @param id the id
	 * @param img the image to add
	 * @throws SQLException thrown if insert fails
	 */
	public static void addBuildingPicture(int id, BufferedImage img) throws SQLException {
		addFloorPlan(id, img, -3);
	}

	/**
	 * Adds a building preview image to the database
	 * @param id the id
	 * @param img the image to add
	 * @throws SQLException thrown if insert fails
	 */
	public static void addBuildingPreviewPicture(int id, BufferedImage img) throws SQLException {
		addFloorPlan(id, img, -4);
	}

	/**
	 * Adds a floorplan to the database
	 * @param id the id
	 * @param img the image containing the floorplan
	 * @param floor the floor the plan belongs to
	 * @throws SQLException thrown if insert fails
	 */
	public static void addFloorPlan(int id, BufferedImage img, int floor) throws SQLException {
		Connection conn = geefVerbindingOwner();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "jpg", baos);
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.insertPicture);
			ps.setInt(1, id);
			ps.setInt(2, floor);
			ps.setBytes(3, baos.toByteArray());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			throw new SQLException("error in addPicture: error inserting picture in database: " + ex);

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(Main.getInstance(), "error in addPicture: encoding bufferedImage failed \n" + ex.getMessage(), "Failed to store image", JOptionPane.ERROR_MESSAGE);
		} finally {
			conn.close();
		}
	}

	/**
	 * Removes picture from the database
	 * @param id the id of the picture
	 * @throws SQLException thrown id delete fails
	 */
	public static void removePicture(int id) throws SQLException {
		Connection conn = geefVerbindingOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.deletePicture);
			System.out.println("command: " + DataBaseConstants.deletePicture);
			System.out.println("deleting picture");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			throw new SQLException("error in RemovePicture: error removing picture: " + id + " from database: " + ex);
		} finally {
			conn.close();
		}
	}

	/**
	 * Gets the buildingpicture from the database
	 * @param id the id
	 * @return the pictures for the building
	 * @throws SQLException thrown if the select fails
	 */
	public static ArrayList<Picture> getBuildingPictures(int id) throws SQLException {
		return getPictures(id, true);
	}

	/**
	 * Gets thte rentablepictures from the database
	 * @param id the id
	 * @return the pictures for the building
	 * @throws SQLException thrown if the select fails
	 */
	public static ArrayList<Picture> getRentablePictures(int id) throws SQLException {
		return getPictures(id, false);
	}

	/**
	 * Gets pictures from the database
	 * @param id the id
	 * @param isBuilding true if its a building, false if its a rentable
	 * @return the pictures gotten
	 * @throws SQLException thrown if select fails
	 */
	private static ArrayList<Picture> getPictures(int id, boolean isBuilding) throws SQLException {
		ArrayList<Picture> pictures = new ArrayList<Picture>();
		try {
			Connection conn = geefVerbindingOwner();
			try {
				ByteArrayInputStream bais = null;
				PreparedStatement ps;
				if (isBuilding) {
					ps = conn.prepareStatement(DataBaseConstants.selectBuildingPictures);
				} else {
					ps = conn.prepareStatement(DataBaseConstants.selectRentablePictures);
				}
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					bais = new ByteArrayInputStream(rs.getBytes(DataBaseConstants.pictureData));
					if (bais != null) {
						pictures.add(new Picture(
								rs.getInt(DataBaseConstants.pictureID),
								ImageIO.read(bais)));
					}
				}

			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new SQLException("error in getPicture: problems with connection: " + ex);
		}
		return pictures;
	}

	public static Vector<Rentable> getRentableFromOwner(int ownerID) {
		Vector<Rentable> rentables = new Vector<Rentable>();
		try {
			//int id, ImageIcon previewImage, int type, int area, String windowDirection, int windowArea, boolean internet, boolean cable, int outletCount, int floor, boolean rented, double price, String description
			Connection conn = geefVerbindingOwner();
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(DataBaseConstants.selectRentablesFromOwner);
				while (rs.next()) {
					Rentable rentable = new Rentable(rs.getInt(DataBaseConstants.rentableID),
							null,
							rs.getInt(DataBaseConstants.rentableType),
							rs.getInt(DataBaseConstants.rentableArea),
							rs.getString(DataBaseConstants.windowDirection),
							rs.getInt(DataBaseConstants.windowArea),
							rs.getInt(DataBaseConstants.internet) == 1 ? true : false,
							rs.getInt(DataBaseConstants.cable) == 1 ? true : false,
							rs.getInt(DataBaseConstants.outletCount),
							rs.getInt(DataBaseConstants.floor),
							rs.getInt(DataBaseConstants.rented) == 1 ? true : false,
							rs.getDouble(DataBaseConstants.price),
							rs.getString(DataBaseConstants.description),
							rs.getInt(DataBaseConstants.buildingID));
					rentables.add(rentable);
				}
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println("Error getting rentables from owner: " + ex.getMessage());
		}
		return rentables;
	}

	/**
	 * Gets the tasks from the database
	 * @return the tasks in the database
	 */
	public static HashMap<Integer, ArrayList<Task>> getTasks() {
		//TODO: select by rentable/owner/...?
		HashMap<Integer, ArrayList<Task>> tasks = new HashMap<Integer, ArrayList<Task>>();
		try {
			Connection conn = geefVerbinding();
			try {
				Statement selectTasks = conn.createStatement();
				ResultSet rsTasks = selectTasks.executeQuery(DataBaseConstants.selectTasks);
				// taskID, rentableID, description,start_time,end_time,repeats_every
				int i = 0;
				while (rsTasks.next()) {
					int taskID = rsTasks.getInt(1);
					int rentableID = rsTasks.getInt(2);
					String description = rsTasks.getString(3);
					Date start = rsTasks.getTimestamp(4);
					Date end = rsTasks.getTimestamp(5);
					int repeats = rsTasks.getInt(6);

					int key = CalendarModel.getKey(start);
					if (!tasks.containsKey(key)) {
						tasks.put(key, new ArrayList<Task>());
					}
					tasks.get(key).add(new Task(taskID, rentableID, description, start, end, repeats));
					i++;
				}
				//System.out.println("found " + i + " tasks.");
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			System.out.println("error in tasks: problems with connection : " + ex);
		}
		return tasks;
	}

	/**
	 * Inserts a task into the database
	 * @param t the task to insert
	 */
	public static void insertTask(Task t) {
		try {
			Connection conn = geefVerbinding();
			try {
				PreparedStatement psTasks = conn.prepareStatement(DataBaseConstants.insertTask);
				//rentableID, description,start_time,end_time,repeats_every
				psTasks.setInt(1, t.getRentableID());
				psTasks.setString(2, t.getDescription());

				psTasks.setTimestamp(3, new java.sql.Timestamp(t.getDate().getTime()));
				psTasks.setTimestamp(4, new java.sql.Timestamp(t.getEnd().getTime()));
				psTasks.setInt(5, t.getRepeats());
				psTasks.execute();
			} finally {
				conn.close();
			}

		} catch (SQLException ex) {
			System.out.println("Error inserting into tasks: " + ex.getMessage());
		}
	}

	/**
	 * Removes a task from the database
	 * @param t the task to remove
	 */
	public static void removeTask(Task t) {
		try {
			Connection conn = geefVerbinding();
			try {
				PreparedStatement psTasks = conn.prepareStatement(DataBaseConstants.deleteTask);
				//rentableID,description,start_time
				psTasks.setInt(1, t.getRentableID());
				psTasks.setString(2, t.getDescription());

				psTasks.setTimestamp(3, new java.sql.Timestamp(t.getDate().getTime()));
				psTasks.execute();
			} finally {
				conn.close();
			}

		} catch (SQLException ex) {
			System.out.println("Error deleting from tasks: " + ex.getMessage());
		}
	}

	/**
	 * Updates a task in the database
	 * @param originalTask the original task
	 * @param newTask the new task
	 */
	public static void updateTask(Task originalTask, Task newTask) {
		try {
			Connection conn = geefVerbinding();
			try {
				PreparedStatement psTasks = conn.prepareStatement(DataBaseConstants.updateTask);

				//rentableID, description,start_time,end_time,repeats_every,
				//rentableID,description,start_time
				psTasks.setInt(1, newTask.getRentableID());
				psTasks.setString(2, newTask.getDescription());
				psTasks.setTimestamp(3, new java.sql.Timestamp(newTask.getDate().getTime()));
				psTasks.setTimestamp(4, new java.sql.Timestamp(newTask.getEnd().getTime()));
				psTasks.setInt(5, newTask.getRepeats());
				psTasks.setInt(6, originalTask.getRentableID());
				psTasks.setString(7, originalTask.getDescription());
				psTasks.setTimestamp(8, new java.sql.Timestamp(originalTask.getDate().getTime()));

				//System.out.println(psTasks);
				psTasks.execute();
			} finally {
				conn.close();
			}

		} catch (SQLException ex) {
			System.out.println("Error updating tasks: " + ex.getMessage());
		}
	}

	/**
	 * Gets the messages from the database for a certain owner
	 * @param id the id of the user
	 * @return a vector of messages
	 */
	public static Vector<Message> getMessageData(int id) {
		// create message vector
		Vector<Message> messages = new Vector<Message>();
		//System.out.println(id);
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectMessage);
				ps.setInt(1, id);
				ResultSet rsMessages = ps.executeQuery();

				while (rsMessages.next()) {

					String text = rsMessages.getString(1);
					String subject = rsMessages.getString(2);
					String sender = rsMessages.getString(3) + " " + rsMessages.getString(4);

					Date date = rsMessages.getTimestamp(5);
					String read = rsMessages.getString(6);
					int recipient = rsMessages.getInt(7);
					int senderID = rsMessages.getInt(8);


					messages.add(new Message(recipient, senderID, sender, subject, date, text, read));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			System.out.println("error in getMessages: " + ex.getMessage());
		}
		//System.out.println("found " + messages.size() + " messages.");
		return messages;
	}

	/**
	 * Gets the renters of a certain owner
	 * @param ownerID the id of the owner
	 * @return an ArrayList of persons containing the renters of an owner
	 */
	public static Vector<Person> getRenters(int ownerID) {
		Vector<Person> renters = new Vector<Person>();
		try {
			Connection conn = geefVerbindingOwner();
			try {
				Statement ts = conn.createStatement();
				ResultSet rsRenters = ts.executeQuery(DataBaseConstants.selectRenters);
				while (rsRenters.next()) {
					//int id, String name, String firstName, String email, String telephone, String cellphone
					renters.add(new Person(
							rsRenters.getInt(DataBaseConstants.personID),
							rsRenters.getString(DataBaseConstants.street),
							rsRenters.getString(DataBaseConstants.streetNumber),
							rsRenters.getString(DataBaseConstants.zipCode),
							rsRenters.getString(DataBaseConstants.city),
							rsRenters.getString(DataBaseConstants.country),
							rsRenters.getString(DataBaseConstants.personName),
							rsRenters.getString(DataBaseConstants.firstName),
							rsRenters.getString(DataBaseConstants.email),
							rsRenters.getString(DataBaseConstants.telephone),
							rsRenters.getString(DataBaseConstants.cellphone)));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "error retrieving renters:  \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}

		return renters;
	}

	//TODO: enkel selecteren van contracten van de eigenaar, komt in orde met views ?
	//public static Vector<Contract> getContracts() {
	//TODO: een enkele query?
	//int id, Rentable rentable, Person renter, Date start, Date end, float price, float monthly_cost, float guarentee
	public static Vector<Contract> getContracts(int ownerID) {
		//int id, Rentable rentable, Person renter, Date start, Date end, float price, float monthly_cost, float guarentee
		Vector<Contract> contracts = new Vector<Contract>();
		try {
			Connection conn = geefVerbinding();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectContracts);
				ps.setInt(1, ownerID);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Person renter = new Person(
							rs.getInt(DataBaseConstants.renterID),
							rs.getString(DataBaseConstants.street),
							rs.getString(DataBaseConstants.streetNumber),
							rs.getString(DataBaseConstants.zipCode),
							rs.getString(DataBaseConstants.city),
							rs.getString(DataBaseConstants.country),
							rs.getString(DataBaseConstants.personName),
							rs.getString(DataBaseConstants.firstName),
							rs.getString(DataBaseConstants.email),
							rs.getString(DataBaseConstants.telephone),
							rs.getString(DataBaseConstants.cellphone));

					Rentable rentable = new Rentable(
							rs.getInt(DataBaseConstants.rentableID),
							null, //TODO add preview image for rooms
							rs.getInt(DataBaseConstants.rentableType),
							rs.getInt(DataBaseConstants.rentableArea),
							rs.getString(DataBaseConstants.windowDirection),
							rs.getInt(DataBaseConstants.windowArea),
							rs.getInt(DataBaseConstants.internet) == 1 ? true : false,
							rs.getInt(DataBaseConstants.cable) == 1 ? true : false,
							rs.getInt(DataBaseConstants.outletCount),
							rs.getInt(DataBaseConstants.floor),
							rs.getInt(DataBaseConstants.rented) == 1 ? true : false,
							rs.getFloat(DataBaseConstants.price),
							rs.getString(DataBaseConstants.rentableDescription));
					//we use renterID to get renter data
//					Person renter = getPerson(renterID);
//
//                    Contract contract = new Contract(rs.getInt(DataBaseConstants.contractID),
//                                                     rentable, renter,
//                                                     rs.getTimestamp(DataBaseConstants.contract_start),
//                                                     rs.getTimestamp(DataBaseConstants.contract_end),
//                                                     rs.getFloat(DataBaseConstants.price),
//                                                     rs.getFloat(DataBaseConstants.monthly_cost),
//                                                     rs.getFloat(DataBaseConstants.guarantee));


					Contract contract = new Contract(rs.getInt(DataBaseConstants.contractID),
							rentable, renter,
							rs.getTimestamp(DataBaseConstants.contract_start),
							rs.getTimestamp(DataBaseConstants.contract_end),
							rs.getFloat(DataBaseConstants.price),
							rs.getFloat(DataBaseConstants.monthly_cost),
							rs.getFloat(DataBaseConstants.guarantee));
					contracts.add(contract);
//                    contracts.add(contract);
//					//we use rentableID to get rentable data
//					Rentable rentable = getRentable(rentableID);
//
//					Contract contract = new Contract(contractID, rentable, renter, start, end, price, monthly_cost, guarantee);
//					contracts.add(contract);
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(Main.getInstance(), "Error retrieving contracts:  \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}

		return contracts;
	}

	/**
	 * Removes a contract from the database
	 * @param contract the contract to remove
	 */
	public static void removeContract(Contract contract) {
		try {
			Connection conn = geefVerbinding();

			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.removeContract);
				ps.setInt(1, contract.getId());
				ps.execute();
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			System.out.println("Error removing contract: " + ex.getMessage());
		}
	}

	static Person getPerson(int id) {
		Person person = null;
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectPerson);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					//int id, String street, String streetNumber, String zipCode, String city, String country, String name, String firstName, String email, String telephone, String cellphone
					person = new Person(
							id,
							rs.getString(DataBaseConstants.street),
							rs.getString(DataBaseConstants.streetNumber),
							rs.getString(DataBaseConstants.zipCode),
							rs.getString(DataBaseConstants.city),
							rs.getString(DataBaseConstants.country),
							rs.getString(DataBaseConstants.personName),
							rs.getString(DataBaseConstants.firstName),
							rs.getString(DataBaseConstants.email),
							rs.getString(DataBaseConstants.telephone),
							rs.getString(DataBaseConstants.cellphone));
				} else {

					throw new PersonNotFoundException("Person with id: " + id + " was not found");
				}
			} finally {

				conn.close();
			}
		} catch (Exception ex) {
			System.out.println("error retrieving person with id: " + id + ": " + ex.getMessage());
		}
		return person;
	}

	/**
	 * Updates the state of a message
	 * @param m the message with an updated state
	 */
	public static void updateMessageState(Message m) {
		try {
			Connection conn = geefVerbindingOwner();

			try {
				//setMessageReplied = "update " + tableMessages + " set "+
				//read + " = 2 where " + text + " = ?  and "+senderID+" = ? and "+recipientID+" = ? and "
				//+dateSent+" = ?";
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.updateMessageReplied);
				ps.setString(1, m.getRead());
				ps.setString(2, m.getText());
				ps.setInt(3, m.getSenderID());
				ps.setInt(4, m.getRecipient());
				ps.setTimestamp(5, new java.sql.Timestamp(m.getDate().getTime()));

				ps.execute();
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			System.out.println("Error sending message: " + ex.getMessage());
		}
	}

	/**
	 * Sends a message, puts it in the database
	 * @param m the message to send
	 */
	public static void sendMessage(Message m) {
		try {
			Connection conn = geefVerbinding();

			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.insertMessage);
				ps.setInt(1, m.getSenderID());
				ps.setInt(2, m.getRecipient());
				ps.setString(3, m.getSubject());
				ps.setTimestamp(4, new java.sql.Timestamp(m.getDate().getTime()));
				ps.setString(5, m.getRead());
				ps.setString(6, m.getText());

				ps.execute();
				//senderid,recipientid, subject,datesent,read,text
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			System.out.println("Error sending message: " + ex.getMessage());
		}

	}

	/**
	 * Gets the building from the database with the id given
	 * @param buildingID the id to search for
	 * @return the building
	 * @throws SQLException thrown if something goes wrong fetching the building
	 */
	public static Building getBuilding(int buildingID) throws SQLException {
		Building building = null;
		try {
			Connection conn = geefVerbindingOwner();
			try {
				//TODO get images for building
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectBuilding);
				ps.setInt(1, buildingID);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					building = new Building(
							buildingID,
							null,
							rs.getString(DataBaseConstants.street),
							rs.getString(DataBaseConstants.streetNumber),
							rs.getString(DataBaseConstants.zipCode),
							rs.getString(DataBaseConstants.city),
							rs.getString(DataBaseConstants.country),
							rs.getDouble(DataBaseConstants.latitude),
							rs.getDouble(DataBaseConstants.longitude));

				}

			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new SQLException("error in getBuilding: " + ex);
		}
		return building;
	}

	/**
	 * Getter for rentables from a building
	 * @param buildingID the id to search for
	 * @return an ArrayList containting the rentables from a building
	 * @throws SQLException thrown if the select fails
	 */
	public static ArrayList<Rentable> getRentablesFromBuilding(int buildingID) throws SQLException {
		ArrayList<Rentable> rentables = new ArrayList<Rentable>();
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectRentablePreviewsFromBuilding);
				ps.setInt(1, buildingID);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					ImageIcon img = null;
					byte[] imgData = rs.getBytes(DataBaseConstants.pictureData);
					if (imgData != null) {
						img = new ImageIcon(ImageIO.read(new ByteArrayInputStream(imgData)));
					}
					rentables.add(new Rentable(
							rs.getInt(DataBaseConstants.rentableID),
							img,
							rs.getInt(DataBaseConstants.rentableType),
							rs.getInt(DataBaseConstants.floor),
							rs.getString(DataBaseConstants.rentableDescription)));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new SQLException("error in getPicture: problems with connection: " + ex);
		}
		return rentables;
	}

	/**
	 * Getter for the rentable with given id
	 * @param rentableID the id to search for
	 * @return the rentable
	 * @throws SQLException thrown if select fails
	 */
	static Rentable getRentable(int rentableID) throws SQLException {
		//int id, ImageIcon previewImage, int type, int area, String windowsDirection, int windowArea, boolean internet, boolean cable, int outletCount, int floor, boolean rented, double price, String description
		Rentable rentable = null;
		try {
			Connection conn = geefVerbindingOwner();
			try {
				//TODO get images for building
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectRentable);
				ps.setInt(1, rentableID);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					rentable = new Rentable(
							rentableID,
							null, //TODO add preview image for rooms
							rs.getInt(DataBaseConstants.rentableType),
							rs.getInt(DataBaseConstants.rentableArea),
							rs.getString(DataBaseConstants.windowDirection),
							rs.getInt(DataBaseConstants.windowArea),
							rs.getInt(DataBaseConstants.internet) == 1 ? true : false,
							rs.getInt(DataBaseConstants.cable) == 1 ? true : false,
							rs.getInt(DataBaseConstants.outletCount),
							rs.getInt(DataBaseConstants.floor),
							rs.getInt(DataBaseConstants.rented) == 1 ? true : false,
							rs.getFloat(DataBaseConstants.price),
							rs.getString(DataBaseConstants.rentableDescription));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new SQLException("error in getRentable: " + ex.getMessage());
		}
		return rentable;
	}

	/**
	 * Checks the login
	 * @param username the username to check
	 * @param password the password to check
	 * @return the userID, null if no user found
	 * @throws SQLException thrown if select fails
	 */
	static Integer checkLogin(String username, String password) throws SQLException {
		Integer userID = null;
		try {
			Connection conn = geefVerbinding();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.checkLogin);
				ps.setString(1, username);
				ps.setString(2, password);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					userID = Integer.parseInt(rs.getString(DataBaseConstants.personID));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SQLException("failed login: " + ex);
		}
		return userID;
	}

	/**
	 * Adds dummy pictures to the application
	 */
	public static void addDummyPictures() {
		ArrayList<Building> buildings = null;
		try {
			BufferedImage imgRentable = ImageIO.read(new File("dummy_rentable_preview.png"));
			BufferedImage imgBuildingPreview = ImageIO.read(new File("dummy_building_preview.png"));
			BufferedImage imgFloor = ImageIO.read(new File("dummy_building_floor.png"));


			buildings = selectBuildingPreviews();
			for (Building building : buildings) {
				addRentablePicture(building.getId(), imgRentable);
				addRentablePicture(building.getId(), imgRentable);
				addRentablePicture(building.getId(), imgRentable);
				addRentablePicture(building.getId(), imgRentable);
				addBuildingPreviewPicture(building.getId(), imgBuildingPreview);
				addFloorPlan(building.getId(), imgFloor, 0);
				addFloorPlan(building.getId(), imgFloor, 1);
				addFloorPlan(building.getId(), imgFloor, 2);
			}

		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "failed to read file:", "Error", JOptionPane.ERROR_MESSAGE);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "error getting buildings: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * gets rentprice for renter in euro/month
	 * @throws SQLException thrown if select fails
	 */
	public static double getRentPriceOrGuarantee(int renterId, boolean guarantee) throws SQLException, ContractNotValidException {
		double value = 0;
		Connection conn = geefVerbindingOwner();
		try {
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectRentPrice);
				ps.setInt(1, renterId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					//existing active contract

					if (guarantee) {
						value = rs.getInt(DataBaseConstants.guarantee);
					} else {
						value = rs.getInt(DataBaseConstants.price);
					}

				} else {
					//no active contract, final contract?
					try {
						ps = conn.prepareStatement(DataBaseConstants.selectRentPriceFinal);
						ps.setInt(1, renterId);
						rs = ps.executeQuery();
						if (rs.next()) {

							if (guarantee) {
								value = rs.getInt(DataBaseConstants.guarantee);
							} else {
								value = rs.getInt(DataBaseConstants.price);
							}


						} else {
							throw new ContractNotValidException(Language.getString("errContractNotValid"));
						}
					} catch (Exception ex) {
						System.out.println("guar exp1");
					}
				}
			} catch (Exception ex) {
				System.out.println("guar exp2");
			}
		} finally {
			conn.close();
		}
		return value;
	}

	static Vector<Invoice> getInvoices(int RenterId) {
		throw new UnsupportedOperationException("Not yet implemented");


	}

	/**
	 * gets utilities prices for renter in euro, eg: gas, electricity, water usage
	 * @throws SQLException thrown if select fails
	 */
	static void getUtilitiesInvoiceItems(int renterId, ArrayList<InvoiceItem> items) throws SQLException {
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectUtilities);
				ps.setInt(1, renterId);
				System.out.println("id: " + renterId);
				System.out.println("command: " + DataBaseConstants.selectUtilities);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					items.add(new InvoiceItem(Language.getString("invoiceGas"), rs.getDouble(DataBaseConstants.gasPrice)));
					items.add(new InvoiceItem(Language.getString("invoiceWater"), rs.getDouble(DataBaseConstants.waterPrice)));
					items.add(new InvoiceItem(Language.getString("invoiceElectricity"), rs.getDouble(DataBaseConstants.electricityPrice)));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SQLException("failed to get utilities: " + ex);
		}
	}

	/**
	 * inserts 1 invoice in the database
	 * @param renterId specifies the renter
	 * @param sendDate specifies the date that invoice has to be send
	 * @param xmlString specifies the invoice items in xml format in a String
	 * @throws SQLException thrown if select fails
	 */
	static void insertInvoice(int renterId, Date sendDate, String xmlString) throws SQLException {
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.insertInvoice);
				ps.setInt(1, renterId);
				ps.setDate(2, new java.sql.Date(sendDate.getTime()));
				ps.setBytes(3, xmlString.getBytes());
				ps.execute();
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SQLException("failed during insert Invoice: " + ex);
		}
	}

	/**
	 * Creates views for the owners
	 * @throws SQLException thrown if select fails
	 */
	public static void createViewsForAllOwners() throws SQLException {
		try {
			Connection conn = geefVerbinding();


			try {
				//TODO get images for building
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectOwners);
				ResultSet rs = ps.executeQuery();


				while (rs.next()) {
				}

			} finally {
				conn.close();


			}
		} catch (Exception ex) {
			throw new SQLException("error in makeViews: " + ex);

		}

	}
}

