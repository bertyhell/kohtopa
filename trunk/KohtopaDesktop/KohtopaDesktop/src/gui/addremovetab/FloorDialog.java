package gui.addremovetab;

import Language.Language;
import floorallocationmodule.view.FloorControls;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FloorDialog extends JFrame implements IdentifiableI {

	private FloorDialog instance;
	private int buildingId;
	private int floor;
	private JButton btnConfirm;
	private JTextField txtFloor;


	public FloorDialog(JRootPane parent, int buildingId, int floor, boolean newFloor) {
		this.buildingId = buildingId;
		this.floor = floor;
		instance = this;
		this.addWindowFocusListener(new WindowAdapter() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				Main.setFocusedDialog(instance);
			}
		});
		setTitle(Language.getString(newFloor ? "floorAdd" : "floorEdit"));
		this.setIconImage(new ImageIcon(getClass().getResource("/images/floor_23.png")).getImage());
		this.setPreferredSize(new Dimension(700, 600));
		this.setMinimumSize(new Dimension(400, 405));
		this.setLayout(new BorderLayout());

		//floor textbox

		JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(pnlTop, BorderLayout.PAGE_START);

		JLabel lblFloor = new JLabel(Language.getString("floor") + ": ");
		pnlTop.add(lblFloor);
		txtFloor = new JTextField(20);
		pnlTop.add(txtFloor);


		//drawing panel
        // Constructor only requires the parent frame and the name of the floor.
        this.add(new FloorControls(this, Integer.toString(floor)));

		//info opvullen:
		fillInfo(newFloor);
		pack();
		setLocationRelativeTo(parent);
	}

	public int getId() {
		return buildingId;
	}

	public String getType() {
		return "BuildingDialog";
	}

	public void fillInfo(boolean isNew) {
		if (isNew) {
			//clear fields
		} else {
			//fill floor info
		}
	}

	public boolean CheckInput() {
//		String errorMessage = Language.getString("faultyInput") + ":\n";
//		boolean error = false;
//		if (txtStreet.getText().isEmpty()) {
//			errorMessage += "   * " + Language.getString("errStreet") + "\n";
//			error = true;
//			txtStreet.setBackground(Color.pink);
//		} else {
//			txtStreet.setBackground(Color.white);
//		}
//		if (!txtStreetNumber.getText().matches("[0-9]+.*")) {
//			errorMessage += "   * " + Language.getString("errStreetNumber") + "\n";
//			error = true;
//			txtStreet.setBackground(Color.pink);
//		} else {
//			txtStreet.setBackground(Color.white);
//		}
//		if (!txtCity.getText().matches("[^0-9]+")) {
//			errorMessage += "   * " + Language.getString("errCity") + "\n";
//			error = true;
//			txtCity.setBackground(Color.pink);
//		} else {
//			txtCity.setBackground(Color.white);
//		}
//		if (!txtZip.getText().matches("[0-9]*")) {
//			errorMessage += "   * " + Language.getString("errZip") + "\n";
//			error = true;
//			txtCity.setBackground(Color.pink);
//		} else {
//			txtCity.setBackground(Color.white);
//		}
//		if (error) {
//			JOptionPane.showMessageDialog(this, errorMessage, Language.getString("error"), JOptionPane.ERROR_MESSAGE);
//		}
//		return !error;
		return true;
	}

	public void UpdateData() {
		fillInfo(false);
	}

	public void UpdatePictures() {
	}

	public void UpdateDataLists() {
	}

	public int[] getSelectedPictures() {
		return null;
	}
}
