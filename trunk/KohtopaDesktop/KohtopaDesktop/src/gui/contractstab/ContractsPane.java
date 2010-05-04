/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.contractstab;

import data.DataConnector;
import data.DataModel;
import data.ProgramSettings;
import data.entities.Contract;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Ruben
 */
public class ContractsPane extends JPanel implements ListSelectionListener {

    private JList lstContracts;
    private Vector<Contract> contracts;

    public ContractsPane(DataModel data) {
        this.setLayout(new BorderLayout());

        JSplitPane sppUserlistContractsList = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sppUserlistContractsList.setDividerSize(5);
        sppUserlistContractsList.setResizeWeight(0.2);
        sppUserlistContractsList.setDividerLocation(250);
        add(sppUserlistContractsList, BorderLayout.CENTER);

        //list of contracts on the left
        contracts = data.getContracts(ProgramSettings.getUserID());
		lstContracts = new JList(contracts);
		lstContracts.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstContracts.setBackground(new Color(217, 217, 217));
		lstContracts.setCellRenderer(new ContractCellRenderer());
        lstContracts.addListSelectionListener(this);

        
        Main.getAction("contractEdit").setEnabled(false);
        Main.getAction("contractRemove").setEnabled(false);
        
		JScrollPane scrolContracts = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrolContracts.setViewportView(lstContracts);
		sppUserlistContractsList.add(scrolContracts, 0);

        JScrollPane scrolContractInfo = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sppUserlistContractsList.add(scrolContractInfo, 0);
    }

    public void valueChanged(ListSelectionEvent e) {
        Main.getAction("contractEdit").setEnabled(lstContracts.getSelectedIndex() != -1);
        Main.getAction("contractRemove").setEnabled(lstContracts.getSelectedIndex() != -1);
        this.updateUI();
    }

    public Object[] getSelectedContracts(){
		return lstContracts.getSelectedValues();
	}

     /**
     * Removes the selected contract
     */
    public void removeSelectedContract() {
        if (lstContracts.getSelectedIndex() != -1) {
            int[] indices = lstContracts.getSelectedIndices();
            int tmp = 0;
            for (int index: indices) {
                System.out.println("removing: " + (tmp+1));
                Contract contract = contracts.get(index);
//                DataConnector.removeContract(contract);
                lstContracts.remove(index - tmp);
                contracts.remove(index - tmp);
                tmp++;
            }
            lstContracts.setListData(contracts);
        }
        this.updateUI();
    }

}
