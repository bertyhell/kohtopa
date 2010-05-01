/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.contractstab;

import data.DataModel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Ruben
 */
public class ContractsPane extends JPanel {

    private JList lstContracts;

    public ContractsPane(DataModel data) {
        this.setLayout(new BorderLayout());

        JSplitPane sppUserlistContractsList = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.add(sppUserlistContractsList, BorderLayout.CENTER);

        //list of contracts
		lstContracts = new JList(data.getContracts());
		lstContracts.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstContracts.setBackground(new Color(217, 217, 217));
		lstContracts.setCellRenderer(new ContractCellRenderer());
		JScrollPane scrolContracts = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrolContracts.setViewportView(lstContracts);
		sppUserlistContractsList.add(scrolContracts, 0);
    }

    public Object[] getSelectedContracts(){
		return lstContracts.getSelectedValues();
	}

}
