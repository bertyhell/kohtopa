package gui.invoicestab;

import Language.Language;
import gui.Layout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import data.entities.Invoice;
import java.awt.Color;
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
	private JLabel lblRoleOwn;
	private JLabel lblNameOwn;
	private JLabel lblStreetLineOwn;
	private JLabel lblCityLineOwn;
	private JLabel lblCountryOwn;
	private JLabel lblEmailOwn;
	private JLabel lblTelephoneOwn;
	private JLabel lblCellphoneOwn;
	private JLabel lblRoleRent;
	private JLabel lblNameRent;
	private JLabel lblStreetLineRent;
	private JLabel lblCityLineRent;
	private JLabel lblCountryRent;
	private JLabel lblEmailRent;
	private JLabel lblTelephoneRent;
	private JLabel lblCellphoneRent;
	private JComboBox cbbMonthFrom;
	private JComboBox cbbYearFrom;
	private JComboBox cbbMonthTo;
	private JComboBox cbbYearTo;
	private DefaultTableModel tmInvoices;

	public InvoiceDialog(int rentInvoiceId, boolean newInvoice) {
		invoice = new Invoice(rentInvoiceId, newInvoice);
		instance = this;
		setTitle(Language.getString(newInvoice ? "invoiceAdd" : "invoiceEdit"));
		this.setIconImage(new ImageIcon(getClass().getResource("/images/invoice_64.png")).getImage());
		this.setPreferredSize(new Dimension(600, 750));
		this.setMinimumSize(new Dimension(350, 405));
		this.setLayout(new BorderLayout());

		JPanel pnlInfo = new JPanel(new BorderLayout());
		//pnlInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.add(pnlInfo, BorderLayout.CENTER);

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel pnlHoofding = new JPanel(gbl);
		pnlHoofding.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		pnlInfo.add(pnlHoofding, BorderLayout.PAGE_START);

		//homeowner info
		//TODO put owner and renter in seperate panels with border
		lblRoleOwn = new JLabel(Language.getString("homeOwner"));
		Layout.buildConstraints(gbc, 0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblRoleOwn, gbc);
		pnlHoofding.add(lblRoleOwn);

		lblNameOwn = new JLabel(invoice.getOwner().toString());
		Layout.buildConstraints(gbc, 0, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblNameOwn, gbc);
		pnlHoofding.add(lblNameOwn);

		lblStreetLineOwn = new JLabel(invoice.getOwner().getAddress().getStreetLine());
		Layout.buildConstraints(gbc, 0, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblStreetLineOwn, gbc);
		pnlHoofding.add(lblStreetLineOwn);

		lblCityLineOwn = new JLabel(invoice.getOwner().getAddress().getCityLine());
		Layout.buildConstraints(gbc, 0, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCityLineOwn, gbc);
		pnlHoofding.add(lblCityLineOwn);

		lblCountryOwn = new JLabel(Language.getCountryByCode(invoice.getOwner().getAddress().getCountry()));
		Layout.buildConstraints(gbc, 0, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCountryOwn, gbc);
		pnlHoofding.add(lblCountryOwn);

		lblEmailOwn = new JLabel(invoice.getOwner().getEmail());
		Layout.buildConstraints(gbc, 0, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblEmailOwn, gbc);
		pnlHoofding.add(lblEmailOwn);

		lblTelephoneOwn = new JLabel(Language.getString("telephone") + ": " + invoice.getOwner().getTelephone());
		Layout.buildConstraints(gbc, 0, 6, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblTelephoneOwn, gbc);
		pnlHoofding.add(lblTelephoneOwn);

		lblCellphoneOwn = new JLabel(Language.getString("cellphone") + ": " + invoice.getOwner().getTelephone());
		Layout.buildConstraints(gbc, 0, 7, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCellphoneOwn, gbc);
		pnlHoofding.add(lblCellphoneOwn);






		//renter info
		lblRoleRent = new JLabel(Language.getString("renter"));
		Layout.buildConstraints(gbc, 1, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblRoleRent, gbc);
		pnlHoofding.add(lblRoleRent);

		lblNameRent = new JLabel(invoice.getRenter().toString());
		Layout.buildConstraints(gbc, 1, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblNameRent, gbc);
		pnlHoofding.add(lblNameRent);

		lblStreetLineRent = new JLabel(invoice.getRenter().getAddress().getStreetLine());
		Layout.buildConstraints(gbc, 1, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblStreetLineRent, gbc);
		pnlHoofding.add(lblStreetLineRent);

		lblCityLineRent = new JLabel(invoice.getRenter().getAddress().getCityLine());
		Layout.buildConstraints(gbc, 1, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCityLineRent, gbc);
		pnlHoofding.add(lblCityLineRent);

		lblCountryRent = new JLabel(Language.getCountryByCode(invoice.getOwner().getAddress().getCountry()));
		Layout.buildConstraints(gbc, 1, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCountryRent, gbc);
		pnlHoofding.add(lblCountryRent);

		lblEmailRent = new JLabel(invoice.getRenter().getEmail());
		Layout.buildConstraints(gbc, 1, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblEmailRent, gbc);
		pnlHoofding.add(lblEmailRent);

		lblTelephoneRent = new JLabel(Language.getString("telephone") + ": " + invoice.getRenter().getTelephone());
		Layout.buildConstraints(gbc, 1, 6, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblTelephoneRent, gbc);
		pnlHoofding.add(lblTelephoneRent);

		lblCellphoneRent = new JLabel(Language.getString("cellphone") + ": " + invoice.getRenter().getTelephone());
		Layout.buildConstraints(gbc, 1, 7, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCellphoneRent, gbc);
		pnlHoofding.add(lblCellphoneRent);

		JPanel pnlDates = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlDates.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		Layout.buildConstraints(gbc, 0, 8, 2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(pnlDates, gbc);
		pnlHoofding.add(pnlDates);

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
		System.out.println("language string description: " + Language.getString("description"));
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
		JTable tableInvoices = new JTable(tmInvoices);
		scrollerItems.setViewportView(tableInvoices);

		tableInvoices.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableInvoices.getColumnModel().getColumn(1).setMaxWidth(100);
		tableInvoices.setColumnSelectionAllowed(false);
		tableInvoices.getTableHeader().setReorderingAllowed(false);
		tableInvoices.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableInvoices.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		tableInvoices.setAutoCreateRowSorter(true);


		//adding Invoice items

		tmInvoices.addRow(new Object[]{"test", 1});



		//buttons
		Box boxButtons = Box.createHorizontalBox();
		boxButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//		pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(boxButtons, BorderLayout.PAGE_END);

		JButton btnAddInvoiceItem = new JButton("", new ImageIcon(getClass().getResource("/images/add_23.png")));
		btnAddInvoiceItem.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				tmInvoices.addRow(new Object[]{Language.getString("newInvoiceItem"), 0});
			}
		});
		boxButtons.add(btnAddInvoiceItem);

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

