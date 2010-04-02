package gui;

import Language.Language;
import data.DataModel;
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

public class LoginDialog extends JDialog implements ActionListener {

    private final String defaultPasswordContent = "password";

    private static LoginDialog instance;
    private JTextField txtUser;
    private JTextField txtPass;
    private JLabel lblLoginFailed1;
    private JLabel lblLoginFailed2;
    private JCheckBox chkRemember;
    private int tries;

    public LoginDialog(JFrame parent, DataModel data) {
	super(parent, Language.getString("login"), true);
	this.setIconImage(new ImageIcon(getClass().getResource("/images/login_16.png")).getImage());
	this.setPreferredSize(new Dimension(350, 200));
	this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	this.setLayout(new BorderLayout());

	instance = this;
	tries = 0;

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
	//TODO fix tab orders everywhere

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

	//FIXME remove this after iterations?
	JLabel lblLoginTip = new JLabel("Username: Adil , Password: Koop");
	lblLoginTip.setForeground(Color.DARK_GRAY);
	Layout.buildConstraints(gbc, 0, 5, 2, 1, 60, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
	gbl.addLayoutComponent(lblLoginTip, gbc);
	pnlLogin.add(lblLoginTip);

	JButton btnLogin = new JButton(null, new ImageIcon(getClass().getResource("/images/next_16.png")));
	Layout.buildConstraints(gbc, 1, 1, 1, 1, 20, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	gbl.addLayoutComponent(btnLogin, gbc);
	pnlLogin.add(btnLogin);

	btnLogin.addActionListener(this);
	getRootPane().setDefaultButton(btnLogin);

	//TODO add password forgot button
	pack();
	setLocationRelativeTo(Main.getInstance());

    }

    public void checkLogin() {
	try {
	    //check login
	    Integer userId = Main.getDataObject().checkLogin(txtUser.getText(), txtPass.getText());
	    if (userId != null) {
		//log in
		Main.getDataObject().setUserId(userId);
		instance.dispose();
	    } else {
		//repeat ask
		lblLoginFailed1.setText(Language.getString("errLoginFailed"));
		lblLoginFailed2.setText(Language.getString("errTryAgain") + "(" + ++tries + ")");
		lblLoginFailed1.setVisible(true);
		lblLoginFailed2.setVisible(true);
		txtPass.requestFocus();
	    }
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(Main.getInstance(), "login attempt failed: \n" + ex.getMessage(), "login fail", JOptionPane.ERROR_MESSAGE);
	}
    }

    public void actionPerformed(ActionEvent e) {
	if (txtPass.getText().equals(defaultPasswordContent)) {
	    txtPass.requestFocus();
	} else {
	    checkLogin();
	}
    }
}
