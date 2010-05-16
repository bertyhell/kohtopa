package gui.interfaces;

import Exceptions.WrongNumberOfSelectedItemsException;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public interface IInvoiceListContainer extends IIdentifiable{

	public Object[] getSelectedInvoices();
	public int getRenterId() throws WrongNumberOfSelectedItemsException;
	public void updateInvoicesList();
}
