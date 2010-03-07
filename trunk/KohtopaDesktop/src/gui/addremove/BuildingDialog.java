package gui.addremove;

import Language.Language;
import Resources.RelativeLayout;
import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BuildingDialog extends JDialog{

	public BuildingDialog(Frame owner, boolean newBuilding) {
		super(owner, Language.getString(newBuilding?"editBuilding":"addBuilding"), true);
		this.setLocationRelativeTo(owner);
		
		this.setLayout(new BorderLayout());

		//images
		JPanel images = new JPanel(new RelativeLayout(RelativeLayout.Y_AXIS, 5));
		JLabel lblPreview = new JLabel();

	}



}
