package gui.actions;

import Language.Language;
import data.entities.Contract;
import gui.JActionButton;
import gui.Main;
import gui.contractstab.ContractEditDialog;
import gui.interfaces.IContractsListContainer;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author Bert
 */
public class ContractEditAction extends AbstractIconAction {

	public ContractEditAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object[] selected = ((IContractsListContainer) ((JActionButton) e.getSource()).getRoot()).getSelectedContracts();
		if (selected.length == 1) {
			new ContractEditDialog(((Contract) selected[0]).getId(), Main.getDataObject()).setVisible(true);
		} else {
			JOptionPane.showMessageDialog(Main.getInstance(), "Please select exacly 1 rentable to edit", Language.getString("error"), JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
