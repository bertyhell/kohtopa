package gui.invoicestab;

import Language.Language;
import data.entities.Person;
import gui.Layout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Bert Verhelst
 */
public class PersonPanel extends JPanel {

	public PersonPanel(Person person, String title) {
		this.setMinimumSize(new Dimension(150, 150));
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(gbl);
		this.setBorder(BorderFactory.createTitledBorder(title));


		JLabel lblName = new JLabel(person.toString());
		Layout.buildConstraints(gbc, 0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblName, gbc);
		this.add(lblName);

		JLabel lblStreetLine = new JLabel(person.getAddress().getStreetLine());
		Layout.buildConstraints(gbc, 0, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblStreetLine, gbc);
		this.add(lblStreetLine);

		JLabel lblCityLine = new JLabel(person.getAddress().getCityLine());
		Layout.buildConstraints(gbc, 0, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCityLine, gbc);
		this.add(lblCityLine);

		JLabel lblCountry = new JLabel(Language.getCountryByCode(person.getAddress().getCountry()));
		Layout.buildConstraints(gbc, 0, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCountry, gbc);
		this.add(lblCountry);

		JLabel lblEmail = new JLabel(person.getEmail());
		Layout.buildConstraints(gbc, 0, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblEmail, gbc);
		this.add(lblEmail);

		JLabel lblTelephone = new JLabel(Language.getString("telephone") + ": " + person.getTelephone());
		Layout.buildConstraints(gbc, 0, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblTelephone, gbc);
		this.add(lblTelephone);

		JLabel lblCellphone = new JLabel(Language.getString("cellphone") + ": " + person.getTelephone());
		Layout.buildConstraints(gbc, 0, 6, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCellphone, gbc);
		this.add(lblCellphone);
	}
}
