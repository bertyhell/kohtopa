package gui.actions;

import Language.Language;
import data.entities.Person;
import gui.Main;
import gui.invoicestab.InvoiceDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author Bert Verhelst
 */
public class InvoiceAddAction extends AbstractIconAction {

	public InvoiceAddAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object[] selected = Main.getInvoicesPane().getSelectedRenters();
		if (selected.length != 1) {
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errSelectOneRenter") + "\n", Language.getString("error"), JOptionPane.INFORMATION_MESSAGE);
		} else {
			new InvoiceDialog(true).setVisible(true);
		}
	}
}
