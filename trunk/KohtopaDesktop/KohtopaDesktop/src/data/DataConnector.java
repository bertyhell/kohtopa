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
import javax.swing.JOptionPane;
import data.entities.*;
import gui.Logger;
import gui.calendartab.CalendarModel;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * this class gets/puts data from/to database + caches information (rentables, buildings).
 */
public class DataConnector {

	//TODO 000 add aspectJ for dataconnection to avoid duplicate code
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
	private static Connection getConnectionOwner() throws SQLException {
		return DriverManager.getConnection(ProgramSettings.getConnectionstring(), ProgramSettings.getUsername(), ProgramSettings.getPassword());
	}

	/**
	 * Checkes login usernale and password
	 * @return boolean in login succeeded
	 */
	public static Integer checkLogin() throws SQLException {
		Logger.logger.info("checking login");
		Integer ownerId = null;
		ownerId = DataConnector.checkLoginInDatabase();
		if (ownerId != null) {
			ProgramSettings.setOwnerId(ownerId);
		}
		return ownerId;
	}

	/**
	 * Checks the login
	 * @return the userID, null if no user found
	 * @throws SQLException thrown if select fails
	 */
	public static Integer checkLoginInDatabase() throws SQLException {
		Integer ownerId = null;
		try {
			Connection conn = getConnectionOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.checkLogin);
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
			Connection conn = getConnectionOwner();
			try {
				Statement selectBuildings = conn.createStatement();
				ResultSet rsBuildings = selectBuildings.executeQuery(DataBaseConstants.selectBuildingPreviews);
				Logger.logger.debug("command select building previews: " + DataBaseConstants.selectBuildingPreviews);
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
			Logger.logger.error("Exception in  selectBuildingPreviews: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
		return buildings;
	}

	/**
	 * Fetches renter previews from the database
	 * @return an Vector of rentersFetches renter previews from the database
	 * @throws SQLException thrown if something goes wrong with the select statements
	 * @throws IOException thrown if there is a problem fetching the images
	 */
	public static Vector<Person> selectRenterPreviews() throws SQLException, IOException {
		Vector<Person> renters = new Vector<Person>();
		Connection conn = getConnectionOwner();
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
	 * Updates the latitude and longitude of a building in the database
	 * @param b the building containing the new data
	 * @throws SQLException thrown if something goes wrong with the update command
	 */
	public static void updateBuildingPosition(Building b) throws SQLException {
		try {
			Connection conn = getConnectionOwner();
			try {
				PreparedStatement psPosition = conn.prepareStatement(null);
				if (b.getLatitude() != 0) {
					psPosition.setDouble(1, b.getLatitude());
					psPosition.setDouble(2, b.getLongitude());
					psPosition.setDouble(3, b.getId());
					psPosition.executeQuery();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException ex) {
			Logger.logger.error("Exception in updateBuildingPosition " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("Exception in updateBuildingPosition " + ex.getMessage());
		}
	}

	/**
	 * Updates a building in the database
	 * @param buildingId the id of the building
	 * @param street the new street of the building
	 * @param streetNumber the new streetnumber of the building
	 * @param zip the new zipcode of the building
	 * @param city the new city of the building
	 * @param ip the ip address of the webcam server atht eremote location
	 * @throws SQLException thrown when the update fails
	 */
	static void updateBuilding(int buildingId, String street, String streetNumber, String zip, String city, String country, int latitude, int longtitude, String ip) throws SQLException {
		Connection conn = getConnectionOwner();
		try {
			PreparedStatement psUpdateBuilding = conn.prepareStatement(DataBaseConstants.updateBuilding);

			Logger.logger.debug("command in update buildings: " + DataBaseConstants.updateBuilding);
			psUpdateBuilding.setInt(1, buildingId);
			psUpdateBuilding.setString(2, streetNumber);
			psUpdateBuilding.setString(3, street);
			psUpdateBuilding.setString(4, zip);
			psUpdateBuilding.setString(5, city);
			psUpdateBuilding.setString(6, country);
			psUpdateBuilding.setDouble(7, latitude);
			psUpdateBuilding.setDouble(8, longtitude);
			psUpdateBuilding.setString(9, ip);
			psUpdateBuilding.execute();
			psUpdateBuilding.close();
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

	public static String getIPAddress(int buildingID) throws SQLException {
		String IPAddress = null;
		Connection conn = getConnectionOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectIPAddress);
			ps.setInt(1, buildingID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				IPAddress = rs.getString(DataBaseConstants.ipaddress);
			} else {
				Logger.logger.info("getIPAddress returned empty result set");
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in getIPAddress " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in getIPAddress: error getting IPAddress in database: " + ex);
		} catch (Exception ex) {
			Logger.logger.error("SQLException in getIPAddress " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			JOptionPane.showMessageDialog(Main.getInstance(), "error in getIPAddress \n" + ex.getMessage(), "Failed to get IPAddress", JOptionPane.ERROR_MESSAGE);
		} finally {
			conn.close();
		}
		return IPAddress;
	}

	public static Picture getPicture(int pictureID) throws SQLException {
		Picture picture = null;
		Connection conn = getConnectionOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectPicture);
			ps.setInt(1, pictureID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ByteArrayInputStream bais = new ByteArrayInputStream(rs.getBytes(DataBaseConstants.picture));
				picture = new Picture(pictureID, ImageIO.read(bais));
			} else {
				Logger.logger.info("get picture returned empty resultset");
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in getPicture " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in getPicture: error getting picture in database: " + ex);
		} catch (Exception ex) {
			Logger.logger.error("SQLException in getPicture " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			JOptionPane.showMessageDialog(Main.getInstance(), "error in getPicture \n" + ex.getMessage(), "Failed to get picture", JOptionPane.ERROR_MESSAGE);
		} finally {
			conn.close();
		}
		return picture;
	}

	public static String getFloor(int buildingID, int floor) throws SQLException {
		String blobString = null;
		Connection conn = getConnectionOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectFloor);
			ps.setInt(1, buildingID);
			ps.setInt(2, floor);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Blob blob = rs.getBlob(DataBaseConstants.xml);
				blobString = new String(blob.getBytes(1, (int) blob.length()));
			} else {
				Logger.logger.info("get floor returned empty resultset");
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in getFloor " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in getFloor: error getting floor in database: " + ex);
		} catch (Exception ex) {
			Logger.logger.error("SQLException in getFloor " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			JOptionPane.showMessageDialog(Main.getInstance(), "error in getFloor \n" + ex.getMessage(), "Failed to get floor", JOptionPane.ERROR_MESSAGE);
		} finally {
			conn.close();
		}
		return blobString;
	}

	// Aid to convert files intop bytearray
	private static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public static void addFloor(int buildingID, int floor, File xml) throws SQLException {
		Connection conn = getConnectionOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.insertFloor);
			ps.setInt(1, buildingID);
			ps.setInt(2, floor);
			ps.setBytes(3, getBytesFromFile(xml));
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in addFloor " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in addFloor: error inserting floor in database: " + ex);
		} catch (Exception ex) {
			Logger.logger.error("SQLException in addFloor " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			JOptionPane.showMessageDialog(Main.getInstance(), "error in addFloor \n" + ex.getMessage(), "Failed to store image", JOptionPane.ERROR_MESSAGE);
		} finally {
			conn.close();
		}
	}

	/**
	 * Adds a floorplan to the database
	 * @param id the id
	 * @param img the image containing the floorplan
	 * @param floor the floor the plan belongs to
	 * @throws SQLException thrown if insert fails
	 */
	public static void addFloorPlan(int id, BufferedImage img, int floor) throws SQLException {
		Connection conn = getConnectionOwner();
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
	 * Getter for the id of a picture
	 * @param rentable_building_ID
	 * @param type_floor
	 * @return the ID used in the database
	 * @throws SQLException thrown if the select statement fails
	 */
	public static Integer getPictureId(int rentable_building_ID, int type_floor) throws SQLException {
		Connection conn = getConnectionOwner();
		Integer id = null;
		try {
			PreparedStatement psCheckPicture = conn.prepareStatement(DataBaseConstants.checkPicture);

			psCheckPicture.setInt(1, rentable_building_ID);
			psCheckPicture.setInt(2, type_floor);
			ResultSet rsCheck = psCheckPicture.executeQuery();
			if (rsCheck.next()) {
				//picture already exists
				id = rsCheck.getInt(DataBaseConstants.pictureID);
			} else {
				Logger.logger.fatal("picture nog niet gevonden na toevoegen picture > error in sql or problem with database");
			}
			rsCheck.close();
			psCheckPicture.close();
		} catch (SQLException ex) {
			Logger.logger.fatal("pictureid is null where it should not be in getPictureId");
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException(ex.getMessage());
		} catch (Exception ex) {
			Logger.logger.error("Exception in  " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		} finally {
			conn.close();
		}
		return id;
	}

	/**
	 * Removes picture from the database
	 * @param id the id of the picture
	 * @throws SQLException thrown id delete fails
	 */
	public static void removePicture(int id) throws SQLException {
		Connection conn = getConnectionOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.deletePicture);

			Logger.logger.debug("command from removePicture: " + DataBaseConstants.deletePicture);
			Logger.logger.debug("id: " + id);
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
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();

			try {
				Statement selectTasks = conn.createStatement();

				Logger.logger.debug("command for getTasks: " + DataBaseConstants.selectTasks);
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
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectContract);
				ps.setInt(1, contractId);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {

					Person renter = getPerson(rs.getInt(DataBaseConstants.renterID), conn);
					Logger.logger.debug("rentable id in getContract: " + rs.getInt(DataBaseConstants.rentableID));
					Rentable rentable = getRentable(rs.getInt(DataBaseConstants.rentableID), conn);
					Logger.logger.debug("buildingid in getrentable: " + rentable.getBuildingID());
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
			Connection conn = getConnectionOwner();
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
	 * Inserts a contract in the database
	 */
	public static void addContract(int rentableId, String firstName, String lastName, String street, String streetNumber, String zipCode, String city, String countryCode, String telephone, String cellphone, String email, Date contractStart, Date contractEnd, double price, double monthCost, double guarantee) {
		try {
			Connection conn = getConnectionOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.addContract);
				ps.setInt(1, rentableId);
				ps.setDate(2, new java.sql.Date(contractStart.getTime()));
				ps.setDate(3, new java.sql.Date(contractEnd.getTime()));
				ps.setDouble(4, price);
				ps.setDouble(5, monthCost);
				ps.setDouble(6, guarantee);
				ps.setString(7, streetNumber);
				ps.setString(8, street);
				ps.setString(9, zipCode);
				ps.setString(10, city);
				ps.setString(11, countryCode);
				ps.setString(12, lastName);
				ps.setString(13, firstName);
				ps.setString(14, email);
				ps.setString(15, telephone);
				ps.setString(16, city);
				ps.execute();
				Logger.logger.info("Contract added!");
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("Exception in addContract " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
	}

	static Person getPerson(int id) {
		Person person = null;
		try {
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();

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
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();
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
		Connection conn = getConnectionOwner();
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
						rs.getString(DataBaseConstants.rentableDescription),
						rs.getInt(DataBaseConstants.buildingID));
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
			Connection conn = getConnectionOwner();
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
		Connection conn = getConnectionOwner();
		try {
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectRentPrice);
				ps.setInt(1, renterId);
				Logger.logger.debug("command getRentPriceOrGuarantee: " + DataBaseConstants.selectRentPrice);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					//existing active contract

					if (guarantee) {
						value = -rs.getInt(DataBaseConstants.guarantee);
					} else {
						value = rs.getInt(DataBaseConstants.price);
					}

				} else {
					//no active contract, final contract?
					try {
						ps = conn.prepareStatement(DataBaseConstants.selectRentPriceFinal);
						Logger.logger.debug("command getRentPriceOrGuarantee (final contracts): " + DataBaseConstants.selectRentPriceFinal);
						ps.setInt(1, renterId);
						rs = ps.executeQuery();
						if (rs.next()) {
							if (guarantee) {
								value = -rs.getInt(DataBaseConstants.guarantee);
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

	/**
	 * gets information about invoices from a specified renter
	 * @param RenterId specifies from wich renter the invoices are requested
	 * @throws SQLException thrown if select fails
	 */
	static Vector<Invoice> getInvoicesPreviews(int RenterId) throws SQLException {
		Vector<Invoice> invoices = new Vector<Invoice>();
		try {
			Connection conn = getConnectionOwner();
			try {
				PreparedStatement psInvoices = conn.prepareStatement(DataBaseConstants.selectInvoices);

				Logger.logger.debug("command get invoice previews: " + DataBaseConstants.selectInvoices);
				Logger.logger.debug("renterid: " + RenterId);

				psInvoices.setInt(1, RenterId);
				ResultSet rsInvoices = psInvoices.executeQuery();
				while (rsInvoices.next()) {
					Logger.logger.info("i found 1 invoice :)");
					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(rsInvoices.getDate(DataBaseConstants.invoiceDate));
					invoices.add(new Invoice(
							rsInvoices.getInt(DataBaseConstants.invoiceId),
							cal.getTime(),
							rsInvoices.getString(DataBaseConstants.invoiceSend).equals("1") ? true : false,
							rsInvoices.getString(DataBaseConstants.invoicePaid).equals("1") ? true : false));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("error in get InvoicesPreviews: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in get invoicesPreviews: " + ex.getMessage());
		}
		Logger.logger.info("size of invoices vector: " + invoices.size());
		return invoices;
	}

	/**
	 * gets utilities prices for renter in euro, eg: gas, electricity, water usage
	 * @throws SQLException thrown if select fails
	 */
	static void getUtilitiesInvoiceItems(int renterId, Vector<InvoiceItem> items) throws SQLException {
		try {
			Connection conn = getConnectionOwner();
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
			Connection conn = getConnectionOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.insertInvoice);

				Logger.logger.debug("command invoice add: " + DataBaseConstants.insertInvoice);
				Logger.logger.debug("date to send invoice: " + new java.sql.Date(sendDate.getTime()));
				Logger.logger.debug("renter id: " + renterId);

				ps.setInt(1, renterId);
				ps.setDate(2, new java.sql.Date(sendDate.getTime()));
				ps.setBytes(3, xmlString.getBytes());
				ps.execute();
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("Exception in insertInvoice: " + ex.getMessage()); //TODO 020 catch double invoices (unique contraint) > ask to replace
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("failed during insert Invoice: " + ex);
		}
	}

	/**
	 * Deletes the specified rentable from database
	 * @param rentableId the identification of the rentable that has to be deleted
	 * @throws SQLException thrown if delete fails
	 */
	static void deleteRentable(int rentableId) throws SQLException {
		Connection conn = getConnectionOwner();
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
		Connection conn = getConnectionOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.deleteBuilding);
			ps.setInt(1, buildingId);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			throw new SQLException("error in remove building: " + buildingId + " from database: " + ex.getMessage());
		} finally {
			conn.close();
		}
	}

	static String getRenterInRentable(int rentableId) throws SQLException {
		String name = Language.getString("notRented");
		try {
			Connection conn = getConnectionOwner();
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
			Logger.logger.error("Exception in getRenterInRentable: " + rentableId + ", : " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
		return name;
	}

	static void deletePictures(int rentBuildId, int type) throws SQLException {
		Connection conn = getConnectionOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.deletePictures);
			ps.setInt(1, rentBuildId);
			ps.setInt(2, type);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("error in remove preview pictures (type: " + type + " from : " + rentBuildId + " from database: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in remove preview pictures (type: " + type + " from : " + rentBuildId + " from database: " + ex.getMessage());
		} finally {
			conn.close();
		}
	}

	static void addRentable(int buildingId, int type, double area, String winDir, double winArea, String internet, String cable, int outlet, int floor, double price) throws SQLException {
		Connection conn = getConnectionOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.insertRentable);

			Logger.logger.debug("command add rentable: " + DataBaseConstants.insertRentable);

			ps.setInt(1, buildingId);
			ps.setInt(2, ProgramSettings.getOwnerId());
			ps.setInt(3, type);
			ps.setString(4, Language.getRentableType(type) + "," + area + "m²," + price + "€");
			ps.setDouble(5, area);
			ps.setString(6, winDir);
			ps.setDouble(7, winArea);
			ps.setString(8, internet);
			ps.setString(9, cable);
			ps.setInt(10, outlet);
			ps.setInt(11, floor);
			ps.setString(12, "0"); //TODO 000 check if rented is correctly fixed with triggers
			ps.setDouble(13, price);

			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("error in adding rentable: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in adding rentable: " + ex.getMessage());
		} finally {
			conn.close();
		}
	}

	static void updateRentable(int rentableId, int type, double area, String winDir, double winArea, String internet, String cable, int outlet, int floor, double price) throws SQLException {
		Connection conn = getConnectionOwner();
		try {

			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.updateRentable);

			Logger.logger.debug("command update rentable: " + DataBaseConstants.updateRentable);

			ps.setInt(1, type);
			ps.setString(2, Language.getRentableType(type) + "," + area + "m²," + price + "€"); //TODO 010 add description to gui so owner can add usefull info to this field
			ps.setDouble(3, area);
			ps.setString(4, winDir);
			ps.setDouble(5, winArea);
			ps.setString(6, internet);
			ps.setString(7, cable);
			ps.setInt(8, outlet);
			ps.setInt(9, floor);
			ps.setDouble(10, price);
			ps.setInt(11, rentableId);

			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("error in update rentable: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in update rentable: " + ex.getMessage());
		} finally {
			conn.close();
		}
	}

	static void addBuilding(
			String street,
			String streetNumber,
			String zip,
			String city,
			String countryCode,
			String ip,
			int type,
			String description,
			String area,
			String winDir,
			int winArea,
			String internet,
			String cable,
			int outlets,
			int floor,
			double price) throws SQLException {
		Connection conn = getConnectionOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.insertBuilding);

			Logger.logger.debug("command add building: " + DataBaseConstants.insertBuilding);

			ps.setString(1, streetNumber);
			ps.setString(2, street);
			ps.setString(3, zip);
			ps.setString(4, city);
			ps.setString(5, countryCode);
			ps.setString(6, ip);
			ps.setInt(7, type);
			ps.setString(8, description);
			ps.setString(9, area);
			ps.setString(10, winDir);
			ps.setInt(11, winArea);
			ps.setString(12, internet);
			ps.setString(13, cable);
			ps.setInt(14, outlets);
			ps.setInt(15, floor);
			ps.setDouble(16, price);

			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("error in adding building: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in adding building: " + ex.getMessage());
		} finally {
			conn.close();
		}
	}

	static void updateContract(int contractId, Date contractEnd) throws SQLException {
		Connection conn = getConnectionOwner();
		try {

			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.updateContract);

			Logger.logger.debug("command update contract: " + DataBaseConstants.updateContract);

			ps.setDate(1, new java.sql.Date(contractEnd.getTime()));
			ps.setInt(2, contractId);
			ps.setInt(3, contractId);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("error in update contract: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in update contract: " + ex.getMessage());
		} finally {
			conn.close();
		}
	}

	static String getInvoiceXmlString(int invoiceId) {
		String xmlString = null;
		try {
			Connection conn = getConnectionOwner();
			try {
				PreparedStatement psInvoiceXml = conn.prepareStatement(DataBaseConstants.selectInvoiceXmlString);

				Logger.logger.debug("command getInvoiceXmlString : " + DataBaseConstants.selectInvoiceXmlString);
				Logger.logger.debug("invoiceid: " + invoiceId);

				psInvoiceXml.setInt(1, invoiceId);
				ResultSet rsInvoiceXml = psInvoiceXml.executeQuery();
				if (rsInvoiceXml.next()) {
					Blob blob = rsInvoiceXml.getBlob(DataBaseConstants.invoiceXml);
					xmlString = new String(blob.getBytes(1, (int) blob.length()));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("Exception in getInvoiceXmlString with invoiceid: " + invoiceId + ", : " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
		return xmlString;
	}

	static void updateInvoice(int invoiceId, Date sendDate, String xmlString) throws SQLException {
		try {
			Connection conn = getConnectionOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.updateInvoice);
				Logger.logger.debug("command invoice add: " + DataBaseConstants.updateInvoice);
				Logger.logger.debug("date to send invoice: " + new java.sql.Date(sendDate.getTime()));
				Logger.logger.debug("invoice id: " + invoiceId);

				ps.setDate(1, new java.sql.Date(sendDate.getTime()));
				ps.setBytes(2, xmlString.getBytes());
				ps.setInt(3, invoiceId);

				ps.execute();
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("Exception in updateInvoice: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("failed during updateInvoice: " + ex);
		}
	}

	static Date getInvoiceSendingDate(int invoiceId) {
		Date sendDate = null;
		try {
			Connection conn = getConnectionOwner();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectInvoiceSendingDate);

				Logger.logger.debug("command getInvoiceSendingDate: " + DataBaseConstants.selectInvoiceSendingDate);
				Logger.logger.debug("invoice id: " + invoiceId);

				ps.setInt(1, invoiceId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					sendDate = new Date(rs.getDate(DataBaseConstants.invoiceDate).getTime());
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			Logger.logger.error("Exception in getInvoiceSendingDate: " + invoiceId + ", : " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			//throw new SQLException("error in getRenterInRentable with rentableId: " + rentableId + ", : " + ex.getMessage());
		}
		return sendDate;
	}

	static void removeInvoice(Integer id) throws SQLException {
		Connection conn = getConnectionOwner();
		try {
			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.deleteInvoice);
			Logger.logger.debug("command delete invoice: " + DataBaseConstants.deleteInvoice);
			Logger.logger.debug("id: " + id);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in removeInvoice " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new SQLException("error in removeInvoice: " + id + " from database: " + ex);
		} finally {
			conn.close();
		}
	}
}