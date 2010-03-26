package data.addremove;

import data.DataConnector;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import data.entities.Rentable;
import javax.swing.DefaultListModel;

public class RentableListModel extends DefaultListModel {

    public void addElement(Rentable Rentable) {
        super.addElement(Rentable);
    }

    public Rentable getRentableAt(int index){
	return (Rentable)super.getElementAt(index);
    }

    public void updateItems(int buildingId) throws SQLException, IOException {
        ArrayList<Rentable> items = DataConnector.getRentablesFromBuilding(buildingId);
	super.clear();
	for (Rentable rentable : items) {
	    super.addElement(rentable);
	}
    }
}
