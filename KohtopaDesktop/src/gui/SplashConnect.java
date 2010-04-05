package gui;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SplashConnect extends JDialog {

    public static SplashConnect instance = new SplashConnect();

    public static void showSplash() {
	instance.setVisible(true);
    }

    public static void hideSplash() {
	instance.setVisible(false);
    }

    private SplashConnect() {
	super(Main.getInstance(), "", false);
	setIconImage(new ImageIcon(getClass().getResource("/images/ico.png")).getImage());
	setUndecorated(true);
	setAlwaysOnTop(true);
	setSize(200, 200);
	ImageIcon img = new ImageIcon(getClass().getResource("/images/connect.png"));
	JLabel lblSplash = new JLabel("", img, SwingConstants.CENTER);
	add(lblSplash);
	setLocationRelativeTo(null);
    }
}
