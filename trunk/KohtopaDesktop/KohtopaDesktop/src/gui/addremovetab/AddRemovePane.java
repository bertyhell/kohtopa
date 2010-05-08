package gui.addremovetab;

import Language.Language;
import data.DataModel;
import data.entities.Building;
import data.entities.Rentable;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class AddRemovePane extends JPanel {

	private AddRemovePane instance;
	private JList lstBuildings;
	private JList lstRentables;
	private DataModel data;

	public AddRemovePane(DataModel data) {
		try {
			instance = this;
			this.data = data;
			this.setLayout(new BorderLayout());

			//add lists of buildings and Rentables
			JScrollPane scrolBuilding = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JScrollPane scrolRentable = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrolBuilding, scrolRentable);
			splitter.setDividerLocation(325);
			splitter.setDividerSize(10);
			this.add(splitter, BorderLayout.CENTER);








//
//				try {
//			lmBuilding.updateItems();
//			return true;
//		} catch (SQLException ex) {
//			SplashConnect.hideSplash();
//			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//			//TODO add connection string settings
//
//			return false;
//		} catch (IOException ex) {
//			SplashConnect.hideSplash();
//			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errImagesFetchFail") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//			//TODO add connection string settings
//
//			return false;
//		}











			//TODO fix list (delete listmodels) do it Jelle's way
			//building preview list
			lstBuildings = new JList(data.getBuildingPreviews());
			lstBuildings.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			lstBuildings.setBackground(new Color(217, 217, 217));
			lstBuildings.setCellRenderer(new BuildingCellRenderer());
			lstBuildings.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						//open building dialog
						new BuildingDialog(Main.getInstance(), ((Building) lstBuildings.getSelectedValue()).getId(), false).setVisible(true);
					}else{
						try {
							instance.getLstRentables().setListData(instance.getDataModel().getRentablesFromBuilding(((Building) lstBuildings.getSelectedValue()).getId()));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(Main.getInstance(), "could not get rentables from selected building from database: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});

			scrolBuilding.setViewportView(lstBuildings);

			//Rentable preview list
			lstRentables = new JList();
			lstBuildings.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			lstRentables.setBackground(new Color(217, 217, 217));
			lstRentables.setCellRenderer(new RentableCellRenderer());
			lstRentables.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						//open rentable dialog
						new RentableDialog(Main.getInstance(), ((Rentable) lstRentables.getSelectedValue()).getId(), false).setVisible(true);
					}
//					else {
//						try {
//							//select rentable
//							//DataModel.setRentableIndex(lstRentables.locationToIndex(e.getPoint()));
//						} catch (Exception ex) {
//							//FIXME exception opsplitsen, translation messages
//							JOptionPane.showMessageDialog(Main.getInstance(), "Couldn't connect to database\n" + ex.getMessage(), "connection failed", JOptionPane.ERROR_MESSAGE);
//						}
//					}
				}
			});

			scrolRentable.setViewportView(lstRentables);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to get buildings info \n" + ex.getMessage(), "title", JOptionPane.ERROR_MESSAGE);
		}
	}

	public int[] getSelectedBuildings() {
		return lstBuildings.getSelectedIndices();
	}

	public int[] getSelectedRentables() {
		return lstRentables.getSelectedIndices();
	}

	public JList getLstRentables() {
		return lstRentables;
	}

	public DataModel getDataModel() {
		return data;
	}
}
