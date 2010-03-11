package gui;

import Language.Language;
import gui.actions.*;
import gui.addremove.PanelCellRenderer;
import gui.addremove.PanelListModel;
import gui.addremove.BuildingListPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import model.Building;
import model.Model;

public class Main extends JFrame {

    private static Main instance = new Main();
    private static HashMap<String, Action> actions;

    public static Main getInstance() {
	return instance;
    }

    private Main() {
	Language.read(); //reads strings in specific language from xml file
	setTitle(Language.getString("titleJFrameDesktopMain"));
	try {
	    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	} catch (Exception e) {
	    JOptionPane.showMessageDialog(this, Language.getString("errLookAndFeelNotFound"), Language.getString("errLookAndFeelNotFoundTitle"), JOptionPane.ERROR_MESSAGE);
	}

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
	tabbed.setMnemonicAt(0, KeyEvent.VK_C);


	//adding Messages panel
	tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/messages_64.png")), createMessagesPanel(), Language.getString("descriptionMesssages"));
	tabbed.setMnemonicAt(0, KeyEvent.VK_M);

	//adding Invoices panel
	tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/invoice_64.png")), createInvoicesPanel(), Language.getString("descriptionInvoices"));
	tabbed.setMnemonicAt(0, KeyEvent.VK_I);

	//adding Settings Panel
	tabbed.addTab(null, new ImageIcon(getClass().getResource("/images/settings_64.png")), createSettingsPanel(), Language.getString("descriptionSettings"));
	tabbed.setMnemonicAt(0, KeyEvent.VK_S);


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
	btnAddBuilding.setToolTipText(Language.getString("addBuilding"));
	pnlButtonsAddRemove.add(btnAddBuilding);

	JButton btnEditBuilding = new JButton(actions.get("buildingEdit"));
	btnEditBuilding.setHideActionText(true);
	btnEditBuilding.setToolTipText(Language.getString("editBuilding"));
	pnlButtonsAddRemove.add(btnEditBuilding);

	JButton btnRemoveBuilding = new JButton(actions.get("buildingRemove"));
	btnRemoveBuilding.setHideActionText(true);
	btnRemoveBuilding.setToolTipText(Language.getString("removeBuildings"));
	pnlButtonsAddRemove.add(btnRemoveBuilding);

	//room operations
	JButton btnAddRoom = new JButton(actions.get("roomAdd"));
	btnAddRoom.setHideActionText(true);
	btnAddRoom.setToolTipText(Language.getString("addRoom"));
	pnlButtonsAddRemove.add(btnAddRoom);

	JButton btnEditRoom = new JButton(actions.get("roomEdit"));
	btnEditRoom.setHideActionText(true);
	btnEditRoom.setToolTipText(Language.getString("editRoom"));
	pnlButtonsAddRemove.add(btnEditRoom);

	JButton btnRemoveRoom = new JButton(actions.get("roomRemove"));
	btnRemoveRoom.setHideActionText(true);
	btnRemoveRoom.setToolTipText(Language.getString("removeRooms"));
	pnlButtonsAddRemove.add(btnRemoveRoom);

	//add lists of buildings and rooms
	JScrollPane scrolBuilding = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrolRoom = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrolBuilding, scrolRoom);
	splitter.setDividerLocation(0.5);
	splitter.setResizeWeight(0.5);
	splitter.setDividerSize(10);


	//building preview list
	PanelListModel lmBuilding = new PanelListModel();
	JList listBuildings = new JList(lmBuilding);
	listBuildings.setBackground(new Color(217, 217, 217));
	listBuildings.setCellRenderer(new PanelCellRenderer());

	ArrayList<Building> buildingPreviews = new ArrayList<Building>();
	try {
	    Model.getInstance().getBuildingPreviews(instance, buildingPreviews);
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(instance, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), Language.getString("errConnectDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
	}

	System.out.println("aantal buildings: " + buildingPreviews.size());

	for (Building building : buildingPreviews) {
	    lmBuilding.add(new BuildingListPanel(
		    building.getId(),
		    building.getName(),
		    new ImageIcon(getClass().getResource("/images/test.png")),
		    building.getStreet() + " " + building.getNumber(),
		    building.getZipcode(),
		    building.getCity()));
	}
	for (int i = 0; i < 20; i++) {
	    lmBuilding.add(new BuildingListPanel(675654,
		    "kot1",
		    new ImageIcon(getClass().getResource("/images/test.png")),
		    "voskeslaan 58",
		    "9000",
		    "Gent"));
	}

	scrolBuilding.setViewportView(listBuildings);

	//room preview list
	PanelListModel lmRoom = new PanelListModel();
	JList listRooms = new JList(lmRoom);
	listRooms.setBackground(new Color(217, 217, 217));
	listRooms.setCellRenderer(new PanelCellRenderer());

	for (int i = 0; i < 20; i++) {
	    lmRoom.add(new BuildingListPanel(675654,
		    "kamer1",
		    new ImageIcon(getClass().getResource("/images/kamer_test.png")),
		    "voskeslaan 58",
		    "9000",
		    "Gent"));
	}

	scrolRoom.setViewportView(listRooms);

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
	btnAddTask.setHideActionText(true);
	btnAddTask.setToolTipText(Language.getString("addTask"));
	pnlButtonsCalendar.add(btnAddTask);
	JButton btnEditTask = new JButton(actions.get("taskEdit"));
	btnEditTask.setHideActionText(true);
	btnEditTask.setToolTipText(Language.getString("editTask"));
	pnlButtonsCalendar.add(btnEditTask);
	JButton btnRemoveTask = new JButton(actions.get("taskRemove"));
	btnRemoveTask.setHideActionText(true);
	btnRemoveTask.setToolTipText(Language.getString("removeTasks"));
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
	btnAddMessage.setHideActionText(true);
	btnAddMessage.setToolTipText(Language.getString("newMessage"));
	pnlButtonsMessage.add(btnAddMessage);
	JButton btnEditMessage = new JButton(actions.get("messageReply"));
	btnEditMessage.setHideActionText(true);
	btnEditMessage.setToolTipText(Language.getString("replyMessage"));
	pnlButtonsMessage.add(btnEditMessage);
	JButton btnRemoveMessage = new JButton(actions.get("messageRemove"));
	btnRemoveMessage.setHideActionText(true);
	btnRemoveMessage.setToolTipText(Language.getString("removeMessages"));
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
	btnAddInvoice.setHideActionText(true);
	btnAddInvoice.setToolTipText(Language.getString("addInvoice"));
	pnlButtonsInvoice.add(btnAddInvoice);
	JButton btnEditInvoice = new JButton(actions.get("invoiceEdit"));
	btnEditInvoice.setHideActionText(true);
	btnEditInvoice.setToolTipText(Language.getString("editInvoice"));
	pnlButtonsInvoice.add(btnEditInvoice);
	JButton btnRemoveInvoice = new JButton(actions.get("invoiceRemove"));
	btnRemoveInvoice.setHideActionText(true);
	btnRemoveInvoice.setToolTipText(Language.getString("removeInvoices"));
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
