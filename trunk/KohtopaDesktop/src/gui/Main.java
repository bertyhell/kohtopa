package gui;

//TODO add possibility to access all functions trough ALT (maybe autohide file bar?)
//TODO add right click menu's in all panels
import Language.Language;
import gui.MessageTab.MessagePane;
import gui.actions.*;
import gui.AddRemoveTab.BuildingCellRenderer;
import gui.AddRemoveTab.BuildingDialog;
import gui.AddRemoveTab.PanelListModel;
import gui.AddRemoveTab.BuildingListPanel;
import gui.AddRemoveTab.RentableCellRenderer;
import gui.AddRemoveTab.RentableListPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.data.Building;
import model.Model;
import model.data.Rentable;

public class Main extends JFrame {

	private static Main instance = new Main();
	private static HashMap<String, Action> actions;
	public final static boolean disableBtnText = true;
	private JList lstBuildings;
	private JList lstRentables;
	private ArrayList<Building> buildingPreviews;
	private ArrayList<Rentable> rentablePreviews;
	private PanelListModel lmBuilding;
	private PanelListModel lmRentable;
	private static int buildingIndex;
	private static int rentableIndex;

	public static Main getInstance() {
		return instance;
	}
	private JPanel pnlMessages;
	private final JTabbedPane tabbed;

	private Main() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			System.out.println("");
			JOptionPane.showMessageDialog(this, "Look and Feel not found, make sure you have latest java version\n" + ex.getMessage(), "Look and Feel not found", JOptionPane.ERROR_MESSAGE);
		}
		Language.read(); //reads strings in specific language from xml file
		setTitle(Language.getString("titleJFrameDesktopMain"));

		//actions
		actions = new HashMap<String, Action>();

		actions.put("buildingAdd", new BuildingAddAction("buildingAdd", new ImageIcon(getClass().getResource("/images/building_add_23.png"))));
		actions.put("buildingEdit", new BuildingEditAction("buildingEdit", new ImageIcon(getClass().getResource("/images/building_edit_23.png"))));
		actions.put("buildingRemove", new BuildingRemoveAction("buildingRemove", new ImageIcon(getClass().getResource("/images/building_remove_23.png"))));
		actions.put("rentableAdd", new RentableAddAction("rentableAdd", new ImageIcon(getClass().getResource("/images/rentable_add_23.png"))));
		actions.put("rentableEdit", new RentableEditAction("rentableEdit", new ImageIcon(getClass().getResource("/images/rentable_edit_23.png"))));
		actions.put("rentableRemove", new RentableRemoveAction("rentableRemove", new ImageIcon(getClass().getResource("/images/rentable_remove_23.png"))));
		actions.put("taskAdd", new TaskAddAction("taskAdd", new ImageIcon(getClass().getResource("/images/task_add_23.png"))));
		actions.put("taskEdit", new TaskEditAction("taskEdit", new ImageIcon(getClass().getResource("/images/task_edit_23.png"))));
		actions.put("taskRemove", new TaskRemoveAction("taskRemove", new ImageIcon(getClass().getResource("/images/task_remove_23.png"))));
		actions.put("messageNew", new MessageNewAction("messageNew", new ImageIcon(getClass().getResource("/images/message_new_23.png"))));
		actions.put("messageReply", new MessageReplyAction("messageReply", new ImageIcon(getClass().getResource("/images/message_reply_23.png"))));
		actions.put("messageRemove", new MessageRemoveAction("messageRemove", new ImageIcon(getClass().getResource("/images/message_remove_23.png"))));
		actions.put("invoiceAdd", new InvoiceAddAction("invoiceAdd", new ImageIcon(getClass().getResource("/images/invoice_add_23.png"))));
		actions.put("invoiceEdit", new InvoiceEditAction("invoiceEdit", new ImageIcon(getClass().getResource("/images/invoice_edit_23.png"))));
		actions.put("invoiceRemove", new InvoiceRemoveAction("invoiceRemove", new ImageIcon(getClass().getResource("/images/invoice_remove_23.png"))));
		actions.put("floorAdd", new FloorAddAction("floorAdd", new ImageIcon(getClass().getResource("/images/floor_add_23.png"))));
		actions.put("floorEdit", new FloorEditAction("floorEdit", new ImageIcon(getClass().getResource("/images/floor_edit_23.png"))));
		actions.put("floorRemove", new FloorRemoveAction("floorRemove", new ImageIcon(getClass().getResource("/images/floor_remove_23.png"))));

		//jframe
		this.setIconImage(new ImageIcon(getClass().getResource("/images/ico.png")).getImage());
		this.setExtendedState(this.getExtendedState() | Main.MAXIMIZED_BOTH);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(370, 300));
		this.setLayout(new BorderLayout());
		tabbed = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

		//fetchaddremove


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

		//adding Settings Panel
		tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/settings_64.png")), createSettingsPanel(), Language.getString("descriptionSettings"));
		tabbed.setMnemonicAt(4, KeyEvent.VK_S);


		pack();
		this.setLocationRelativeTo(null);
	}

	private JPanel createAddRemovePanel() {
		//Add / Remove tab
		JPanel pnlAddRemove = new JPanel();
		pnlAddRemove.setLayout(new BorderLayout());

		JPanel pnlButtonsAddRemove = new JPanel();
		pnlButtonsAddRemove.setBackground(new Color(180, 180, 180, 100));
		pnlAddRemove.add(pnlButtonsAddRemove, BorderLayout.PAGE_START);

		//building operations
		JButton btnAddBuilding = new JButton(actions.get("buildingAdd"));
		btnAddBuilding.setHideActionText(true);
		pnlButtonsAddRemove.add(btnAddBuilding);

		JButton btnEditBuilding = new JButton(actions.get("buildingEdit"));
		btnEditBuilding.setHideActionText(disableBtnText);
		pnlButtonsAddRemove.add(btnEditBuilding);

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


		//building preview list
		lmBuilding = new PanelListModel();
		lstBuildings = new JList();
		lstBuildings.setBackground(Color.red);
		lstBuildings.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstBuildings.setBackground(new Color(217, 217, 217));
		lstBuildings.setCellRenderer(new BuildingCellRenderer());

		scrolBuilding.setViewportView(lstBuildings);
		lstBuildings.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int index = lstBuildings.locationToIndex(e.getPoint());
				if (e.getClickCount() == 1) {
					try {
						rentablePreviews = Model.getInstance().getRentablePreviews(buildingPreviews.get(index).getId());
					} catch (Exception ex) {
						//FIXME exception opsplitsen, translation messages
						JOptionPane.showMessageDialog(instance, "Couldn't connect to database\n" + ex.getMessage(), "connection failed", JOptionPane.ERROR_MESSAGE);
					}
					lmRentable.clear();
					for (Rentable rentable : rentablePreviews) {
						lmRentable.addElement(new RentableListPanel(
								rentable.getId(), null, rentable.getType(), rentable.getFloor()));
					}
					lstRentables.setModel(lmRentable);
					System.out.println("rentables updated");
				} else {
					//open building dialog
					BuildingDialog.show(instance, buildingPreviews.get(index).getId(), false);
				}
			}
		});
		buildingIndex = -1; //none selected
		lstBuildings.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				int index = lstBuildings.locationToIndex(e.getPoint());
				if (index != buildingIndex) {
					buildingIndex = index;
					lstBuildings.repaint();
				}
			}
		});


		//Rentable preview list
		lmRentable = new PanelListModel();
		lstRentables = new JList(lmRentable);
		lstRentables.setBackground(Color.red);
		//listRentables.setBackground(new Color(217, 217, 217));
		lstRentables.setCellRenderer(new RentableCellRenderer());
		lstRentables.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				int index = lstRentables.locationToIndex(e.getPoint());
				if (index != rentableIndex) {
					rentableIndex = index;
					lstRentables.repaint();
				}
			}
		});

		rentableIndex = -1; //none selected
		splitter.setPreferredSize(new Dimension(1000, 600));
		pnlAddRemove.add(splitter, BorderLayout.CENTER);

		return pnlAddRemove;
	}

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

		JPanel pnlDays = new JPanel();
		pnlDays.setPreferredSize(new Dimension(500, 600));
		pnlCalendar.add(pnlDays, BorderLayout.CENTER);

		return pnlCalendar;
	}

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

		return pnlMessages;
	}

	private JPanel createInvoicesPanel() {
		//Invoices tab (facturen)
		JPanel pnlInvoices = new JPanel();
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


		JPanel pnlInvoicesInfo = new JPanel();
		pnlInvoicesInfo.setPreferredSize(new Dimension(500, 600));
		pnlInvoices.add(pnlInvoicesInfo, BorderLayout.CENTER);

		return pnlInvoices;
	}

	private JPanel createSettingsPanel() {
		//Settings tab
		JPanel pnlSettings = new JPanel();
		pnlSettings.setPreferredSize(new Dimension(500, 600));
		pnlSettings.setLayout(new BorderLayout());

		return pnlSettings;
	}

	public static Action getAction(String type) {
		return actions.get(type);
	}

	public static int getBuildingIndex() {
		return buildingIndex;
	}

	public static int getRentableIndex() {
		return rentableIndex;
	}

	public JList getListBuildings() {
		return lstBuildings;
	}

	public JList getListRentables() {
		return lstRentables;
	}

	public boolean fetchDatabaseInformation() {
		tabbed.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				int tab = tabbed.getSelectedIndex();
				if (tab == 0) {
					System.out.println("fetching buildings");
					fetchAddRemove();
				} else if (tab == 2) {
					fetchMessages();
				}
				System.out.println("Tab=" + tabbed.getSelectedIndex());
				//add fetch methods
			}
		});
		return fetchAddRemove();
	}

	public boolean fetchAddRemove() {
		try {
			buildingPreviews = Model.getInstance().getBuildingPreviews(instance);

			lmBuilding.clear();
			for (Building building : buildingPreviews) {
				lmBuilding.addElement(new BuildingListPanel(
						building.getId(),
						building.getPreviewImage(),
						building.getStreet() + " " + building.getNumber(),
						building.getZipcode(),
						building.getCity()));
			}
			lstBuildings.setModel(lmBuilding);
			return true;
		} catch (SQLException ex) {
			System.out.println("fetch failed(listener)");
			JOptionPane.showMessageDialog(instance, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), Language.getString("errConnectDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
			//TODO add connection string settings

			return false;
		} catch (IOException ex) {
			System.out.println("fetch failed io (listener)");
			JOptionPane.showMessageDialog(instance, Language.getString("errImagesFetchFail") + "\n" + ex.getMessage(), Language.getString("errImagesFetchFailTitle"), JOptionPane.ERROR_MESSAGE);
			//TODO add connection string settings

			return false;
		}
	}

	public void fetchMessages() {
		//plugin messagepanel by jelle:
		MessagePane pnlMessagesInfo = new MessagePane();

		pnlMessagesInfo.setPreferredSize(new Dimension(500, 600));
		pnlMessages.add(pnlMessagesInfo, BorderLayout.CENTER);
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				System.out.println("start construction");
				instance.setVisible(true);
				System.out.println("end const. show splash");
				SplashConnect.showSplash();
				System.out.println("start database connection");
				//TODO change to while with possibility to abort
				while (!instance.fetchDatabaseInformation()) {
					//database connection failed
					//TODO show connection string dialog
					JOptionPane.showMessageDialog(instance, "here comes connection string dialog", "connection", JOptionPane.INFORMATION_MESSAGE);
				}
				System.out.println("finich database, hide splash");
				SplashConnect.hideSplash();
			}
		});
	}
}
