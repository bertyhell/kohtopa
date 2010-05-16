package tasks;

import java.util.Vector;
import main.DataConnector;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class RunnableClean implements Runnable{

	public void run() {
            
            try{
                Vector<Integer> buildingIds;
                buildingIds = DataConnector.selectBuildingsToBeDeleted();                
                DataConnector.removeBuildings(buildingIds);
                Vector<Integer> addressIds;
                addressIds = DataConnector.selectAddressesToBeDeleted();
                DataConnector.removeAddresses(addressIds);                
            }catch(Exception ex){
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
            System.out.println("cleaned database");
	}

}
