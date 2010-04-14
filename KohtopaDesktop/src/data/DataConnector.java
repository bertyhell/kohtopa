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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import data.entities.Message;
import data.entities.Person;
import data.entities.Picture;
import data.entities.Rentable;
import data.entities.Task;
import gui.calendartab.CalendarModel;
import java.util.Date;
import java.util.HashMap;

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

    /**
     * Returns an optimal login, having the best combination of username/password length,
     * rentables owned, messages received and contracts given.
     * @return
     */
    public static String getOptimalLogin() {
        try {
            Connection conn = geefVerbinding();
            try {
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery(
                "select distinct p.personid,max(p.username),max(p.password),count(1) ,count(1)/length(max(p.password) || max(p.username)) "+
                "from messages m "+
                "join persons p on m.recipientid = p.personid "+
                "join rentables r on r.ownerid = p.personid "+
                "join contracts c on c.rentableid = r.rentableid "+
                "group by p.personid "+
                "order by 5 desc");
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
        conn.close(); //TODO jelle: put this in final block
    }

    static void addBuilding(String street, String streetNumber, String zip, String city) throws SQLException {
        Connection conn = geefVerbinding();
        try {
            Integer addressID = addAddress(conn, street, streetNumber, zip, city);
            //adding building with correct address id
            PreparedStatement psAddBuilding = conn.prepareStatement(DataBaseConstants.addBuilding);
            psAddBuilding.setInt(1, addressID);
            psAddBuilding.execute();
            psAddBuilding.close();
        } catch (SQLException ex) {
            throw new SQLException("error in addBuilding: " + ex);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(Main.getInstance(), "error in addPicture: encoding bufferedImage failed \n" + ex.getMessage(), "Failed to store image", JOptionPane.ERROR_MESSAGE);
        } finally {
            conn.close();
        }
    }

    static Integer addAddress(Connection conn, String street, String streetNumber, String zip, String city) throws SQLException {

        Integer addressID = getAddressID(conn, street, streetNumber, zip, city);
        try {
            if (addressID == null) {
                //address does not exists > make it and then get id
                PreparedStatement psAddAddress = conn.prepareStatement(DataBaseConstants.addAddress);
                psAddAddress.setString(2, street);
                psAddAddress.setString(1, streetNumber);//number comes first in database :s
                psAddAddress.setString(3, zip);
                psAddAddress.setString(4, city);
                psAddAddress.execute();
                addressID = getAddressID(conn, street, streetNumber, zip, city);
            }
        } catch (SQLException ex) {
            System.out.println("sql exception in addadress: " + ex.getMessage());
            throw new SQLException(ex.getMessage());
        }
        return addressID;
    }

    private static Integer getAddressID(Connection conn, String street, String streetNumber, String zip, String city) throws SQLException {
        Integer id = null;
        try {
            PreparedStatement psCheckAddress = conn.prepareStatement(DataBaseConstants.checkAddress);
            psCheckAddress.setString(1, street);
            psCheckAddress.setString(2, streetNumber);
            psCheckAddress.setString(3, zip);
            psCheckAddress.setString(4, city);
            ResultSet rsCheck = psCheckAddress.executeQuery();
            if (rsCheck.next()) { //TODO read: count how many addresses, can never be more then 1 => can be used as consistency check
                //address already exists
                id = rsCheck.getInt(DataBaseConstants.addressID);
            }
            rsCheck.close();
            psCheckAddress.close();
        } catch (SQLException ex) {
            System.out.println("sql exception in check address: " + ex.getMessage());
            throw new SQLException(ex.getMessage());
        }
        return id;
    }

    public static void updateBuilding(int id, String street, String streetNumber, String zip, String city) throws SQLException {
        Connection conn = geefVerbinding();
        try {
            //TODO: adding new address with new data (old address: has to be cleaned up by garbagecollect if it is not used anymore)
            Integer addressID = addAddress(conn, street, streetNumber, zip, city);
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

    public static void addRentablePicture(int id, BufferedImage bufferedImage) throws SQLException {
        addFloorPlan(id, bufferedImage, -1);
    }

    public static void addRentablePreviewPicture(int id, BufferedImage img) throws SQLException {
        addFloorPlan(id, img, -2);
    }

    public static void addBuildingPicture(int id, BufferedImage img) throws SQLException {
        addFloorPlan(id, img, -3);
    }

    public static void addBuildingPreviewPicture(int id, BufferedImage img) throws SQLException {
        addFloorPlan(id, img, -4);
    }

    public static void addFloorPlan(int id, BufferedImage img, int floor) throws SQLException {
        Connection conn = geefVerbinding();
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

    public static void removePicture(int id) throws SQLException {
        Connection conn = geefVerbinding();
        try {
            PreparedStatement ps = conn.prepareStatement(DataBaseConstants.deletePicture);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new SQLException("error in RemovePicture: error removing picture: " + id + " from database: " + ex);
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

    public static ArrayList<Rentable> getRentablesFromUser(String username,String password) {
        ArrayList<Rentable> rentables = new ArrayList<Rentable>();
        try {
            //int id, ImageIcon previewImage, int type, int floor, String description
            Connection conn = geefVerbinding();
            try {
                PreparedStatement psRentables = conn.prepareStatement(DataBaseConstants.selectRentablesFromUser);
                psRentables.setString(1, username);
                psRentables.setString(2, password);
                ResultSet rs = psRentables.executeQuery();
                while(rs.next()) {
                    int id = rs.getInt(1);
                    int type = rs.getInt(2);
                    int floor = rs.getInt(3);
                    String description = rs.getString(4);
                    rentables.add(new Rentable(id,null,type,floor,description));
                }
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("error getting rentables from user: "+ex.getMessage());
        }

        return rentables;
    }

    public static HashMap<Integer, ArrayList<Task>> getTasks() {
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
    public static void updateTask(Task originalTask, Task newTask) {
        try {
            Connection conn = geefVerbinding();
            try {
                PreparedStatement psTasks = conn.prepareStatement(DataBaseConstants.updateTask);

                //rentableID, description,start_time,end_time,repeats_every,
                //rentableID,description,start_time
                psTasks.setInt(1,newTask.getRentableID());
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

    public static Vector<Message> getMessageData(int id) {
        // create message vector
        Vector<Message> messages = new Vector<Message>();
        //System.out.println(id);
        try {
            Connection conn = geefVerbinding();
            try {
                PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectMessage);
                ps.setInt(1,id);
                ResultSet rsMessages = ps.executeQuery();
                //PreparedStatement selectMessages = conn.prepareStatement(DataBaseConstants.selectMessages);
                //selectMessages.setInt(1, 0);

                //ResultSet rsMessages = selectMessages.executeQuery();

                while (rsMessages.next()) {
                    //text,subject,personName,firstName,dateSent,read,recipientID
                    String text = rsMessages.getString(1);
                    String subject = rsMessages.getString(2);
                    String sender = rsMessages.getString(3) + " " + rsMessages.getString(4);
//                    String dateSent = DateFormat.getDateInstance().format(rsMessages.getDate(5)) + " "
//                            + DateFormat.getTimeInstance().format(rsMessages.getTimestamp(5));
                    Date date = rsMessages.getTimestamp(5);
                    String read = rsMessages.getString(6);
                    int recipient = rsMessages.getInt(7);
                    int senderID = rsMessages.getInt(8);
                    // int recipient, String sender, String subject, String date, String text, boolean read)

                    messages.add(new Message(recipient, senderID, sender, subject, date, text, read));
                }
            } finally {
                conn.close();
            }
        } catch (Exception ex) {
            System.out.println("error in getMessages: problems with connection : " + ex.getMessage());
        }
        //System.out.println("found " + messages.size() + " messages.");
        return messages;
    }

    public static ArrayList<Person> getRenters(int ownerID) {
        ArrayList<Person> renters = new ArrayList<Person>();
        try {
            Connection conn = geefVerbinding();
            try {
                PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectRenters);
                ps.setInt(1, ownerID);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()) {
                    int renterID = rs.getInt(1);
                    String name = rs.getString(2);
                    String firstName = rs.getString(3);
                    String email = rs.getString(4);
                    String telephone = rs.getString(5);
                    String cellphone = rs.getString(6);
                    //int id, String name, String firstName, String email, String telephone, String cellphone
                    renters.add(new Person(renterID,name,firstName,email,telephone,cellphone));
                }

            } finally {
                conn.close();
            }
        } catch(Exception ex) {
            System.out.println("error retrieving renters: "+ex.getMessage());
        }

        return renters;
    }

    public static void updateMessageState(Message m) {
        try {
            Connection conn = geefVerbinding();

            try {
                //setMessageReplied = "update " + tableMessages + " set "+
                //read + " = 2 where " + text + " = ?  and "+senderID+" = ? and "+recipientID+" = ? and "
                //+dateSent+" = ?";
                PreparedStatement ps = conn.prepareStatement(DataBaseConstants.setMessageReplied);
                ps.setString(1, m.getRead());
                ps.setString(2, m.getText());
                ps.setInt(3, m.getSenderID());
                ps.setInt(4,m.getRecipient());
                ps.setTimestamp(5, new java.sql.Timestamp(m.getDate().getTime()));

                ps.execute();
            } finally {
                conn.close();
            }
        } catch(Exception ex) {
            System.out.println("Error sending message: " + ex.getMessage());
        }
    }

    public static void removeMessage(Message m) {
        try {
            Connection conn = geefVerbinding();

            try {
                //removeMessage = "delete from " + tableMessages +
                //" where " + text + " = ?  and "+senderID+" = ? and "+recipientID+" = ? and "+dateSent+" = ?";
                PreparedStatement ps = conn.prepareStatement(DataBaseConstants.removeMessage);
                ps.setString(1, m.getText());
                ps.setInt(2, m.getSenderID());
                ps.setInt(3,m.getRecipient());
                ps.setTimestamp(4, new java.sql.Timestamp(m.getDate().getTime()));

                ps.execute();
            } finally {
                conn.close();
            }
        } catch(Exception ex) {
            System.out.println("Error removing message: " + ex.getMessage());
        }
    }
    
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
        } catch(Exception ex) {
            System.out.println("Error sending message: " + ex.getMessage());
        }

    }

    public static Building getBuilding(int buildingID) throws SQLException {
        Building building = null;
        try {
            Connection conn = geefVerbinding();
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

    public static ArrayList<Rentable> getRentablesFromBuilding(int buildingID) throws SQLException {
        ArrayList<Rentable> rentables = new ArrayList<Rentable>();
        try {
            Connection conn = geefVerbinding();
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

    static Rentable getRentable(int rentableID) throws SQLException {
        Rentable rentable = null;
        try {
            Connection conn = geefVerbinding();
            try {
                //TODO get images for building
                PreparedStatement ps = conn.prepareStatement(DataBaseConstants.selectRentable);
                ps.setInt(1, rentableID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    rentable = new Rentable(
                            rentableID,
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

