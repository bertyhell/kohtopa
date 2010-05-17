package gui.contractstab;

import Language.Language;
import data.DataConnector;
import data.DataModel;
import data.ProgramSettings;
import data.entities.Address;
import data.entities.Building;
import data.entities.Rentable;
import gui.Layout;
import gui.Logger;
import gui.Main;
import gui.invoicestab.PersonPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class ContractAddDialog extends JDialog {

	private ContractAddDialog instance;
	private JComboBox cbbMonthFrom;
	private JComboBox cbbYearFrom;
	private JComboBox cbbMonthTo;
	private JComboBox cbbYearTo;
	private JTextField txtPrice;
	private JTextField txtMonthlyCost;
	private JTextField txtGuarantee;
	private JComboBox cbbBuildings;
	private JComboBox cbbRentables;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtStreet;
	private JTextField txtZip;
	private JTextField txtCity;
	private JComboBox cbbCountry;
	private JTextField txtTel;
	private JTextField txtCellphone;
	private JTextField txtEmail;
	private JTextField txtStreetNumber;
	private JButton btnOK;

	public ContractAddDialog(DataModel data) {
		this.setTitle(Language.getString("contractAdd"));

		this.setLayout(new BorderLayout());


		instance = this;
		this.setIconImage(new ImageIcon(getClass().getResource("/images/user_23.png")).getImage());

		//info
		GridBagLayout gbl1 = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel pnlInfo = new JPanel(gbl1);
		pnlInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(pnlInfo, BorderLayout.CENTER); //TODO 000 add scrolpanes to all forms? or make minimal size

		//persons info
		JPanel pnlPersons = new JPanel(new GridLayout(1, 2));
		Layout.buildConstraints(gbc, 0, 0, 3, 1, 8, 4, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlPersons, gbc);
		pnlInfo.add(pnlPersons);

		//owner
		JPanel pnlOwner = new PersonPanel(Main.getDataObject().getPerson(ProgramSettings.getOwnerId()), Language.getString("homeOwner"));
		pnlPersons.add(pnlOwner);

		//renter
		GridBagLayout gbl = new GridBagLayout();
		JPanel pnlInputRenter = new JPanel(gbl);
		pnlPersons.add(pnlInputRenter);
		pnlInputRenter.setBorder(BorderFactory.createTitledBorder(Language.getString("renter")));
		pnlInputRenter.setMinimumSize(new Dimension(200, 300));
		pnlInputRenter.setPreferredSize(new Dimension(400, 300));

		int row = 0;

		JLabel lblFirstName = new JLabel(Language.getString("firstName") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblFirstName, gbc);
		pnlInputRenter.add(lblFirstName);

		txtFirstName = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtFirstName, gbc);
		pnlInputRenter.add(txtFirstName);

		JLabel lblLastName = new JLabel(Language.getString("lastName") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblLastName, gbc);
		pnlInputRenter.add(lblLastName);

		txtLastName = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtLastName, gbc);
		pnlInputRenter.add(txtLastName);

		JLabel lblStreet = new JLabel(Language.getString("street") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreet, gbc);
		pnlInputRenter.add(lblStreet);

		txtStreet = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreet, gbc);
		pnlInputRenter.add(txtStreet);

		JLabel lblStreetNumber = new JLabel(Language.getString("streetNumber") + ":");
		Layout.buildConstraints(gbc, 3, row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreetNumber, gbc);
		pnlInputRenter.add(lblStreetNumber);

		txtStreetNumber = new JTextField();
		txtStreetNumber.setColumns(3);
		Layout.buildConstraints(gbc, 4, row, 1, 1, 5, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreetNumber, gbc);
		pnlInputRenter.add(txtStreetNumber);

		JLabel lblZip = new JLabel(Language.getString("zipCode") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblZip, gbc);
		pnlInputRenter.add(lblZip);

		txtZip = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtZip, gbc);
		pnlInputRenter.add(txtZip);

		JLabel lblCity = new JLabel(Language.getString("city") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCity, gbc);
		pnlInputRenter.add(lblCity);

		txtCity = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtCity, gbc);
		pnlInputRenter.add(txtCity);

		JLabel lblCountry = new JLabel(Language.getString("country") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCountry, gbc);
		pnlInputRenter.add(lblCountry);

		cbbCountry = new JComboBox(Language.getCountries());
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(cbbCountry, gbc);
		pnlInputRenter.add(cbbCountry);

		JLabel lblTel = new JLabel(Language.getString("telephone") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblTel, gbc);
		pnlInputRenter.add(lblTel);

		txtTel = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtTel, gbc);
		pnlInputRenter.add(txtTel);

		JLabel lblCellphone = new JLabel(Language.getString("cellphone") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCellphone, gbc);
		pnlInputRenter.add(lblCellphone);

		txtCellphone = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtCellphone, gbc);
		pnlInputRenter.add(txtCellphone);

		JLabel lblEmail = new JLabel(Language.getString("email") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblEmail, gbc);
		pnlInputRenter.add(lblEmail);

		txtEmail = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtEmail, gbc);
		pnlInputRenter.add(txtEmail);


		//eID
		JPanel pnlEID = new JPanel();
		pnlEID.setLayout(new BoxLayout(pnlEID, BoxLayout.X_AXIS));
		Layout.buildConstraints(gbc, 2, 1, 1, 2, 2, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlEID, gbc);
		pnlInfo.add(pnlEID);
		pnlEID.add(Box.createHorizontalGlue());
		JButton btnEID = new JButton(Language.getString("eid"), new ImageIcon(getClass().getResource("/images/credit_card.png")));
		btnEID.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				fetchDataFromEIDCard();
			}
		});
		pnlEID.add(btnEID);


		// options
		JPanel pnlDates = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Layout.buildConstraints(gbc, 0, 1, 2, 1, 6, 1, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlDates, gbc);
		pnlInfo.add(pnlDates);

		pnlDates.add(new JLabel(Language.getString("contractInterval") + ":"));

		cbbMonthFrom = new JComboBox(Language.getMonthsOfYear());
		pnlDates.add(cbbMonthFrom);


		cbbYearFrom = new JComboBox();
		int yearFrom = GregorianCalendar.getInstance().get(Calendar.YEAR) - 10;
		for (int i = yearFrom; i < yearFrom + 100; i++) {
			cbbYearFrom.addItem(i);
		}
		pnlDates.add(cbbYearFrom);

		pnlDates.add(new JLabel(Language.getString("to")));

		cbbMonthTo = new JComboBox(Language.getMonthsOfYear());
		pnlDates.add(cbbMonthTo);

		cbbYearTo = new JComboBox();
		int yearTo = GregorianCalendar.getInstance().get(Calendar.YEAR) - 10 + 1;
		for (int i = yearTo; i < yearTo + 100; i++) {
			cbbYearTo.addItem(i);
		}
		pnlDates.add(cbbYearTo);


		//kosten: price
		JPanel pnlPrice = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Layout.buildConstraints(gbc, 0, 2, 2, 1, 6, 1, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlPrice, gbc);
		pnlInfo.add(pnlPrice);

		JLabel lblPrice = new JLabel(Language.getString("contractPrice") + ": ");
		pnlPrice.add(lblPrice);

		txtPrice = new JTextField();
		txtPrice.setColumns(5);
		pnlPrice.add(txtPrice);

		//kosten: montly cost
		JPanel pnlMontlyCost = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlPrice.add(pnlMontlyCost);
		JLabel lblMonthlyCost = new JLabel(Language.getString("contractMonthlyCost") + ": ");
		pnlMontlyCost.add(lblMonthlyCost);
		txtMonthlyCost = new JTextField();
		txtMonthlyCost.setColumns(5);
		pnlMontlyCost.add(txtMonthlyCost);


		//kosten: guarantee
		JPanel pnlGuarantee = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlPrice.add(pnlGuarantee);
		JLabel lblGuarantee = new JLabel(Language.getString("invoiceGuarantee") + ": ");
		pnlGuarantee.add(lblGuarantee);
		txtGuarantee = new JTextField();
		txtGuarantee.setColumns(5);
		pnlGuarantee.add(txtGuarantee);


		//choise building
		JPanel pnlRentable = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlDates.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		Layout.buildConstraints(gbc, 0, 3, 3, 1, 8, 1, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlRentable, gbc);
		pnlInfo.add(pnlRentable);

		try {
			cbbBuildings = new JComboBox(data.getBuildingPreviews());
		} catch (Exception ex) {
			Logger.logger.error("Exception in constructor ContractsPane while getting buildingpreviews: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			cbbBuildings = new JComboBox();
		}
		cbbBuildings.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				try {
					cbbRentables = new JComboBox(DataConnector.getRentablesFromBuilding(((Building) ((JComboBox) e.getSource()).getSelectedItem()).getId()));

				} catch (SQLException ex) {
					Logger.logger.error("SQLException in ContractsPane while getting rentablePreviews on select building from combo: " + ex.getMessage());
					Logger.logger.debug("StackTrace: ", ex);
				}
			}
		});
		pnlRentable.add(cbbBuildings);
		try {
			//choise rentable from building
			cbbRentables = new JComboBox(DataConnector.getRentablesFromBuilding(((Building) cbbBuildings.getSelectedItem()).getId()));
		} catch (SQLException ex) {
			Logger.logger.error("Exception in constructor ContractsPane while getting rentables from building: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			cbbRentables = new JComboBox(new Object[]{Language.getString("error")});
		}
		pnlRentable.add(cbbRentables);

		//buttons// final Buttons
		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlButtons.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		this.add(pnlButtons, BorderLayout.PAGE_END);

		JButton btnCancel = new JButton(Language.getString("cancel"), new ImageIcon(getClass().getResource("/images/cancel.png")));
		btnCancel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				instance.dispose();
			}
		});
		pnlButtons.add(btnCancel);



		//add button
		btnOK = new JButton(Language.getString("add"), new ImageIcon(getClass().getResource("/images/ok.png")));
		btnOK.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (checkInput()) {
					//correct input


					Calendar calendar = Calendar.getInstance();
					calendar.set((Integer) cbbYearFrom.getSelectedItem(), cbbMonthFrom.getSelectedIndex(), 1);
					Date start = calendar.getTime();
					calendar.set((Integer) cbbYearTo.getSelectedItem(), cbbMonthTo.getSelectedIndex(), 1);
					calendar.add(Calendar.MONTH, 1);
					calendar.add(Calendar.DATE, -1);
					Date end = calendar.getTime();


					//adding contract to database
					Main.getDataObject().addContract(
							((Rentable) cbbRentables.getSelectedItem()).getId(),
							txtFirstName.getText(),
							txtLastName.getText(),
							txtStreet.getText(),
							txtStreetNumber.getText(),
							txtZip.getText(),
							txtCity.getText(),
							Language.getCountryCodeByIndex(cbbCountry.getSelectedIndex()),
							txtTel.getText(),
							txtCellphone.getText(),
							txtEmail.getText(),
							start,
							end,
							Double.parseDouble(txtPrice.getText()),
							Double.parseDouble(txtMonthlyCost.getText()),
							Double.parseDouble(txtGuarantee.getText()));

					JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("contractSuccesAdd") + "\n" + Language.getString("contractSuccesAdd2"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
					instance.dispose();
				}
			}
		});
		pnlButtons.add(btnOK);

		this.pack();
		this.setLocationRelativeTo(null);
	}

	public void fillInfo() {
		//new contract
		cbbBuildings.setSelectedIndex(0);
		txtFirstName.setText("");
		txtLastName.setText("");
		txtStreet.setText("");
		txtStreetNumber.setText("");
		txtZip.setText("");
		txtCity.setText("");
		cbbCountry.setSelectedItem(Language.getCountryByCode("BE"));
		txtTel.setText("");
		txtCellphone.setText("");
		txtEmail.setText("");
		//set times in combobox
		cbbMonthFrom.setSelectedIndex(GregorianCalendar.getInstance().get(Calendar.MONTH));
		cbbYearFrom.setSelectedItem(GregorianCalendar.getInstance().get(Calendar.YEAR));
		cbbMonthTo.setSelectedIndex(GregorianCalendar.getInstance().get(Calendar.MONTH));
		cbbYearTo.setSelectedItem(GregorianCalendar.getInstance().get(Calendar.YEAR) + 1);
		//price
		txtPrice.setText("");
		txtMonthlyCost.setText("");
		txtGuarantee.setText("");

	}

	private boolean checkInput() {
		String errorMessage = Language.getString("wrongInput") + ":\n";
		boolean error = false;
		if (!txtFirstName.getText().matches("[^0-9]+")) {
			errorMessage += "   * " + Language.getString("errFirstName") + "\n";
			error = true;
			txtFirstName.setBackground(Color.pink);
		} else {
			txtFirstName.setBackground(Color.white);
		}
		if (!txtLastName.getText().matches("[^0-9]+")) {
			errorMessage += "   * " + Language.getString("errLastName") + "\n";
			error = true;
			txtLastName.setBackground(Color.pink);
		} else {
			txtLastName.setBackground(Color.white);
		}
		if (!txtStreet.getText().matches("[^0-9]+")) {
			errorMessage += "   * " + Language.getString("errStreet") + "\n";
			error = true;
			txtTel.setBackground(Color.pink);
		} else {
			txtTel.setBackground(Color.white);
		}
		if (!txtStreetNumber.getText().matches("[0-9]+.*")) {
			errorMessage += "   * " + Language.getString("errStreetNumber") + "\n";
			error = true;
			txtStreetNumber.setBackground(Color.pink);
		} else {
			txtStreetNumber.setBackground(Color.white);
		}
		if (!txtZip.getText().matches("[^0-9]+")) {
			errorMessage += "   * " + Language.getString("errZipCode") + "\n";
			error = true;
			txtZip.setBackground(Color.pink);
		} else {
			txtZip.setBackground(Color.white);
		}
		if (!txtCity.getText().matches("[^0-9]+")) {
			errorMessage += "   * " + Language.getString("errCity") + "\n";
			error = true;
			txtCity.setBackground(Color.pink);
		} else {
			txtCity.setBackground(Color.white);
		}

		if (!txtTel.getText().matches("[0-9]*")) {
			errorMessage += "   * " + Language.getString("errTelephone") + "\n";
			error = true;
			txtTel.setBackground(Color.pink);
		} else {
			txtTel.setBackground(Color.white);
		}
		if (!txtCellphone.getText().matches("[0-9]*")) {
			errorMessage += "   * " + Language.getString("errCellphone") + "\n";
			error = true;
			txtCellphone.setBackground(Color.pink);
		} else {
			txtCellphone.setBackground(Color.white);
		}
		if (!txtEmail.getText().matches("[a-z0-9][a-z0-9_.\\-]*@([a-z0-9]+\\.)*[a-z0-9][a-z0-9\\-]+\\.([a-z]{2,6})")) {
			errorMessage += "   * " + Language.getString("errEmail") + "\n";
			error = true;
			txtEmail.setBackground(Color.pink);
		} else {
			txtEmail.setBackground(Color.white);
		}
		if (!(((Integer) cbbYearFrom.getSelectedItem()).intValue() < ((Integer) cbbYearTo.getSelectedItem()).intValue()
				|| ((Integer) cbbYearTo.getSelectedItem()).equals((Integer) cbbYearFrom.getSelectedItem())
				&& (cbbMonthFrom.getSelectedIndex() + 1) <= (cbbMonthTo.getSelectedIndex() + 1))) {
			errorMessage += "   * " + Language.getString("errDates") + "\n";
			error = true;
		}
		if (!txtGuarantee.getText().matches("[\\.0-9]+")) {
			errorMessage += "   * " + Language.getString("errGuarantee") + "\n";
			error = true;
			txtGuarantee.setBackground(Color.pink);
		} else {
			txtGuarantee.setBackground(Color.white);
		}
		if (!txtPrice.getText().matches("[\\.0-9]+")) {
			errorMessage += "   * " + Language.getString("errPrice") + "\n";
			error = true;
			txtPrice.setBackground(Color.pink);
		} else {
			txtPrice.setBackground(Color.white);
		}
		if (!txtMonthlyCost.getText().matches("[\\.0-9]+")) {
			errorMessage += "   * " + Language.getString("errMontlyCost") + "\n";
			error = true;
			txtMonthlyCost.setBackground(Color.pink);
		} else {
			txtMonthlyCost.setBackground(Color.white);
		}
		if (error) {
			JOptionPane.showMessageDialog(Main.getInstance(), errorMessage, Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			return true;
		}
	}

	private void fetchDataFromEIDCard() {
		try {
			EIDPerson person = new EIDPerson();
			txtFirstName.setText(person.getFirstName());
			txtLastName.setText(person.getName());
			Address address = person.getAddress();
			txtStreet.setText(address.getStreet());
			txtStreetNumber.setText(address.getStreetNumber());
			txtZip.setText(address.getZipcode());
			cbbCountry.setSelectedItem(address.getCountry()); //wont work if language is different from id card
			txtCity.setText(address.getCity());
		} catch (Exception ex) {
			Logger.logger.error("Exception in fetchDataFromEIDCard in contractspane: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errFetchEIDPerson") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
