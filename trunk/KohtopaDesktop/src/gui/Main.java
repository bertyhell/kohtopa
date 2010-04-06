package gui;

//TODO add possibility to access all functions trough ALT (maybe autohide file bar?)
//TODO add right click menu's in all panels
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
import gui.addremovetab.RentableDialog;

public class Main extends JFrame {

    private static Main instance = new Main();
    private static HashMap<String, Action> actions;
    public final static boolean disableBtnText = true;
    private JList lstBuildings;
    private JList lstRentables;
    private static DataModel data;
    private JPanel pnlMessages;
    private JTabbedPane tabbed;

    public static Main getInstance() {
	return instance;
    }

    private Main() {
	try {
	    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	} catch (Exception ex) {
	    System.out.println("");
	    JOptionPane.showMessageDialog(this, "Look and Feel not found, make sure you have latest java version\n" + ex.getMessage(), "Look and Feel not found", JOptionPane.ERROR_MESSAGE);
	}
	Language.read(); //reads strings in specific language from xml file
	setTitle(Language.getString("titleJFrameDesktopMain"));
	data = new DataModel();

	//actions
	actions = new HashMap<String, Action>();

	actions.put("buildingAdd", new BuildingAddAction("buildingAdd", new ImageIcon(getClass().getResource("/images/building_add_23.png"))));
	actions.put("buildingEdit", new BuildingEditAction("buildingEdit", new ImageIcon(getClass().getResource("/images/building_edit_23.png"))));
	actions.put("buildingRemove", new BuildingRemoveAction("buildingRemove", new ImageIcon(getClass().getResource("/images/building_remove_23.png"))));
	actions.put("pictureAdd", new BuildingAddAction("pictureAdd", new ImageIcon(getClass().getResource("/images/picture_add_23.png"))));
	actions.put("picturePreview", new BuildingEditAction("picturePreview", new ImageIcon(getClass().getResource("/images/picture_preview_23.png"))));
	actions.put("pictureRemove", new BuildingRemoveAction("pictureRemove", new ImageIcon(getClass().getResource("/images/picture_remove_23.png"))));
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

	MessageListPanel.addIcon("0", new ImageIcon(getClass().getResource("/images/message_new_23.png")));
	MessageListPanel.addIcon("1", new ImageIcon(getClass().getResource("/images/message_read_23.png")));
	MessageListPanel.addIcon("2", new ImageIcon(getClass().getResource("/images/message_reply_23.png")));

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

    //TODO move dataconnection of messages to model class and make sure it only connects to database outside constructor
    public void fetchMessages() {
	MessagePane pnlMessagesInfo = new MessagePane();

	pnlMessagesInfo.setPreferredSize(new Dimension(500, 600));
	pnlMessages.add(pnlMessagesInfo, BorderLayout.CENTER);
    }

    public static DataModel getDataObject() {
	return data;
    }

    public void init() {
	lstBuildings.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
		    //open building dialog
		    (new BuildingDialog(instance, data.getSelectedBuildingId(), false)).setVisible(true);
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

		    new RentableDialog(instance, data.getSelectedRentableId(), false).setVisible(true);
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
		    System.out.println("fetching buildings");
		    data.fetchAddRemove();
		} else if (tab == 2) {
		    fetchMessages();
		}
		//TODO add rest off fetch methods
	    }
	});
	this.setVisible(true);
    }

    public void updateList() {
	lstBuildings.setModel(data.getLmBuilding());
    }

    public static void main(String args[]) {
	java.awt.EventQueue.invokeLater(new Runnable() {

	    public void run() {
//		new LoginDialog(instance, data).setVisible(true);
//		//check login
//		if(data.isLoggedIn()){

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








//		}else{
//		    Main.getInstance().dispose();
//		}
	    }
	});
    }
}
