/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package floorallocationmodule.view;

import floorallocationmodule.listeners.FloorImageInputListener;
import floorallocationmodule.objects.Camera;
import floorallocationmodule.objects.FireExtinguisher;
import floorallocationmodule.model.FloorConstants;
import floorallocationmodule.model.FloorContent;
import floorallocationmodule.objects.EmergencyExit;
import floorallocationmodule.objects.NamedPolygon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Ruben
 */
public class FloorImage extends JPanel {

    // Floorcontent is called each time we repaint.
    private FloorContent floorContent;
    private Rectangle imageRect;
    private int dx;
    private int dy;

    public FloorImage() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        setPreferredSize(new Dimension(700, 700));
        dx = 0;
        dy = 0;


        FloorImageInputListener floorImageInputListener = new FloorImageInputListener(this);
        addMouseListener(floorImageInputListener);
        addMouseMotionListener(floorImageInputListener);
        addKeyListener(floorImageInputListener);
    }



    public void setFloorContent(FloorContent floorContent) {
        this.floorContent = floorContent;
    }

    public FloorContent getFloorContent() {
        return floorContent;
    }

    public Rectangle getImageRectangle() {
        return imageRect;
    }

    public void setImageRectangle(Rectangle r) {
        imageRect = r;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Image
        if (floorContent.getImageFile() == null) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            try {
                Image image = ImageIO.read(floorContent.getImageFile());
                if (Math.min(getWidth(), getHeight()) == getWidth()) {
                    image = image.getScaledInstance(getWidth(), -1, Image.SCALE_SMOOTH);
                } else {
                    image = image.getScaledInstance(-1, getHeight(), Image.SCALE_SMOOTH);
                }

                dx = (getWidth() - image.getWidth(null)) / 2;
                dy = (getHeight() - image.getHeight(null)) / 2;
                
                imageRect = new Rectangle(dx,dy,image.getWidth(null),image.getHeight(null));

                
                g2D.translate(dx, dy);
                g2D.drawImage(image, 0, 0, imageRect.width, imageRect.height, this);
            } catch (Exception exc) {
            }
        }

        // Grid
        g2D.setColor(FloorConstants.FLOORIMAGECOLORGRID);
        if (floorContent.isGridEnabled()) {
            for (int i = FloorConstants.FLOORIMAGEGRIDSTEP; i < getWidth(); i = i + FloorConstants.FLOORIMAGEGRIDSTEP) {
                g2D.drawLine(i, 0, i, getHeight());
            }
            for (int i = FloorConstants.FLOORIMAGEGRIDSTEP; i < getHeight(); i = i + FloorConstants.FLOORIMAGEGRIDSTEP) {
                g2D.drawLine(0, i, getWidth(), i);
            }
        }

        // Uncompleted polygons
        g2D.setColor(FloorConstants.FLOORIMAGECOLORNAMEDPOLYGONISSTRING);
        Vector<Point> points = floorContent.getPointQueu();
        for (int i = 0; i < points.size(); i++) {
            g2D.fillOval(points.get(i).x - FloorConstants.FLOORIMAGEDIAMETERDOT / 2, points.get(i).y - FloorConstants.FLOORIMAGEDIAMETERDOT / 2, FloorConstants.FLOORIMAGEDIAMETERDOT, FloorConstants.FLOORIMAGEDIAMETERDOT);
            if (i < points.size() - 1) {
                g2D.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
            }
        }

        // Namedpolygons
        for (NamedPolygon namedPolygon : floorContent.getNamedPolygons()) {
            if (namedPolygon.isNumber()) {
                g2D.setColor(FloorConstants.FLOORIMAGECOLORNAMEDPOLYGONISNUMBERBACKGROUND);
                g2D.fillPolygon(namedPolygon);
                if (namedPolygon.isSelected()) {
                    g2D.setStroke(new BasicStroke(3));
                    g2D.setColor(FloorConstants.FLOORIMAGECOLORNAMEDPOLYGONISNUMBER);
                    g2D.drawPolygon(namedPolygon);
                } else {
                    float[] dashPattern = {3};
                    g2D.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 5, dashPattern, 5));
                    g2D.setColor(FloorConstants.FLOORIMAGECOLORNAMEDPOLYGONISNUMBER);
                    g2D.drawPolygon(namedPolygon);
                }
                for (Point point : namedPolygon.getPoints()) {
                    g2D.fillOval(point.x - FloorConstants.FLOORIMAGEDIAMETERDOT / 2, point.y - FloorConstants.FLOORIMAGEDIAMETERDOT / 2, FloorConstants.FLOORIMAGEDIAMETERDOT, FloorConstants.FLOORIMAGEDIAMETERDOT);
                }
                g2D.drawString(namedPolygon.getName(), namedPolygon.getCentre().x, namedPolygon.getCentre().y);
            } else {
                g2D.setColor(FloorConstants.FLOORIMAGECOLORNAMEDPOLYGONSISSTRINGBACKGROUND);
                g2D.fillPolygon(namedPolygon);
                if (namedPolygon.isSelected()) {
                    g2D.setStroke(new BasicStroke(3));
                    g2D.setColor(FloorConstants.FLOORIMAGECOLORNAMEDPOLYGONISSTRING);
                    g2D.drawPolygon(namedPolygon);
                } else {
                    float[] dashPattern = {6};
                    g2D.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 5, dashPattern, 5));
                    g2D.setColor(FloorConstants.FLOORIMAGECOLORNAMEDPOLYGONISSTRING);
                    g2D.drawPolygon(namedPolygon);
                }
                for (Point point : namedPolygon.getPoints()) {
                    g2D.fillOval(point.x - FloorConstants.FLOORIMAGEDIAMETERDOT / 2, point.y - FloorConstants.FLOORIMAGEDIAMETERDOT / 2, FloorConstants.FLOORIMAGEDIAMETERDOT, FloorConstants.FLOORIMAGEDIAMETERDOT);
                }
                g2D.drawString(namedPolygon.getName(), namedPolygon.getCentre().x, namedPolygon.getCentre().y);
            }
        }

        // Icons
        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(1));
        Image cameraImage = new Camera().getCameraIcon().getImage();
        Image fireExtinguisherImage = new FireExtinguisher().getFireIcon().getImage();
        Image emergencyExitImage = new EmergencyExit().getEmergencyExitIcon().getImage();
        for (FireExtinguisher fireExtinguisher : floorContent.getFireExtinguishers()) {
            if (fireExtinguisher.isSelected()) {
                g2D.setColor(Color.LIGHT_GRAY);
                g2D.drawOval(fireExtinguisher.x - fireExtinguisherImage.getWidth(null) / 2 - 1, fireExtinguisher.y - fireExtinguisherImage.getHeight(null) / 2 - 1, fireExtinguisherImage.getWidth(null) + 2, fireExtinguisherImage.getHeight(null) + 2);
                g2D.setColor(Color.BLACK);
            }
            g2D.drawImage(fireExtinguisherImage, fireExtinguisher.x - fireExtinguisherImage.getWidth(null) / 2, fireExtinguisher.y - fireExtinguisherImage.getHeight(null) / 2, null);
        }
        for (Camera camera : floorContent.getCameras()) {
            if (camera.isSelected()) {
                g2D.setColor(Color.LIGHT_GRAY);
                g2D.drawOval(camera.x - cameraImage.getWidth(null) / 2 - 1, camera.y - cameraImage.getHeight(null) / 2 - 1, cameraImage.getWidth(null) + 2, cameraImage.getHeight(null) + 2);
                g2D.setColor(Color.BLACK);
            }
            g2D.drawImage(cameraImage, camera.x - cameraImage.getWidth(null) / 2, camera.y - cameraImage.getHeight(null) / 2, null);
        }
        for (EmergencyExit emergencyExit : floorContent.getEmergencyExits()) {
            if (emergencyExit.isSelected()) {
                g2D.setColor(Color.LIGHT_GRAY);
                g2D.drawOval(emergencyExit.x - emergencyExitImage.getWidth(null) / 2 - 1, emergencyExit.y - emergencyExitImage.getHeight(null) / 2 - 1, emergencyExitImage.getWidth(null) + 2, emergencyExitImage.getHeight(null) + 2);
                g2D.setColor(Color.BLACK);
            }
            g2D.drawImage(emergencyExitImage, emergencyExit.x - emergencyExitImage.getWidth(null) / 2, emergencyExit.y - emergencyExitImage.getHeight(null) / 2, null);
        }

        g2D.setStroke(new BasicStroke(1));
        g2D.setColor(Color.BLACK);

        if(floorContent.getImageFile() != null)
            g2D.translate(-dx, -dy);
    }
}
