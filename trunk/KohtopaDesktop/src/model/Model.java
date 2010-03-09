package model;

import Language.Language;
import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Model {

	private static Model instance = new Model();

	public static Model getInstance(){
		return instance;
	}

	private Model() {
	}
	
	public void getBuildingPreviews(Component requesterFrame, ArrayList<Building> buildings) throws SQLException{
		DataConnector.selectBuildingPreviews(buildings);
	}





}
