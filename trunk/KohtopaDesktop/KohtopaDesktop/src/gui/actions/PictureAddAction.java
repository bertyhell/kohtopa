package gui.actions;

import Language.Language;
import gui.GuiConstants;
import gui.JActionButton;
import gui.Logger;
import gui.Main;
import gui.actions.filefilters.FileFilterGif;
import gui.actions.filefilters.FileFilterImage;
import gui.actions.filefilters.FileFilterJpg;
import gui.actions.filefilters.FileFilterPng;
import gui.interfaces.IIdentifiable;
import gui.interfaces.IPictureListContainer;
import gui.interfaces.IRentableListContainer;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

public class PictureAddAction extends AbstractIconAction {

	private final JFileChooser fc = new JFileChooser(); //TODO remeber last dir in settings file

	public PictureAddAction(String id, Icon img) {
		super(id, img);
		fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileFilterImage defaultFilter = new FileFilterImage();
		fc.addChoosableFileFilter(defaultFilter);
		fc.addChoosableFileFilter(new FileFilterGif());
		fc.addChoosableFileFilter(new FileFilterJpg());
		fc.addChoosableFileFilter(new FileFilterPng()); //TODO add more file types?
		fc.setFileFilter(defaultFilter);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (fc.showOpenDialog(((JComponent) e.getSource()).getTopLevelAncestor()) == JFileChooser.APPROVE_OPTION) {
			//open button was pressed
			File[] sourceimages = fc.getSelectedFiles();
			IIdentifiable root = ((JActionButton) e.getSource()).getRoot();
			try {
				// Read from Filelist
				for (File file : sourceimages) {//TODO add loading bar in second thread that updates between images
					//in database laden
					BufferedImage img = Main.resizeImage(ImageIO.read(file), GuiConstants.maxSize);
					if (root instanceof IRentableListContainer) {
						//add building picture
						Main.getDataObject().addBuildingImage(root.getId(), img);
					} else {
						// add rentable picture
						Main.getDataObject().addRentableImage(root.getId(), img);
					}
					//update list
					((IPictureListContainer) root).updatePictureList();
				}
//				JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("PicturesSuccesAdd"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
			} catch (SQLException ex) {
				Logger.logger.error("SQLException in PictureAddAction " + ex.getMessage());
				Logger.logger.debug("StackTrace: ", ex);
				JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errInSql") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException ex) {
				Logger.logger.error("IOException in PictureAddAction " + ex.getMessage());
				Logger.logger.debug("StackTrace: ", ex);
				JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errReadingImage") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
