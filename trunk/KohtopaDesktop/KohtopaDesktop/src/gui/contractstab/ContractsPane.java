package gui.contractstab;

import gui.interfaces.IContractsListContainer;
import Language.Language;
import data.DataModel;
import data.entities.Contract;
import data.entities.Person;
import gui.Main;
import gui.interfaces.IRenterListContainer;
import gui.invoicestab.RenterCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class ContractsPane extends JPanel implements IContractsListContainer, IRenterListContainer {

	private JList lstRenters;
	private JList lstContracts;

	public ContractsPane(DataModel data) {
		this.setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500, 600));

		JSplitPane sppUserlistContractsList = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppUserlistContractsList.setDividerSize(10);
		sppUserlistContractsList.setResizeWeight(0.2);
		sppUserlistContractsList.setDividerLocation(250);
		add(sppUserlistContractsList, BorderLayout.CENTER);



		//list of contracts on the left
		lstRenters = new JList(data.getRenters());
		lstRenters.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstRenters.setBackground(new Color(217, 217, 217));
		lstRenters.setCellRenderer(new RenterCellRenderer());
		JScrollPane scrollRenters = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollRenters.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), Language.getString("renters")));
		scrollRenters.setViewportView(lstRenters);
		lstRenters.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				lstContracts.setListData(Main.getDataObject().getPreviewContractsFromRenter(((Person)lstRenters.getSelectedValue()).getId()));
			}
		});
		sppUserlistContractsList.add(scrollRenters, 0);



		//list of contracts on the right
		lstContracts = new JList();
		lstContracts.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstContracts.setBackground(new Color(217, 217, 217));
		lstContracts.setCellRenderer(new ContractCellRenderer());
		lstContracts.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					new ContractEditDialog(((Contract) lstContracts.getSelectedValue()).getId(),Main.getDataObject()).setVisible(true);
				}
			}
		});

		//TODO 050 enable disable actions on right moments
		JScrollPane scrollContracts = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollContracts.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), Language.getString("contracts")));
		scrollContracts.setViewportView(lstContracts);
		sppUserlistContractsList.add(scrollContracts, 1);



	}

	public Object[] getSelectedContracts() {
		return lstContracts.getSelectedValues();
	}

	public void updateContractList() {
		if(lstRenters.getSelectedIndex() != -1){
			lstContracts.setListData(Main.getDataObject().getPreviewContractsFromRenter(((Person)lstRenters.getSelectedValue()).getId()));
		}else{
			lstContracts.setListData(new Object[]{});
		}
	}

	public int getId() {
		return ((Contract) lstContracts.getSelectedValue()).getId();
	}

	public Object[] getSelectedRenters() {
		return lstRenters.getSelectedValues();
	}

	public void updateRenterList() {
		lstRenters.setListData(Main.getDataObject().getRenters());
	}
}
