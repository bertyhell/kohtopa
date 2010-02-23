package gui;

import gui.actions.AddBuildingAction;
import gui.actions.AddTaskAction;
import gui.actions.EditBuildingAction;
import gui.actions.EditTaskAction;
import gui.actions.RemoveBuildingAction;
import gui.actions.RemoveTaskAction;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author bert
 */
public class Main extends JFrame {

	private static Main instance = new Main();
	private static ResourceBundle lang;
	private static Action addBuilding;
	private static Action editBuilding;
	private static Action removeBuilding;
	private static Action addTask;
	private static Action editTask;
	private static Action removeTask;

	public static Main getInstance() {
		return instance;
	}

	private Main() {
		lang = ResourceBundle.getBundle("Language/language_ENG");
//		//write language file
//		try {
//			serialize();
//		} catch (Exception ex) {
//			JOptionPane.showMessageDialog(instance, lang.getString("errWriteLangFile"), lang.getString("errWriteLangFileTitle"), JOptionPane.ERROR_MESSAGE);
//		}


//
//		try {
//			lang = readLanguage();
//		} catch (JAXBException ex) {
//			JOptionPane.showMessageDialog(instance, "Failed to read language file: Language_EN.xml\nmake sure it is in the right directory", "Language file not found", JOptionPane.ERROR_MESSAGE);
//		}



		this.setTitle(lang.getString("titleJFrameDesktopMain"));
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, lang.getString("errLookAndFeelNotFound"), lang.getString("errLookAndFeelNotFoundTitle"), JOptionPane.ERROR_MESSAGE);
		}

		this.setIconImage(new ImageIcon(getClass().getResource("/images/ico.png")).getImage());
		this.setExtendedState(this.getExtendedState() | Main.MAXIMIZED_BOTH);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		//actions
		addBuilding = new AddBuildingAction(new ImageIcon(getClass().getResource("/images/add.png")));
		editBuilding = new EditBuildingAction(new ImageIcon(getClass().getResource("/images/edit.png")));
		removeBuilding = new RemoveBuildingAction(new ImageIcon(getClass().getResource("/images/remove.png")));
		addTask = new AddTaskAction(new ImageIcon(getClass().getResource("/images/add.png")));
		editTask = new EditTaskAction(new ImageIcon(getClass().getResource("/images/edit.png")));
		removeTask = new RemoveTaskAction(new ImageIcon(getClass().getResource("/images/remove.png")));
		this.setLayout(new BorderLayout());
		JTabbedPane tabbed = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		this.add(tabbed, BorderLayout.CENTER);

		//Add / Remove tab
		JPanel pnlAddRemove = new JPanel();
		pnlAddRemove.setLayout(new BorderLayout());
		tabbed.add(pnlAddRemove, lang.getString("titleAddRemove"));

		JPanel pnlButtonsAddRemove = new JPanel();
		pnlAddRemove.add(pnlButtonsAddRemove, BorderLayout.PAGE_START);

		JButton btnAddBuilding = new JButton(addBuilding);
		btnAddBuilding.setHideActionText(true);
		pnlButtonsAddRemove.add(btnAddBuilding);
		JButton btnEditBuilding = new JButton(editBuilding);
		btnEditBuilding.setHideActionText(true);
		pnlButtonsAddRemove.add(btnEditBuilding);
		JButton btnRemoveBuilding = new JButton(removeBuilding);
		btnRemoveBuilding.setHideActionText(true);
		pnlButtonsAddRemove.add(btnRemoveBuilding);

		JPanel pnlData = new JPanel();
		pnlData.setPreferredSize(new Dimension(500, 600));
		pnlAddRemove.add(pnlData, BorderLayout.CENTER);



		//Calendar tab
		JPanel pnlCalendar = new JPanel();
		pnlCalendar.setLayout(new BorderLayout());
		tabbed.add(pnlCalendar, lang.getString("titleCalendar"));

		JPanel pnlButtonsCalendar = new JPanel();
		pnlCalendar.add(pnlButtonsCalendar, BorderLayout.PAGE_START);

		JButton btnAddTask = new JButton(addTask);
		btnAddTask.setHideActionText(true);
		pnlButtonsCalendar.add(btnAddTask);
		JButton btnEditTask = new JButton(editTask);
		btnEditTask.setHideActionText(true);
		pnlButtonsCalendar.add(btnEditTask);
		JButton btnRemoveTask = new JButton(removeTask);
		btnRemoveTask.setHideActionText(true);
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

//	public static Language readLanguage() throws JAXBException {
//		JAXBContext context = JAXBContext.newInstance("Language");
//		Unmarshaller unmarshaller = context.createUnmarshaller();
//		return (Language) unmarshaller.unmarshal(new File("Language_EN.xml"));
//	}
//
//	public static void serialize() throws Exception {
//		FileOutputStream fos = null;
//		fos = new FileOutputStream("Translation_EN.xml");
//		JAXBContext context = JAXBContext.newInstance(Language.class);
//		Marshaller m = context.createMarshaller();
//		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		FileOutputStream fo = new FileOutputStream(new File("Translation_EN.xml"));
//		m.marshal(new Language(), fo);
//
//	}
	
	public static void serialize() throws Exception {
		FileOutputStream fos = null;
		fos = new FileOutputStream("Translation_EN.xml");
		JAXBContext context = JAXBContext.newInstance(Hashmap.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		FileOutputStream fo = new FileOutputStream(new File("Translation_EN.xml"));
		m.marshal(new HashMap<String, String>(), fo);

	}


}
