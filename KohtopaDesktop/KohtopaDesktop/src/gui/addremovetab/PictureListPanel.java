package gui.addremovetab;

import gui.AbstractListPanel;
import gui.GuiConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PictureListPanel extends AbstractListPanel {

	private BufferedImage picture;
	private boolean isSelected;

	public PictureListPanel(BufferedImage img, boolean isSelected) {
		this.picture = img;
		this.isSelected = isSelected;
		this.setMinimumSize(new Dimension(GuiConstants.previewSize, img.getHeight() + 10));
		this.setPreferredSize(new Dimension(GuiConstants.previewSize, img.getHeight() + 10));
	}

	@Override
	public void paint(Graphics g) {
		int w = picture.getWidth();
		int h = picture.getHeight();
		int ps = GuiConstants.previewSize;
		int ss = GuiConstants.shadowSize;
		g.setColor(new Color(50, 50, 50));
		g.fillRect((ps - w) / 2 + 2 + ss, 2 + ss, w + ss, h + ss);
		g.drawImage(picture, (ps - w) / 2 + 2, 2, w, h, Color.gray, this);
		if (isSelected) {
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect((ps - w) / 2 + 2, 2, w, h);
		}
	}
}
