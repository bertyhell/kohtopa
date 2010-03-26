package gui;

import Language.Language;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTextUI.BasicCaret;

public class LoginDialog extends JDialog {
    private JTextField txtUser;
    private JTextField txtPass;

    public LoginDialog(JFrame parent) {
	super(parent, Language.getString("login"), true);
	this.setIconImage(new ImageIcon(getClass().getResource("/images/login_16.png")).getImage());
	this.setPreferredSize(new Dimension(300, 200));
	this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	this.setLayout(new BorderLayout());

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


	txtPass = new JPasswordField("password");
	txtPass.setCaret(new BasicCaret());
	txtUser.setForeground(Color.GRAY);
	Layout.buildConstraints(gbc, 0, 1, 1, 1, 50, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	gbl.addLayoutComponent(txtPass, gbc);
	pnlLogin.add(txtPass);


	JCheckBox chkRemember = new JCheckBox(Language.getString("savePass"));
	Layout.buildConstraints(gbc, 0, 2, 1, 1, 40, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
	gbl.addLayoutComponent(chkRemember, gbc);
	pnlLogin.add(chkRemember);

	JButton btnLogin = new JButton(null, new ImageIcon(getClass().getResource("/images/next_23.png")));
	Layout.buildConstraints(gbc, 1, 1, 1, 1, 20, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	gbl.addLayoutComponent(btnLogin, gbc);
	pnlLogin.add(btnLogin);



	//TODO add password forgot button



	pack();
	this.setLocationRelativeTo(Main.getInstance());

    }
}
