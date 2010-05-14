package data;

import Exceptions.ContractNotValidException;
import Exceptions.PersonNotFoundException;
import Language.Language;
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
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import data.entities.*;
import gui.Logger;
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
			Logger.logger.warn("initializing DataConnector");
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Class.forName(DataBaseConstants.driver);
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in init of dataconnector: " + ex.getMessage());
		} catch (ClassNotFoundException ex) {
			Logger.logger.error("ClassNotFoundException in init of dataconnector: " + ex.getMessage());
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
	 * Checkes login usernale and password
	 * @return boolean in login succeeded
	 */
	public static Integer checkLogin(String username, String password) {
		Logger.logger.info("checking login");
		Integer ownerId = null;
		try {
			ownerId = DataConnector.checkLoginInDatabase(username, password);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "login attempt failed: \n" + ex.getMessage(), "login fail", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (ownerId != null) {
			ProgramSettings.setOwnerId(ownerId);
		}
		return ownerId;
	}

	/**
	 * Checks the login
	 * @param username the username to check
	 * @param password the password to check
	 * @return the userID, null if no user found
	 * @throws SQLException thrown if select fails
	 */
	public static Integer checkLoginInDatabase(String username, String password) throws SQLException {
		Integer ownerId = null;
		try {
			Connection conn = geefVerbinding();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.checkLogin);
				ps.setString(1, username);
				ps.setString(2, password);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Logger.logger.info("database acquired ownerid (login succesfull)");
					ownerId = Integer.parseInt(rs.getString(DataBaseConstants.personID));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("failed login: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("failed login: " + ex);
		}
		return ownerId;
	}

	/**
	 * Fetches buildingPreviews from the database
	 * @return an Vector of buildings
	 * @throws SQLException thrown if something goes wrong with the select statements
	 * @throws IOException thrown if there is a problem fetching the images
	 */
	public static Vector<Building> selectBuildingPreviews() throws SQLException, IOException {
		Vector<Building> buildings = new Vector<Building>();
		try {
			Connection conn = geefVerbindingOwner();
			try {
				Statement selectBuildings = conn.createStatement();
				ResultSet rsBuildings = selectBuildings.executeQuery(DataBaseConstants.selectBuildingPreviews);
				while (rsBuildings.next()) {
					BufferedImage img = null;
					byte[] imgData = rsBuildings.getBytes(DataBaseConstants.pictureData);
					if (imgData != null) {
						img = ImageIO.read(new ByteArrayInputStream(imgData));
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
		} catch (Exception ex) {
			System.out.println("exception during selectBuildingPreviews: " + ex.getMessage());

		}
		System.out.println("buildings: " + buildings);
		return buildings;
	}

	/**
	 * Fetches renter previews from the database
	 * @return an Vector of renters
	 * @throws SQLException thrown if something goes wrong with the select statements
	 * @throws IOException thrown if there is a problem fetching the images
	 */
	public static Vector<Person> selectRenterPreviews() throws SQLException, IOException {
		Vector<Person> renters = new Vector<Person>();
		Connection conn = geefVerbindingOwner();
		try {
			PreparedStatement psSelectRenters = conn.prepareStatement(DataBaseConstants.selectRenterPreviews);
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
				Logger.logger.error("SQLException in getOptimalLogin " + ex.getMessage());
				Logger.logger.debug("StackTrace: ", ex);
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in getOptimalLogin (connection failure) " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
		return "no owners found";
	}

	/**
	 * Updates the latitude and longitude of a building in the database
	 * @param b the building containing the new data
	 * @throws SQLException thrown if something goes wrong with the update command
	 */
	public static void updateBuildingPosition(Building b) throws SQLException {
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
			Logger.logger.error("Exception in updateBuildings " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
			if (addressID == null) {
				Logger.logger.info("addressid is null > address doesn't exest yet");
			}
			psAddBuilding.setInt(1, addressID);
			psAddBuilding.execute();
			psAddBuilding.close();
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in addBuilding " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in addBuilding: " + ex);
		} catch (Exception ex) {
			Logger.logger.error("Exception in addBuilding " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
		System.out.println("address gevonden on first try: " + addressID);
		try {
			if (addressID == null) {
				System.out.println("address niet gevonden op 1e try");
				//address does not exists > make it and then get id
				System.out.println("adding address (should end up in notconnectedview");
				PreparedStatement psAddAddress = conn.prepareStatement(DataBaseConstants.addAddress);
				psAddAddress.setString(1, streetNumber);
				psAddAddress.setString(2, street);
				psAddAddress.setString(3, zip);
				psAddAddress.setString(4, city);
				psAddAddress.setString(5, country);
				psAddAddress.execute();
				addressID = getAddressID(conn, street, streetNumber, zip, city, country, false);
				if (addressID == null) {
					Logger.logger.error("addressid is null where it should not be in addAddress");
					throw new NullPointerException("address id is still null > proigramming error");
				}
			}
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in addAddress " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
			if (connected) {
				psCheckAddress = conn.prepareStatement(DataBaseConstants.checkAddressConnected);
			} else {
				System.out.println("getting address from not connected");
				psCheckAddress = conn.prepareStatement(DataBaseConstants.checkAddressNotConnected);
			}

			System.out.println("command: " + DataBaseConstants.checkAddressConnected);
			System.out.println("street: " + street);
			System.out.println("streetNumber: " + streetNumber);
			System.out.println("zip: " + zip);
			System.out.println("city: " + city);
			System.out.println("country: " + country);
			psCheckAddress.setString(1, street);
			psCheckAddress.setString(2, streetNumber);
			psCheckAddress.setString(3, zip);
			psCheckAddress.setString(4, city);
			psCheckAddress.setString(5, country);
			ResultSet rsCheck = psCheckAddress.executeQuery();
			if (rsCheck.next()) {
				System.out.println("address has been found: " + rsCheck.getInt(DataBaseConstants.addressID));
				//address already exists
				id = rsCheck.getInt(DataBaseConstants.addressID);
			} else {
				Logger.logger.fatal("!!!!!!!!!!!!! address nog niet gevonden na toevoegen address > error in sql or problem with database");
			}
			rsCheck.close();
			psCheckAddress.close();
		} catch (SQLException ex) {
			Logger.logger.info("addressid is null where it should not be in addAddress");
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
			//TODO: old address: has to be cleaned up by garbagecollect if it is not used anymore
			System.out.println("checking address");
			Integer addressID = addAddress(conn, street, streetNumber, zip, city, country);
			System.out.println("addres is: " + addressID);
			System.out.println("updating building****");
			//adding building with correct address id
			PreparedStatement psAddBuilding = conn.prepareStatement(DataBaseConstants.updateBuilding);
			psAddBuilding.setInt(1, addressID);
			psAddBuilding.setInt(2, id);
			psAddBuilding.execute();
			psAddBuilding.close();
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in updateBuilding " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in updateBuilding: " + ex);
		} catch (Exception ex) {
			Logger.logger.error("Exception in updateBuilding (encoding error?) " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			JOptionPane.showMessageDialog(Main.getInstance(), "error in updateBuilding: encoding bufferedImage failed \n" + ex.getMessage(), "Failed to store image", JOptionPane.ERROR_MESSAGE);
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
			Logger.logger.error("SQLException in addFloorPlan " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in addPicture: error inserting picture in database: " + ex);
		} catch (Exception ex) {
			Logger.logger.error("Exception in addFloorPlan " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
			System.out.println("coommand: " + DataBaseConstants.deletePicture);
			System.out.println("id: " + id);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in removePicture " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
	public static Vector<Picture> getBuildingPictures(int id) throws SQLException {
		return getPictures(id, true);
	}

	/**
	 * Gets thte rentablepictures from the database
	 * @param id the id
	 * @return the pictures for the building
	 * @throws SQLException thrown if the select fails
	 */
	public static Vector<Picture> getRentablePictures(int id) throws SQLException {
		return getPictures(id, false);
	}

	/**
	 * Gets pictures from the database
	 * @param id the id
	 * @param isBuilding true if its a building, false if its a rentable
	 * @return the pictures gotten
	 * @throws SQLException thrown if select fails
	 */
	private static Vector<Picture> getPictures(int id, boolean isBuilding) throws SQLException {
		Vector<Picture> pictures = new Vector<Picture>();
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
			Logger.logger.error("SQLException in getPictures " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in getPicture: problems with connection: " + ex);
		}
		return pictures;
	}

	public static Vector<Rentable> getRentableFromOwner() {
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
			Logger.logger.error("SQLException in getRentableFromOwner " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to get rentables from owner \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
		return rentables;
	}

	/**
	 * Gets the tasks from the database
	 * @return the tasks in the database
	 */
	public static HashMap<Integer, Vector<Task>> getTasks() {
		//TODO: select by rentable/owner/...?
		HashMap<Integer, Vector<Task>> tasks = new HashMap<Integer, Vector<Task>>();
		try {
			Connection conn = geefVerbindingOwner();

			try {
				Statement selectTasks = conn.createStatement();
				System.out.println(DataBaseConstants.selectTasks);
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
						tasks.put(key, new Vector<Task>());
					}
					tasks.get(key).add(new Task(taskID, rentableID, description, start, end, repeats));
					i++;
				}
				Logger.logger.info("found " + i + " tasks.");
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("Exception in getTasks " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
		return tasks;
	}

	/**
	 * Inserts a task into the database
	 * @param t the task to insert
	 */
	public static void insertTask(Task t) {
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement psTasks = conn.prepareStatement(DataBaseConstants.insertTask);
				//rentableID, description,start_time,end_time,repeats_every
				psTasks.setInt(1, t.getRentableID());
				psTasks.setString(2, t.getDescription());

				psTasks.setTimestamp(3, new java.sql.Timestamp(t.getDate().getTime()));
				psTasks.setTimestamp(4, new java.sql.Timestamp(t.getEnd().getTime()));
				psTasks.setInt(5, t.getRepeats());

				psTasks.executeUpdate();
			} finally {
				conn.close();
			}

		} catch (SQLException ex) {
			Logger.logger.error("Exception in insertTask " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
	}

	/**
	 * Removes a task from the database
	 * @param t the task to remove
	 */
	public static void removeTask(Task t) {
		try {
			Connection conn = geefVerbindingOwner();
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
			Logger.logger.error("Exception in removeTask " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
	}

	/**
	 * Updates a task in the database
	 * @param originalTask the original task
	 * @param newTask the new task
	 */
	public static void updateTask(Task originalTask, Task newTask) {
		try {
			Connection conn = geefVerbindingOwner();
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
				psTasks.execute();
			} finally {
				conn.close();
			}

		} catch (SQLException ex) {
			Logger.logger.error("Exception in updateTask " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
			Logger.logger.error("Exception in getMessageData " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
		Logger.logger.info("found " + messages.size() + " messages.");
		return messages;
	}

	/**
	 * Gets the renters of a certain owner
	 * @return an Vector of persons containing the renters of an owner
	 */
	public static Vector<Person> getRenters() {
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
			Logger.logger.error("Exception in getRenters " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			JOptionPane.showMessageDialog(Main.getInstance(), "error retrieving renters:  \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}

		return renters;
	}

	/**
	 * Gets the contractPreviews of the owner, this is anly the info that is needed to show the table
	 * @return an Vector of contracts
	 */
	public static Vector<Contract> getPreviewContractsFromRenter(int RenterId) {
		Vector<Contract> contracts = new Vector<Contract>();
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectPreviewContracts);
				ps.setInt(1, RenterId);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					contracts.add(new Contract(
							rs.getInt(DataBaseConstants.contractID),
							new Person(
							rs.getInt(DataBaseConstants.personID),
							null,
							rs.getString(DataBaseConstants.firstName),
							rs.getString(DataBaseConstants.personName)),
							rs.getTimestamp(DataBaseConstants.contract_start),
							rs.getTimestamp(DataBaseConstants.contract_end)));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("Exception in getPreviewContracts " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			JOptionPane.showMessageDialog(Main.getInstance(), "Error retrieving previewcontracts:  \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}

		return contracts;
	}

	/**
	 * Gets the contract with id contractId
	 * @param contractId specifies wich contract to select
	 * @return 1 Contract
	 */
	static Contract getContract(int contractId) {
		Contract contract = null;
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectContract);
				ps.setInt(1, contractId);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {

					Person renter = getPerson(rs.getInt(DataBaseConstants.renterID), conn);

					Rentable rentable = getRentable(rs.getInt(DataBaseConstants.rentableID), conn);

					contract = new Contract(
							rs.getInt(DataBaseConstants.contractID),
							rentable,
							renter,
							rs.getTimestamp(DataBaseConstants.contract_start),
							rs.getTimestamp(DataBaseConstants.contract_end),
							rs.getFloat(DataBaseConstants.price),
							rs.getFloat(DataBaseConstants.monthly_cost),
							rs.getFloat(DataBaseConstants.guarantee));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("Exception in get contract with id: " + contractId + ", : " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			JOptionPane.showMessageDialog(Main.getInstance(), "Error retrieving contract with id: " + contractId + "\n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}

		return contract;
	}

	/**
	 * Gets the contracts of the owner
	 * @return an Vector of contracts
	 */
	public static Vector<Contract> getContracts() {
		//int id, Rentable rentable, Person renter, Date start, Date end, float price, float monthly_cost, float guarentee
		Vector<Contract> contracts = new Vector<Contract>();
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectContracts);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {

					Person renter = getPerson(rs.getInt(DataBaseConstants.renterID), conn);

					Rentable rentable = getRentable(rs.getInt(DataBaseConstants.rentableID), conn);

					contracts.add(new Contract(
							rs.getInt(DataBaseConstants.contractID),
							rentable,
							renter,
							rs.getTimestamp(DataBaseConstants.contract_start),
							rs.getTimestamp(DataBaseConstants.contract_end),
							rs.getFloat(DataBaseConstants.price),
							rs.getFloat(DataBaseConstants.monthly_cost),
							rs.getFloat(DataBaseConstants.guarantee)));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("Exception in getContracts " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
			Logger.logger.error("Exception in removeContract " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
	}

	static Person getPerson(int id) {
		Person person = null;
		try {
			Connection conn = geefVerbindingOwner();
			try {
				person = getPerson(id, conn);
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("error retrieving person with id: " + id + ": " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
		return person;
	}

	static Person getPerson(int id, Connection conn) {
		Person person = null;
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
		} catch (Exception ex) {
			Logger.logger.error("error retrieving person with id: " + id + ": " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
			Logger.logger.error("error setting read status on messages: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
	}

	/**
	 * Sends a message, puts it in the database
	 * @param m the message to send
	 */
	public static void sendMessage(Message m) {
		try {
			Connection conn = geefVerbindingOwner();

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
			Logger.logger.error("Exception in sendMessage: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
			Logger.logger.error("Exception in getBuilding: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
		return building;
	}

	/**
	 * Getter for rentables from a building
	 * @param buildingID the id to search for
	 * @return an Vector containting the rentables from a building
	 * @throws SQLException thrown if the select fails
	 */
	public static Vector<Rentable> getRentablesFromBuilding(int buildingID) throws SQLException {
		Vector<Rentable> rentables = new Vector<Rentable>();
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectRentablePreviewsFromBuilding);
				ps.setInt(1, buildingID);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					BufferedImage img = null;
					byte[] imgData = rs.getBytes(DataBaseConstants.pictureData);
					if (imgData != null) {
						img = ImageIO.read(new ByteArrayInputStream(imgData));
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
			Logger.logger.error("error in getRentablesFromBuilding: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in getRentablesFromBuilding: " + ex.getMessage());
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
		Connection conn = geefVerbindingOwner();
		try {
			rentable = getRentable(rentableID, conn);
		} finally {
			conn.close();
		}
		return rentable;
	}

	/**
	 * Getter for the rentable with given id
	 * @param rentableID the id to search for
	 * @return the rentable
	 * @throws SQLException thrown if select fails
	 */
	static Rentable getRentable(int rentableID, Connection conn) throws SQLException {
		Rentable rentable = null;
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
		} catch (Exception ex) {
			Logger.logger.error("error in getRentable: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in getRentable: " + ex.getMessage());
		}
		return rentable;
	}

	/**
	 * gets floor id's of building
	 * @param buildingId specifies what building to search in
	 * @return Vector of integers that represent the floor id's
	 * @throws SQLException thrown if select fails
	 */
	static Vector<Integer> getFloors(int buildingId) throws SQLException {
		Vector<Integer> floors = new Vector<Integer>();
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement psFloors = conn.prepareStatement(DataBaseConstants.selectFloors);
				psFloors.setInt(1, buildingId);
				ResultSet rsFloors = psFloors.executeQuery();
				while (rsFloors.next()) {
					floors.add(rsFloors.getInt(DataBaseConstants.floor));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("error in get floors: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in get floors: " + ex.getMessage());
		}
		return floors;
	}

	/**
	 * Adds dummy pictures to the application
	 */
	public static void addDummyPictures() {
		Vector<Building> buildings = null;
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
			Logger.logger.error("IOException in addDummyPictures: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in addDummyPictures: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
						Logger.logger.error("Exception in getRentPriceOrGuarantee(1): " + ex.getMessage());
						Logger.logger.debug("StackTrace: ", ex);
					}
				}
			} catch (Exception ex) {
				Logger.logger.error("Exception in getRentPriceOrGuarantee(2): " + ex.getMessage());
				Logger.logger.debug("StackTrace: ", ex);
			}
		} finally {
			conn.close();
		}
		return value;
	}

	static Vector<Invoice> getInvoices(int RenterId) {
		Logger.logger.warn("Not yet implemented");
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * gets utilities prices for renter in euro, eg: gas, electricity, water usage
	 * @throws SQLException thrown if select fails
	 */
	static void getUtilitiesInvoiceItems(int renterId, Vector<InvoiceItem> items) throws SQLException {
		try {
			Connection conn = geefVerbindingOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectUtilities);
				ps.setInt(1, renterId);
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
			Logger.logger.error("Exception in getUtilitiesInvoiceItems: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
			Logger.logger.error("Exception in insertInvoice: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
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
			Logger.logger.error("Exception in createViewsForAllOwners: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in makeViews: " + ex);
		}
	}

	/**
	 * Deletes the specified rentable from database
	 * @param rentableId the identification of the rentable that has to be deleted
	 * @throws SQLException thrown if delete fails
	 */
	static void deleteRentable(int rentableId) throws SQLException {
		Connection conn = geefVerbindingOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.deleteRentable);
			ps.setInt(1, rentableId);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("error in remove rentable: " + rentableId + " from database: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in remove rentable: " + rentableId + " from database: " + ex.getMessage());
		} finally {
			conn.close();
		}
	}

	/**
	 * Deletes the specified building from database
	 * @param buildingId the identification of the building that has to be deleted
	 * @throws SQLException thrown if delete fails
	 */
	static void deleteBuilding(Integer buildingId) throws SQLException {
		Connection conn = geefVerbindingOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.deleteBuilding);
			ps.setInt(1, buildingId);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("error in remove building: " + buildingId + " from database: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in remove building: " + buildingId + " from database: " + ex.getMessage());
		} finally {
			conn.close();
		}
	}

	static String getRenterInRentable(int rentableId) throws SQLException {
		String name = Language.getString("notRented");
		try {
			Connection conn = geefVerbinding();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectRenterInRentable);
				ps.setInt(1, rentableId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					name = rs.getString(DataBaseConstants.firstName) + rs.getString(DataBaseConstants.personName);
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			System.out.println("Exception in getRenterInRentable: " + rentableId + ", : " + ex.getMessage());
			Logger.logger.error("Exception in getRenterInRentable: " + rentableId + ", : " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			//throw new SQLException("error in getRenterInRentable with rentableId: " + rentableId + ", : " + ex.getMessage());
		}
		return name;
	}
}

