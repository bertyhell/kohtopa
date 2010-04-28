/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package floorallocationmodule.model;

import floorallocationmodule.dom.DOMParser;
import floorallocationmodule.objects.NamedPolygon;
import floorallocationmodule.objects.FireExtinguisher;
import floorallocationmodule.objects.Camera;
import floorallocationmodule.objects.EmergencyExit;
import floorallocationmodule.view.FloorImage;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Ruben
 */
public class FloorContent {

    // FloorImage is needed to call repaint each time content gets changed.
    // In no other ocassion we use floorImage.
    // Floorcontent never changes something in floorImage.
    private FloorImage floorImage;

    // Floor Name
    private String floorName;

    // Variables regarding the actual information to draw the floorImage.
    private Vector<NamedPolygon> namedPolygons;
    private Vector<Point> pointQueu;
    private File imageFile;

    // WasteBin items for undo and redo actions.
    private Vector<NamedPolygon> namedPolygonsWasteBin;
    private Vector<Point> pointQueuWasteBin;

    // Variables regarding options to draw the floorImage.
    private boolean gridEnabled;
    private boolean drawEnabled;

    // Variables regarding icons to add on the floorImage.
    private boolean addFireExtinguisher;
    private boolean addCamera;
    private boolean addEmergencyExit;
    private Vector<FireExtinguisher> fireExtinguishers;
    private Vector<Camera> cameras;
    private Vector<EmergencyExit> emergencyExits;

    private int x;
    private int y;

    public FloorContent() {
        this(0,0);
    }
    public FloorContent(int x, int y) {
        this.x = x;
        this.y = y;
        namedPolygons = new Vector<NamedPolygon>();
        pointQueu = new Vector<Point>();
        namedPolygonsWasteBin = new Vector<NamedPolygon>();
        pointQueuWasteBin = new Vector<Point>();
        gridEnabled = false;
        drawEnabled = false;
        addFireExtinguisher = false;
        addCamera = false;
        addEmergencyExit = false;
        fireExtinguishers = new Vector<FireExtinguisher>();
        cameras = new Vector<Camera>();
        emergencyExits = new Vector<EmergencyExit>();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void clear() {
        namedPolygons.clear();
        pointQueu.clear();
        namedPolygonsWasteBin.clear();
        pointQueuWasteBin.clear();
        fireExtinguishers.clear();
        cameras.clear();
        floorImage.repaint();
        imageFile = null;
    }

    // Returns true if the undo button should be disabled; to be implemented.
    public boolean undo() {
        if (pointQueu.size() > 0) {
            pointQueuWasteBin.add(pointQueu.lastElement());
            pointQueu.remove(pointQueu.lastElement());
        } else if (namedPolygons.size() > 0) {
            namedPolygonsWasteBin.add(namedPolygons.lastElement());
            namedPolygons.remove(namedPolygons.lastElement());
        }
        floorImage.repaint();

        if (pointQueu.isEmpty() && namedPolygons.isEmpty()) {
            return true;
        } else{
            return false;
        }
    }

    // Returns ture if the redo button should be disabled; to be implemented.
    public boolean redo() {
        if (namedPolygonsWasteBin.size() > 0) {
            namedPolygons.add(namedPolygonsWasteBin.lastElement());
            namedPolygonsWasteBin.remove(namedPolygonsWasteBin.lastElement());
        } else if (pointQueuWasteBin.size() > 0) {
            pointQueu.add(pointQueuWasteBin.lastElement());
            pointQueuWasteBin.remove(pointQueuWasteBin.lastElement());
        }
        floorImage.repaint();

        if (pointQueuWasteBin.isEmpty() && namedPolygonsWasteBin.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    // Check if the point already exists in the pointQueu.
    // Used to check if the unfinished polygon should be finished.
    public boolean hasPoint(int x, int y) {
        boolean hasPoint = false;
        for (Point point: pointQueu) {
            if (point.distance(x, y) < FloorConstants.FLOORIMAGEDIAMETERDOT) {
                hasPoint = true;
            }
        }
        return hasPoint;
    }

    /**
     * Selection Methods
     */

    // Deselect all selected objects.
    public void deselectAll() {
        for (NamedPolygon namedPolygon: namedPolygons) {
            namedPolygon.setSelected(false);
        }
        for (Camera camera: cameras) {
            camera.setSelected(false);
        }
        for (FireExtinguisher fireExtinguisher: fireExtinguishers) {
            fireExtinguisher.setSelected(false);
        }
        for (EmergencyExit emergencyExit: emergencyExits) {
            emergencyExit.setSelected(false);
        }
    }

    // Precondition: namedPolygons can't overlap.
    // Returns true if a namedPolygon has been found
    public boolean selectNamedPolgyon(int x, int y) {
        deselectAll();
        boolean found = false;
        for (NamedPolygon namedPolygon: namedPolygons) {
            if (namedPolygon.contains(x, y)) {
                namedPolygon.setSelected(true);

                // TODO: Frame with information from selected namedPolygon.

                found = true;
            }
        }
        floorImage.repaint();
        return found;
    }

    // Returns true if a fireExtinguisher has been found
    public boolean selecteFireExtinguisher(int x, int y) {
        deselectAll();
        boolean found = false;
        for (FireExtinguisher fireExtinguisher: fireExtinguishers) {
            if (!found && new Rectangle(fireExtinguisher.x - fireExtinguisher.getFireIcon().getImage().getWidth(null) / 2,
                              fireExtinguisher.y - fireExtinguisher.getFireIcon().getImage().getHeight(null) / 2,
                              fireExtinguisher.getFireIcon().getImage().getWidth(null),
                              fireExtinguisher.getFireIcon().getImage().getHeight(null)).contains(x, y)) {
                fireExtinguisher.setSelected(true);
                found = true;
            }
        }
        floorImage.repaint();
        return found;
    }

    // Returns true if a camera has been found
    public boolean selectCamera(int x, int y) {
        deselectAll();
        boolean found = false;
        for (Camera camera: cameras) {
            if (!found && new Rectangle(camera.x - camera.getCameraIcon().getImage().getWidth(null) / 2,
                              camera.y - camera.getCameraIcon().getImage().getHeight(null) / 2,
                              camera.getCameraIcon().getImage().getWidth(null),
                              camera.getCameraIcon().getImage().getHeight(null)).contains(x, y)) {
                camera.setSelected(true);
                found = true;
            }
        }
        floorImage.repaint();
        return found;
    }

    // Returns true if an emergencyExit has been found
    public boolean selectEmergencyExit(int x, int y) {
        deselectAll();
        boolean found = false;
        for (EmergencyExit emergencyExit: emergencyExits) {
            if (!found && new Rectangle(emergencyExit.x - emergencyExit.getEmergencyExitIcon().getImage().getWidth(null) / 2,
                              emergencyExit.y - emergencyExit.getEmergencyExitIcon().getImage().getHeight(null) / 2,
                              emergencyExit.getEmergencyExitIcon().getImage().getWidth(null),
                              emergencyExit.getEmergencyExitIcon().getImage().getHeight(null)).contains(x, y)) {
                emergencyExit.setSelected(true);
                found = true;
            }
        }
        floorImage.repaint();
        return found;
    }

    public void removeSelected() {
        Object toBeRemoved = null;
        for (NamedPolygon namedPolygon: namedPolygons) {
            if (namedPolygon.isSelected()) {
                toBeRemoved = namedPolygon;
            }
        }
        namedPolygons.remove(toBeRemoved);
        toBeRemoved = null;

        for (Camera camera: cameras) {
            if (camera.isSelected()) {
                toBeRemoved = camera;
            }
        }
        cameras.remove(toBeRemoved);
        toBeRemoved = null;

        for (FireExtinguisher fireExtinguisher: fireExtinguishers) {
            if (fireExtinguisher.isSelected()) {
                toBeRemoved = fireExtinguisher;
            }
        }
        fireExtinguishers.remove(toBeRemoved);
        toBeRemoved = null;

        for (EmergencyExit emergencyExit: emergencyExits) {
            if (emergencyExit.isSelected()) {
                toBeRemoved = emergencyExit;
            }
        }
        emergencyExits.remove(toBeRemoved);

        floorImage.repaint();
    }

    // Check if a namedPolygon contains this point, if so cancel. Two rooms can't overlap.
    // If the future last segment of the unfinished polygon overlaps, they overlap as well.
    private boolean checkAvailability(Point point) {
        boolean insidePolygon = false;
        boolean hasOverlap = false;

        if(floorImage.getImageRectangle() != null) {
            Point translatedPoint = new Point(point.x+floorImage.getImageRectangle().x,
                   point.y+floorImage.getImageRectangle().y );
            if(!floorImage.getImageRectangle().contains(translatedPoint)) {
                //System.out.println("out of bounds");
                return false;
            }
        } else {
            return false;
        }
        
        if (!namedPolygons.isEmpty()) {
            for (NamedPolygon namedPolygon: namedPolygons) {
                if (namedPolygon.contains(point)) {
                    insidePolygon = true;
                }
            }

            if (!pointQueu.isEmpty()) {
                Line2D.Double lineToIntersect = new Line2D.Double(pointQueu.lastElement(), point);
                for (NamedPolygon namedPolygon: namedPolygons) {
                    for (int i=0; i<namedPolygon.getPoints().size() - 1; i++) {
                        Line2D.Double line = new Line2D.Double(namedPolygon.getPoints().get(i), namedPolygon.getPoints().get(i + 1));
                        if (lineToIntersect.intersectsLine(line)) {
                            hasOverlap = true;
                        }
                    }
                    // Last linesegment !!
                    Line2D.Double line = new Line2D.Double(namedPolygon.getPoints().firstElement(), namedPolygon.getPoints().lastElement());
                    if (lineToIntersect.intersectsLine(line)) {
                        hasOverlap = true;
                    }
                }
            }
        }

        // TODO: Check if new polygon is inside another namedPolygon.

        if (!insidePolygon && !hasOverlap) {
            return true;
        } else {
            return false;
        }
    }

    public void finishNamedPolygon() {
        // First check if the finishing line won't cross another namedPolygon.
        if (checkAvailability(pointQueu.firstElement())) {
            String name = JOptionPane.showInputDialog(null, "Room identifier: ", "Please enter a valid room identifier!", JOptionPane.QUESTION_MESSAGE);

            // TODO: At this point we can test if the room identifier is possible in the current context.
            // For example: room with number 7 isn't possible if the building contains only 6 rooms.

            if (name != null) {
                while (name.equals("")) {
                    name = JOptionPane.showInputDialog(null, "Room identifier: ", "Please enter a valid room identifier!", JOptionPane.QUESTION_MESSAGE);
                }

                NamedPolygon namedPolygon = new NamedPolygon(name);
                for (Point point: pointQueu) {
                    namedPolygon.addPoint(point.x, point.y);
                }
                namedPolygons.add(namedPolygon);
                pointQueu.clear();
                floorImage.repaint();
            }
        }
    }

    // Add a point to the unfinished polygon.
    public void addPointToQueu(Point point) {
        if (checkAvailability(point)) {
            pointQueu.add(point);            
        }
        floorImage.repaint();
    }

    public void addCamera(Camera camera) {
        cameras.add(camera);
        floorImage.repaint();
    }

    public void addFireExtinguisher(FireExtinguisher fireExtinguisher) {
        fireExtinguishers.add(fireExtinguisher);
        floorImage.repaint();
    }

    public void addEmergencyExit(EmergencyExit emergencyExit) {
        emergencyExits.add(emergencyExit);
        floorImage.repaint();
    }

    // Method which writes the content to XML.
    public void save() {
        DOMParser parser = new DOMParser();
        setX(floorImage.getImageRectangle().width);
        setY(floorImage.getImageRectangle().height);
        
        parser.setFloorContent(this);
        parser.parse();
    }

    /**
     * Setters
     */

    public void setFloorImage(FloorImage floorImage) {
        this.floorImage = floorImage;
    }

    public void setGridEnabled(boolean gridEnabled) {
        this.gridEnabled = gridEnabled;
        floorImage.repaint();
    }
    
    public void setDrawEnabled(boolean drawEnabled) {
        this.drawEnabled = drawEnabled;
    }    

    public void setAddCamera(boolean addCamera) {
        this.addCamera = addCamera;
    }

    public void setAddFireExtinguisher(boolean addFireExtinguisher) {
        this.addFireExtinguisher = addFireExtinguisher;
    }

    public void setAddEmergencyExit(boolean addEmergencyExit) {
        this.addEmergencyExit = addEmergencyExit;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
        floorImage.repaint();
    }

    /**
     * Getters
     */

    public Vector<NamedPolygon> getNamedPolygons() {
        return namedPolygons;
    }

    public Vector<Point> getPointQueu() {
        return pointQueu;
    }

    public Vector<Camera> getCameras() {
        return cameras;
    }

    public Vector<FireExtinguisher> getFireExtinguishers() {
        return fireExtinguishers;
    }

    public Vector<EmergencyExit> getEmergencyExits() {
        return emergencyExits;
    }

    public boolean isAddFireExtinguisher() {
        return addFireExtinguisher;
    }

    public boolean isGridEnabled() {
        return gridEnabled;
    }

    public boolean isDrawEnabled() {
        return drawEnabled;
    }

    public boolean isAddCamera() {
        return addCamera;
    }

    public boolean isAddEmergencyExit() {
        return addEmergencyExit;
    }

    public String getFloorName() {
        return floorName;
    }

    public File getImageFile() {
        return imageFile;
    }

}
