package gui.addremovetab;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public interface IBuildingListContainer extends IIdentifiable{

	public Object[] getSelectedBuildings();
	public void updateBuildingList();
}
