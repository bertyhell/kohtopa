package gui.invoicestab;

import data.DataModel;
import data.entities.Person;
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
	private JList lstInvoices;

	public InvoicesPane(DataModel data) {
		this.setLayout(new BorderLayout());

		//adding lists of renters, invoices and detail invoice editor
		JSplitPane sppUserlistInvoicesList = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.add(sppUserlistInvoicesList, BorderLayout.CENTER);

		//list of renters
		lstRenters = new JList(data.getRenters());
		lstRenters.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstRenters.setBackground(new Color(217, 217, 217));
		lstRenters.setCellRenderer(new RenterCellRenderer());
		JScrollPane scrolRenters = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrolRenters.setViewportView(lstRenters);
		sppUserlistInvoicesList.add(scrolRenters, 0);

		//list of invoices
		lstInvoices = new JList(); //TODO add invoices
		if(lstRenters.getSelectedValue()!= null){
			data.getInvoices(((Person)lstRenters.getSelectedValue()).getId());
		}
		lstInvoices.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstInvoices.setBackground(new Color(217, 217, 217));
		lstInvoices.setCellRenderer(new RenterCellRenderer());
		JScrollPane scrolInvoices = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrolInvoices.setViewportView(lstInvoices);
		sppUserlistInvoicesList.add(scrolInvoices, 1);

	}

	public Object[] getSelectedRenters(){
		return lstRenters.getSelectedValues();
	}

	public Object[] getSelectedInvoices(){
		return lstInvoices.getSelectedValues();
	}
}
