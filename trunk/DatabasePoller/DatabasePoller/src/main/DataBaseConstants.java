package main;

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
	public static String pw = "admin";
//	public static String pw = "e=mc**2"; //ruben
	public static String driver = "oracle.jdbc.driver.OracleDriver";
	public static String connectiestring = "jdbc:oracle:thin:@localhost:1521:XE"; //jelle & ruben
//	public static String connectiestring = "jdbc:oracle:thin:@192.168.58.128:1521:kohtopa"; //laptop bert
	//dataconnector statement strings

	public static String selectInvoicesToBeSend = "SELECT * FROM " + tableInvoices +
			" WHERE " + invoiceDate + " >= sysdate AND " + invoiceSend + " = 0";

        public static String selectBuildingsToBeDeleted = "with x as( "
            + "select b.buildingid,max(r.rentableid) as rentableid "
            + "from buildings b "
            + "left join rentables r on r.buildingid = b.buildingid "
            + "group by b.buildingid)"
            + " select buildingid "
            + "from x "
            + "where rentableid is null ";

       public static String deleteBuildingById = "delete from buildings where buildingid = ?";

       public static String selectAddressesToBeDeleted = "with x as(select a.addressid,b.buildingid "
            + "from addresses a "
            + "left join buildings b on a.addressid = b.addressid "
            + "),y as( "
            + "select  x.addressid,p.personid "
            + "from x x "
            + "left join persons p on p.addressid = x.addressid "
            + "where x.buildingid is null ) "
            + "select y.addressid "
            + "from y y "
            + "where y.personid is null ";

       public static String deleteAddressById = "delete from addresses where addressid = ?";
}
