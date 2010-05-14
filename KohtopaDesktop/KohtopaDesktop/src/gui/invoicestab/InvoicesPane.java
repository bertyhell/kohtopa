package gui.invoicestab;

import Language.Language;
import data.DataModel;
import data.entities.Person;
import gui.Logger;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
		JScrollPane scrollRenters = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollRenters.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), Language.getString("renters")));
		scrollRenters.setViewportView(lstRenters);
		lstRenters.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					lstInvoices.setListData(Main.getDataObject().getInvoicesPreviews(((Person) lstRenters.getSelectedValue()).getId()));
				} catch (SQLException ex) {
					Logger.logger.error("Exception in collecting invoices on select renter: " + ex.getMessage());
				}
			}
		});

		sppUserlistInvoicesList.add(scrollRenters, 0);

		//list of invoices
		if (lstRenters.getSelectedIndices().length != 0) {
			try {
				lstInvoices = new JList(data.getInvoicesPreviews(((Person) lstRenters.getSelectedValue()).getId()));
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(Main.getInstance(), ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		} else {
			lstInvoices = new JList();
		}
		
		lstInvoices.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstInvoices.setBackground(new Color(217, 217, 217));
		lstInvoices.setCellRenderer(new RenterCellRenderer());
		JScrollPane scrolInvoices = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrolInvoices.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), Language.getString("invoices")));
		scrolInvoices.setViewportView(lstInvoices);
		sppUserlistInvoicesList.add(scrolInvoices, 1);

	}

	public Object[] getSelectedRenters() {
		return lstRenters.getSelectedValues();
	}

	public Object[] getSelectedInvoices() {
		return lstInvoices.getSelectedValues();
	}
}
