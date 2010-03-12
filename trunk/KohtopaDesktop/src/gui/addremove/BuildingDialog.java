package gui.addremove;

import Language.Language;
import Resources.RelativeLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Model;

public class BuildingDialog extends JDialog{

	ArrayList<Image> images;
	Image preview;
	ArrayList<Image> floors;

	public BuildingDialog(Frame owner, int buildingId, boolean newBuilding) {
		super(owner, Language.getString(newBuilding?"editBuilding":"addBuilding"), true);
		this.setLocationRelativeTo(owner);
		
		this.setLayout(new BorderLayout());

		//images
		JPanel pnlImages = new JPanel(new RelativeLayout(RelativeLayout.Y_AXIS, 5));
		this.add(pnlImages, BorderLayout.LINE_START);

		//preview
		JLabel lblPreview = new JLabel();
		pnlImages.add(lblPreview);

		//pictures
		PicturesListModel listModelBuildingPictures = new PicturesListModel();
		JList listPictures = new JList(listModelBuildingPictures);
		listPictures.setBackground(new Color(217, 217, 217));
		listPictures.setCellRenderer(new PictureCellRenderer());

		HashMap<Integer, BufferedImage> buildingPictures = null;
		try {
			buildingPictures = Model.getInstance().getPictures(buildingId, true);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), Language.getString("errConnectDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
		}
		for(Integer id : buildingPictures.keySet()){
			listModelBuildingPictures.add(id, buildingPictures.get(id));
		}
		
		//info
		JPanel pnlInformation = new JPanel();
		this.add(pnlInformation, BorderLayout.CENTER);



	}



}
