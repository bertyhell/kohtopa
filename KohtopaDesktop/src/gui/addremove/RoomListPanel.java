package gui.addremove;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RoomListPanel extends AbstractListPanel {

	private String renter;
	private int number;

	public RoomListPanel(int id,
			String title,
			ImageIcon preview,
			String renter,
			int number) {
		super(id, title, preview);
		this.setMaximumSize(new Dimension(1000, 100));
		this.setMinimumSize(new Dimension(300, 100));
		this.setPreferredSize(new Dimension(300, 100));


		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);

		//adding information labels

		//preview
		JLabel lblImage = new JLabel(preview);
		buildConstraints(gbc, 0, 0, 1, 3, 30, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblImage, gbc);
		this.add(lblImage);

		//name
		JLabel lblName = new JLabel(title);
		buildConstraints(gbc, 1, 0, 1, 1, 100, 30, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblName, gbc);
		this.add(lblName);

		//street
		JLabel lblStreet = new JLabel(renter);
		buildConstraints(gbc, 1, 1, 1, 1, 100, 30, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblStreet, gbc);
		this.add(lblStreet);

		//postcode
		JLabel lblZipcode = new JLabel(Integer.toString(number));
		buildConstraints(gbc, 1, 2, 1, 1, 100, 15, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblZipcode, gbc);
		this.add(lblZipcode);
	}

	public int getNumber() {
		return number;
	}

	public String getRenter() {
		return renter;
	}
}
