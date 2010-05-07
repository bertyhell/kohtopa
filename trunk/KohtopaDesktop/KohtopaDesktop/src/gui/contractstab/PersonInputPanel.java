/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.contractstab;

import Language.Language;
import data.entities.Person;
import gui.Layout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ruben
 */
public class PersonInputPanel extends JPanel {

    private Person person;

    private JTextField txtFirstName;
    private JTextField txtName;
    private JTextField txtStreet;
    private JTextField txtStreetNumber;
    private JTextField txtZipCode;
    private JTextField txtCity;
    private JTextField txtCountry;
    private JTextField txtEmail;
    private JTextField txtTelephone;
    private JTextField txtCellphone;

	public PersonInputPanel(Person person, String title) {
        this.person = person;

		this.setMinimumSize(new Dimension(250, 150));
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(gbl);
		this.setBorder(BorderFactory.createTitledBorder(title));

        if (person == null) {
            JLabel lblFirstName = new JLabel("First Name: ");
            Layout.buildConstraints(gbc, 0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblFirstName, gbc);
            this.add(lblFirstName);

            txtFirstName = new JTextField();
            txtFirstName.setColumns(10);
            Layout.buildConstraints(gbc, 1, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtFirstName, gbc);
            this.add(txtFirstName);

            JLabel lblName = new JLabel("Name: ");
            Layout.buildConstraints(gbc, 0, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblName, gbc);
            this.add(lblName);

            txtName = new JTextField();
            txtName.setColumns(10);
            Layout.buildConstraints(gbc, 1, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtName, gbc);
            this.add(txtName);

            JLabel lblStreet = new JLabel("Street: ");
            Layout.buildConstraints(gbc, 0, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblStreet, gbc);
            this.add(lblStreet);

            txtStreet = new JTextField();
            txtStreet.setColumns(15);
            Layout.buildConstraints(gbc, 1, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtStreet, gbc);
            this.add(txtStreet);

            JLabel lblStreetNumber = new JLabel("Street Number: ");
            Layout.buildConstraints(gbc, 0, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblStreetNumber, gbc);
            this.add(lblStreetNumber);

            txtStreetNumber = new JTextField();
            txtStreetNumber.setColumns(5);
            Layout.buildConstraints(gbc, 1, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtStreetNumber, gbc);
            this.add(txtStreetNumber);

            JLabel lblZipCode = new JLabel("Zip Code: ");
            Layout.buildConstraints(gbc, 0, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblZipCode, gbc);
            this.add(lblZipCode);

            txtZipCode = new JTextField();
            txtZipCode.setColumns(5);
            Layout.buildConstraints(gbc, 1, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtZipCode, gbc);
            this.add(txtZipCode);

            JLabel lblCity = new JLabel("City: ");
            Layout.buildConstraints(gbc, 0, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblCity, gbc);
            this.add(lblCity);

            txtCity = new JTextField();
            txtCity.setColumns(10);
            Layout.buildConstraints(gbc, 1, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtCity, gbc);
            this.add(txtCity);

            JLabel lblCountry = new JLabel("Country: ");
            Layout.buildConstraints(gbc, 0, 6, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblCountry, gbc);
            this.add(lblCountry);

            txtCountry = new JTextField();
            txtCountry.setColumns(10);
            Layout.buildConstraints(gbc, 1, 6, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtCountry, gbc);
            this.add(txtCountry);

            JLabel lblEmail = new JLabel("Email: ");
            Layout.buildConstraints(gbc, 0, 7, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblEmail, gbc);
            this.add(lblEmail);

            txtEmail = new JTextField();
            txtEmail.setColumns(15);
            Layout.buildConstraints(gbc, 1, 7, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtEmail, gbc);
            this.add(txtEmail);

            JLabel lblTelephone = new JLabel(Language.getString("telephone"));
            Layout.buildConstraints(gbc, 0, 8, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblTelephone, gbc);
            this.add(lblTelephone);

            txtTelephone = new JTextField();
            txtTelephone.setColumns(10);
            Layout.buildConstraints(gbc, 1, 8, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtTelephone, gbc);
            this.add(txtTelephone);

            JLabel lblCellphone = new JLabel(Language.getString("cellphone"));
            Layout.buildConstraints(gbc, 0, 9, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblCellphone, gbc);
            this.add(lblCellphone);

            txtCellphone = new JTextField();
            txtCellphone.setColumns(10);
            Layout.buildConstraints(gbc, 1, 9, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtCellphone, gbc);
            this.add(txtCellphone);
        } else {
            JLabel lblFirstName = new JLabel("First Name: ");
            Layout.buildConstraints(gbc, 0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblFirstName, gbc);
            this.add(lblFirstName);

            txtFirstName = new JTextField(person.getFirstName());
            txtFirstName.setColumns(10);
            Layout.buildConstraints(gbc, 1, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtFirstName, gbc);
            this.add(txtFirstName);

            JLabel lblName = new JLabel("Name: ");
            Layout.buildConstraints(gbc, 0, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblName, gbc);
            this.add(lblName);

            txtName = new JTextField(person.getName());
            txtName.setColumns(10);
            Layout.buildConstraints(gbc, 1, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtName, gbc);
            this.add(txtName);

            JLabel lblStreet = new JLabel("Street: ");
            Layout.buildConstraints(gbc, 0, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblStreet, gbc);
            this.add(lblStreet);

            txtStreet = new JTextField(person.getAddress().getStreet());
            txtStreet.setColumns(15);
            Layout.buildConstraints(gbc, 1, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtStreet, gbc);
            this.add(txtStreet);

            JLabel lblStreetNumber = new JLabel("Street Number: ");
            Layout.buildConstraints(gbc, 0, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblStreetNumber, gbc);
            this.add(lblStreetNumber);

            txtStreetNumber = new JTextField(person.getAddress().getStreetNumber());
            txtStreetNumber.setColumns(5);
            Layout.buildConstraints(gbc, 1, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtStreetNumber, gbc);
            this.add(txtStreetNumber);

            JLabel lblZipCode = new JLabel("Zip Code: ");
            Layout.buildConstraints(gbc, 0, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblZipCode, gbc);
            this.add(lblZipCode);

            txtZipCode = new JTextField(person.getAddress().getZipcode());
            txtZipCode.setColumns(5);
            Layout.buildConstraints(gbc, 1, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtZipCode, gbc);
            this.add(txtZipCode);

            JLabel lblCity = new JLabel("City: ");
            Layout.buildConstraints(gbc, 0, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblCity, gbc);
            this.add(lblCity);

            txtCity = new JTextField(person.getAddress().getCity());
            txtCity.setColumns(10);
            Layout.buildConstraints(gbc, 1, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtCity, gbc);
            this.add(txtCity);

            JLabel lblCountry = new JLabel("Country: ");
            Layout.buildConstraints(gbc, 0, 6, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblCountry, gbc);
            this.add(lblCountry);

            txtCountry = new JTextField(person.getAddress().getCountry());
            txtCountry.setColumns(10);
            Layout.buildConstraints(gbc, 1, 6, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtCountry, gbc);
            this.add(txtCountry);

            JLabel lblEmail = new JLabel("Email: ");
            Layout.buildConstraints(gbc, 0, 7, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblEmail, gbc);
            this.add(lblEmail);

            txtEmail = new JTextField(person.getEmail());
            txtEmail.setColumns(15);
            Layout.buildConstraints(gbc, 1, 7, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtEmail, gbc);
            this.add(txtEmail);

            JLabel lblTelephone = new JLabel(Language.getString("telephone"));
            Layout.buildConstraints(gbc, 0, 8, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblTelephone, gbc);
            this.add(lblTelephone);

            txtTelephone = new JTextField(person.getTelephone());
            txtTelephone.setColumns(10);
            Layout.buildConstraints(gbc, 1, 8, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtTelephone, gbc);
            this.add(txtTelephone);

            JLabel lblCellphone = new JLabel(Language.getString("cellphone"));
            Layout.buildConstraints(gbc, 0, 9, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(lblCellphone, gbc);
            this.add(lblCellphone);

            txtCellphone = new JTextField(person.getCellphone());
            txtCellphone.setColumns(10);
            Layout.buildConstraints(gbc, 1, 9, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
            gbl.addLayoutComponent(txtCellphone, gbc);
            this.add(txtCellphone);
        }
	}

}
