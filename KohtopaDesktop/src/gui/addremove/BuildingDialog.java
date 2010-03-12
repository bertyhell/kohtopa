package gui.addremove;

import Language.Language;
import Resources.RelativeLayout;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BuildingDialog extends JDialog{

	ArrayList<Image> images;
	Image preview;
	ArrayList<Image> floors;

	public BuildingDialog(Frame owner, String buildingId, boolean newBuilding) {
		super(owner, Language.getString(newBuilding?"editBuilding":"addBuilding"), true);
		this.setLocationRelativeTo(owner);
		
		this.setLayout(new BorderLayout());

		//images
		JPanel pnlImages = new JPanel(new RelativeLayout(RelativeLayout.Y_AXIS, 5));
		this.add(pnlImages, BorderLayout.LINE_START);

		JLabel lblPreview = new JLabel();
		pnlImages.add(lblPreview);

		JLabel pnlPictures = new JLabel();
		pnlImages.add(pnlPictures);
		
		//info
		JPanel pnlInformation = new JPanel();
		this.add(pnlInformation, BorderLayout.CENTER);



	}



}
