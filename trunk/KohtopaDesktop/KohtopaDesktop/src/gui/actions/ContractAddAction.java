package gui.actions;

import gui.Main;
import gui.contractstab.ContractDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

/**
 *
 * @author Bert
 */
public class ContractAddAction extends AbstractIconAction {

	public ContractAddAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new ContractDialog(0, true,Main.getDataObject());
	}
}
