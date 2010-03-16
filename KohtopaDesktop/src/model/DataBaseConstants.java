package model;

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
	public static String firstName = "firstName";
	public static String username = "username";
	public static String password = "password";
	public static String email = "email";
	public static String telephone = "telephone";
	public static String cellphone = "cellphone";
	//buildings column labels
	public static String buildingId = "buildingid";
	//rentables column labels
	public static String rentableTypeFloor = "type_floor";      //-1: rentable        -2: building      -3: building preview     0-n: building floor
	public static String rentableArea = "area";
	public static String windowDirection = "window_direction";
	public static String windowsArea = "window_area";
	public static String internet = "internet";
	public static String cable = "cable";
	public static String floor = "floor";
	public static String outletCount = "outletcount";
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
	public static String dataSent = "date_sent";
	public static String text = "text";
	public static String rentableId = "rentableId";
	//contracts column labels
	public static String ownerId = "ownerid";
	public static String renterId = "renterid";
	public static String contractStart = "contract_start";
	public static String contractEnd = "contract_end";
	//pictures column labels
	public static String pictureId = "pictureid";
	public static String RentBuildId = "rentable_building_id";
	public static String pictureType = "type_floor";
	public static String pictureData = "picture";
	//dataconnector connection strings
	//driver and connectionstring for oracle express
	public static String un = "system";
	public static String pw = "e=mc**2";
	public static String driver = "oracle.jdbc.OracleDriver";
	//public static String connectiestring = "jdbc:oracle:thin:@localhost:1521:XE";
	public static String connectiestring = "jdbc:oracle:thin:@192.168.58.128:1521:kohtopa";
	//dataconnector statement strings
	public static String selectBuildingPreviews = "SELECT " + buildingId + "," + pictureData + ","
			+ street + "," + streetNumber + "," + zipCode + "," + city
			+ " FROM " + tableBuildings + " b"
			+ " JOIN " + tableAddresses + " a on a." + addressId + " = b." + addressId
			+ " LEFT JOIN " + tablePictures + " p ON p." + RentBuildId + " = b." + buildingId
			+ " AND p." + pictureType + " = -3";
	//pictures
	public static String insertPicture = "INSERT INTO " + tablePictures + " VALUES (0,?,?,?)";
	public static String selectBuildingPictures = "SELECT " + pictureData + "," + pictureId
			+ " FROM " + tablePictures
			+ " WHERE " + RentBuildId + " = ? AND " + pictureType + " = -2";
	public static String selectRentablePictures = "SELECT " + pictureData + "," + pictureId
			+ " FROM " + tablePictures
			+ " WHERE " + RentBuildId + " = ? AND " + pictureType + " = -1";
}
