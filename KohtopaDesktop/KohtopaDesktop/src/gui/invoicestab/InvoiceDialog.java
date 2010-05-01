package gui.invoicestab;

import Language.Language;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import data.entities.Invoice;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Bert Verhelst
 */
public class InvoiceDialog extends JFrame {

	private InvoiceDialog instance;
	private Invoice invoice;
	private JButton btnConfirm;
	private JComboBox cbbMonthFrom;
	private JComboBox cbbYearFrom;
	private JComboBox cbbMonthTo;
	private JComboBox cbbYearTo;
	private DefaultTableModel tmInvoices;
	private JTable tableInvoices;

	public InvoiceDialog(int rentInvoiceId, boolean newInvoice) {
		invoice = new Invoice(rentInvoiceId, newInvoice);
		instance = this;
		setTitle(Language.getString(newInvoice ? "invoiceAdd" : "invoiceEdit"));
		this.setIconImage(new ImageIcon(getClass().getResource("/images/invoice_64.png")).getImage());
		this.setPreferredSize(new Dimension(600, 750));
		this.setMinimumSize(new Dimension(510, 405));
		this.setLayout(new BorderLayout());

		//total
		JPanel pnlInfo = new JPanel(new BorderLayout());
		this.add(pnlInfo, BorderLayout.CENTER);

		//header (persons and dates)
		JPanel pnlHeader = new JPanel(new BorderLayout());
		pnlInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		pnlInfo.add(pnlHeader, BorderLayout.PAGE_START);

		pnlHeader.add(new PersonPanel(invoice.getOwner(), Language.getString("homeOwner")), BorderLayout.LINE_START);
		pnlHeader.add(new PersonPanel(invoice.getRenter(), Language.getString("renter")), BorderLayout.LINE_END);

		//dates
		JPanel pnlDates = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlDates.setBorder(BorderFactory.createEmptyBorder(20, 0,20, 0));
		pnlHeader.add(pnlDates, BorderLayout.PAGE_END);

		pnlDates.add(new JLabel(Language.getString("invoiceInterval")));

		cbbMonthFrom = new JComboBox(Language.getMonthsOfYear()); //TODO set selected date to last factuur date
		pnlDates.add(cbbMonthFrom);

		cbbYearFrom = new JComboBox();
		int year = GregorianCalendar.getInstance().get(Calendar.YEAR);
		for (int i = year; i > year - 100; i--) {
			cbbYearFrom.addItem(i);
		}
		cbbYearFrom.setSelectedIndex(0);
		pnlDates.add(cbbYearFrom);

		pnlDates.add(new JLabel(Language.getString("to")));

		cbbMonthTo = new JComboBox(Language.getMonthsOfYear()); //TODO set selected date to last factuur date
		pnlDates.add(cbbMonthTo);

		cbbYearTo = new JComboBox();
		for (int i = year; i > year - 100; i--) {
			cbbYearTo.addItem(i);
		}
		cbbYearTo.setSelectedIndex(0);
		pnlDates.add(cbbYearTo);




		//invoice details
		JScrollPane scrollerItems = new JScrollPane();
		pnlInfo.add(scrollerItems, BorderLayout.CENTER);
		tmInvoices = new DefaultTableModel(
				new Object[][]{},
				new String[]{Language.getString("description"), Language.getString("price") + " (€)"}) {

			@Override
			public Class getColumnClass(int columnIndex) {
				if (columnIndex == 0) {
					return String.class;
				} else {
					return Integer.class;
				}
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return true;
			}
		};
		tableInvoices = new JTable(tmInvoices);
		scrollerItems.setViewportView(tableInvoices);

		tableInvoices.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableInvoices.getColumnModel().getColumn(1).setMaxWidth(100);
		tableInvoices.setColumnSelectionAllowed(false);
		tableInvoices.getTableHeader().setReorderingAllowed(false);
		tableInvoices.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableInvoices.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableInvoices.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		tableInvoices.setAutoCreateRowSorter(true);


		//adding Invoice items


		//buttons
		Box boxButtons = Box.createHorizontalBox();
		boxButtons.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		this.add(boxButtons, BorderLayout.PAGE_END);

		JButton btnAddInvoiceItem = new JButton("", new ImageIcon(getClass().getResource("/images/add_23.png")));
		btnAddInvoiceItem.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				tmInvoices.addRow(new Object[]{Language.getString("newInvoiceItem"), 0});
			}
		});
		boxButtons.add(btnAddInvoiceItem);

		JButton btnRemoveInvoiceItem = new JButton("", new ImageIcon(getClass().getResource("/images/remove_23.png")));
		btnRemoveInvoiceItem.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {

				int[] rows = tableInvoices.getSelectedRows();
				for (int i = rows.length - 1; i >= 0; i--) {
					tmInvoices.removeRow(rows[i]);
				}
			}
		});
		boxButtons.add(btnRemoveInvoiceItem);

		boxButtons.add(Box.createHorizontalGlue());


		JButton btnCancel = new JButton(Language.getString("cancel"), new ImageIcon(getClass().getResource("/images/cancel.png")));
		btnCancel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				instance.dispose();
			}
		});
		boxButtons.add(btnCancel);

		btnConfirm = new JButton("", new ImageIcon(getClass().getResource("/images/ok.png")));
		if (newInvoice) {
			//add new invoice
			System.out.println("adding add handler");
			btnConfirm.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
				}
			});
		} else {
			//update invoice in database
			System.out.println("adding update handler");
			btnConfirm.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
				}
			});
		}
		boxButtons.add(btnConfirm);



		//info opvullen:
		fillInfo(newInvoice);
		pack();
		setLocationRelativeTo(null);
	}

	public void fillInfo(boolean isNew) {
		if (isNew) {
			//clear fields

			btnConfirm.setText(Language.getString("add"));
		} else {
			//fill building info

			btnConfirm.setText(Language.getString("update"));
		}
	}
}

