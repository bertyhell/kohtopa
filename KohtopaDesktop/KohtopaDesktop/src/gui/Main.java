package gui;

//TODO 002 add possibility to access all functions trough ALT (maybe autohide file bar?)
//TODO 005 add right click menu's in all panels
import Language.Language;
import gui.messagetab.MessagePane;
import gui.actions.*;
import gui.messagetab.MessageListPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import data.DataModel;
import data.ProgramSettings;
import gui.addremovetab.AddRemovePane;
import gui.calendartab.CalendarPane;
import gui.contractstab.ContractsPane;
import gui.invoicestab.InvoicesPane;
import gui.userstab.UsersPane;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

public class Main extends JFrame {

	private static Main instance;
	private static HashMap<String, Action> actions;
	public final static boolean disableBtnText = false;
	private static DataModel data;
	private JPanel pnlAddremove;
	private JPanel pnlUsers;
	private JPanel pnlCalendar;
	private JPanel pnlMessages;
	private JPanel pnlInvoices;
	private JPanel pnlContracts;
	private AddRemovePane pnlAddremoveInfo;
	private UsersPane pnlUsersInfo;
	private CalendarPane pnlCalendarInfo;
	private MessagePane pnlMessagesInfo;
	private InvoicesPane pnlInvoicesInfo;
	private ContractsPane pnlContractsInfo;
	private JTabbedPane tabbed;
	private static JComboBox cbbLanguages;
	private JFrame parent;

	/**
	 * Getter for the main instance
	 * @return instance
	 */
	public static Main getInstance() {
		return instance;
	}

	/**
	 * Initializing the main frame
	 * @return instance
	 */
	public static Main init(JFrame parent) {
		instance = new Main(parent);
		return instance;
	}

	/**
	 * Constructs the Main class
	 */
	private Main(JFrame parent) {
		Logger.logger.setLevel(ProgramSettings.getLoggerLevel());
		Logger.logger.info("Logger.logger level is: " + ProgramSettings.getLoggerLevel());

		Logger.logger.info("------------------application started ---------------------");

		this.parent = parent;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(1000, 700));
		//setVisible(false);

		setTitle(Language.getString("titleJFrameDesktopMain"));
		data = new DataModel();

		//actions
		actions = new HashMap<String, Action>();

		actions.put("restart", new RestartAction(new ImageIcon(getClass().getResource("/images/ok.png"))));
		actions.put("buildingAdd", new BuildingAddAction("buildingAdd", new ImageIcon(getClass().getResource("/images/building_add_23.png"))));
		actions.put("buildingEdit", new BuildingEditAction("buildingEdit", new ImageIcon(getClass().getResource("/images/building_edit_23.png"))));
		actions.put("buildingRemove", new BuildingRemoveAction("buildingRemove", new ImageIcon(getClass().getResource("/images/building_remove_23.png"))));
		actions.put("userAdd", new UserAddAction("userAdd", new ImageIcon(getClass().getResource("/images/user_add_23.png"))));
		actions.put("userEdit", new UserEditAction("userEdit", new ImageIcon(getClass().getResource("/images/user_edit_23.png"))));
		actions.put("userRemove", new UserRemoveAction("userRemove", new ImageIcon(getClass().getResource("/images/user_remove_23.png"))));
		actions.put("pictureAdd", new PictureAddAction("pictureAdd", new ImageIcon(getClass().getResource("/images/picture_add_23.png"))));
		actions.put("picturePreview", new PicturePreviewAction("picturePreview", new ImageIcon(getClass().getResource("/images/picture_preview_23.png"))));
		actions.put("pictureRemove", new PictureRemoveAction("pictureRemove", new ImageIcon(getClass().getResource("/images/picture_remove_23.png"))));
		actions.put("rentableAdd", new RentableAddAction("rentableAdd", new ImageIcon(getClass().getResource("/images/rentable_add_23.png"))));
		actions.put("rentableEdit", new RentableEditAction("rentableEdit", new ImageIcon(getClass().getResource("/images/rentable_edit_23.png"))));
		actions.put("rentableRemove", new RentableRemoveAction("rentableRemove", new ImageIcon(getClass().getResource("/images/rentable_remove_23.png"))));
		actions.put("taskAdd", new TaskAddAction("taskAdd", new ImageIcon(getClass().getResource("/images/task_add_23.png"))));
		actions.put("taskEdit", new TaskEditAction("taskEdit", new ImageIcon(getClass().getResource("/images/task_edit_23.png"))));
		actions.put("taskRemove", new TaskRemoveAction("taskRemove", new ImageIcon(getClass().getResource("/images/task_remove_23.png"))));
		actions.put("messageNew", new MessageNewAction("messageNew", new ImageIcon(getClass().getResource("/images/message_new_23.png"))));
		actions.put("messageReply", new MessageReplyAction("messageReply", new ImageIcon(getClass().getResource("/images/message_reply_23.png"))));
		actions.put("messageMarkUnread", new MessageMarkUnreadAction("messageMarkUnread", new ImageIcon(getClass().getResource("/images/message_new_23.png"))));
		actions.put("invoiceAdd", new InvoiceAddAction("invoiceAdd", new ImageIcon(getClass().getResource("/images/invoice_add_23.png"))));
		actions.put("invoiceEdit", new InvoiceEditAction("invoiceEdit", new ImageIcon(getClass().getResource("/images/invoice_edit_23.png"))));
		actions.put("invoiceRemove", new InvoiceRemoveAction("invoiceRemove", new ImageIcon(getClass().getResource("/images/invoice_remove_23.png"))));
		actions.put("contractAdd", new ContractAddAction("contractAdd", new ImageIcon(getClass().getResource("/images/contract_add_23.png"))));
		actions.put("contractEdit", new ContractEditAction("contractEdit", new ImageIcon(getClass().getResource("/images/contract_edit_23.png"))));
		actions.put("contractRemove", new ContractRemoveAction("contractRemove", new ImageIcon(getClass().getResource("/images/contract_remove_23.png"))));
		actions.put("floorAdd", new FloorAddAction("floorAdd", new ImageIcon(getClass().getResource("/images/floor_add_23.png"))));
		actions.put("floorEdit", new FloorEditAction("floorEdit", new ImageIcon(getClass().getResource("/images/floor_edit_23.png"))));
		actions.put("floorRemove", new FloorRemoveAction("floorRemove", new ImageIcon(getClass().getResource("/images/floor_remove_23.png"))));

		//jframe
		this.setIconImage(new ImageIcon(getClass().getResource("/images/ico.png")).getImage());
		this.setExtendedState(this.getExtendedState() | Main.MAXIMIZED_BOTH);
		//TODO 001 add onclose handler > check if dialogs have changed data if so ask if user wants to save
		this.setMinimumSize(new Dimension(370, 300));
		this.setLayout(new BorderLayout());
		tabbed = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		this.add(tabbed, BorderLayout.CENTER);


		//adding Add/Remove panel
		pnlAddremoveInfo = new AddRemovePane(data);
		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/building_64.png")), createAddRemovePanel(), Language.getString("descriptionAddRemove"));
		tabbed.setMnemonicAt(0, KeyEvent.VK_A);
		pnlAddremove.add(pnlAddremoveInfo, BorderLayout.CENTER);

		//adding Users panel
		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/user_64.png")), createUsers(), Language.getString("descriptionUsers"));
		tabbed.setMnemonicAt(0, KeyEvent.VK_A);

		//adding Calendar panel
		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/calendar_64.png")), createCalendarPanel(), Language.getString("descriptionCalendar"));
		tabbed.setMnemonicAt(1, KeyEvent.VK_C);

		//adding Messages panel
		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/messages_64.png")), createMessagesPanel(), Language.getString("descriptionMesssages"));
		tabbed.setMnemonicAt(2, KeyEvent.VK_M);

		//adding Invoices panel
		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/invoice_64.png")), createInvoicesPanel(), Language.getString("descriptionInvoices"));
		tabbed.setMnemonicAt(3, KeyEvent.VK_I);

		//adding Contracts panel
		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/contract_64.png")), createContractsPanel(), Language.getString("descriptionContracts"));
		tabbed.setMnemonicAt(4, KeyEvent.VK_C);

//		//adding Settings Panel
//		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/settings_64.png")), createSettingsPanel(), Language.getString("descriptionSettings"));
//		tabbed.setMnemonicAt(5, KeyEvent.VK_S);

		//adding Language Panel //TODO 060 make language adjust
		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/language_64.png")), createLanguagePanel(), Language.getString("descriptionLanguage"));
		tabbed.setMnemonicAt(5, KeyEvent.VK_L);

		//add tab contant dynamically
		tabbed.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				int tab = tabbed.getSelectedIndex();

				//TODO 020 adjust tab 1 to fit like rest
				if (tab == 2) {
					pnlCalendarInfo = new CalendarPane();
					pnlCalendar.add(pnlCalendarInfo, BorderLayout.CENTER);
				} else if (tab == 3) {
					pnlMessagesInfo = new MessagePane();
					pnlMessages.add(pnlMessagesInfo, BorderLayout.CENTER);
				} else if (tab == 4 && pnlInvoicesInfo == null) {
					pnlInvoicesInfo = new InvoicesPane(data);
					pnlInvoices.add(pnlInvoicesInfo, BorderLayout.CENTER);
				} else if (tab == 5 && pnlContractsInfo == null) {
					pnlContractsInfo = new ContractsPane(data);
					pnlContracts.add(pnlContractsInfo, BorderLayout.CENTER);
				}
			}
		});

		pack();
		this.setLocationRelativeTo(null);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				//TODO 010 check all open windows (dialogs) if there is unsaved data and request to save
				Logger.logger.info("writing settings file on close main, settings language is: " + ProgramSettings.getLanguage());
				ProgramSettings.write();//store settings
				ProgramSettings.print();
				instance.getParent().dispose();
			}
		});
	}

	/**
	 * Creates the add/remove rentables panel
	 * @return the panel
	 */
	private JPanel createAddRemovePanel() {
		pnlAddremove = new JPanel();
		pnlAddremove.setLayout(new BorderLayout());

		JPanel pnlButtonsAddRemove = new JPanel();
		pnlButtonsAddRemove.setBackground(new Color(180, 180, 180, 100));
		pnlAddremove.add(pnlButtonsAddRemove, BorderLayout.PAGE_START);

		//building operations
		JButton btnAddBuilding = new JButton(Main.getAction("buildingAdd"));
		pnlButtonsAddRemove.add(btnAddBuilding);

		JActionButton btnEditBuilding = new JActionButton(Main.getAction("buildingEdit"), pnlAddremoveInfo);
		pnlButtonsAddRemove.add(btnEditBuilding);

		//TODO also multiple selection on buildings > show multiple rentables
		JActionButton btnRemoveBuilding = new JActionButton(Main.getAction("buildingRemove"), pnlAddremoveInfo);
		pnlButtonsAddRemove.add(btnRemoveBuilding);

		//rentable operations
		JActionButton btnAddRentable = new JActionButton(Main.getAction("rentableAdd"), pnlAddremoveInfo);
		pnlButtonsAddRemove.add(btnAddRentable);

		JActionButton btnEditRentable = new JActionButton(Main.getAction("rentableEdit"), pnlAddremoveInfo);
		pnlButtonsAddRemove.add(btnEditRentable);

		JActionButton btnRemoveRentable = new JActionButton(Main.getAction("rentableRemove"), pnlAddremoveInfo);
		pnlButtonsAddRemove.add(btnRemoveRentable);



		return pnlAddremove;
	}

	/**
	 * Creates the users panel
	 * @return the users panel
	 */
	private JPanel createUsers() {
		pnlUsers = new JPanel();
		pnlUsers.setLayout(new BorderLayout());

		JPanel pnlButtonsUsers = new JPanel();
		pnlButtonsUsers.setBackground(new Color(180, 180, 180, 100));
		pnlUsers.add(pnlButtonsUsers, BorderLayout.PAGE_START);

		//user operations
		JButton btnAddUser = new JButton(Main.getAction("userAdd"));
		pnlButtonsUsers.add(btnAddUser);

		JActionButton btnEditUser = new JActionButton(Main.getAction("userEdit"), pnlAddremoveInfo);
		pnlButtonsUsers.add(btnEditUser);

		JActionButton btnRemoveUser = new JActionButton(Main.getAction("userRemove"), pnlAddremoveInfo);
		pnlButtonsUsers.add(btnRemoveUser);

		return pnlUsers;
	}

	/**
	 * Creates the calendar panel
	 * @return the calendar panel
	 */
	private JPanel createCalendarPanel() {

		//Calendar tab
		pnlCalendar = new JPanel();
		pnlCalendar.setLayout(new BorderLayout());

		//TODO 5 change workflow to fit rest of app > on click > select cel > then use buttons on top
		//make tasks idividually selectable in mini lists for each cel

//		JPanel pnlButtonsCalendar = new JPanel();
//		pnlButtonsCalendar.setBackground(new Color(180, 180, 180, 100));
//		pnlCalendar.add(pnlButtonsCalendar, BorderLayout.PAGE_START);
//
//		JButton btnAddTask = new JButton(actions.get("taskAdd"));
//		btnAddTask.setHideActionText(disableBtnText);
//		pnlButtonsCalendar.add(btnAddTask);
//		JButton btnEditTask = new JButton(actions.get("taskEdit"));
//		btnEditTask.setHideActionText(disableBtnText);
//		pnlButtonsCalendar.add(btnEditTask);
//		JButton btnRemoveTask = new JButton(actions.get("taskRemove"));
//		btnRemoveTask.setHideActionText(disableBtnText);
//		pnlButtonsCalendar.add(btnRemoveTask);

		return pnlCalendar;
	}

	/**
	 * Creates the messages panel
	 * @return the messages panel
	 */
	private JPanel createMessagesPanel() {
		//Messages tab
		pnlMessages = new JPanel();
		pnlMessages.setLayout(new BorderLayout());

		JPanel pnlButtonsMessage = new JPanel();
		pnlButtonsMessage.setBackground(new Color(180, 180, 180, 100));
		pnlMessages.add(pnlButtonsMessage, BorderLayout.PAGE_START);

		JButton btnAddMessage = new JButton(actions.get("messageNew"));
		btnAddMessage.setHideActionText(disableBtnText);
		pnlButtonsMessage.add(btnAddMessage);
		JButton btnEditMessage = new JButton(actions.get("messageReply"));
		btnEditMessage.setHideActionText(disableBtnText);
		pnlButtonsMessage.add(btnEditMessage);

		MessageListPanel.addIcon("0", new ImageIcon(getClass().getResource("/images/message_new_23.png")));
		MessageListPanel.addIcon("1", new ImageIcon(getClass().getResource("/images/message_read_23.png")));
		MessageListPanel.addIcon("2", new ImageIcon(getClass().getResource("/images/message_reply_23.png")));

		return pnlMessages;
	}

	/**
	 * Creates the invoices panel
	 * @return the invoices panel
	 */
	private JPanel createInvoicesPanel() {
		//Invoices tab (facturen)
		pnlInvoices = new JPanel();
		pnlInvoices.setLayout(new BorderLayout());

		JPanel pnlButtonsInvoice = new JPanel();
		pnlButtonsInvoice.setBackground(new Color(180, 180, 180, 100));
		pnlInvoices.add(pnlButtonsInvoice, BorderLayout.PAGE_START);

		JButton btnAddInvoice = new JButton(actions.get("invoiceAdd"));
		btnAddInvoice.setHideActionText(disableBtnText);
		pnlButtonsInvoice.add(btnAddInvoice);
		JButton btnEditInvoice = new JButton(actions.get("invoiceEdit"));
		btnEditInvoice.setHideActionText(disableBtnText);
		pnlButtonsInvoice.add(btnEditInvoice);
		JButton btnRemoveInvoice = new JButton(actions.get("invoiceRemove"));
		btnRemoveInvoice.setHideActionText(disableBtnText);
		pnlButtonsInvoice.add(btnRemoveInvoice);

		return pnlInvoices;
	}

	private JPanel createContractsPanel() {
		//Contracts tab (contracten)
		pnlContracts = new JPanel();
		pnlContracts.setLayout(new BorderLayout());

		JPanel pnlButtonsContract = new JPanel();
		pnlButtonsContract.setBackground(new Color(180, 180, 180, 100));
		pnlContracts.add(pnlButtonsContract, BorderLayout.PAGE_START);

		JButton btnAddContract = new JButton(actions.get("contractAdd"));
		btnAddContract.setHideActionText(disableBtnText);
		pnlButtonsContract.add(btnAddContract);
		JButton btnEditContract = new JButton(actions.get("contractEdit"));
		btnEditContract.setHideActionText(disableBtnText);
		pnlButtonsContract.add(btnEditContract);
		JButton btnRemoveContract = new JButton(actions.get("contractRemove"));
		btnRemoveContract.setHideActionText(disableBtnText);
		pnlButtonsContract.add(btnRemoveContract);

		return pnlContracts;
	}

//	/**
//	 * Creates the settings panel
//	 * @return the settings panel
//	 */
//	private JPanel createSettingsPanel() {
//		//Settings tab
//		JPanel pnlSettings = new JPanel();
//		pnlSettings.setPreferredSize(new Dimension(500, 600));
//		pnlSettings.setLayout(new BorderLayout());
//
//		JTabbedPane tabSettings = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.WRAP_TAB_LAYOUT);
//		pnlSettings.add(tabSettings, BorderLayout.CENTER);
//
//		//adding confirm tab
//		JLabel lblConfirm = new JLabel(Language.getString("confirmDialogs"), new ImageIcon(getClass().getResource("/images/warning_32.png")), SwingConstants.RIGHT);
//		JPanel pnlConfirm = new JPanel();
//		pnlConfirm.add(new JLabel("this is the confirm settings dialog"));
//		tabSettings.addTab("", pnlConfirm);
//		tabSettings.setTabComponentAt(tabSettings.getTabCount() - 1, lblConfirm);
//
//		//adding settings tab
//		JLabel lblConnection = new JLabel(Language.getString("connectionSettings"), new ImageIcon(getClass().getResource("/images/connection_32.png")), SwingConstants.RIGHT);
//		JPanel pnlConnection = new JPanel();
//		pnlConnection.add(new JLabel("this is the connection settings dialog"));
//		tabSettings.addTab("", pnlConnection);
//		tabSettings.setTabComponentAt(tabSettings.getTabCount() - 1, lblConnection);
//
//		return pnlSettings;
//	}
	/**
	 * Creates the language panel
	 * @return the language panel
	 */
	private JPanel createLanguagePanel() {
		//Language tab
		JPanel pnlLanguage = new JPanel();
		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.matches("language_.*\\.xml");
			}
		};
		String[] languageFiles = new File(".").list(filter);
		String[] languages = new String[languageFiles.length];
		int selected = 0;
		for (int i = 0; i < languageFiles.length; i++) {
			languages[i] = languageFiles[i].split("[\\._]")[1]; // split filename _or. and get part 2
			if (languages[i].equals(ProgramSettings.getLanguage())) {
				selected = i;
				Logger.logger.info("selected language is: " + languages[selected]);
			}
		}
		cbbLanguages = new JComboBox(languages);
		cbbLanguages.setSelectedIndex(selected);
		pnlLanguage.add(cbbLanguages);

		//apply button
		JButton btnApply = new JButton(getAction("restart"));
		pnlLanguage.add(btnApply);

		return pnlLanguage;
	}

	/**
	 * Getter for an action of a certain type
	 * @param type the type of action
	 * @return the action
	 */
	public static Action getAction(String type) {
		return actions.get(type);
	}

	/**
	 * Getter for the data object
	 * @return the DataModel object
	 */
	public static DataModel getDataObject() {
		return data;
	}

	/**
	 * Rescales an image
	 * @param img the image to resize
	 * @param maxSize the maximum size (the image will never be bigger than
	 * this)
	 * @return the resizes image
	 */
	public static BufferedImage resizeImage(BufferedImage img, int maxSize) {
		double w = img.getWidth();
		double h = img.getHeight();

		//scale image if needed to fit in MAXSIZE
		if (w > maxSize || h > maxSize) {
			double scale = 2;
			if (w > h) {
				scale = maxSize / w;
			} else {
				scale = maxSize / h;
			}
			BufferedImage scaledImage = new BufferedImage(((int) (w * scale)), ((int) (h * scale)), BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			graphics2D.drawImage(img, AffineTransform.getScaleInstance(scale, scale), null);
			graphics2D.dispose();
			return scaledImage;
		}
		return img;

	}

	/**
	 * Getter for the message pane
	 * @return the message pane
	 */
	public static MessagePane getMessagePane() {
		return instance.pnlMessagesInfo;
	}

	/**
	 * Getter for the invoice pane
	 * @return the invoice pane
	 */
	public static InvoicesPane getInvoicesPane() {
		return instance.pnlInvoicesInfo;
	}

	/**
	 * Getter for the contract pane
	 * @return the contract pane
	 */
	public static ContractsPane getContractsPane() {
		return instance.pnlContractsInfo;
	}

	/**
	 * Updates the panels
	 */
	public static void updatePanels() {
		instance.tabbed.updateUI();
	}

	public static String getSelectedLanguage() {
		return (String) cbbLanguages.getSelectedItem();
	}

	@Override
	public JFrame getParent() {
		return parent;
	}
}
