package gui.contractstab;

import Language.Language;
import data.DataConnector;
import data.DataModel;
import data.entities.Contract;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
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
		setPreferredSize(new Dimension(500, 600));

        JSplitPane sppUserlistContractsList = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sppUserlistContractsList.setDividerSize(10);
        sppUserlistContractsList.setResizeWeight(0.2);
        sppUserlistContractsList.setDividerLocation(250);
        add(sppUserlistContractsList, BorderLayout.CENTER);

        //list of contracts on the left
		lstContracts = new JList(data.getContracts());
		lstContracts.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstContracts.setBackground(new Color(217, 217, 217));
		lstContracts.setCellRenderer(new ContractCellRenderer());
        lstContracts.addListSelectionListener(this);
        
        Main.getAction("contractEdit").setEnabled(false);
        Main.getAction("contractRemove").setEnabled(false);
        
		JScrollPane scrollContracts = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollContracts.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), Language.getString("contracts")));
		scrollContracts.setViewportView(lstContracts);
		sppUserlistContractsList.add(scrollContracts, 0);

        //contract info on the right
        JScrollPane scrollContractInfo = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollContractInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), Language.getString("contract")));
		sppUserlistContractsList.add(scrollContractInfo, 1);
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
        // TODO: bug waarbij het antal geselecteerde contracten na het heen en weer switchen tussen dit tabblad
        // en andere tabbladen altijd 0 is ook al is er 1 geselecteerd
        if (lstContracts.getSelectedIndex() != -1) {
            DefaultListModel dlm = (DefaultListModel)lstContracts.getModel();
            int[] indices = lstContracts.getSelectedIndices();
            int tmp = 0;
            for (int index: indices) {
                Contract contract = (Contract)contracts.get(index);
                DataConnector.removeContract(contract);
                dlm.removeElementAt(index - tmp);
                contracts.remove(index - tmp);
                tmp++;
            }
        }
        this.updateUI();
    }
}
