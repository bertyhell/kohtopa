package gui.addremovetab;

import gui.AbstractListPanel;
import gui.Main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class PicturePreviewPanel extends AbstractListPanel {

	private BufferedImage picture;

	public PicturePreviewPanel(BufferedImage picture, boolean isBuilding) {
		if (picture == null) {
			Image image = new ImageIcon(getClass().getResource("/images/dummy_" + (isBuilding ? "building" : "rentable") + "_preview.png")).getImage();
			this.picture = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			this.picture.getGraphics().drawImage(image, 0, 0, null);
		}
		this.setMinimumSize(new Dimension(this.picture.getWidth() + 10, this.picture.getHeight() + 10));
		this.setPreferredSize(new Dimension(this.picture.getWidth() + 10, this.picture.getHeight() + 10));
	}

	public void setImage(BufferedImage picture) {
		this.picture = Main.resizeImage(picture, 100);
	}

	@Override
	public void paint(Graphics g) {
		int w = picture.getWidth();
		int h = picture.getHeight();
		g.drawImage(picture, 5, 5, w, h, this);
	}
}
