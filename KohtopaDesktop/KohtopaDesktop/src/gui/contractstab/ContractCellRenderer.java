/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.contractstab;

import data.entities.Contract;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Ruben
 */
public class ContractCellRenderer implements ListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Contract contract = (Contract)list.getModel().getElementAt(index);
        ContractListPanel pnlContract = new ContractListPanel(contract.getId(), null,
                                                              contract.getRenter().getFirstName(),
                                                              contract.getRenter().getName(),                                                              
                                                              contract.getStart(),
                                                              contract.getEnd());
        //TODO: alle juiste gegevens ophalen
        if (isSelected) {
            pnlContract.setBgColor(new Color(150, 150, 150));
        } else {
            if (index % 2 == 0) {
				pnlContract.setBgColor(Color.LIGHT_GRAY);
			} else {
				pnlContract.setBgColor(new Color(170, 170, 170));
			}
        }

        return pnlContract;
    }

}
