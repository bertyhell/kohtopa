package gui;

import Language.Language;
import gui.actions.*;
import gui.addremove.PanelCellRenderer;
import gui.addremove.PanelListModel;
import gui.addremove.BuildingListPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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

public class Main extends JFrame {

	private static Main instance = new Main();
	private static Action buildingAdd;
	private static Action buildingEdit;
	private static Action buildingRemove;
	private static Action roomAdd;
	private static Action roomEdit;
	private static Action roomRemove;
	private static Action taskAdd;
	private static Action taskEdit;
	private static Action taskRemove;
	private static Action invoiceAdd;
	private static Action invoiceEdit;
	private static Action invoiceRemove;

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
		buildingAdd = new BuildingAddAction(new ImageIcon(getClass().getResource("/images/building_add_23.png")));
		buildingEdit = new BuildingEditAction(new ImageIcon(getClass().getResource("/images/building_edit_23.png")));
		buildingRemove = new BuildingRemoveAction(new ImageIcon(getClass().getResource("/images/building_remove_23.png")));
		roomAdd = new RoomAddAction(new ImageIcon(getClass().getResource("/images/room_add_23.png")));
		roomEdit = new RoomEditAction(new ImageIcon(getClass().getResource("/images/room_edit_23.png")));
		roomRemove = new RoomRemoveAction(new ImageIcon(getClass().getResource("/images/room_remove_23.png")));
		taskAdd = new TaskAddAction(new ImageIcon(getClass().getResource("/images/task_add_23.png")));
		taskEdit = new TaskEditAction(new ImageIcon(getClass().getResource("/images/task_edit_23.png")));
		taskRemove = new TaskRemoveAction(new ImageIcon(getClass().getResource("/images/task_remove_23.png")));
		invoiceAdd = new InvoiceAddAction(new ImageIcon(getClass().getResource("/images/invoice_add_23.png")));
		invoiceEdit = new InvoiceEditAction(new ImageIcon(getClass().getResource("/images/invoice_edit_23.png")));
		invoiceRemove = new InvoiceRemoveAction(new ImageIcon(getClass().getResource("/images/invoice_remove_23.png")));

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
		JButton btnAddBuilding = new JButton(buildingAdd);
		btnAddBuilding.setHideActionText(true);
		btnAddBuilding.setToolTipText(Language.getString("addBuilding"));
		pnlButtonsAddRemove.add(btnAddBuilding);
		JButton btnEditBuilding = new JButton(buildingEdit);
		btnEditBuilding.setHideActionText(true);
		btnEditBuilding.setToolTipText(Language.getString("editBuilding"));
		pnlButtonsAddRemove.add(btnEditBuilding);
		JButton btnRemoveBuilding = new JButton(buildingRemove);
		btnRemoveBuilding.setHideActionText(true);
		btnRemoveBuilding.setToolTipText(Language.getString("removeBuildings"));
		pnlButtonsAddRemove.add(btnRemoveBuilding);

		//room operations
		JButton btnAddRoom = new JButton(roomAdd);
		btnAddRoom.setHideActionText(true);
		btnAddRoom.setToolTipText(Language.getString("addRoom"));
		pnlButtonsAddRemove.add(btnAddRoom);
		JButton btnEditRoom = new JButton(roomEdit);
		btnEditRoom.setHideActionText(true);
		btnEditRoom.setToolTipText(Language.getString("editRoom"));
		pnlButtonsAddRemove.add(btnEditRoom);
		JButton btnRemoveRoom = new JButton(roomRemove);
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


		PanelListModel lmBuilding = new PanelListModel();
		JList listBuildings = new JList(lmBuilding);
		listBuildings.setBackground(new Color(217,217,217));
		listBuildings.setCellRenderer(new PanelCellRenderer());

		lmBuilding.add(new BuildingListPanel(675654,
				"kot1",
				new ImageIcon(getClass().getResource("/images/test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmBuilding.add(new BuildingListPanel(675654,
				"kot1",
				new ImageIcon(getClass().getResource("/images/test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmBuilding.add(new BuildingListPanel(675654,
				"kot1",
				new ImageIcon(getClass().getResource("/images/test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmBuilding.add(new BuildingListPanel(675654,
				"kot1",
				new ImageIcon(getClass().getResource("/images/test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmBuilding.add(new BuildingListPanel(675654,
				"kot1",
				new ImageIcon(getClass().getResource("/images/test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmBuilding.add(new BuildingListPanel(675654,
				"kot1",
				new ImageIcon(getClass().getResource("/images/test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmBuilding.add(new BuildingListPanel(675654,
				"kot1",
				new ImageIcon(getClass().getResource("/images/test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmBuilding.add(new BuildingListPanel(675654,
				"kot1",
				new ImageIcon(getClass().getResource("/images/test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmBuilding.add(new BuildingListPanel(675654,
				"kot1",
				new ImageIcon(getClass().getResource("/images/test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmBuilding.add(new BuildingListPanel(675654,
				"kot1",
				new ImageIcon(getClass().getResource("/images/test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		scrolBuilding.setViewportView(listBuildings);

		PanelListModel lmRoom = new PanelListModel();
		JList listRooms = new JList(lmRoom);
		listRooms.setBackground(new Color(217,217,217));
		listRooms.setCellRenderer(new PanelCellRenderer());

		lmRoom.add(new BuildingListPanel(675654,
				"kamer1",
				new ImageIcon(getClass().getResource("/images/kamer_test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmRoom.add(new BuildingListPanel(675654,
				"kamer1",
				new ImageIcon(getClass().getResource("/images/kamer_test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmRoom.add(new BuildingListPanel(675654,
				"kamer1",
				new ImageIcon(getClass().getResource("/images/kamer_test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmRoom.add(new BuildingListPanel(675654,
				"kamer1",
				new ImageIcon(getClass().getResource("/images/kamer_test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmRoom.add(new BuildingListPanel(675654,
				"kamer1",
				new ImageIcon(getClass().getResource("/images/kamer_test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmRoom.add(new BuildingListPanel(675654,
				"kamer1",
				new ImageIcon(getClass().getResource("/images/kamer_test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmRoom.add(new BuildingListPanel(675654,
				"kamer1",
				new ImageIcon(getClass().getResource("/images/kamer_test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmRoom.add(new BuildingListPanel(675654,
				"kamer1",
				new ImageIcon(getClass().getResource("/images/kamer_test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmRoom.add(new BuildingListPanel(675654,
				"kamer1",
				new ImageIcon(getClass().getResource("/images/kamer_test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		lmRoom.add(new BuildingListPanel(675654,
				"kamer1",
				new ImageIcon(getClass().getResource("/images/kamer_test.png")),
				"voskeslaan 58",
				"9000",
				"Gent"));
		scrolRoom.setViewportView(listRooms);

		splitter.setPreferredSize(new Dimension(1000, 600));
		pnlAddRemove.add(splitter, BorderLayout.CENTER);

		return pnlAddRemove;
	}

	private JPanel createCalendarPanel(){

		//Calendar tab
		JPanel pnlCalendar = new JPanel();
		pnlCalendar.setLayout(new BorderLayout());
		
		JPanel pnlButtonsCalendar = new JPanel();
		pnlButtonsCalendar.setBackground(new Color(180, 180, 180, 100));
		pnlCalendar.add(pnlButtonsCalendar, BorderLayout.PAGE_START);

		JButton btnAddTask = new JButton(taskAdd);
		btnAddTask.setHideActionText(true);
		btnAddTask.setToolTipText(Language.getString("addTask"));
		pnlButtonsCalendar.add(btnAddTask);
		JButton btnEditTask = new JButton(taskEdit);
		btnEditTask.setHideActionText(true);
		btnEditTask.setToolTipText(Language.getString("editTask"));
		pnlButtonsCalendar.add(btnEditTask);
		JButton btnRemoveTask = new JButton(taskRemove);
		btnRemoveTask.setHideActionText(true);
		btnRemoveTask.setToolTipText(Language.getString("removeTasks"));
		pnlButtonsCalendar.add(btnRemoveTask);

		JPanel pnlDays = new JPanel();
		pnlDays.setPreferredSize(new Dimension(500, 600));
		pnlCalendar.add(pnlDays, BorderLayout.CENTER);

		return pnlCalendar;
	}

	private JPanel createInvoicesPanel(){
				//Invoices tab (facturen)
		JPanel pnlInvoices = new JPanel();
		pnlInvoices.setLayout(new BorderLayout());

		JPanel pnlButtonsInvoice = new JPanel();
		pnlButtonsInvoice.setBackground(new Color(180, 180, 180, 100));
		pnlInvoices.add(pnlButtonsInvoice, BorderLayout.PAGE_START);

		JButton btnAddInvoice = new JButton(invoiceAdd);
		btnAddInvoice.setHideActionText(true);
		btnAddInvoice.setToolTipText(Language.getString("addInvoice"));
		pnlButtonsInvoice.add(btnAddInvoice);
		JButton btnEditInvoice = new JButton(invoiceEdit);
		btnEditInvoice.setHideActionText(true);
		btnEditInvoice.setToolTipText(Language.getString("editInvoice"));
		pnlButtonsInvoice.add(btnEditInvoice);
		JButton btnRemoveInvoice = new JButton(invoiceRemove);
		btnRemoveInvoice.setHideActionText(true);
		btnRemoveInvoice.setToolTipText(Language.getString("removeInvoices"));
		pnlButtonsInvoice.add(btnRemoveInvoice);


		JPanel pnlInvoicesInfo = new JPanel();
		pnlInvoicesInfo.setPreferredSize(new Dimension(500, 600));
		pnlInvoices.add(pnlInvoicesInfo, BorderLayout.CENTER);

		return pnlInvoices;
	}

	private JPanel createSettingsPanel(){
		//Settings tab
		JPanel pnlSettings = new JPanel();
		pnlSettings.setPreferredSize(new Dimension(500, 600));
		pnlSettings.setLayout(new BorderLayout());

		return pnlSettings;
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {

				instance.setVisible(true);
			}
		});
	}
}
