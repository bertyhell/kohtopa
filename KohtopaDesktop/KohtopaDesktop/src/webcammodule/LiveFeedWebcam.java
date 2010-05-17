/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package webcammodule;

import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Ruben
 */
public class LiveFeedWebcam extends JPanel implements ActionListener {

    private int buildingID;

    private JLabel lblPicture;

    private JLabel lblError;

    private JRadioButton rdWebcam1;
    private JRadioButton rdWebcam2;
    private JRadioButton rdWebcam3;

    private JButton btnConnect;

    // A new image is being requested every 3 seconds, therefore we need a timer.
    private Timer timer;

    public LiveFeedWebcam(int buildingID) {
        this.buildingID = buildingID;

        int delay = 3000;
        ActionListener tick = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loadImage();
            }
        };
        timer = new Timer(delay, tick);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Picture
        lblPicture = new JLabel();
        lblPicture.setPreferredSize(new Dimension(320, 240));
        lblPicture.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblPicture, BorderLayout.CENTER);

        // Error
        lblError = new JLabel();
        lblError.setForeground(Color.RED);

        JPanel panelMessages = new JPanel();
        panelMessages.setLayout(new BoxLayout(panelMessages, BoxLayout.Y_AXIS));
        panelMessages.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelMessages.add(lblError);

        // Webcam
        ButtonGroup btnGroup = new ButtonGroup();
        rdWebcam1 = new JRadioButton("Webcam 1");
        rdWebcam1.setSelected(true);
        btnGroup.add(rdWebcam1);
        rdWebcam2 = new JRadioButton("Webcam 2");
        btnGroup.add(rdWebcam2);
        rdWebcam3 = new JRadioButton("Webcam 3");
        btnGroup.add(rdWebcam3);

        JPanel panelWebcams = new JPanel();
        panelWebcams.setLayout(new BoxLayout(panelWebcams, BoxLayout.Y_AXIS));
        panelWebcams.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelWebcams.add(rdWebcam1);
        panelWebcams.add(rdWebcam2);
        panelWebcams.add(rdWebcam3);

        // Buttons
        btnConnect = new JButton("Connect");
        btnConnect.addActionListener(this);

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
        panelButtons.add(Box.createHorizontalGlue());
        panelButtons.add(btnConnect);

        JPanel panelControls = new JPanel();
        panelControls.setLayout(new BorderLayout());
        panelControls.add(panelWebcams, BorderLayout.NORTH);
        panelControls.add(panelButtons, BorderLayout.CENTER);
        panelControls.add(panelMessages, BorderLayout.SOUTH);

        add(panelControls, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Connect")) {
            timer.start();
            btnConnect.setText("Disconnect");
            btnConnect.setActionCommand("Disconnect");
        } else if (e.getActionCommand().equals("Disconnect")) {
            timer.stop();
            btnConnect.setText("Connect");
            btnConnect.setActionCommand("Connect");
        }
    }

    private void loadImage() {
        try {
            // Retrieving IPAddress
            String IPAddress = Main.getDataObject().getIPAddress(buildingID);
            if (IPAddress == null) {
                throw new Exception("No suitable IPAddress found for this building");
            }

            // Socket
            Socket clientSocket = new Socket("81.245.95.100", 4040);

            // Send message
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            if (rdWebcam1.isSelected()) {
                writer.println("1");
            } else if (rdWebcam2.isSelected()) {
                writer.println("2");
            } else {
                writer.println("3");
            }

            // Receive image
            DataInputStream stream = new DataInputStream(clientSocket.getInputStream());
            byte[] buffer = new byte[1024 * 1024];
            stream.read(buffer);
            ImageIcon icon = new ImageIcon(buffer);
            lblPicture.setIcon(icon);
            lblError.setText("");

            clientSocket.close();
        } catch (Exception exc) {
            lblError.setText("Error: " + exc.getMessage());
            exc.printStackTrace();
            timer.stop();
            btnConnect.setText("Connect");
            btnConnect.setActionCommand("Connect");
        }
    }

}
