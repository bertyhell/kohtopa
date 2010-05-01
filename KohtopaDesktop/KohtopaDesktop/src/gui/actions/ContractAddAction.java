/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * @author Ruben
 */
public class ContractAddAction extends AbstractIconAction {

	public ContractAddAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		Object[] selected = Main.getContractsPane().getSelectedRenters();
//		if (selected.length != 1) {
//			//TODO fix bug: go to invoice>add>cancel, go to different tab, back to invoices>add >failes :s
//			System.out.println("aantal selected: " + selected.length);
//			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errSelectOneRenter") + "\n", Language.getString("error"), JOptionPane.INFORMATION_MESSAGE);
//		} else {
//			new InvoiceDialog(((Person) selected[0]).getId(), true).setVisible(true);
//		}
	}
}
