package gui;

import Language.Language;
import data.DataConnector;
import data.ProgramSettings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTextUI.BasicCaret;

public class LoginDialog extends JFrame implements ActionListener {

	private final String defaultPasswordContent = "password";
	private static LoginDialog instance;
	private static JTextField txtUser;
	private JTextField txtPass;
	private JLabel lblLoginFailed1;
	private JLabel lblLoginFailed2;
	private JCheckBox chkRemember;
	private int tries;

	public static LoginDialog getInstance() {
		if (instance == null) {
			instance = new LoginDialog();
		}
		return instance;
	}

	private LoginDialog() {
		super(Language.getString("login"));

		Logger.logger.info("login dialog displayed");

		this.setIconImage(new ImageIcon(getClass().getResource("/images/login_16.png")).getImage());
		this.setPreferredSize(new Dimension(350, 200));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());

		tries = 0;

		this.setAlwaysOnTop(true);

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel pnlLogin = new JPanel(gbl);
		pnlLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		this.add(pnlLogin, BorderLayout.CENTER);


		txtUser = new JTextField(Language.getString("username"));
		txtUser.setForeground(Color.GRAY);
		Layout.buildConstraints(gbc, 0, 0, 1, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtUser, gbc);
		pnlLogin.add(txtUser);
		txtUser.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (txtUser.getText().equals(Language.getString("username"))) {
					txtUser.setForeground(Color.black);
					txtUser.setText("");
				}
			}
		});

		txtPass = new JPasswordField(defaultPasswordContent);
		txtPass.setCaret(new BasicCaret());
		txtUser.setForeground(Color.GRAY);
		Layout.buildConstraints(gbc, 0, 1, 1, 1, 50, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtPass, gbc);
		pnlLogin.add(txtPass);
		txtPass.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				if (txtPass.getText().equals(defaultPasswordContent)) {
					txtPass.setText("");
				}
			}
		});
		//TODO 000 fix tab orders everywhere

		chkRemember = new JCheckBox(Language.getString("savePass"));
		Layout.buildConstraints(gbc, 0, 2, 1, 1, 40, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(chkRemember, gbc);
		pnlLogin.add(chkRemember);

		lblLoginFailed1 = new JLabel();
		lblLoginFailed1.setForeground(Color.red);
		lblLoginFailed1.setVisible(false);
		Layout.buildConstraints(gbc, 0, 3, 2, 1, 60, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblLoginFailed1, gbc);
		pnlLogin.add(lblLoginFailed1);

		lblLoginFailed2 = new JLabel();
		lblLoginFailed2.setForeground(Color.red);
		lblLoginFailed2.setVisible(false);
		Layout.buildConstraints(gbc, 0, 4, 2, 1, 60, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblLoginFailed2, gbc);
		pnlLogin.add(lblLoginFailed2);

		JButton btnLogin = new JButton(null, new ImageIcon(getClass().getResource("/images/next_16.png")));
		Layout.buildConstraints(gbc, 1, 1, 1, 1, 20, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(btnLogin, gbc);
		pnlLogin.add(btnLogin);

		btnLogin.addActionListener(this);
		getRootPane().setDefaultButton(btnLogin);

		//TODO 000 add password forgot button
		pack();
		setLocationRelativeTo(null);
	}

	public void checkLogin() {
		//check login
		ProgramSettings.setUsername(txtUser.getText());
		ProgramSettings.setPassword(txtPass.getText());
		Integer ownerId = null;
		try {
			ownerId = DataConnector.checkLogin();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(LoginDialog.getInstance(), "login attempt failed: \n" + ex.getMessage(), "login fail", JOptionPane.ERROR_MESSAGE);
		}
		if (ownerId != null) {
			logIn(ownerId, false);
		} else {
			//repeat ask
			lblLoginFailed1.setText(Language.getString("errLoginFailed"));
			lblLoginFailed2.setText(Language.getString("errTryAgain") + "(" + ++tries + ")");
			lblLoginFailed1.setVisible(true);
			lblLoginFailed2.setVisible(true);
			txtPass.requestFocus();
		}
	}

	private void logIn(int ownerId, boolean stored) {
		//logged in
		Logger.logger.info("loggin succesfull");
		if (!stored) {
			ProgramSettings.setUsername(txtUser.getText());
			ProgramSettings.setPassword(txtPass.getText());
			if (chkRemember.isSelected()) {
				//store login
				ProgramSettings.setRemeberPassword(true);
			} else {
				ProgramSettings.setRemeberPassword(false);
			}
		}
		ProgramSettings.setOwnerId(ownerId);

		instance.setVisible(false);
		Main.init().setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (txtPass.getText().equals(defaultPasswordContent)) {
			txtPass.requestFocus();
		} else {
			checkLogin();
		}
	}

	/**
	 * Main method, starts the program
	 * @param args
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception ex) {
					Logger.logger.warn("Look and Feel not found: " + ex.getMessage());
				}

				Logger.init();
				DataConnector.init();

				ProgramSettings.read(); //reads settings from xml file using dom
				Language.read(); //reads strings in specific language from xml file

				ProgramSettings.print();

				//check settings if remember pass is true:
				if (ProgramSettings.isRemeberPassword()) {
					//check if stored pass is correct
					Integer ownerId = null;
					try {
						ownerId = DataConnector.checkLogin();
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(LoginDialog.getInstance(), "login attempt failed: \n" + ex.getMessage(), "login fail", JOptionPane.ERROR_MESSAGE);
					}
					if (ownerId == null) {
						LoginDialog.getInstance().setVisible(true);
					} else {
						LoginDialog.getInstance().logIn(ownerId, true);
					}
				} else {
					LoginDialog.getInstance().setVisible(true);
				}
			}
		});
	}
}
