package gui;

//TODO add possibility to access all functions trough ALT (maybe autohide file bar?)
//TODO add right click menu's in all panels
import Language.Language;
import gui.actions.*;
import gui.addremove.BuildingCellRenderer;
import gui.addremove.BuildingDialog;
import gui.addremove.PanelListModel;
import gui.addremove.BuildingListPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import model.Building;
import model.Model;

public class Main extends JFrame {

	private static Main instance = new Main();
	private static HashMap<String, Action> actions;
	private static boolean btnText = false;

	public static Main getInstance() {
		return instance;
	}

	private Main() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Look and Feel not found, make sure you have latest java version\n" + ex.getMessage(), "Look and Feel not found", JOptionPane.ERROR_MESSAGE);
		}
		Language.read(); //reads strings in specific language from xml file
		setTitle(Language.getString("titleJFrameDesktopMain"));


		//splashscreen
		SplashConnect.showSplash();


//
//
//		Model.getInstance().addDummyPictures();
		//		Model.getInstance().addDummyPictures();
//		BuildingDialog bd = new BuildingDialog(this, -2147483648, false);
//		bd.setVisible(true);






		//actions
		actions = new HashMap<String, Action>();

		actions.put("buildingAdd", new BuildingAddAction(new ImageIcon(getClass().getResource("/images/building_add_23.png"))));
		actions.put("buildingEdit", new BuildingEditAction(new ImageIcon(getClass().getResource("/images/building_edit_23.png"))));
		actions.put("buildingRemove", new BuildingRemoveAction(new ImageIcon(getClass().getResource("/images/building_remove_23.png"))));
		actions.put("roomAdd", new RoomAddAction(new ImageIcon(getClass().getResource("/images/room_add_23.png"))));
		actions.put("roomEdit", new RoomEditAction(new ImageIcon(getClass().getResource("/images/room_edit_23.png"))));
		actions.put("roomRemove", new RoomRemoveAction(new ImageIcon(getClass().getResource("/images/room_remove_23.png"))));
		actions.put("taskAdd", new TaskAddAction(new ImageIcon(getClass().getResource("/images/task_add_23.png"))));
		actions.put("taskEdit", new TaskEditAction(new ImageIcon(getClass().getResource("/images/task_edit_23.png"))));
		actions.put("taskRemove", new TaskRemoveAction(new ImageIcon(getClass().getResource("/images/task_remove_23.png"))));
		actions.put("messageNew", new MessageNewAction(new ImageIcon(getClass().getResource("/images/message_new_23.png"))));
		actions.put("messageReply", new MessageReplyAction(new ImageIcon(getClass().getResource("/images/message_reply_23.png"))));
		actions.put("messageRemove", new MessageRemoveAction(new ImageIcon(getClass().getResource("/images/message_remove_23.png"))));
		actions.put("invoiceAdd", new InvoiceAddAction(new ImageIcon(getClass().getResource("/images/invoice_add_23.png"))));
		actions.put("invoiceEdit", new InvoiceEditAction(new ImageIcon(getClass().getResource("/images/invoice_edit_23.png"))));
		actions.put("invoiceRemove", new InvoiceRemoveAction(new ImageIcon(getClass().getResource("/images/invoice_remove_23.png"))));
		actions.put("floorAdd", new FloorAddAction(new ImageIcon(getClass().getResource("/images/floor_add_32.png"))));
		actions.put("floorEdit", new FloorEditAction(new ImageIcon(getClass().getResource("/images/floor_edit_32.png"))));
		actions.put("floorRemove", new FloorRemoveAction(new ImageIcon(getClass().getResource("/images/floor_remove_32.png"))));

		//jframe
		this.setIconImage(new ImageIcon(getClass().getResource("/images/ico.png")).getImage());
		this.setExtendedState(this.getExtendedState() | Main.MAXIMIZED_BOTH);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(370, 300));
		this.setLayout(new BorderLayout());
		JTabbedPane tabbed = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
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
		SplashConnect.hideSplash();
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
		btnAddBuilding.setHideActionText(btnText);
		pnlButtonsAddRemove.add(btnAddBuilding);

		JButton btnEditBuilding = new JButton(actions.get("buildingEdit"));
		btnEditBuilding.setHideActionText(btnText);
		pnlButtonsAddRemove.add(btnEditBuilding);

		JButton btnRemoveBuilding = new JButton(actions.get("buildingRemove"));
		btnRemoveBuilding.setHideActionText(btnText);
		pnlButtonsAddRemove.add(btnRemoveBuilding);

		//room operations
		JButton btnAddRoom = new JButton(actions.get("roomAdd"));
		btnAddRoom.setHideActionText(btnText);
		pnlButtonsAddRemove.add(btnAddRoom);

		JButton btnEditRoom = new JButton(actions.get("roomEdit"));
		btnEditRoom.setHideActionText(btnText);
		pnlButtonsAddRemove.add(btnEditRoom);

		JButton btnRemoveRoom = new JButton(actions.get("roomRemove"));
		btnRemoveRoom.setHideActionText(btnText);
		pnlButtonsAddRemove.add(btnRemoveRoom);

		//add lists of buildings and rooms
		JScrollPane scrolBuilding = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane scrolRoom = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrolBuilding, scrolRoom);
		splitter.setDividerLocation(325);
		splitter.setDividerSize(10);


		//building preview list
		PanelListModel lmBuilding = new PanelListModel();
		JList listBuildings = new JList(lmBuilding);
		listBuildings.setBackground(new Color(217, 217, 217));
		listBuildings.setCellRenderer(new BuildingCellRenderer());

		ArrayList<Building> buildingPreviews = null;
		try {
			buildingPreviews = Model.getInstance().getBuildingPreviews(instance);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(instance, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), Language.getString("errConnectDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}

		try {
			System.out.println("aantal buildings: " + buildingPreviews.size());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}

		for (Building building : buildingPreviews) {
			lmBuilding.add(new BuildingListPanel(
					building.getId(),
					building.getPreviewImage(),
					building.getStreet() + " " + building.getNumber(),
					building.getZipcode(),
					building.getCity()));
		}

		scrolBuilding.setViewportView(listBuildings);


		//room preview list
		PanelListModel lmRoom = new PanelListModel();
		JList listRooms = new JList(lmRoom);
		listRooms.setBackground(new Color(217, 217, 217));
		listRooms.setCellRenderer(new BuildingCellRenderer());
		//		//room preview list
		//		PanelListModel lmRoom = new PanelListModel();
		//		JList listRooms = new JList(lmRoom);
		//		listRooms.setBackground(new Color(217, 217, 217));
		//		listRooms.setCellRenderer(new BuildingCellRenderer());
		//
		//		for (int i = 0; i < 20; i++) {
		//			lmRoom.add(new RoomListPanel(675654, "jannes", 12));
		//		}
		//
		//		scrolRoom.setViewportView(listRooms);
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
		btnAddTask.setHideActionText(btnText);
		pnlButtonsCalendar.add(btnAddTask);
		JButton btnEditTask = new JButton(actions.get("taskEdit"));
		btnEditTask.setHideActionText(btnText);
		pnlButtonsCalendar.add(btnEditTask);
		JButton btnRemoveTask = new JButton(actions.get("taskRemove"));
		btnRemoveTask.setHideActionText(btnText);
		pnlButtonsCalendar.add(btnRemoveTask);

		JPanel pnlDays = new JPanel();
		pnlDays.setPreferredSize(new Dimension(500, 600));
		pnlCalendar.add(pnlDays, BorderLayout.CENTER);

		return pnlCalendar;
	}

	private JPanel createMessagesPanel() {
		//Messages tab
		JPanel pnlMessages = new JPanel();
		pnlMessages.setLayout(new BorderLayout());

		JPanel pnlButtonsMessage = new JPanel();
		pnlButtonsMessage.setBackground(new Color(180, 180, 180, 100));
		pnlMessages.add(pnlButtonsMessage, BorderLayout.PAGE_START);

		JButton btnAddMessage = new JButton(actions.get("messageNew"));
		btnAddMessage.setHideActionText(btnText);
		pnlButtonsMessage.add(btnAddMessage);
		JButton btnEditMessage = new JButton(actions.get("messageReply"));
		btnEditMessage.setHideActionText(btnText);
		pnlButtonsMessage.add(btnEditMessage);
		JButton btnRemoveMessage = new JButton(actions.get("messageRemove"));
		btnRemoveMessage.setHideActionText(btnText);
		pnlButtonsMessage.add(btnRemoveMessage);

		//replace by plugin messagepanel by jelle:
		MessagePane pnlMessagesInfo = new MessagePane();




		pnlMessagesInfo.setPreferredSize(new Dimension(500, 600));
		pnlMessages.add(pnlMessagesInfo, BorderLayout.CENTER);

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
		btnAddInvoice.setHideActionText(btnText);
		pnlButtonsInvoice.add(btnAddInvoice);
		JButton btnEditInvoice = new JButton(actions.get("invoiceEdit"));
		btnEditInvoice.setHideActionText(btnText);
		pnlButtonsInvoice.add(btnEditInvoice);
		JButton btnRemoveInvoice = new JButton(actions.get("invoiceRemove"));
		btnRemoveInvoice.setHideActionText(btnText);
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

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				instance.setVisible(true);
			}
		});
	}
}
