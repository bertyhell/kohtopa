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
	public static String tableInvoices = "invoices";
	public static String tableConsumption = "consumption";
	public static String tableConstants = "constants";
	public static String tableMessages = "messages";
	public static String tablePictures = "pictures";
	public static String tableTasks = "tasks";
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
	//dataconnector connection strings
	//driver and connectionstring for oracle express
	public static String un = "system";
//	public static String pw = "admin";
	public static String pw = "e=mc**2"; //ruben
	public static String driver = "oracle.jdbc.driver.OracleDriver";
//	public static String connectiestring = "jdbc:oracle:thin:@localhost:1521:XE"; //jelle & ruben
	public static String connectiestring = "jdbc:oracle:thin:@192.168.58.128:1521:kohtopa"; //laptop bert







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
			+ " FROM " + tablePersons + " p"
			+ " JOIN " + tableAddresses + " a ON a." + addressID + " = p." + addressID
			+ " WHERE " + personID + " = ?";
	public static String selectRentPrice = "SELECT "
			+ price + "," + guarantee
			+ " FROM " + tableContracts
			+ " WHERE " + renterID + " = ? and sysdate between " + contract_start + " and " + contract_end;
	public static String insertInvoice = "INSERT INTO " + tableInvoices + " VALUES (0, ("
			+ " SELECT " + contractID
			+ " FROM ("
			+ " SELECT " + contractID + ", rank() over(order by " + contract_end + " asc) rank"
			+ " FROM " + tableContracts
			+ " WHERE " + renterID + " = ? and sysdate - " + contract_end + " >= 0)"
			+ " WHERE rank=1) ,?,0,?,0)";
	public static String selectRentPriceFinal = "WITH x AS( SELECT "
			+ price + "," + guarantee + ", rank() over(order by " + contract_end + " asc) rank"
			+ " FROM " + tableContracts
			+ " WHERE " + renterID + " = ? and sysdate - " + contract_end + " >= 0)"
			+ " SELECT " + price
			+ " FROM x WHERE rank = 1";
	public static String selectUtilities = "SELECT "
			+ "u." + gasPrice
			+ ",u." + waterPrice
			+ ",u." + electricityPrice
			+ " FROM " + tableContracts + " c"
			+ " JOIN " + tableRentables + " r ON r." + rentableID + " = c." + rentableID
			+ " JOIN " + tableConstants + " u ON u." + buildingID + " = r." + buildingID
			+ " WHERE c." + renterID + " = ? and sysdate between c." + contract_start + " and c." + contract_end;
	public static String selectBuildingPreviews = "SELECT " + buildingID + "," + pictureData + ","
			+ street + "," + streetNumber + "," + zipCode + "," + city + ","
			+ latitude + "," + longitude + "," + country
			+ " FROM " + tableBuildings + " b"
			+ " JOIN " + tableAddresses + " a on a." + addressID + " = b." + addressID
			+ " LEFT JOIN " + tablePictures + " p ON p." + RentBuildID + " = b." + buildingID
			+ " AND p." + pictureType + " = -4"
			+ " ORDER BY " + street;
	public static String selectRentablePreviewsFromBuilding = "SELECT " + rentableID + "," + pictureData + "," + floor + "," + rentableType + "," + rentableDescription
			+ " FROM " + tableRentables + " r"
			+ " LEFT JOIN " + tablePictures + " p ON p." + RentBuildID + " = r." + rentableID
			+ " AND p." + pictureType + " = -2"
			+ " WHERE r." + buildingID + " = ?";
	public static String selectRenterPreviews = "SELECT p." + personID + ", p." + firstName + ", p." + personName
			+ " FROM " + tablePersons + " p"
			+ " JOIN " + tableContracts + " c ON p." + personID + " = c." + renterID
			+ " JOIN " + tableRentables + " r ON c." + rentableID + " = r." + rentableID
			+ " WHERE r." + ownerID + " = ?";
	public static String selectBuilding = "SELECT"
			+ " a." + street
			+ ",a." + streetNumber
			+ ",a." + zipCode
			+ ",a." + city
			+ ",a." + country
			+ ",b." + latitude
			+ ",b." + longitude
			+ " FROM " + tableAddresses + " a"
			+ " INNER JOIN " + tableBuildings + " b ON a." + addressID + " = b." + addressID
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
			+ "," + rentableDescription
			+ " FROM " + tableRentables
			+ " WHERE " + rentableID + " = ?";
	public static String selectRentablesFromBuilding = "SELECT " + rentableID + "," + floor + "," + rentableType + "," + rentableDescription
			+ " FROM " + tableRentables
			+ " WHERE " + buildingID + " = ?"
			+ " ORDER BY " + floor;
    
    public static String selectRentablesFromOwner = "SELECT " + rentableID + ", " + buildingID + ", " + rentableType + ", " + description + ", " + rentableArea + ", " + windowDirection + ", " + windowArea + ", " + internet + ", " + cable + ", " + outletCount + ", " + floor + ", " + rented + ", " + price +
                                                    " FROM " + tableRentables + " WHERE " + ownerID + " = ?";

	public static String selectRentablesFromUser =
			" select " + rentableID + "," + rentableType + "," + floor + "," + rentableDescription
			+ " from " + tableRentables + " r "
			+ " join " + tablePersons + " p on r." + ownerID + " = p." + personID
			+ " where " + ownerID + " = ?";
	public static String addBuilding = "INSERT INTO " + tableBuildings + " VALUES (0,?,0,0)";
	public static String updateBuilding = "UPDATE " + tableBuildings + " SET " + addressID + " = ? WHERE " + buildingID + " = ?";
	public static String checkAddress = "SELECT " + addressID
			+ " FROM " + tableAddresses
			+ " WHERE " + street + "= ? AND " + streetNumber + "=? AND " + zipCode + "=? AND " + city + "=?";
	public static String addAddress = "INSERT INTO " + tableAddresses + " VALUES (0,?,?,?,?,'BE')"; //TODO add country
	//pictures
	public static String insertPicture = "INSERT INTO " + tablePictures + " VALUES (0,?,?,?)";
	public static String deletePicture = "DELETE FROM " + tablePictures + " WHERE " + pictureID + " = ?";
	public static String selectBuildingPictures = "SELECT " + pictureData + "," + pictureID
			+ " FROM " + tablePictures
			+ " WHERE " + RentBuildID + " = ? AND " + pictureType + " = -3";
	public static String selectRentablePictures = "SELECT " + pictureData + "," + pictureID
			+ " FROM " + tablePictures
			+ " WHERE " + RentBuildID + " = ? AND " + pictureType + " = -1";
	// messages
	public static String setMessageReplied = "update " + tableMessages + " set "
			+ read + " = ? where " + text + " = ?  and " + senderID + " = ? and " + recipientID + " = ? and " + dateSent + " = ?";
	public static String removeMessage = "delete from " + tableMessages
			+ " where " + text + " = ?  and " + senderID + " = ? and " + recipientID + " = ? and " + dateSent + " = ?";
	public static String selectMessage = buildString("select @,@,@,@,@,@,@,@ from @ join @ on @ = @ where @=? order by 6 asc ,5 desc",
			text, subject, personName, firstName, dateSent, read, recipientID, senderID, tableMessages, tablePersons, personID, senderID, recipientID);
	public static String selectMessages = buildString("select @,@,@,@,@,@,@,@ from @ join @ on @ = @ order by 6 asc ,5 desc",
			text, subject, personName, firstName, dateSent, read, recipientID, senderID, tableMessages, tablePersons, personID, senderID);
	public static String insertMessage = "insert into messages values(?,?,?,?,?,?)";
	public static String selectRenters = "SELECT DISTINCT p." + personID + ", p." + personName + ",p." + firstName
			+ ", p." + email + ", p." + telephone + ",p." + cellphone
			+ ",a." + street + ",a." + streetNumber + ",a." + zipCode + ",a." + city + ",a." + country
			+ " FROM " + tableContracts + " c "
			+ " JOIN " + tableRentables + " r on c." + rentableID + " = r." + rentableID
			+ " JOIN " + tablePersons + " p on p." + personID + " = c." + renterID
			+ " JOIN " + tableAddresses + " a on a." + addressID + " = p." + addressID
			+ " WHERE " + ownerID + " = ?";
	// tasks
	public static String selectTasks = buildString("select @,@,@,@,@,@  from @ ", taskID, rentableID, description, start_time, end_time, repeats_every, tableTasks);
	public static String insertTask = buildString("insert into @ values (0,?,?,?,?,?)", tableTasks);
	public static String deleteTask = buildString("delete from @ where @ = ? and @ = ? and @ = ?",
			tableTasks, rentableID, description, start_time);
	public static String updateTask = buildString(
			"update @ set(@, @, @, @, @) = "
			+ "(select ?,?,?,?,? from dual)"
			+ "where @ = ? and @ = ? and @ = ?",
			tableTasks, rentableID, description, start_time, end_time, repeats_every,
			rentableID, description, start_time);
	// contracts
	public static String selectContracts = "SELECT DISTINCT c." + contractID + ", c." + rentableID + ", c." + renterID + ", c." + contract_start + ", c." + contract_end + ", c." + price + ", c." + monthly_cost + ", c." + guarantee
			+ ", r." + buildingID + ", r." + ownerID + ", r." + rentableType + ", r." + description + ", r." + rentableArea + ", r." + windowDirection + ", r." + windowArea + ", r." + internet + ", r." + cable + ", r." + outletCount + ", r." + floor + ", r." + rented + ", r." + price
			+ ", p." + addressID + ", p." + roleID + ", p." + personName + ", p." + firstName + ", p." + email + ", p." + telephone + ", p." + cellphone + ", p." + username + ", p." + password
			+ ", a." + streetNumber + ", a." + street + ", a." + zipCode + ", a." + city + ", a." + country + " FROM " + tableContracts + " c"
			+ " JOIN " + tableRentables + " r on r." + rentableID + " = c." + rentableID
			+ " JOIN " + tablePersons + " p on p." + personID + " = c." + renterID
			+ " JOIN " + tableAddresses + " a on a." + addressID + " = p." + addressID
			+ " WHERE r." + ownerID + " = ?";
	public static String removeContract = "DELETE FROM " + tableContracts + " WHERE " + contractID + " = ?";

	// create string, stuff to fill in: @
	// contracts
	//public static String selectContracts = "SELECT " + contractID + ", " + rentableID + ", " + renterID + ", " + contract_start + ", " + contract_end + "," + price + ", " + monthly_cost + ", " + guarantee + " FROM " + contracts;
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
