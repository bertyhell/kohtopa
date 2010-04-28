/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package floorallocationmodule.view;

//import floorallocationmodule.listeners.OpenImage;
import floorallocationmodule.listeners.CustomKeyDispatcher;
import floorallocationmodule.listeners.LoadImageAction;
import floorallocationmodule.objects.Camera;
import floorallocationmodule.objects.FireExtinguisher;
import floorallocationmodule.model.FloorContent;
import floorallocationmodule.objects.EmergencyExit;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

/**
 *
 * @author Ruben
 */
public class FloorControls extends JPanel implements ActionListener {

    // Parent Frame
    private JFrame parentFrame;
    // Floor Name
    private String floorName;

    private FloorContent floorContent;

    // ImageIcons
    private final ImageIcon saveIcon = new ImageIcon(getClass().getResource("/images/save_23.png"));
    private final ImageIcon selectIcon = new ImageIcon(getClass().getResource("/images/select_23.png"));
    private final ImageIcon drawIcon = new ImageIcon(getClass().getResource("/images/draw_23.png"));
    private final ImageIcon clearIcon = new ImageIcon(getClass().getResource("/images/cancel.png"));
    private final ImageIcon undoIcon = new ImageIcon(getClass().getResource("/images/undo_23.png"));
    private final ImageIcon redoIcon = new ImageIcon(getClass().getResource("/images/redo_23.png"));
    private final ImageIcon loadImageIcon = new ImageIcon(getClass().getResource("/images/floor_upload_23.png"));
    private final ImageIcon gridIcon = new ImageIcon(getClass().getResource("/images/grid.png"));

    // Top
    private JRadioButton rbSelect;
    private JLabel lblSelect;

    private JRadioButton rbDraw;
    private JLabel lblDraw;

    private JRadioButton rbFireExtinguisher;
    private JLabel lblFireExtinguisher;

    private JRadioButton rbCamera;
    private JLabel lblCamera;
    
    private JRadioButton rbEmergencyExit;
    private JLabel lblEmergencyExit;

    // Bottom
    private JButton btnUndo;
    private JButton btnRedo;
    private JButton btnLoadImage;

    private JCheckBox chckGrid;
    private JLabel lblGrid;

    private JButton btnClear;
    private JButton btnSave;

    public FloorControls (JFrame parentFrame, String floorName) {
        this.parentFrame = parentFrame;
        this.floorName = floorName;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        FloorImage floorImage = new FloorImage();
        floorContent = new FloorContent();
        floorImage.setFloorContent(floorContent);
        floorContent.setFloorImage(floorImage);
        floorContent.setFloorName(floorName);

            // Settings regarding parentFrame.
            parentFrame.setResizable(false);
            // Add KeyDispatcher for key events like VK_DELETE.
            KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            manager.addKeyEventDispatcher(new CustomKeyDispatcher(floorImage));
        
        // Top
        JPanel panelControlsTop = new JPanel();
        panelControlsTop.setLayout(new BoxLayout(panelControlsTop, BoxLayout.X_AXIS));
        panelControlsTop.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        ButtonGroup btnGroup = new ButtonGroup();

        rbSelect = new JRadioButton();
        rbSelect.setSelected(true);
        rbSelect.addActionListener(this);
        rbSelect.setActionCommand("Select");
        rbSelect.setToolTipText("Select");
        btnGroup.add(rbSelect);

        lblSelect = new JLabel("Select", selectIcon, SwingConstants.LEFT);

        rbDraw = new JRadioButton();
        rbDraw.addActionListener(this);
        rbDraw.setActionCommand("Draw");
        rbDraw.setToolTipText("Draw");
        btnGroup.add(rbDraw);

        lblDraw = new JLabel("Draw", drawIcon, SwingConstants.LEFT);

        rbFireExtinguisher = new JRadioButton();
        rbFireExtinguisher.addActionListener(this);
        rbFireExtinguisher.setActionCommand("FireExtinguisher");
        rbFireExtinguisher.setToolTipText("Fire Extinguisher");
        btnGroup.add(rbFireExtinguisher);

        lblFireExtinguisher = new JLabel("Fire Extinguisher", new FireExtinguisher().getFireIcon(), SwingConstants.LEFT);

        rbCamera = new JRadioButton();
        rbCamera.addActionListener(this);
        rbCamera.setActionCommand("Camera");
        rbCamera.setToolTipText("Camera");
        btnGroup.add(rbCamera);

        lblCamera = new JLabel("Camera", new Camera().getCameraIcon(), SwingConstants.LEFT);
        
        rbEmergencyExit = new JRadioButton();
        rbEmergencyExit.addActionListener(this);
        rbEmergencyExit.setActionCommand("EmergencyExit");
        rbEmergencyExit.setToolTipText("Emergency Exit");
        btnGroup.add(rbEmergencyExit);

        lblEmergencyExit = new JLabel("Emergency Exit", new EmergencyExit().getEmergencyExitIcon(), SwingConstants.LEFT);

        panelControlsTop.add(rbSelect);
        panelControlsTop.add(Box.createRigidArea(new Dimension(5, 0)));
        panelControlsTop.add(lblSelect);
        panelControlsTop.add(Box.createHorizontalGlue());
        panelControlsTop.add(rbDraw);
        panelControlsTop.add(Box.createRigidArea(new Dimension(5, 0)));
        panelControlsTop.add(lblDraw);
        panelControlsTop.add(Box.createHorizontalGlue());
        panelControlsTop.add(rbFireExtinguisher);
        panelControlsTop.add(Box.createRigidArea(new Dimension(5, 0)));
        panelControlsTop.add(lblFireExtinguisher);
        panelControlsTop.add(Box.createHorizontalGlue());
        panelControlsTop.add(rbCamera);
        panelControlsTop.add(Box.createRigidArea(new Dimension(5, 0)));
        panelControlsTop.add(lblCamera);
        panelControlsTop.add(Box.createHorizontalGlue());
        panelControlsTop.add(rbEmergencyExit);
        panelControlsTop.add(Box.createRigidArea(new Dimension(5, 0)));
        panelControlsTop.add(lblEmergencyExit);

        // Bottom
        JPanel panelControlsBottom = new JPanel();
        panelControlsBottom.setLayout(new BoxLayout(panelControlsBottom, BoxLayout.X_AXIS));
        panelControlsBottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnUndo = new JButton("Undo Drawing", undoIcon);
        btnUndo.addActionListener(this);
        btnUndo.setActionCommand("Undo");

        btnRedo = new JButton("Redo Drawing", redoIcon);
        btnRedo.addActionListener(this);
        btnRedo.setActionCommand("Redo");

        btnLoadImage = new JButton("Load Image", loadImageIcon);
        btnLoadImage.addActionListener(new LoadImageAction(floorContent));

        chckGrid = new JCheckBox();
        chckGrid.addActionListener(this);
        chckGrid.setActionCommand("Grid");
        chckGrid.setToolTipText("Draw");

        lblGrid = new JLabel(gridIcon);

        btnClear = new JButton("Clear All", clearIcon);
        btnClear.addActionListener(this);
        btnClear.setActionCommand("Clear");

        btnSave = new JButton("Save", saveIcon);
        btnSave.addActionListener(this);
        btnSave.setActionCommand("Save");
        btnSave.setToolTipText("Save");

        panelControlsBottom.add(btnUndo);
        panelControlsBottom.add(btnRedo);
        panelControlsBottom.add(Box.createRigidArea(new Dimension(20, 0)));
        panelControlsBottom.add(btnLoadImage);
        panelControlsBottom.add(Box.createHorizontalGlue());
        panelControlsBottom.add(chckGrid);
        panelControlsBottom.add(Box.createRigidArea(new Dimension(5, 0)));
        panelControlsBottom.add(lblGrid);
        panelControlsBottom.add(Box.createHorizontalGlue());
        panelControlsBottom.add(btnClear);
        panelControlsBottom.add(btnSave);

        add(panelControlsTop, BorderLayout.NORTH);
        add(floorImage, BorderLayout.CENTER);
        add(panelControlsBottom, BorderLayout.SOUTH);
        floorContent.setX(floorImage.getX());
        floorContent.setY(floorImage.getY());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save")) {
            floorContent.save();
        } else if (e.getActionCommand().equals("Clear")) {
            floorContent.clear();
        } else if (e.getActionCommand().equals("Undo")) {
            floorContent.undo();
        } else if (e.getActionCommand().equals("Redo")) {
            floorContent.redo();
        } else if (e.getActionCommand().equals("Grid")) {
            if (chckGrid.isSelected()) {
                floorContent.setGridEnabled(true);
            } else {
                floorContent.setGridEnabled(false);
            }
        } else if (e.getActionCommand().equals("Select")) {
            if (rbSelect.isSelected()) {
                floorContent.setDrawEnabled(false);
                floorContent.setAddCamera(false);
                floorContent.setAddFireExtinguisher(false);
                floorContent.setAddEmergencyExit(false);
            }
        } else if (e.getActionCommand().equals("Draw")) {
            if (rbDraw.isSelected()) {
                floorContent.setDrawEnabled(true);
                floorContent.setAddCamera(false);
                floorContent.setAddFireExtinguisher(false);
                floorContent.setAddEmergencyExit(false);
            }
        } else if (e.getActionCommand().equals("FireExtinguisher")) {
            if (rbFireExtinguisher.isSelected()) {
                floorContent.setAddFireExtinguisher(true);
                floorContent.setDrawEnabled(false);
                floorContent.setAddCamera(false);
                floorContent.setAddEmergencyExit(false);
            }
        } else if (e.getActionCommand().equals("Camera")) {
            if (rbCamera.isSelected()) {
                floorContent.setAddCamera(true);
                floorContent.setDrawEnabled(false);
                floorContent.setAddFireExtinguisher(false);
                floorContent.setAddEmergencyExit(false);
            }
        } else if (e.getActionCommand().equals("EmergencyExit")) {
            if (rbEmergencyExit.isSelected()) {
                floorContent.setAddCamera(false);
                floorContent.setDrawEnabled(false);
                floorContent.setAddFireExtinguisher(false);
                floorContent.setAddEmergencyExit(true);
            }
        }
    }

}
