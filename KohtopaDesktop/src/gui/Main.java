package gui;

import Language.Language;
import gui.actions.AddBuildingAction;
import gui.actions.AddRoomAction;
import gui.actions.AddTaskAction;
import gui.actions.EditBuildingAction;
import gui.actions.EditRoomAction;
import gui.actions.EditTaskAction;
import gui.actions.RemoveBuildingAction;
import gui.actions.RemoveRoomAction;
import gui.actions.RemoveTaskAction;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class Main extends JFrame {

    private static Main instance = new Main();
    private static Action addBuilding;
    private static Action editBuilding;
    private static Action removeBuilding;
    private static Action addRoom;
    private static Action editRoom;
    private static Action removeRoom;
    private static Action addTask;
    private static Action editTask;
    private static Action removeTask;

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

        this.setIconImage(new ImageIcon(getClass().getResource("/images/ico.png")).getImage());
        this.setExtendedState(this.getExtendedState() | Main.MAXIMIZED_BOTH);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        //actions
        addBuilding = new AddBuildingAction(new ImageIcon(getClass().getResource("/images/building_add_23.png")));
        editBuilding = new EditBuildingAction(new ImageIcon(getClass().getResource("/images/building_edit_23.png")));
        removeBuilding = new RemoveBuildingAction(new ImageIcon(getClass().getResource("/images/building_remove_23.png")));
        addRoom = new AddRoomAction(new ImageIcon(getClass().getResource("/images/room_add_23.png")));
        editRoom = new EditRoomAction(new ImageIcon(getClass().getResource("/images/room_edit_23.png")));
        removeRoom = new RemoveRoomAction(new ImageIcon(getClass().getResource("/images/room_remove_23.png")));
        addTask = new AddTaskAction(new ImageIcon(getClass().getResource("/images/task_add_23.png")));
        editTask = new EditTaskAction(new ImageIcon(getClass().getResource("/images/task_edit_23.png")));
        removeTask = new RemoveTaskAction(new ImageIcon(getClass().getResource("/images/task_remove_23.png")));
        this.setLayout(new BorderLayout());
        JTabbedPane tabbed = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        this.add(tabbed, BorderLayout.CENTER);

        //Add / Remove tab
        JPanel pnlAddRemove = new JPanel();
        pnlAddRemove.setLayout(new BorderLayout());
        tabbed.add(pnlAddRemove, Language.getString("titleAddRemove"));

        JPanel pnlButtonsAddRemove = new JPanel();
        pnlAddRemove.add(pnlButtonsAddRemove, BorderLayout.PAGE_START);

	//building operations
        JButton btnAddBuilding = new JButton(addBuilding);
        btnAddBuilding.setHideActionText(true);
	btnAddBuilding.setToolTipText(Language.getString("addBuilding"));
        pnlButtonsAddRemove.add(btnAddBuilding);
        JButton btnEditBuilding = new JButton(editBuilding);
        btnEditBuilding.setHideActionText(true);
	btnEditBuilding.setToolTipText(Language.getString("editBuilding"));
        pnlButtonsAddRemove.add(btnEditBuilding);
        JButton btnRemoveBuilding = new JButton(removeBuilding);
        btnRemoveBuilding.setHideActionText(true);
	btnRemoveBuilding.setToolTipText(Language.getString("removeBuildings"));
        pnlButtonsAddRemove.add(btnRemoveBuilding);

	//room operations
	JButton btnAddRoom = new JButton(addRoom);
        btnAddRoom.setHideActionText(true);
	btnAddRoom.setToolTipText(Language.getString("addRoom"));
        pnlButtonsAddRemove.add(btnAddRoom);
        JButton btnEditRoom = new JButton(editRoom);
        btnEditRoom.setHideActionText(true);
	btnEditRoom.setToolTipText(Language.getString("editRoom"));
        pnlButtonsAddRemove.add(btnEditRoom);
        JButton btnRemoveRoom = new JButton(removeRoom);
        btnRemoveRoom.setHideActionText(true);
	btnRemoveRoom.setToolTipText(Language.getString("removeRooms"));
        pnlButtonsAddRemove.add(btnRemoveRoom);

        JPanel pnlData = new JPanel();
        pnlData.setPreferredSize(new Dimension(500, 600));
        pnlAddRemove.add(pnlData, BorderLayout.CENTER);



        //Calendar tab
        JPanel pnlCalendar = new JPanel();
        pnlCalendar.setLayout(new BorderLayout());
        tabbed.add(pnlCalendar, Language.getString("titleCalendar"));

        JPanel pnlButtonsCalendar = new JPanel();
        pnlCalendar.add(pnlButtonsCalendar, BorderLayout.PAGE_START);

        JButton btnAddTask = new JButton(addTask);
        btnAddTask.setHideActionText(true);
	btnAddTask.setToolTipText(Language.getString("addTask"));
        pnlButtonsCalendar.add(btnAddTask);
        JButton btnEditTask = new JButton(editTask);
        btnEditTask.setHideActionText(true);
	btnEditTask.setToolTipText(Language.getString("editTask"));
        pnlButtonsCalendar.add(btnEditTask);
        JButton btnRemoveTask = new JButton(removeTask);
        btnRemoveTask.setHideActionText(true);
	btnRemoveTask.setToolTipText(Language.getString("removeTasks"));
        pnlButtonsCalendar.add(btnRemoveTask);

        JPanel pnlDays = new JPanel();
        pnlDays.setPreferredSize(new Dimension(500, 600));
        pnlCalendar.add(pnlDays, BorderLayout.CENTER);







        pack();
        this.setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                instance.setVisible(true);
            }
        });
    }
}
