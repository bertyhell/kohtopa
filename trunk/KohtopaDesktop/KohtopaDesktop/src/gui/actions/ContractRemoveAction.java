/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions;

import gui.Main;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

/**
 *
 * @author Ruben
 */
public class ContractRemoveAction extends AbstractIconAction {

    public ContractRemoveAction(String id, Icon img) {
        super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Main.getContractsPane().removeSelectedContract();
    }

}
