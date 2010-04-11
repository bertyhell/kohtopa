/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package floorallocationmodule.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Vector;

/**
 *
 * @author Ruben
 */
public class NamedPolygon extends Polygon {

    private String name;
    private boolean isNumber;
    private boolean selected;

    // Polygon doesn't has a function which returns every point, therefore we do it ourselves.
    private Vector<Point> points;

    public NamedPolygon(String name) {
        super();
        this.name = name;

        try {
            Integer.parseInt(name);
            isNumber = true;
        } catch (NumberFormatException exc) {
            isNumber = false;
        }

        selected = false;
        points = new Vector<Point>();
    }

    @Override
    public void addPoint(int x, int y) {
        super.addPoint(x, y);
        points.add(new Point(x, y));
    }

    public Vector<Point> getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public Point getCentre() {
        Rectangle polygonBounds = getBounds();
        return new Point(polygonBounds.x + polygonBounds.width / 2, polygonBounds.y + polygonBounds.height / 2);
    }

    public boolean isIsNumber() {
        return isNumber;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
