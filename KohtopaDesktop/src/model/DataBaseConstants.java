package model;

public class DataBaseConstants {

	//database table labels
	public static String tableAddressess = "addresses";
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
	public static String buildingName = "name";
	//rentables column labels
	public static String rentableType = "type";
	public static String rentableArea = "area";
	public static String windowDirection = "window_direction";
	public static String windowsArea = "window_area";
	public static String internet = "internet";
	public static String cable = "cable";
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
	public static String pictureType = "type";
	public static String pictureData = "data";
	//dataconnector connection strings
	//driver and connectionstring for oracle express
	public static String un = "system";
	public static String pw = "e=mc**2";
	public static String driver = "oracle.jdbc.OracleDriver";
	//public static String connectiestring = "jdbc:oracle:thin:@localhost:1521:XE";
	public static String connectiestring = "jdbc:oracle:thin:@192.168.58.128:1521:kohtopa";
	//dataconnector statement strings
	public static String selectBuildingPreviews = "SELECT " + buildingId + "," + streetNumber + "," + street + "," + zipCode + "," + city + " FROM " + tableBuildings + " b join " + tableAddressess + " a on a." + addressId + " = b." + addressId;
	public static String selectNextId = "with x as ( select columnName, rank() over(order by columnName)-2147483649 as rn from tableName), y as (select columnName, case  when rn <> columnName then rn else null end as rn from x) select case when min(rn) is null then case when count(1) is null then -2147483648 else count(1) - 2147483648 end else min(rn) end as id from y";
	public static String selectPictureIds = "select " + pictureId + " from " + tablePictures + " where " + rentableId + " = ?";
	public static String selectPictureData = "select " + pictureData + " from " + tablePictures + " where " + pictureId + " = ?";
	public static String insertPicture = "INSERT INTO " + tablePictures + " VALUES (0,?,?,?)";
	public static String selectBuildingPictures = "SELECT " + pictureData + "," + pictureId + " FROM " + tablePictures + " WHERE " + rentableId + " IN (SELECT " + rentableId + " FROM " + tableRentables + " WHERE " + buildingId + " = ?";
	public static String selectRentablePictures = "SELECT " + pictureData + "," + pictureId + " FROM " + tablePictures + " WHERE " + rentableId + " = ?";
}