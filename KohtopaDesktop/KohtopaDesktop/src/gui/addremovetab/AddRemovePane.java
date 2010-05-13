package gui.addremovetab;

import gui.interfaces.IBuildingListContainer;
import gui.interfaces.IRentableListContainer;
import Exceptions.WrongNumberOfSelectedItemsException;
import Language.Language;
import data.DataModel;
import data.entities.Building;
import data.entities.Rentable;
import gui.Logger;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class AddRemovePane extends JPanel implements IBuildingListContainer, IRentableListContainer {

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
			scrolBuilding.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(0, 0, 0, 0), Language.getString("buildings")));
			JScrollPane scrolRentable = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrolRentable.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(0, 0, 0, 0), Language.getString("rentables")));
			JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrolBuilding, scrolRentable);
			splitter.setDividerLocation(325);
			splitter.setDividerSize(10);
			this.add(splitter, BorderLayout.CENTER);

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
					} else {
						try {
							instance.getLstRentables().setListData(Main.getDataObject().getRentablesFromBuilding(((Building) lstBuildings.getSelectedValue()).getId()));
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
				}
			});

			scrolRentable.setViewportView(lstRentables);
		} catch (Exception ex) {
			Logger.logger.error("Failed to get buildings info in AddRemovePane: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}
	}

	public JList getLstRentables() {
		return lstRentables;
	}

	public int getId() {
		if (lstRentables.getSelectedIndices().length == 0) {
			return ((Building) lstBuildings.getSelectedValue()).getId();
		} else {
			return ((Rentable) lstRentables.getSelectedValue()).getId();
		}
	}

	public Object[] getSelectedBuildings() {
		return lstBuildings.getSelectedValues();
	}

	public Object[] getSelectedRentables() {
		return lstRentables.getSelectedValues();
	}

	public int getBuildingId() throws WrongNumberOfSelectedItemsException {
		if (lstBuildings.getSelectedValues().length == 1) {
			return ((Building) lstBuildings.getSelectedValue()).getId();
		} else {
			throw new WrongNumberOfSelectedItemsException("please select 1 building");
		}
	}

	public void updateBuildingList() {
		try {
			lstBuildings.setListData(data.getBuildingPreviews());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to collect buildings from database:  \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void updateRentableList() {
		try {
			lstRentables.setListData(data.getRentablePreviews(((Building) instance.getSelectedBuildings()[0]).getId()));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to collect rentables from database:  \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}
}
