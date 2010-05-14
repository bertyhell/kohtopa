package gui.addremovetab;

import gui.AbstractListPanel;
import gui.Logger;
import gui.Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class PicturePreviewPanel extends AbstractListPanel {

	private BufferedImage picture;

	public PicturePreviewPanel(String type) {
		Image image = new ImageIcon(getClass().getResource("/images/dummy_" + type + "_preview.png")).getImage();
		picture = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		picture.getGraphics().drawImage(image, 0, 0, null);
		this.setMinimumSize(new Dimension(picture.getWidth() + 10, picture.getHeight() + 10));
		this.setPreferredSize(new Dimension(picture.getWidth() + 10, picture.getHeight() + 10));
	}

	public void setImage(BufferedImage picture) {
		Logger.logger.debug("setting previewimage in preview panel");
		this.picture = Main.resizeImage(picture, 100);
		this.repaint();  //TODO 000 doesn't work yet
	}

	@Override
	public void paint(Graphics g) {
		Logger.logger.debug("painting preview image");
		int w = picture.getWidth();
		int h = picture.getHeight();
		g.drawImage(picture, 5, 5, w, h, this);
	}
}
