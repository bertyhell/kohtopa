package databasepoller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * this class gets/puts data from/to database + caches information (rentables, buildings).
 * @author Jelle
 */
public class DataConnector {

	/**
	 * Creates the class, registers and starts the JDBC oracle-driver
	 */
	public static void init() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Class.forName(DataBaseConstants.driver);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (ClassNotFoundException ex) {
			System.out.println("failed to open driver: \n" + ex.getMessage());
		}
	}

	/**
	 * Makes a connection
	 * @return the connection
	 * @throws SQLException thrown if something goes wrong creating the connection
	 */
	private static Connection geefVerbinding() throws SQLException {
		return DriverManager.getConnection(DataBaseConstants.connectiestring, DataBaseConstants.un, DataBaseConstants.pw);
	}

	/**
	 * Fetches Invoices that need to be send
	 * @return an ArrayList of buildings
	 * @throws SQLException thrown if something goes wrong with the select statements
	 * @throws IOException thrown if there is a problem fetching the images
	 */
	public static Vector<String> selectInvoicesToBeSend() throws SQLException, IOException {
		Vector<String> invoices = new Vector<String>();
		Connection conn = geefVerbinding();
		try {
			Statement selectBuildings = conn.createStatement();
			ResultSet rsInvoices = selectBuildings.executeQuery(DataBaseConstants.selectInvoicesToBeSend);
			while (rsInvoices.next()) {
				//TODO check invoices
			}
		} finally {
			conn.close();
		}
		return invoices;
	}
}

