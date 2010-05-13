package gui.addremovetab;

import Language.Language;
import floorallocationmodule.view.FloorControls;
import gui.interfaces.IIdentifiable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;

public class FloorDialog extends JFrame implements IIdentifiable {

	private int buildingId;
	private JTextField txtFloor;


	public FloorDialog(JRootPane parent, int buildingId, int floor, boolean newFloor) {
		this.buildingId = buildingId;

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

	public void fillInfo(boolean isNew) {
		if (isNew) {
			//clear fields
		} else {
			//fill floor info
			//TODO 060 fill floor id in txtFloor
		}
	}
}
