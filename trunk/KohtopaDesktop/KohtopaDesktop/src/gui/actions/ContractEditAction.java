/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions;

import Language.Language;
import data.entities.Contract;
import gui.Main;
import gui.contractstab.ContractDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author Ruben
 */
public class ContractEditAction extends AbstractIconAction {

    public ContractEditAction(String id, Icon img) {
        super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object[] selected = Main.getContractsPane().getSelectedContracts();
        // TODO: bug waarbij het antal geselecteerde contracten na het heen en weer switchen tussen dit tabblad
        // en andere tabbladen altijd 0 is ook al is er 1 geselecteerd
        if (selected.length != 1) {
            JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errSelectOneContract") + "\n", Language.getString("error"), JOptionPane.INFORMATION_MESSAGE);
        } else {
            new ContractDialog((Contract)selected[0]).setVisible(true);
		}
    }

}
