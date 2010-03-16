package model;

import gui.Main;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

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

	private static int getNextId(String tableName, String columnName) {
		try {
			Connection conn = geefVerbinding();
			try {
				Statement st = conn.createStatement();
				String query = DataBaseConstants.selectNextId.replaceAll("tableName", tableName).replaceAll("columnName", columnName);
				ResultSet rs = st.executeQuery(query);
				rs.next();
				return rs.getInt(1);
			} finally {
				conn.close();
			}
		} catch (Exception exc) {
			System.out.println("getNextId: problems with connection: " + exc);
		}
		return 1;
	}

	public static ArrayList<Building> selectBuildingPreviews() throws SQLException {
		System.out.println("start selection");
		ArrayList<Building> buildings = new ArrayList<Building>();
//		Connection conn = geefVerbinding();
//		try {
//			Statement selectBuildings = conn.createStatement();
//			ResultSet rsBuildings = selectBuildings.executeQuery(DataBaseConstants.selectBuildingPreviews);
//			while (rsBuildings.next()) {
//				buildings.add(new Building(
//						rsBuildings.getInt(DataBaseConstants.buildingId),
//						rsBuildings.getString(DataBaseConstants.buildingName),
//						rsBuildings.getString(DataBaseConstants.street),
//						rsBuildings.getString(DataBaseConstants.streetNumber),
//						rsBuildings.getString(DataBaseConstants.zipCode),
//						rsBuildings.getString(DataBaseConstants.city)));
//			}
//		} finally {
//			conn.close();
//		}
//		System.out.println("end selection");
		return buildings;
	}

	public static void addPicture(int id, BufferedImage bufferedImage, int type) throws SQLException {
		Connection conn = geefVerbinding();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(bufferedImage, "jpg", baos);
			} catch (Exception exc) {
				System.out.println("error in addPicture: encoding bufferedImage failed; " + exc);
			}
			PreparedStatement ps = conn.prepareStatement("insert into pictures values(?,?,?)");
			try {
				ps.setInt(1, id);
				ps.setInt(2, type);
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
		BufferedImage img = new BufferedImage(520, 390, BufferedImage.TYPE_INT_RGB);
		BufferedImage imgPreview = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		BufferedImage imgFloor = new BufferedImage(376, 414, BufferedImage.TYPE_INT_RGB);
		try {
			buildings = selectBuildingPreviews();
			for (Building building : buildings) {
				addPicture(building.getId(), img, 1);
				addPicture(building.getId(), img, 1);
				addPicture(building.getId(), img, 1);
				addPicture(building.getId(), img, 1);
				addPicture(building.getId(), imgPreview, 2);
				addPicture(building.getId(), imgFloor, 3);
				addPicture(building.getId(), imgFloor, 3);
				addPicture(building.getId(), imgFloor, 3);
			}
			System.out.println("finiched adding dummy pictures");
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "error getting buildings: \n" + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static ArrayList<Integer> getPictureIds(int rentableId) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		try {
			Connection conn = geefVerbinding();
			try {
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectPictureIds);
				ps.setInt(1, rentableId);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					ids.add(rs.getInt(1));
				}
			} finally {
				conn.close();
			}
		} catch (Exception exc) {
			System.out.println("error in addPicture: problems with connection : " + exc);
		}
		return ids;
	}

	public static BufferedImage getPicture(int pictureId) {
		BufferedImage bi = null;
		try {
			Connection conn = geefVerbinding();
			try {
				ByteArrayInputStream bais = null;
				PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectPictureData);
				ps.setInt(1, pictureId);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					bais = new ByteArrayInputStream(rs.getBytes(1));
				}
				if (bais != null) {
					bi = ImageIO.read(bais);
				} else {
					System.out.println("error in getPicture: empty resultset");
				}
			} finally {
				conn.close();
			}
		} catch (Exception exc) {
			System.out.println("error in getPicture: problems with connection: " + exc);
		}
		return bi;
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
}
