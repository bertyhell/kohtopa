package gui;

//TODO add possibility to access all functions trough ALT (maybe autohide file bar?)
//TODO add right click menu's in all panels
//TODO logging
//TODO add titles to all lists
import Language.Language;
import gui.messagetab.MessagePane;
import gui.actions.*;
import gui.addremovetab.BuildingCellRenderer;
import gui.addremovetab.BuildingDialog;
import gui.addremovetab.RentableCellRenderer;
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
import gui.addremovetab.IdentifiableI;
import gui.addremovetab.RentableDialog;
import gui.calendartab.CalendarPanel;
import gui.contractstab.ContractsPane;
import gui.invoicestab.InvoicesPane;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class Main extends JFrame {

	private static Main instance = new Main();
	private static HashMap<String, Action> actions;
	public final static boolean disableBtnText = false;
	private JList lstBuildings;
	private JList lstRentables;
	private static DataModel data;
	private JPanel pnlMessages;
	private JPanel pnlInvoices;
	private JPanel pnlContracts;
	private MessagePane pnlMessagesInfo;
	private InvoicesPane pnlInvoicesInfo;
    private ContractsPane pnlContractsInfo;
	private JTabbedPane tabbed;
	private static ArrayList<IdentifiableI> dialogs; //TODO on close check all dialogs if they have any unsaved data
	private static int focusedDialog;

	/**
	 * Getter for the main instance
	 * @return instance
	 */
	public static Main getInstance() {
		return instance;
	}

	/**
	 * initializes the program
	 */
	private Main() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			System.out.println("");
			JOptionPane.showMessageDialog(this, "Look and Feel not found, make sure you have latest java version\n" + ex.getMessage(), "Look and Feel not found", JOptionPane.ERROR_MESSAGE);
		}
		Language.read(); //reads strings in specific language from xml file
		ProgramSettings.read(); //TODO fix, if needed do it manually with dom

		setTitle(Language.getString("titleJFrameDesktopMain"));
		data = new DataModel();
		dialogs = new ArrayList<IdentifiableI>();

		//actions
		actions = new HashMap<String, Action>();

		actions.put("buildingAdd", new BuildingAddAction("buildingAdd", new ImageIcon(getClass().getResource("/images/building_add_23.png"))));
		actions.put("buildingEdit", new BuildingEditAction("buildingEdit", new ImageIcon(getClass().getResource("/images/building_edit_23.png"))));
		actions.put("buildingRemove", new BuildingRemoveAction("buildingRemove", new ImageIcon(getClass().getResource("/images/building_remove_23.png"))));
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
		actions.put("messageRemove", new MessageRemoveAction("messageRemove", new ImageIcon(getClass().getResource("/images/message_remove_23.png"))));
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
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//TODO add onclose handler > check if dialogs have changed data if so ask if user wants to save
		this.setMinimumSize(new Dimension(370, 300));
		this.setLayout(new BorderLayout());
		tabbed = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		this.add(tabbed, BorderLayout.CENTER);

		//adding Add/Remove panel
		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/building_64.png")), createAddRemovePanel(), Language.getString("descriptionAddRemove"));
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
        tabbed.setMnemonicAt(4, KeyEvent.VK_O);

//		//adding Settings Panel
//		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/settings_64.png")), createSettingsPanel(), Language.getString("descriptionSettings"));
//		tabbed.setMnemonicAt(5, KeyEvent.VK_S);

		//adding Language Panel
		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/language_64.png")), createLanguagePanel(), Language.getString("descriptionLanguage"));
		tabbed.setMnemonicAt(5, KeyEvent.VK_L);


		pack();
		this.setLocationRelativeTo(null);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				//TODO check all open windows (dialogs) if there is unsaved data and request to save
				ProgramSettings.write();//store settings
			}
		});
	}

	/**
	 * Creates the add/remove rentables panel
	 * @return the panel
	 */
	private JPanel createAddRemovePanel() {
		//Add / Remove tab
		JPanel pnlAddRemove = new JPanel();
		pnlAddRemove.setLayout(new BorderLayout());

		JPanel pnlButtonsAddRemove = new JPanel();
		pnlButtonsAddRemove.setBackground(new Color(180, 180, 180, 100));
		pnlAddRemove.add(pnlButtonsAddRemove, BorderLayout.PAGE_START);

		//building operations
		JButton btnAddBuilding = new JButton(actions.get("buildingAdd"));
		btnAddBuilding.setHideActionText(disableBtnText);
		pnlButtonsAddRemove.add(btnAddBuilding);

		JButton btnEditBuilding = new JButton(actions.get("buildingEdit"));
		btnEditBuilding.setHideActionText(disableBtnText); //replace disableBtnText by setting in settings tab
		pnlButtonsAddRemove.add(btnEditBuilding);

		//remove selected buildings > make it remove all selected buildings
		//also multiple selection on buildings > show multiple rentables
		JButton btnRemoveBuilding = new JButton(actions.get("buildingRemove"));
		btnRemoveBuilding.setHideActionText(disableBtnText);
		pnlButtonsAddRemove.add(btnRemoveBuilding);

		//rentable operations
		JButton btnAddRentable = new JButton(actions.get("rentableAdd"));
		btnAddRentable.setHideActionText(disableBtnText);
		pnlButtonsAddRemove.add(btnAddRentable);

		JButton btnEditRentable = new JButton(actions.get("rentableEdit"));
		btnEditRentable.setHideActionText(disableBtnText);
		pnlButtonsAddRemove.add(btnEditRentable);

		JButton btnRemoveRentable = new JButton(actions.get("rentableRemove"));
		btnRemoveRentable.setHideActionText(disableBtnText);
		pnlButtonsAddRemove.add(btnRemoveRentable);

		//add lists of buildings and Rentables
		JScrollPane scrolBuilding = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane scrolRentable = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrolBuilding, scrolRentable);
		splitter.setDividerLocation(325);
		splitter.setDividerSize(10);


		//TODO fix list (delete listmodels) do it Jelle's way
		//building preview list
		lstBuildings = new JList();
		lstBuildings.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstBuildings.setBackground(new Color(217, 217, 217));
		lstBuildings.setCellRenderer(new BuildingCellRenderer());


		scrolBuilding.setViewportView(lstBuildings);
		//TODO mouse over color change
//        lstBuildings.addMouseMotionListener(new MouseMotionAdapter() {
//
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                //if mouse moves over new building > update list backgroundcolors
//                if (data.mouseOver(lstBuildings.locationToIndex(e.getPoint()), true)) {
//                    lstBuildings.repaint();
//                }
//            }
//        });


		//Rentable preview list
		lstRentables = new JList();
		lstBuildings.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstRentables.setBackground(new Color(217, 217, 217));
		lstRentables.setCellRenderer(new RentableCellRenderer());

		scrolRentable.setViewportView(lstRentables);
//        lstRentables.addMouseMotionListener(new MouseMotionAdapter() {
//
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                int index = lstBuildings.locationToIndex(e.getPoint());
//                if (data.mouseOver(index, false)) {
//                    lstBuildings.repaint();
//                }
//            }
//        });

		splitter.setPreferredSize(new Dimension(1000, 600));
		pnlAddRemove.add(splitter, BorderLayout.CENTER);

		return pnlAddRemove;
	}

	/**
	 * Creates the calendar panel
	 * @return the calendar panel
	 */
	private JPanel createCalendarPanel() {

		//Calendar tab
		JPanel pnlCalendar = new JPanel();
		pnlCalendar.setLayout(new BorderLayout());

		JPanel pnlButtonsCalendar = new JPanel();
		pnlButtonsCalendar.setBackground(new Color(180, 180, 180, 100));
		pnlCalendar.add(pnlButtonsCalendar, BorderLayout.PAGE_START);

		JButton btnAddTask = new JButton(actions.get("taskAdd"));
		btnAddTask.setHideActionText(disableBtnText);
		pnlButtonsCalendar.add(btnAddTask);
		JButton btnEditTask = new JButton(actions.get("taskEdit"));
		btnEditTask.setHideActionText(disableBtnText);
		pnlButtonsCalendar.add(btnEditTask);
		JButton btnRemoveTask = new JButton(actions.get("taskRemove"));
		btnRemoveTask.setHideActionText(disableBtnText);
		pnlButtonsCalendar.add(btnRemoveTask);

		JPanel pnlDays = new CalendarPanel();
		pnlDays.setPreferredSize(new Dimension(500, 600));
		pnlDays.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlCalendar.add(pnlDays, BorderLayout.CENTER);

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
		JButton btnRemoveMessage = new JButton(actions.get("messageRemove"));
		btnRemoveMessage.setHideActionText(disableBtnText);
		pnlButtonsMessage.add(btnRemoveMessage);

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
				return name.matches("language_[A-Z]{2}_.*\\.xml");
			}
		};
		String[] languageFiles = new File(".").list(filter);
		String[] languages = new String[languageFiles.length];
		for (int i = 0; i < languageFiles.length; i++) {
			languages[i] = languageFiles[i].split("[\\._]")[2]; // split filename _or. and get part 2
		}
		JComboBox cbbLanguages = new JComboBox(languages);
		pnlLanguage.add(cbbLanguages);
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
	 * Fetches the messages, fills up the message panel
	 */
	public void fetchMessages() {
		pnlMessagesInfo = new MessagePane();
		pnlMessagesInfo.setPreferredSize(new Dimension(500, 600));
		pnlMessages.add(pnlMessagesInfo, BorderLayout.CENTER);
	}

	/**
	 * Fetches the invoices, fills up the invoices panel
	 */
	public void fetchInvoices() {
		if (pnlInvoicesInfo == null) {
			pnlInvoicesInfo = new InvoicesPane(data);
			pnlInvoicesInfo.setPreferredSize(new Dimension(500, 600));
			pnlInvoices.add(pnlInvoicesInfo, BorderLayout.CENTER);
		}
	}

	/**
	 * Fetches the contracts, fills up the contracts panel
	 */
	public void fetchContracts() {
		System.out.println("fetching contracts");
        pnlContractsInfo = new ContractsPane(data);
        pnlContractsInfo.setPreferredSize(new Dimension(500, 600));
        pnlContracts.add(pnlContractsInfo, BorderLayout.CENTER);
	}

	/**
	 * Getter for the data object
	 * @return the DataModel object
	 */
	public static DataModel getDataObject() {
		return data;
	}

	/**
	 * Getter for focused dialog
	 * @return focusedDialog
	 */
	public static IdentifiableI getFocusedDialog() {
		return dialogs.get(focusedDialog);
	}

	/**
	 * Setter for focsed dialog
	 * @param dialog the dialog to focus
	 */
	public static void setFocusedDialog(IdentifiableI dialog) {
		int index = 0;
		while (index < dialogs.size() && dialogs.get(index) != dialog) {
			index++;
		}
		if (index == dialogs.size()) {
			focusedDialog = dialogs.size(); //equals the same line after "add" but with size()-1 (more efficient this way)
			dialogs.add(dialog);
		} else {
			focusedDialog = index;
		}
	}

	/**
	 * Updates the picture list
	 */
	public void updatePictureList() {
		for (IdentifiableI dialog : dialogs) {
			dialog.UpdatePictures(); //FIXME werkt nog niet goed (bij picture add)
		}
	}

	/**
	 * Updates the data list
	 */
	public void updateDataList() {
		for (IdentifiableI dialog : dialogs) {
			dialog.UpdateDataLists();
		}
	}

	/**
	 * Updates the list
	 */
	public void updateList() {
		lstBuildings.setModel(data.getLmBuilding());
	}

	/**
	 * Initialises the listeners and shows the application
	 */
	public void init() {
		lstBuildings.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					//open building dialog
					(new BuildingDialog(instance.getRootPane(), data.getSelectedBuildingId(), false)).setVisible(true);
				} else {
					try {
						//get rentables from the selected building
						int index = lstBuildings.locationToIndex(e.getPoint());
						lstRentables.setModel(data.updateRentables(index));
						DataModel.setBuildingIndex(index);
					} catch (Exception ex) {
						//FIXME exception opsplitsen, translation messages
						JOptionPane.showMessageDialog(instance, "Couldn't connect to database\n" + ex.getMessage(), "connection failed", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		lstRentables.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					//open rentable dialog

					new RentableDialog(instance.getRootPane(), data.getSelectedRentableId(), false).setVisible(true);
				} else {
					try {
						//select rentable
						DataModel.setRentableIndex(lstRentables.locationToIndex(e.getPoint()));
					} catch (Exception ex) {
						//FIXME exception opsplitsen, translation messages
						JOptionPane.showMessageDialog(instance, "Couldn't connect to database\n" + ex.getMessage(), "connection failed", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		tabbed.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				int tab = tabbed.getSelectedIndex();
				if (tab == 0) {
					data.fetchAddRemove();
				} else if (tab == 2) {
					fetchMessages();
				} else if (tab == 3) {
					fetchInvoices();
				} else if (tab == 4) {
                    fetchContracts();
                }
				//TODO add rest off fetch methods
			}
		});
		this.setVisible(true);
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
	 * Updates the picture list
	 */
	public static void updatePictureLists() {
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

	/**
	 * Main method, starts the program
	 * @param args
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				boolean loginChecked = false;
				//check settings if remember pass is true:
				if (ProgramSettings.isRemeberPassword()) {
					//check if stored pass is correct
					if (Main.getDataObject().checkLogin(ProgramSettings.getUsername(), ProgramSettings.getPassword())) {
						loginChecked = true;
					} else {
						//check user for login
						new LoginDialog(instance, data).setVisible(true);
						if (data.isLoggedIn()) {
							loginChecked = true;
						}
					}
				} else {
					//check user for login
					new LoginDialog(instance, data).setVisible(true);
					if (data.isLoggedIn()) {
						loginChecked = true;
					}
				}

				if (loginChecked) {
					//logged in
					SplashConnect.showSplash();
					//TODO show splash with different tread?
					//SplashConnect.showSplash();

					//TODO change to while with possibility to abort
					while (!data.fetchAddRemove()) {
						//database connection failed
						//TODO show connection string dialog

						SplashConnect.hideSplash();
						JOptionPane.showMessageDialog(instance, "connecting", "connection", JOptionPane.INFORMATION_MESSAGE);

						SplashConnect.showSplash();
					}

					SplashConnect.hideSplash();
					instance.init();
					instance.updateList();
				} else {
					Main.getInstance().dispose();
				}
			}
		});
	}
}
