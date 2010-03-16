package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SplashConnect extends JFrame {

	public static SplashConnect instance = new SplashConnect();

	public static void showSplash(){
		instance.setVisible(true);
	}

	public static void hideSplash(){
		instance.setVisible(false);
	}

    private SplashConnect() {
        super("");

		this.setIconImage(new ImageIcon(getClass().getResource("/images/ico.png")).getImage());
		this.setUndecorated(true);
        setSize(200, 200);
		ImageIcon img = new ImageIcon(getClass().getResource("/images/connect.png"));
		JLabel lblSplash = new JLabel("",img, SwingConstants.CENTER);
		this.add(lblSplash);

        this.setLocationRelativeTo(null);
    }
}
