package gui.invoicestab;

import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Bert Verhelst
 */
public class InvoicesPane extends JPanel {

	private JList lstRenters;

	public InvoicesPane() {
		this.setLayout(new BorderLayout());

		//adding lists of renters, invoices and detail invoice editor
		JSplitPane sppListDetail = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JSplitPane sppUserlistInvoicesList = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppListDetail.add(sppUserlistInvoicesList, 0);
		this.add(sppListDetail, BorderLayout.CENTER);

		//list of renters

		lstRenters = new JList(Main.getDataObject().getRenters());
		lstRenters.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstRenters.setBackground(new Color(217, 217, 217));
		lstRenters.setCellRenderer(new RenterCellRenderer());
		JScrollPane scrolBuilding = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrolBuilding.setViewportView(lstRenters);
		sppUserlistInvoicesList.add(scrolBuilding, 0);


	}
}
