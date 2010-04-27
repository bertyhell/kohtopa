package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;

public abstract class AbstractListPanel extends JPanel {

	private Color bgColor;
	protected int id;

        public AbstractListPanel() {
            this.id = 0;
        }

	public AbstractListPanel(int id) {
		this.id = id;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		this.setBackground(bgColor);
	}

	public void restore() {
		this.setBackground(bgColor);
	}

	public int getId() {
		return id;
	}



	protected void buildConstraints(GridBagConstraints gbc, int x, int y, int w, int h, int wx, int wy, int fill, int anchor) {

		gbc.gridx = x; // start cell in a row
		gbc.gridy = y; // start cell in a column
		gbc.gridwidth = w; // how many column does the control occupy in the row
		gbc.gridheight = h; // how many column does the control occupy in the column
		gbc.weightx = wx; // relative horizontal size
		gbc.weighty = wy; // relative vertical size
		gbc.fill = fill; // the way how the control fills cells
		gbc.anchor = anchor; // alignment
	}
}
