package gui.actions;

import Language.Language;
import data.entities.Invoice;
import gui.JActionButton;
import gui.Main;
import gui.interfaces.IInvoiceListContainer;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author bert
 */
public class InvoiceRemoveAction extends AbstractIconAction {

    public InvoiceRemoveAction(String id, Icon img) {
        super(id, img);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		Vector<Integer> selected = new Vector<Integer>();
		for (Object invoice : ((IInvoiceListContainer) ((JActionButton) e.getSource()).getRoot()).getSelectedInvoices()) {
			selected.add(((Invoice) invoice).getId());
		}
		if (JOptionPane.showConfirmDialog(
				null,
				Language.getString("confirmDelete") + " " + selected.size() + " " + Language.getString("invoice_s"), //TODO 000 change building(s) to building if 1 else buildings
				Language.getString("confirm"),
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			try {
				//delete invoice
				Main.getDataObject().deleteSelectedInvoices(selected);
				Main.updateInvoiceList();
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(Main.getInstance(), "You can not delete invoices that have been send.", Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}