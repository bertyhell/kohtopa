/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions;

import gui.contractstab.ContractDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

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
        new ContractDialog(null).setVisible(true);
	}
}
