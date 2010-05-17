package gui.actions;

import Language.Language;
import data.entities.Invoice;
import gui.Main;
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

		Object[] selected =  Main.getInvoicesPane().getSelectedInvoices();
		Object[] renters = Main.getInvoicesPane().getSelectedRenters();
                if(renters.length != 1) {
                    JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errSelectOneRenter") + "\n", Language.getString("error"), JOptionPane.INFORMATION_MESSAGE);
                } else if(selected.length != 1) {
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errSelectOneInvoice") + "\n", Language.getString("error"), JOptionPane.INFORMATION_MESSAGE);
		} else {
			new InvoiceDialog(false).setVisible(true);
		}
	}
}
