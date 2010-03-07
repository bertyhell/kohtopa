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
	public static String type = "type";
	public static String area = "area";
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
	public static String recipientic = "recipientic";
	public static String dataSent = "date_sent";
	public static String text = "text";




	//dataconnector connection strings
    public static String un = "kohtopa";
    public static String pw = "e=mc**2";
    public static String driver = "com.mysql.jdbc.Driver";
    public static String connectiestring = "jdbc:mysql://localhost:3306/kohtopa";

	
	//dataconnector statement strings
    public static String selectBuildingPreviews = "SELECT " + buildingId + "," + streetNumber + "," + street + "," + zipCode + "," + city + " FROM " + tableBuildings + " b join " + tableAddressess + " a on a." + addressId + " = b." + addressId;
}