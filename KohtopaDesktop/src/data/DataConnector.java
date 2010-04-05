package data;

import data.entities.Building;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import data.entities.Message;
import data.entities.Picture;
import data.entities.Rentable;

public class DataConnector {
    /* this class gets/puts data from/to database + caches information (rentables, buildings) */

    //TODO add aspectJ for dataconnection to avoid duplicate code

    private static DataConnector instance = new DataConnector();

    public static DataConnector getInstance() {
        return instance;
    }

    private DataConnector() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Class.forName(DataBaseConstants.driver);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("failed to open driver: \n" + ex.getMessage());
        }
    }

    private static Connection geefVerbinding() throws SQLException {
        return DriverManager.getConnection(DataBaseConstants.connectiestring, DataBaseConstants.un, DataBaseConstants.pw);
    }

    public static ArrayList<Building> selectBuildingPreviews() throws SQLException, IOException {
        System.out.println("getting buildings from database");
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

    public static String getShortLogin() {
        try {
            Connection conn = geefVerbinding();
            try {
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery("select username,password from persons where roleid = 'owner' order by LENGTH(username)+LENGTH(password) asc");
                if (rs.next()) {
                    return "Username: " + rs.getString("username") + " , Password: " + rs.getString("password");
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

    public static void updateBuildings(Building b) throws SQLException {
        Connection conn = geefVerbinding();
        try {
            Statement s = conn.createStatement();
            if (b.getLatitude() != 0) {
                s.executeUpdate("update buildings"
                        + " set latitude = " + b.getLatitude()
                        + " , longitude = " + b.getLongitude()
                        + " where buildingid = " + b.getId());
            }
        } catch (SQLException ex) {
            throw new SQLException("update buildings"
                    + " set latitude = " + b.getLatitude()
                    + " set longitude = " + b.getLongitude()
                    + " where buildingid = " + b.getId() + "/" + ex.getMessage());
        }
        conn.close();


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

    public static ArrayList<Picture> getBuildingPictures(int id) throws SQLException {
	return getPictures(id, true);
    }

    public static ArrayList<Picture> getRentablePictures(int id) throws SQLException {
	return getPictures(id, false);
    }

    private static ArrayList<Picture> getPictures(int id, boolean isBuilding) throws SQLException {
	ArrayList<Picture> pictures = new ArrayList<Picture>();
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
			pictures.add(new Picture(
				rs.getInt(DataBaseConstants.pictureId),
				new ImageIcon(ImageIO.read(bais))));
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

    public static Vector<Message> getMessageData() {
        // create message vector
        Vector<Message> messages = new Vector<Message>();
        try {
            Connection conn = geefVerbinding();
            try {
                Statement selectMessages = conn.createStatement();
                ResultSet rsMessages = selectMessages.executeQuery(DataBaseConstants.selectMessages);
                //PreparedStatement selectMessages = conn.prepareStatement(DataBaseConstants.selectMessages);
                //selectMessages.setInt(1, 0);

                //ResultSet rsMessages = selectMessages.executeQuery();

                while (rsMessages.next()) {
                    //text,subject,personName,firstName,dateSent,read,recipientId
                    String text = rsMessages.getString(1);
                    String subject = rsMessages.getString(2);
                    String sender = rsMessages.getString(3) + " " + rsMessages.getString(4);
                    String dateSent = DateFormat.getDateInstance().format(rsMessages.getDate(5)) + " "
                            + DateFormat.getTimeInstance().format(rsMessages.getTime(5));
                    String read = rsMessages.getString(6);
                    int recipient = rsMessages.getInt(7);
                    // int recipient, String sender, String subject, String date, String text, boolean read)

                    messages.add(new Message(recipient, sender, subject, dateSent, text, read));
                }
            } finally {
                conn.close();
            }
        } catch (Exception exc) {
            System.out.println("error in getMessages: problems with connection : " + exc);
        }
        System.out.println("found " + messages.size() + " messages.");
        return messages;
    }

    public static Building getBuilding(int buildingId) throws SQLException {
        Building building = null;
        try {
            Connection conn = geefVerbinding();
            try {
                //TODO get images for building
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

    public static ArrayList<Rentable> getRentablesFromBuilding(int buildingId) throws SQLException {
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
				null, //TODO add preview image for rooms
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

    static Rentable getRentable(int rentableId) throws SQLException {
	Rentable rentable = null;
	try {
	    Connection conn = geefVerbinding();
	    try {
		//TODO get images for building
		PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectRentable);
		ps.setInt(1, rentableId);
		ResultSet rs = ps.executeQuery();
		System.out.println("command: " + DataBaseConstants.selectRentable);
		while (rs.next()) {
		    rentable = new Rentable(
			    rentableId,
				null, //TODO add preview image for rooms
			    rs.getInt(DataBaseConstants.rentableType),
			    rs.getInt(DataBaseConstants.rentableArea),
			    rs.getString(DataBaseConstants.windowDirection),
			    rs.getInt(DataBaseConstants.windowsArea),
			    rs.getInt(DataBaseConstants.internet) == 1 ? true : false,
			    rs.getInt(DataBaseConstants.cable) == 1 ? true : false,
			    rs.getInt(DataBaseConstants.outletCount),
			    rs.getInt(DataBaseConstants.floor),
			    false,
			    rs.getDouble(DataBaseConstants.price),
			    rs.getString(DataBaseConstants.rentableDescription));
		}

            } finally {
                conn.close();
            }
        } catch (Exception ex) {
            throw new SQLException("error in getRentable: " + ex);
        }
        return rentable;
    }

    static Integer checkLogin(String username, String password) throws SQLException {
        Integer userId = null;
        try {
            Connection conn = geefVerbinding();
            try {
                PreparedStatement ps = conn.prepareStatement(DataBaseConstants.checkLogin);
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    userId = Integer.parseInt(rs.getString(DataBaseConstants.personId));
                }
            } finally {
                conn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException("failed login: " + ex);
        }
        return userId;
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

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "failed to read file:", "title", JOptionPane.ERROR_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(Main.getInstance(), "error getting buildings: \n" + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
