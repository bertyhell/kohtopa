/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.contractstab;

import gui.AbstractListPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Ruben
 */
public class ContractListPanel extends AbstractListPanel {

    public ContractListPanel(int id, ImageIcon preview, String name) {
		super(id);
		ImageIcon img;
		if (preview == null) {
			img = new ImageIcon(getClass().getResource("/images/dummy_person_preview.png")); //TODO: ander image
		} else {
			img = preview;
		}

		this.setMaximumSize(new Dimension(1000, 50));
		this.setMinimumSize(new Dimension(200, 50));
		this.setPreferredSize(new Dimension(200, 50));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		//adding information labels
		this.add(new JLabel(img));
		this.add(new JLabel(name));
	}

}
