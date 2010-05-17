package gui.actions;

import Exceptions.WrongNumberOfSelectedItemsException;
import Language.Language;
import data.entities.Invoice;
import gui.JActionButton;
import gui.Main;
import gui.interfaces.IInvoiceListContainer;
import gui.invoicestab.InvoiceDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author bert
 */
public class InvoiceEditAction extends AbstractIconAction {

	public InvoiceEditAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IInvoiceListContainer root = null;
		root = (IInvoiceListContainer) ((JActionButton) e.getSource()).getRoot();
		try {
			new InvoiceDialog(root.getRenterId(), ((Invoice) root.getSelectedInvoices()[0]).getId(), false).setVisible(true);
		} catch (WrongNumberOfSelectedItemsException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Please select exactly 1 renter \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}
}
