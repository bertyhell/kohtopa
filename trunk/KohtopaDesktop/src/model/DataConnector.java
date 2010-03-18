package model;

import model.data.Building;
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
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.data.Rentable;

public class DataConnector {
	/* this class gets/puts data from/to database */

	private static DataConnector instance = new DataConnector();

	public static DataConnector getInstance() {
		return instance;
	}

	private DataConnector() {
		try {
			Class.forName(DataBaseConstants.driver);
		} catch (ClassNotFoundException ex) {
			System.out.println("failed to open driver: \n" + ex.getMessage());
		}
	}

	private static Connection geefVerbinding() throws SQLException {
		return DriverManager.getConnection(DataBaseConstants.connectiestring, DataBaseConstants.un, DataBaseConstants.pw);
	}

	public static ArrayList<Building> selectBuildingPreviews() throws SQLException, IOException {
		System.out.println("start selection");
		ArrayList<Building> buildings = new ArrayList<Building>();
		Connection conn = geefVerbinding();
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
						rsBuildings.getInt(DataBaseConstants.buildingId),
						img,
						rsBuildings.getString(DataBaseConstants.street),
						rsBuildings.getString(DataBaseConstants.streetNumber),
						rsBuildings.getString(DataBaseConstants.zipCode),
						rsBuildings.getString(DataBaseConstants.city)));
			}
		} finally {
			conn.close();
		}
		System.out.println("end selection");
		return buildings;
	}

	public static void addBuildingPicture(int id, BufferedImage bufferedImage) throws SQLException {
		addFloorPlan(id, bufferedImage, -2);
	}

	public static void addBuildingPreviewPicture(int id, BufferedImage bufferedImage) throws SQLException {
		addFloorPlan(id, bufferedImage, -3);
	}

	public static void addRentablePicture(int id, BufferedImage bufferedImage) throws SQLException {
		addFloorPlan(id, bufferedImage, -1);
	}

	public static void addFloorPlan(int id, BufferedImage bufferedImage, int floor) throws SQLException {
		Connection conn = geefVerbinding();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(bufferedImage, "jpg", baos);
			} catch (Exception exc) {
				System.out.println("error in addPicture: encoding bufferedImage failed; " + exc);
			}

			PreparedStatement ps = conn.prepareStatement(DataBaseConstants.insertPicture);
			try {
				ps.setInt(1, id);
				ps.setInt(2, floor);
				ps.setBytes(3, baos.toByteArray());
				ps.executeUpdate();
			} catch (SQLException ex) {
				throw new SQLException("error in addPicture: error inserting picture in database: " + ex);
			} finally {
				ps.close();
			}
		} finally {
			conn.close();
		}
	}

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
			System.out.println("finiched adding dummy pictures");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "failed to read file:", "title", JOptionPane.ERROR_MESSAGE);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "error getting buildings: \n" + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static HashMap<Integer, BufferedImage> getPictures(int id, boolean isBuilding) throws SQLException {
		HashMap<Integer, BufferedImage> images = new HashMap<Integer, BufferedImage>();
		try {
			Connection conn = geefVerbinding();
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
						images.put(rs.getInt(DataBaseConstants.pictureId), ImageIO.read(bais));
					}
				}

			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new SQLException("error in getPicture: problems with connection: " + ex);
		}
		return images;
	}

	public static Building getBuilding(int buildingId) throws SQLException{
	    Building building = null;
		try {
			Connection conn = geefVerbinding();
			try {
			    //TODO get images for building
				ByteArrayInputStream bais = null;
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectBuilding);
				ps.setInt(1, buildingId);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					building = new Building(
						buildingId,
						null,
						rs.getString(DataBaseConstants.street),
						rs.getString(DataBaseConstants.streetNumber),
						rs.getString(DataBaseConstants.zipCode),
						rs.getString(DataBaseConstants.city),
						rs.getString(DataBaseConstants.country));
				}

			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new SQLException("error in getPicture: problems with connection: " + ex);
		}
		return building;
	}

	public static ArrayList<Rentable> getRentablesFromBuilding(int buildingId) throws SQLException{
	    ArrayList<Rentable> rentables = new ArrayList<Rentable>();
		try {
			Connection conn = geefVerbinding();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectRentablesFromBuilding);
				ps.setInt(1, buildingId);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					rentables.add(new Rentable(
						rs.getInt(DataBaseConstants.rentableId),
						rs.getInt(DataBaseConstants.floor)));
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new SQLException("error in getPicture: problems with connection: " + ex);
		}
		return rentables;
	}
}
