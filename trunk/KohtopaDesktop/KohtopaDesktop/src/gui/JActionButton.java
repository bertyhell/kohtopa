package gui;

import gui.interfaces.IIdentifiable;
import javax.swing.Action;
import javax.swing.JButton;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class JActionButton extends JButton {

	private IIdentifiable root;

	public JActionButton(Action action, IIdentifiable root) {
		super(action);
		this.root = root;
	}

	public IIdentifiable getRoot() {
		return root;
	}
}
