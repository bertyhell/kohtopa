/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions;

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
		//TODO 000 only able to delete contract before active else error
    }

}
