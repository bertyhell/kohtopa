/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package floorallocationmodule.dom;

import floorallocationmodule.model.*;
import floorallocationmodule.objects.Camera;
import floorallocationmodule.objects.FireExtinguisher;
import floorallocationmodule.objects.NamedPolygon;
import java.awt.Point;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


/**
 * DOMParser writes important information from floorContent into an XML-file
 * using Document Object Model.
 * @author Ruben
 */
public class DOMParser {

    private FloorContent floorContent;
    private Document document;

    /**
     * Create DOM Document
     */
    public DOMParser() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }

    public void setFloorContent(FloorContent floorContent) {
        this.floorContent = floorContent;
    }

    /**
    * Create Object Tree
    */
    public void parse() {
        try {
            // Root
            Element root = document.createElement(DOMConstants.FLOORCONTENT);
            document.appendChild(root);

            // Name
            Element name = document.createElement(DOMConstants.NAME);
            Text txtName = document.createTextNode(floorContent.getFloorName());
            name.appendChild(txtName);
            root.appendChild(name);

            // NamedPolygons
            Element namedPolygons = document.createElement(DOMConstants.NAMEDPOLYGONS);
            root.appendChild(namedPolygons);

            Element namedPolygon;
            Element namedPolygonName;
            Element namedPolygonPoints;
            Element namedPolygonPoint;
            Element namedPolygonPointX;
            Text txtPointX;
            Element namedPolygonPointY;            
            Text txtPointY;
            for (NamedPolygon polygon: floorContent.getNamedPolygons()) {
                namedPolygon = document.createElement(DOMConstants.NAMEDPOLYGON);
                namedPolygons.appendChild(namedPolygon);

                // Name
                namedPolygonName = document.createElement(DOMConstants.NAME);
                namedPolygon.appendChild(namedPolygonName);
                txtName = document.createTextNode(polygon.getName());
                namedPolygonName.appendChild(txtName);

                // Points
                namedPolygonPoints = document.createElement(DOMConstants.POINTS);
                namedPolygon.appendChild(namedPolygonPoints);

                for (Point point: polygon.getPoints()) {
                    namedPolygonPoint = document.createElement(DOMConstants.POINT);
                    namedPolygonPoints.appendChild(namedPolygonPoint);

                    namedPolygonPointX = document.createElement(DOMConstants.X);
                    namedPolygonPoint.appendChild(namedPolygonPointX);
                    txtPointX = document.createTextNode(((Integer)point.x).toString());
                    namedPolygonPointX.appendChild(txtPointX);

                    namedPolygonPointY = document.createElement(DOMConstants.Y);
                    namedPolygonPoint.appendChild(namedPolygonPointY);
                    txtPointY = document.createTextNode(((Integer)point.y).toString());
                    namedPolygonPointY.appendChild(txtPointY);
                }
            }

            // Cameras
            Element cameras = document.createElement(DOMConstants.CAMERAS);
            root.appendChild(cameras);

            Element camera;
            Element cameraPoint;
            Element cameraPointX;
            Element cameraPointY;
            for (Camera cam: floorContent.getCameras()) {
                camera = document.createElement(DOMConstants.CAMERA);
                cameras.appendChild(camera);

                // Point
                cameraPoint = document.createElement(DOMConstants.POINT);
                camera.appendChild(cameraPoint);

                cameraPointX = document.createElement(DOMConstants.X);
                cameraPoint.appendChild(cameraPointX);
                txtPointX = document.createTextNode(((Integer)cam.x).toString());
                cameraPointX.appendChild(txtPointX);

                cameraPointY = document.createElement(DOMConstants.Y);
                cameraPoint.appendChild(cameraPointY);
                txtPointY = document.createTextNode(((Integer)cam.y).toString());
                cameraPointY.appendChild(txtPointY);
            }

            // FireExtinguishers
            Element fireExtinguishers = document.createElement(DOMConstants.FIREEXTINGUISHERS);
            root.appendChild(fireExtinguishers);

            Element fireExtinguisher;
            Element fireExtinguisherPoint;
            Element fireExtinguisherPointX;
            Element fireExtinguisherPointY;
            for (FireExtinguisher fe: floorContent.getFireExtinguishers()) {
                fireExtinguisher = document.createElement(DOMConstants.FIREEXTINGUISHER);
                fireExtinguishers.appendChild(fireExtinguisher);

                // Point
                fireExtinguisherPoint = document.createElement(DOMConstants.POINT);
                fireExtinguisher.appendChild(fireExtinguisherPoint);

                fireExtinguisherPointX = document.createElement(DOMConstants.X);
                fireExtinguisherPoint.appendChild(fireExtinguisherPointX);
                txtPointX = document.createTextNode(((Integer)fe.x).toString());
                fireExtinguisherPointX.appendChild(txtPointX);

                fireExtinguisherPointY = document.createElement(DOMConstants.Y);
                fireExtinguisherPoint.appendChild(fireExtinguisherPointY);
                txtPointY = document.createTextNode(((Integer)fe.y).toString());
                fireExtinguisherPointY.appendChild(txtPointY);
            }
           
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }

        write();
    }

    private void write() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            File file = new File("test.xml");
            StreamResult streamResult = new StreamResult(file);
            DOMSource source = new DOMSource(document);
            transformer.transform(source, streamResult);
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }

}
