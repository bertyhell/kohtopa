package data;

public class DataBaseConstants {

    //database table labels
    public static String tableAddresses = "addresses";
    public static String tableRoles = "roles";
    public static String tablePersons = "persons";
    public static String tableBuildings = "buildings";
    public static String tableRentables = "rentables";
    public static String tableFurniture = "furniture";
    public static String tableContracts = "contracts";
    public static String tableConsumption = "consumption";
    public static String tableMessages = "messages";
    public static String tablePictures = "pictures";
    //addresses column labels
    public static String addressId = "addressid";
    public static String streetNumber = "street_number";
    public static String street = "street";
    public static String zipCode = "zipcode";
    public static String city = "city";
    public static String country = "country";
    //roles column labels
    public static String roleId = "roleid";
    public static String websiteAccess = "websiteAccess";
    public static String guiAccess = "GUI_access";
    //persons column labels
    public static String personId = "personid";
    public static String personName = "name";
    public static String firstName = "first_name";
    public static String username = "username";
    public static String password = "password";
    public static String email = "email";
    public static String telephone = "telephone";
    public static String cellphone = "cellphone";
    //buildings column labels
    public static String buildingId = "buildingid";
    //rentables column labels
    public static String rentableId = "rentableid";
    public static String rentableType = "type";
    public static String rentableArea = "area";
    public static String windowDirection = "window_direction";
    public static String windowsArea = "window_area";
    public static String internet = "internet";
    public static String cable = "cable";
    public static String floor = "floor";
    public static String outletCount = "outlet_count";
    public static String rentableDescription = "description";
    //furniture column labels
    public static String furnitureId = "furnitureId";
    public static String furnitureName = "name";
    public static String price = "price";
    public static String damaged = "damaged";
    //consumption column labels
    public static String consumptionId = "consumptionid";
    public static String gas = "gas";
    public static String water = "water";
    public static String electricity = "electricity";
    public static String dataConsumption = "data_consumption";
    //messages column labels
    public static String senderId = "senderid";
    public static String recipientId = "recipientid";
    public static String dateSent = "date_sent";
    public static String text = "text";
    public static String subject = "subject";
    public static String read = "message_read";
    //contracts column labels
    public static String ownerId = "ownerid";
    public static String renterId = "renterid";
    public static String contractStart = "contract_start";
    public static String contractEnd = "contract_end";
    //pictures column labels
    public static String pictureType = "type_floor";      //-1: rentable        -2: building      -3: building preview     0-n: building floor
    public static String pictureId = "pictureid";
    public static String RentBuildId = "rentable_building_id";
    public static String pictureData = "picture";
    //dataconnector connection strings
    //driver and connectionstring for oracle express
    public static String un = "system";
    public static String pw = "e=mc**2";
    public static String driver = "oracle.jdbc.OracleDriver";
    //public static String connectiestring = "jdbc:oracle:thin:@localhost:1521:XE";
    public static String connectiestring = "jdbc:oracle:thin:@192.168.58.128:1521:kohtopa"; //laptop bert
    //public static String connectiestring = "jdbc:oracle:thin:@192.168.19.128:1521:kohtopa";   //pc bert
    //dataconnector statement strings
    public static String checkLogin = "SELECT " + personId
	    + " FROM " + tablePersons
	    + " WHERE " + username + " = ? AND " + password + " = ?";
    public static String selectOwners = "SELECT " + personId + "," + personName + ","
	    + firstName + "," + email + "," + telephone + "," + cellphone
	    + " FROM " + tablePersons + " b"
	    + " WHERE " + roleId + " = owner";
    public static String selectBuildingPreviews = "SELECT " + buildingId + "," + pictureData + ","
	    + street + "," + streetNumber + "," + zipCode + "," + city
	    + " FROM " + tableBuildings + " b"
	    + " JOIN " + tableAddresses + " a on a." + addressId + " = b." + addressId
	    + " LEFT JOIN " + tablePictures + " p ON p." + RentBuildId + " = b." + buildingId
	    + " AND p." + pictureType + " = -3"
	    + " ORDER BY " + street;
    public static String selectBuilding = "SELECT"
	    + " a." + street
	    + ",a." + streetNumber
	    + ",a." + zipCode
	    + ",a." + city
	    + ",a." + country
	    + " FROM " + tableAddresses + " a"
	    + " INNER JOIN " + tableBuildings + " b ON a." + addressId + " = b." + addressId
	    + " WHERE b." + buildingId + " = ?";
    public static String selectRentable = "SELECT "
	    + rentableType
	    + "," + rentableArea
	    + "," + windowsArea
	    + "," + windowDirection
	    + "," + internet
	    + "," + cable
	    + "," + outletCount
	    + "," + floor
	    + "," + price
	    + "," + rentableDescription
	    + " FROM " + tableRentables
	    + " WHERE " + rentableId + " = ?";
    public static String selectRentablesFromBuilding = "SELECT " + rentableId + "," + floor + "," + rentableType + "," + rentableDescription
	    + " FROM " + tableRentables
	    + " WHERE " + buildingId + " = ?"
	    + " ORDER BY " + floor;
    //pictures
    public static String insertPicture = "INSERT INTO " + tablePictures + " VALUES (0,?,?,?)";
    public static String selectBuildingPictures = "SELECT " + pictureData + "," + pictureId
	    + " FROM " + tablePictures
	    + " WHERE " + RentBuildId + " = ? AND " + pictureType + " = -2";
    public static String selectRentablePictures = "SELECT " + pictureData + "," + pictureId
	    + " FROM " + tablePictures
	    + " WHERE " + RentBuildId + " = ? AND " + pictureType + " = -1";
    public static String selectMessage = buildString("select @,@,@,@,@,@,@ from @ join @ on @ = @ where @=?",
	    text, subject, personName, firstName, dateSent, read, recipientId, tableMessages, tablePersons, personId, senderId, recipientId);
    public static String selectMessages = buildString("select @,@,@,@,@,@,@ from @ join @ on @ = @ order by " + read,
	    text, subject, personName, firstName, dateSent, read, recipientId, tableMessages, tablePersons, personId, senderId);

    // create string, stuff to fill in: @
    private static String buildString(String base, String... data) {
	String[] parts = base.split("@");
	//System.out.println(parts.length + ", " + data.length);
	StringBuffer sb = new StringBuffer(parts[0]);
	for (int i = 1; i < parts.length; i++) {
	    sb.append(data[i - 1]);
	    sb.append(parts[i]);
	}
	//System.out.println(sb);
	return sb.toString();
    }
}
