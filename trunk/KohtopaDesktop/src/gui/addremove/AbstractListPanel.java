package gui.addremove;

import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public abstract class AbstractListPanel extends JPanel {

	private Color bgColor;
	protected int id;
	protected String title;
	protected ImageIcon preview;

	public AbstractListPanel(int id, String title, ImageIcon preview) {
		this.id = id;
		this.title = title;
		this.preview = preview;
	}

	public String getTitle() {
		return title;
	}

	public ImageIcon getPreview() {
		return preview;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		this.setBackground(bgColor);
	}

	public void resoreColor() {
		this.setBackground(bgColor);
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
