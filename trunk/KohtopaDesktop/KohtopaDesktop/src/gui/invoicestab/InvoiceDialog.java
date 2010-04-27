package gui.invoicestab;

import Language.CountryNotFoundException;
import Language.Language;
import gui.Layout;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import data.entities.Invoice;

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

	public InvoiceDialog(int rentInvoiceId, boolean newInvoice) {
		invoice = new Invoice(rentInvoiceId, newInvoice);
		instance = this;
		setTitle(Language.getString(newInvoice ? "invoiceAdd" : "invoiceEdit"));
		this.setIconImage(new ImageIcon(getClass().getResource("/images/invoice_64.png")).getImage());
		this.setPreferredSize(new Dimension(1000, 600));
		this.setMinimumSize(new Dimension(600, 405));
		this.setLayout(new BorderLayout());

		JPanel pnlInfo = new JPanel(new BorderLayout());

		//top
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();


		JPanel pnlHoofding = new JPanel(gbl);
		pnlInfo.add(pnlHoofding, BorderLayout.PAGE_START);

		//homeowner info
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
		try {
			lblCountryOwn = new JLabel(Language.getCountryByCode(invoice.getOwner().getAddress().getCountry()));
			Layout.buildConstraints(gbc, 0, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
			gbl.addLayoutComponent(lblCountryOwn, gbc);
			pnlHoofding.add(lblCountryOwn);
		} catch (CountryNotFoundException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errCountryCodeNotFound") + "\n" + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
		}

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

		lblNameRent = new JLabel(invoice.getOwner().toString());
		Layout.buildConstraints(gbc, 1, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblNameRent, gbc);
		pnlHoofding.add(lblNameRent);

		lblStreetLineRent = new JLabel(invoice.getOwner().getAddress().getStreetLine());
		Layout.buildConstraints(gbc, 1, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblStreetLineRent, gbc);
		pnlHoofding.add(lblStreetLineRent);

		lblCityLineRent = new JLabel(invoice.getOwner().getAddress().getCityLine());
		Layout.buildConstraints(gbc, 1, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCityLineRent, gbc);
		pnlHoofding.add(lblCityLineRent);
		try {
			lblCountryRent = new JLabel(Language.getCountryByCode(invoice.getOwner().getAddress().getCountry()));
			Layout.buildConstraints(gbc, 1, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
			gbl.addLayoutComponent(lblCountryRent, gbc);
			pnlHoofding.add(lblCountryRent);
		} catch (CountryNotFoundException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errCountryCodeNotFound") + "\n" + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
		}

		lblEmailRent = new JLabel(invoice.getOwner().getEmail());
		Layout.buildConstraints(gbc, 1, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblEmailRent, gbc);
		pnlHoofding.add(lblEmailRent);

		lblTelephoneRent = new JLabel(Language.getString("telephone") + ": " + invoice.getOwner().getTelephone());
		Layout.buildConstraints(gbc, 1, 6, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblTelephoneRent, gbc);
		pnlHoofding.add(lblTelephoneRent);

		lblCellphoneRent = new JLabel(Language.getString("cellphone") + ": " + invoice.getOwner().getTelephone());
		Layout.buildConstraints(gbc, 1, 7, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCellphoneRent, gbc);
		pnlHoofding.add(lblCellphoneRent);

		//buttons
		JPanel pnlButtons = new JPanel();
		pnlButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
		pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(pnlButtons, BorderLayout.PAGE_END);
		JButton btnCancel = new JButton(Language.getString("cancel"), new ImageIcon(getClass().getResource("/images/cancel.png")));
		btnCancel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				instance.dispose();
			}
		});
		pnlButtons.add(btnCancel);

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
		pnlButtons.add(btnConfirm);

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

