/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.contractstab;

import gui.AbstractListPanel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Ruben
 */
public class ContractListPanel extends AbstractListPanel {

    public ContractListPanel(int id, ImageIcon preview, String firstName, String name, Date start) {
		super(id);
		ImageIcon img;
		if (preview == null) {
			img = new ImageIcon(getClass().getResource("/images/dummy_person_preview.png"));
		} else {
			img = preview;
		}

		this.setMaximumSize(new Dimension(1000, 50));
		this.setMinimumSize(new Dimension(200, 50));
		this.setPreferredSize(new Dimension(200, 50));

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);

        // icon
        JLabel lblImage = new JLabel(img);
        buildConstraints(gbc, 0, 0, 1, 3, 30, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
        gbl.addLayoutComponent(lblImage, gbc);
        this.add(lblImage);

        // name
        JLabel lblFirstname = new JLabel(((String)firstName).trim() + " " + ((String)name).trim());
        buildConstraints(gbc, 1, 1, 2, 1, 150, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
        gbl.addLayoutComponent(lblFirstname, gbc);
        this.add(lblFirstname);

        // date
        JLabel lblDate = new JLabel(DateFormat.getDateInstance().format(start));
        lblDate.setFont(lblDate.getFont().deriveFont(Font.BOLD + Font.ITALIC));
        buildConstraints(gbc, 1, 0, 2, 1, 150, 40, GridBagConstraints.WEST, GridBagConstraints.WEST);
        gbl.addLayoutComponent(lblDate, gbc);
        this.add(lblDate);
	}

}
