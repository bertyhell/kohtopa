package data;

public class DataBaseConstants {

	//dataconnector connection strings
	//driver and connectionstring for oracle express
	public static String un = "system";
//	public static String pw = "admin";
	public static String pw = "e=mc**2"; //ruben
	public static String driver = "oracle.jdbc.driver.OracleDriver";
	//connection string is now under data.programsettings or in file settings.xml
	//database table labels
	public static String tableAddresses = "addresses";
	public static String tableAddressesRead = "system.addressesview";
	public static String tableAddressesWrite = "system.changeaddressesview";
	public static String tableAddressNotConnected = "system.notconnectedaddressesview";
	public static String tableRoles = "roles";
	public static String tableRolesRead = "system.rolesview";
	public static String tableRolesWrite = "system.changerolesview";
	public static String tablePersons = "persons";
	public static String tablePersonsRead = "system.personsview";
	public static String tablePersonsWrite = "system.changepersonsview";
	public static String tableBuildings = "buildings";
	public static String tableBuildingsRead = "system.buildingsview";
	public static String tableBuildingsWrite = "system.changebuildingsview";
	public static String tableRentables = "rentables";
	public static String tableRentablesRead = "system.rentablesview";
	public static String tableRentablesWrite = "system.changerentablesview";
	public static String tableFurniture = "furniture";
	public static String tableFurnitureRead = "system.furnitureview";
	public static String tableFurnitureWrite = "system.changefurnitureview";
	public static String tableContracts = "contracts";
	public static String tableContractsRead = "system.contractsview";
	public static String tableContractsWrite = "system.changecontractsview";
	public static String tableInvoices = "invoices";
	public static String tableInvoicesRead = "system.invoicesview";
	public static String tableInvoicesWrite = "system.changeinvoicesview";
	public static String tableConsumption = "consumption";
	public static String tableConsumptionRead = "system.consumptionview";
	public static String tableConsumptionWrite = "system.changeconsumptionview";
	public static String tableConstants = "constants";
	public static String tableConstantsRead = "system.constantsview";
	public static String tableConstantsWrite = "system.changeconstantsview";
	public static String tableMessages = "messages";
	public static String tableMessagesRead = "system.messagesview";
	public static String tableMessagesWrite = "system.changemessagesview";
	public static String tablePictures = "pictures";
	public static String tablePicturesRead = "system.picturesview";
	public static String tablePicturesWrite = "system.changepicturesview";
	public static String tableTasks = "tasks";
	public static String tableTasksRead = "system.tasksview";
	public static String tableTasksWrite = "system.changetasksview";
    public static String tableFloors = "floors";
    public static String tableFloorsRead = "system.floorsview";
    public static String tableFloorsWrite = "system.changefloorsview";
	//addresses column labels
	public static String addressID = "addressid";
	public static String streetNumber = "street_number";
	public static String street = "street";
	public static String zipCode = "zipcode";
	public static String city = "city";
	public static String country = "country";
	//roles column labels
	public static String roleID = "roleid";
	public static String websiteAccess = "websiteAccess";
	public static String guiAccess = "GUI_access";
	//persons column labels
	public static String personID = "personid";
	public static String personName = "name";
	public static String firstName = "first_name";
	public static String username = "username";
	public static String password = "password";
	public static String email = "email";
	public static String telephone = "telephone";
	public static String cellphone = "cellphone";
	//buildings column labels
	public static String buildingID = "buildingid";
	public static String latitude = "latitude";
	public static String longitude = "longitude";
    public static String ipaddress = "ipaddress";
	//rentables column labels
	public static String rentableID = "rentableid";
	public static String rentableType = "type";
	public static String rentableArea = "area";
	public static String windowDirection = "window_direction";
	public static String windowArea = "window_area";
	public static String internet = "internet";
	public static String cable = "cable";
	public static String floor = "floor";
	public static String outletCount = "outlet_count";
	public static String rented = "rented";
	public static String rentableDescription = "description";
	//furniture column labels
	public static String furnitureID = "furnitureID";
	public static String furnitureName = "name";
	public static String price = "price";
	public static String damaged = "damaged";
	//consumption column labels
	public static String consumptionID = "consumptionid";
	public static String gas = "gas";
	public static String water = "water";
	public static String electricity = "electricity";
	public static String dataConsumption = "data_consumption";
	//constants column labels
	public static String gasPrice = "gasprice";
	public static String waterPrice = "waterprice";
	public static String electricityPrice = "electricityprice";
	//messages column labels
	public static String senderID = "senderid";
	public static String recipientID = "recipientid";
	public static String dateSent = "date_sent";
	public static String text = "text";
	public static String subject = "subject";
	public static String read = "message_read";
	//contracts column labels
	public static String contracts = "contracts";
	public static String contractID = "contractid";
	public static String renterID = "renterid";
	public static String ownerID = "ownerid";
	public static String contract_start = "contract_start";
	public static String contract_end = "contract_end";
	public static String monthly_cost = "monthly_cost";
	public static String guarantee = "guarantee";
	//invoices column labels
	public static String invoiceId = "invoiceid";
	public static String invoiceDate = "invoicedate";
	public static String invoicePaid = "paid";
	public static String invoiceXml = "invoice_xml";
	public static String invoiceSend = "send";
	//tasks column labels
	public static String taskID = "taskID";
	public static String description = "description";
	public static String start_time = "start_time";
	public static String end_time = "end_time";
	public static String repeats_every = "repeats_every";
	//pictures column labels
	public static String pictureType = "type_floor";      //-1: rentable        -2: building      -3: building preview     0-n: building floor
	public static String pictureID = "pictureid";
	public static String RentBuildID = "rentable_building_id";
	public static String pictureData = "picture";
    public static String xml = "xml";
    public static String picture = "picture";

	//dataconnector statement strings
	public static String checkLogin = "SELECT " + personID
			+ " FROM " + tablePersons
			+ " WHERE " + username + " = ? AND " + password + " = ?";
	public static String selectOwners = "SELECT " + personID + "," + personName + ","
			+ firstName + "," + email + "," + telephone + "," + cellphone
			+ " FROM " + tablePersons + " b"
			+ " WHERE " + roleID + " = owner";
	public static String selectPerson = "SELECT "
			+ "p." + personName
			+ ",p." + firstName
			+ ",p." + email
			+ ",p." + telephone
			+ ",p." + cellphone
			+ ",a." + street
			+ ",a." + streetNumber
			+ ",a." + zipCode
			+ ",a." + city
			+ ",a." + country
			+ " FROM " + tablePersonsRead + " p"
			+ " JOIN " + tableAddressesRead + " a ON a." + addressID + " = p." + addressID
			+ " WHERE " + personID + " = ?";
	public static String selectRentPrice = "SELECT "
			+ price + "," + guarantee
			+ " FROM " + tableContractsRead
			+ " WHERE " + renterID + " = ? AND sysdate BETWEEN " + contract_start + " AND " + contract_end;
	public static String insertRentable = "INSERT INTO " + tableRentablesWrite + " VALUES (0,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static String updateRentable = "UPDATE " + tableRentablesWrite + " SET "
			+ rentableType + " =?, "
			+ rentableDescription + " =?, "
			+ rentableArea + " =?, "
			+ windowDirection + " =?, "
			+ windowArea + " =?, "
			+ internet + " =?, "
			+ cable + " =?, "
			+ outletCount + " =?, "
			+ floor + " =?, "
			+ price + " =? "
			+ " WHERE " + rentableID + " = ?";
		public static String selectInvoices = "SELECT "
			+ "i." + invoiceDate
			+ ",i." + invoiceId
			+ ",i." + invoicePaid
			+ ",i." + invoiceSend
			+ " FROM " + tableContractsRead + " c"
			+ " JOIN " + tableInvoicesRead + " i ON i." + contractID + " = c." + contractID
			+ " WHERE c." + renterID + " = ?"
			+ " ORDER BY i." + invoiceDate;
	public static String insertInvoice = "INSERT INTO " + tableInvoicesWrite + " VALUES (0, ("
			+ " SELECT " + contractID
			+ " FROM ("
			+ " SELECT " + contractID + ", rank() over(order by " + contract_end + " asc) rank"
			+ " FROM " + tableContractsRead
			+ " WHERE " + renterID + " = ? AND sysdate - " + contract_end + " >= 0)"
			+ " WHERE rank=1) ,?,0,?,0)";
	public static String selectRentPriceFinal = "WITH x AS( SELECT "
			+ price + "," + guarantee + ", rank() OVER(ORDER BY " + contract_end + " asc) rank"
			+ " FROM " + tableContractsRead
			+ " WHERE " + renterID + " = ? AND sysdate - " + contract_end + " >= 0)"
			+ " SELECT " + price  + "," + guarantee
			+ " FROM x WHERE rank = 1";
	public static String selectUtilities = "SELECT "
			+ "u." + gasPrice
			+ ",u." + waterPrice
			+ ",u." + electricityPrice
			+ " FROM " + tableContractsRead + " c"
			+ " JOIN " + tableRentablesRead + " r ON r." + rentableID + " = c." + rentableID
			+ " JOIN " + tableConstantsRead + " u ON u." + buildingID + " = r." + buildingID
			+ " WHERE c." + renterID + " = ? AND sysdate BETWEEN c." + contract_start + " AND c." + contract_end;
	public static String selectBuildingPreviews = "SELECT " + buildingID + "," + pictureData + ","
			+ street + "," + streetNumber + "," + zipCode + "," + city + ","
			+ latitude + "," + longitude + "," + country
			+ " FROM " + tableBuildingsRead + " b"
			+ " JOIN " + tableAddressesRead + " a ON a." + addressID + " = b." + addressID
			+ " LEFT JOIN " + tablePicturesRead + " p ON p." + RentBuildID + " = b." + buildingID
			+ " AND p." + pictureType + " = -4"
			+ " ORDER BY " + street;
	public static String selectRentablePreviewsFromBuilding = "SELECT " + rentableID + "," + pictureData + "," + floor + "," + rentableType + "," + rentableDescription
			+ " FROM " + tableRentablesRead + " r"
			+ " LEFT JOIN " + tablePicturesRead + " p ON p." + RentBuildID + " = r." + rentableID
			+ " AND p." + pictureType + " = -2"
			+ " WHERE r." + buildingID + " = ?";
	public static String selectRenterPreviews = "SELECT p." + personID + ", p." + firstName + ", p." + personName
			+ " FROM " + tablePersonsRead + " p"
			+ " JOIN " + tableContractsRead + " c ON p." + personID + " = c." + renterID
			+ " JOIN " + tableRentablesRead + " r ON c." + rentableID + " = r." + rentableID;
	public static String selectBuilding = "SELECT"
			+ " a." + street
			+ ",a." + streetNumber
			+ ",a." + zipCode
			+ ",a." + city
			+ ",a." + country
			+ ",b." + latitude
			+ ",b." + longitude
			+ " FROM " + tableAddressesRead + " a"
			+ " INNER JOIN " + tableBuildingsRead + " b ON a." + addressID + " = b." + addressID
			+ " WHERE b." + buildingID + " = ?";
	public static String selectRentable = "SELECT "
			+ rentableType
			+ "," + rentableArea
			+ "," + windowArea
			+ "," + windowDirection
			+ "," + internet
			+ "," + cable
			+ "," + outletCount
			+ "," + floor
			+ "," + price
			+ "," + rented
			+ "," + rentableDescription
			+ " FROM " + tableRentablesRead
			+ " WHERE " + rentableID + " = ?";
	public static String selectRentablesFromOwner = "SELECT "
			+ rentableID + ", "
			+ buildingID + ", "
			+ rentableType + ", "
			+ description + ", "
			+ rentableArea + ", "
			+ windowDirection + ", "
			+ windowArea + ", "
			+ internet + ", "
			+ cable + ", "
			+ outletCount + ", "
			+ floor + ", "
			+ rented + ", "
			+ price
			+ " FROM " + tableRentablesRead;
	public static String insertBuilding = "INSERT INTO " + tableBuildingsWrite + " VALUES (0,?,0,0,null)";
	public static String updateBuilding = "UPDATE " + tableBuildingsWrite + " SET " + addressID + " = ? WHERE " + buildingID + " = ?";
	public static String checkAddressConnected = "SELECT " + addressID
			+ " FROM " + tableAddressesRead
			+ " WHERE " + street + "= ? AND " + streetNumber + "=? AND " + zipCode + "=? AND " + city + "=? AND " + country + "=?";
	public static String checkAddressNotConnected = "SELECT " + addressID
			+ " FROM " + tableAddressNotConnected
			+ " WHERE " + street + "= ? AND " + streetNumber + "=? AND " + zipCode + "=? AND " + city + "=? AND " + country + "=?";
	public static String addAddress = "INSERT INTO " + tableAddressesWrite + " VALUES (0,?,?,?,?,?)";
	public static String selectFloors = "SELECT " + floor
			+ " FROM " + tableRentablesRead
			+ " WHERE " + buildingID + " = ?";
	public static String deleteBuilding = "DELETE FROM " + tableBuildingsWrite + " WHERE " + buildingID + " = ?";
	public static String deleteRentable = "DELETE FROM " + tableRentablesWrite + " WHERE " + rentableID + " = ?";
	//floors
    public static String insertFloor = "INSERT INTO " + tableFloorsWrite + " VALUES (?,?,?)";
    public static String selectFloor = "SELECT " + xml
            + " FROM "  + tableFloorsRead
            + " WHERE " + buildingID + " = ? AND " + floor + " = ?";
    //pictures
    public static String selectPicture = "SELECT " + picture
            + " FROM " + tablePicturesRead
            + " WHERE " + pictureID + " = ?";
    public static String checkPicture = "SELECT " + pictureID
            + " FROM " + tablePicturesRead
            + " WHERE " + RentBuildID + " = ? AND " + pictureType + " = ?";
	public static String insertPicture = "INSERT INTO " + tablePicturesWrite + " VALUES (0,?,?,?)";
	public static String deletePicture = "DELETE FROM " + tablePicturesWrite + " WHERE " + pictureID + " = ?";
	public static String selectBuildingPictures = "SELECT " + pictureData + "," + pictureID
			+ " FROM " + tablePicturesRead
			+ " WHERE " + RentBuildID + " = ? AND " + pictureType + " = -3";
	public static String selectRentablePictures = "SELECT " + pictureData + "," + pictureID
			+ " FROM " + tablePicturesRead
			+ " WHERE " + RentBuildID + " = ? AND " + pictureType + " = -1";
	// messages
	public static String updateMessageReplied = "update " + tableMessagesWrite
			+ " set " + read + " = ?"
			+ " WHERE " + text + " = ?"
			+ " AND " + senderID + " = ?"
			+ " AND " + recipientID + " = ?"
			+ " AND " + dateSent + " = ?";
	public static String selectMessage = "SELECT "
			+ text + ","
			+ subject + ","
			+ personName + ","
			+ firstName + ","
			+ dateSent + ","
			+ read + ","
			+ recipientID + ","
			+ senderID
			+ " from " + tableMessagesRead
			+ " join " + tablePersonsRead + " ON " + personID + " = " + senderID
			+ " where " + recipientID + "=?"
			+ " order by 6 asc ,5 desc";
	public static String insertMessage = "INSERT INTO " + tableMessagesWrite + " values(?,?,?,?,?,?)";
	public static String selectRenters = "SELECT DISTINCT p." + personID + ", p." + personName + ",p." + firstName
			+ ", p." + email + ", p." + telephone + ",p." + cellphone
			+ ",a." + street + ",a." + streetNumber + ",a." + zipCode + ",a." + city + ",a." + country
			+ " FROM " + tableContractsRead + " c "
			+ " JOIN " + tableRentablesRead + " r ON c." + rentableID + " = r." + rentableID
			+ " JOIN " + tablePersonsRead + " p ON p." + personID + " = c." + renterID
			+ " JOIN " + tableAddressesRead + " a ON a." + addressID + " = p." + addressID;
	// tasks
	public static String selectTasks = "SELECT "
			+ taskID + ","
			+ rentableID + ","
			+ description + ","
			+ start_time + ","
			+ end_time + ","
			+ repeats_every
			+ " from " + tableTasksRead;
	public static String insertTask = "INSERT INTO " + tableTasksWrite + " values (0,?,?,?,?,?)";
	public static String deleteTask = "DELETE FROM " + tableTasksWrite
			+ " where " + rentableID + " = ? AND " + description + " = ? AND " + start_time + " = ?";
	public static String updateTask = "update " + tableTasksWrite + " set("
			+ rentableID + ", "
			+ description + ", "
			+ start_time + ", "
			+ end_time + ", "
			+ repeats_every + ") = "
			+ "(SELECT ?,?,?,?,? from dual)"
			+ "where " + rentableID + " = ? AND " + description + " = ? AND " + start_time + " = ?";
	// contracts
	public static String selectContract = "SELECT  "
			+ contractID
			+ ", " + rentableID
			+ ", " + renterID
			+ ", " + contract_start
			+ ", " + contract_end
			+ ", " + price
			+ ", " + monthly_cost
			+ ", " + guarantee
			+ " FROM " + tableContractsRead
			+ " WHERE " + contractID + " = ?";
	public static String selectContracts = "SELECT  "
			+ contractID
			+ ", " + rentableID
			+ ", " + renterID
			+ ", " + contract_start
			+ ", " + contract_end
			+ ", " + price
			+ ", " + monthly_cost
			+ ", " + guarantee
			+ " FROM " + tableContractsRead;
	public static String selectPreviewContracts = "SELECT"
			+ "  c." + contractID
			+ ", c." + contract_start
			+ ", c." + contract_end
			+ ", p." + personID
			+ ", p." + firstName
			+ ", p." + personName
			+ " FROM " + tableContractsRead + " c"
			+ " JOIN " + tablePersonsRead + " p ON p." + personID + " = c." + renterID
			+ " WHERE c." + renterID + " = ?";
	public static String selectRenterInRentable = "SELECT "
			+ "p." + firstName
			+ ", p." + personName
			+ " FROM " + tableContractsRead + " c"
			+ " JOIN " + tablePersonsRead + " p ON p." + personID + " = c." + renterID
			+ " WHERE c." + rentableID + " = ?";
	public static String removeContract = "DELETE FROM " + tableContractsWrite + " WHERE " + contractID + " = ?";

    public static String selectIPAddress = "SELECT " + ipaddress
            + " FROM " + tableBuildingsRead
            + " WHERE " + buildingID + " = ?";
	public static String deletePictures = "DELETE FROM " + tablePicturesWrite
			+ " WHERE " + RentBuildID + " = ? AND " + pictureType + " = ?";
}
