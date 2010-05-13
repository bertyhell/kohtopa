package gui.interfaces;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public interface IContractsListContainer extends IIdentifiable{

	public Object[] getSelectedContracts();
	public void updateContractList();
}
