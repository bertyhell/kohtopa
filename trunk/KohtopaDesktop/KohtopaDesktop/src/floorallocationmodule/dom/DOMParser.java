/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package floorallocationmodule.dom;

import data.entities.Picture;
import floorallocationmodule.model.*;
import floorallocationmodule.objects.Camera;
import floorallocationmodule.objects.EmergencyExit;
import floorallocationmodule.objects.FireExtinguisher;
import floorallocationmodule.objects.NamedPolygon;
import floorallocationmodule.view.FloorImage;
import gui.Logger;
import gui.Main;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.InputSource;

/**
 * DOMParser writes important information from floorContent into an XML-file
 * using Document Object Model.
 * @author Ruben
 */
public class DOMParser {

    private FloorContent floorContent;
    private int pictureID;
    private Document document;

    /**
     * Create DOM Document
     */
    public DOMParser() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (Exception ex) {
			Logger.logger.error("Exception in DOMParser " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
        }
    }

         /**
     * Create a new DOM Document by using a Blob
     */
    public DOMParser(String blobString) {
        try {
            if (blobString == null) {
               Logger.logger.debug("blobString is null");
            } else {
               Logger.logger.debug("blobstring: " + blobString);
            }
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(blobString));
			document = builder.parse(is);
        } catch (Exception ex) {
			Logger.logger.error("ParseException in DOMparser " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
        }
    }

    public void setFloorContent(FloorContent floorContent, int pictureID) {
        this.floorContent = floorContent;
        this.pictureID = pictureID;
    }

    public FloorContent getFloorContent(FloorImage floorImage) {
        FloorContent localFloorContent = new FloorContent();
        localFloorContent.setFloorImage(floorImage);

        try {
            NodeList nodeFloorContent = document.getElementsByTagName(DOMConstants.FLOORCONTENT);
            NodeList children = nodeFloorContent.item(0).getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeName().equals(DOMConstants.NAME)) {
                   Logger.logger.debug("name detected");
                   Logger.logger.debug("name: " + child.getFirstChild().getNodeValue());
                    localFloorContent.setFloorName(child.getFirstChild().getNodeValue());
                } else if (child.getNodeName().equals(DOMConstants.WIDTH)) {
                   Logger.logger.debug("width detected");
                   Logger.logger.debug("width: " + child.getFirstChild().getNodeValue());
                    localFloorContent.setX(Integer.parseInt(child.getFirstChild().getNodeValue()));
                } else if (child.getNodeName().equals(DOMConstants.HEIGHT)) {
                   Logger.logger.debug("height detected");
                   Logger.logger.debug("height: " + child.getFirstChild().getNodeValue());
                    localFloorContent.setY(Integer.parseInt(child.getFirstChild().getNodeValue()));
                } else if (child.getNodeName().equals(DOMConstants.IMAGE)) {
                   Logger.logger.debug("image detected");
                    NodeList imageDetails = child.getChildNodes();
                    for (int j = 0; j < imageDetails.getLength(); j++) {
                        if (imageDetails.item(j).getNodeName().equals(DOMConstants.NAME)) {

                        } else if (imageDetails.item(j).getNodeName().equals(DOMConstants.IMAGEID)) {
                           Logger.logger.debug("imageid: " + imageDetails.item(j).getFirstChild().getNodeValue());
                            Picture picture = Main.getDataObject().getPicture(Integer.parseInt(imageDetails.item(j).getFirstChild().getNodeValue()));
                            BufferedImage img = picture.getPicture();
                            File imgFile = new File("test.jpg");
                            ImageIO.write(img, "jpg", imgFile);
                            localFloorContent.setImageFile(imgFile);
                        }
                    }
                } else if (child.getNodeName().equals(DOMConstants.NAMEDPOLYGONS)) {
                   Logger.logger.debug("named polygon detected");
                    NodeList polygons = child.getChildNodes();
                    NamedPolygon polygon = null;
                    for (int j = 0; j < polygons.getLength(); j++) {
                        if (polygons.item(j).getNodeName().equals(DOMConstants.NAMEDPOLYGON)) {
                           Logger.logger.debug("constructing named polygon");
                            polygon = makePolygon(polygons.item(j));
                            if (polygon != null) {
                                localFloorContent.addNamedPolygon(polygon);
                            }
                            polygon = null;
                        }
                    }                    
                } else if (child.getNodeName().equals(DOMConstants.CAMERAS)) {
                   Logger.logger.debug("camera detected");
                    NodeList cameras = child.getChildNodes();
                    Camera camera = null;
                    for (int j = 0; j < cameras.getLength(); j++) {
                        camera = makeCamera(cameras.item(j));
                        if (camera != null) {
                            localFloorContent.addCamera(camera);
                        }
                        camera = null;
                    }                    
                } else if (child.getNodeName().equals(DOMConstants.FIREEXTINGUISHERS)) {
                   Logger.logger.debug("fireextinguisher detected");
                    NodeList fireExtinguishers = child.getChildNodes();
                    FireExtinguisher fireExtinguisher = null;
                    for (int j = 0; j < fireExtinguishers.getLength(); j++) {
                        fireExtinguisher = makeFireExtinguisher(fireExtinguishers.item(j));
                        if (fireExtinguisher != null) {
                            localFloorContent.addFireExtinguisher(fireExtinguisher);
                        }
                        fireExtinguisher = null;
                    }                    
                } else if (child.getNodeName().equals(DOMConstants.EMERGENCYEXITS)) {
                   Logger.logger.debug("emergency exit detected");
                    NodeList emergencyExits = child.getChildNodes();
                    EmergencyExit emergencyExit = null;
                    for (int j = 0; j < emergencyExits.getLength(); j++) {
                        emergencyExit = makeEmergencyExit(emergencyExits.item(j));
                        if (emergencyExit != null) {
                            localFloorContent.addEmergencyExit(emergencyExit);
                        }
                        emergencyExit = null;
                    }                    
                }
            }
        } catch (Exception ex) {
			Logger.logger.error("Exception in getFloorContent in DOMParser " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
        }
        return localFloorContent;
    }

    private NamedPolygon makePolygon(Node node) {
        NamedPolygon polygon = null;

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child  = children.item(i);
            if (child.getNodeName().equals(DOMConstants.NAME)) {
               Logger.logger.debug("polygon name: " + child.getFirstChild().getNodeValue());
                polygon = new NamedPolygon(child.getFirstChild().getNodeValue());
            } else if (child.getNodeName().equals(DOMConstants.POINTS)) {
                NodeList points = child.getChildNodes();
                for (int k = 0; k < points.getLength(); k++) {
                    Node point = points.item(k);
                    if (point.getNodeName().equals(DOMConstants.POINT)) {
                        NodeList coordinates = point.getChildNodes();
                        int x = 0;
                        int y = 0;
                        for (int j = 0 ; j < coordinates.getLength(); j++) {
                            Node coordinate = coordinates.item(j);
                           Logger.logger.debug("inner node name:" + coordinate.getNodeName());
                            if (coordinate.getNodeName().equals(DOMConstants.X)) {
                               Logger.logger.debug("named polygon x found");
                                x = Integer.parseInt(coordinate.getFirstChild().getNodeValue());
                            } else if (coordinate.getNodeName().equals(DOMConstants.Y)) {
                               Logger.logger.debug("named polygon y found");
                                y = Integer.parseInt(coordinate.getFirstChild().getNodeValue());
                            }
                        }
                       Logger.logger.debug("adding polygon point: " + x + ", " + y);
                        polygon.addPoint(x, y);
                    }
                }
            }
        }

        return polygon;
    }

    private Camera makeCamera(Node node) {
        Camera camera = null;

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeName().equals(DOMConstants.POINT)) {
               Logger.logger.debug("constructing camera");
                NodeList coordinates = child.getChildNodes();
                int x = 0;
                int y = 0;
                for (int j = 0 ; j < coordinates.getLength(); j++) {
                    Node coordinate = coordinates.item(j);
                    if (coordinate.getNodeName().equals(DOMConstants.X)) {
                        x = Integer.parseInt(coordinate.getFirstChild().getNodeValue());
                    } else if (coordinate.getNodeName().equals(DOMConstants.Y)) {
                        y = Integer.parseInt(coordinate.getFirstChild().getNodeValue());
                    }
                }
               Logger.logger.debug("x: " + x + " y: " + y);
                camera = new Camera(x, y);
            }
        }

        return camera;
    }

    private FireExtinguisher makeFireExtinguisher(Node node) {
        FireExtinguisher fireExtinguisher = null;

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeName().equals(DOMConstants.POINT)) {
               Logger.logger.debug("constructing fireextinguisher");
                NodeList coordinates = child.getChildNodes();
                int x = 0;
                int y = 0;
                for (int j = 0 ; j < coordinates.getLength(); j++) {
                    Node coordinate = coordinates.item(j);
                    if (coordinate.getNodeName().equals(DOMConstants.X)) {
                        x = Integer.parseInt(coordinate.getFirstChild().getNodeValue());
                    } else if (coordinate.getNodeName().equals(DOMConstants.Y)) {
                        y = Integer.parseInt(coordinate.getFirstChild().getNodeValue());
                    }
                }
               Logger.logger.debug("x: " + x + " y: " + y);
                fireExtinguisher = new FireExtinguisher(x, y);
            }
        }

        return  fireExtinguisher;
    }

    private EmergencyExit makeEmergencyExit(Node node) {
        EmergencyExit emergencyExit = null;

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeName().equals(DOMConstants.POINT)) {
               Logger.logger.debug("constructing emergency exit");
                NodeList coordinates = child.getChildNodes();
                int x = 0;
                int y = 0;
                for (int j = 0 ; j < coordinates.getLength(); j++) {
                    Node coordinate = coordinates.item(j);
                    if (coordinate.getNodeName().equals(DOMConstants.X)) {
                        x = Integer.parseInt(coordinate.getFirstChild().getNodeValue());
                    } else if (coordinate.getNodeName().equals(DOMConstants.Y)) {
                        y = Integer.parseInt(coordinate.getFirstChild().getNodeValue());
                    }
                }
               Logger.logger.debug("x: " + x + " y: " + y);
                emergencyExit = new EmergencyExit(x, y);
            }
        }

        return  emergencyExit;
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

            // Dimension
            Element width = document.createElement(DOMConstants.WIDTH);
            Text txtWidth = document.createTextNode(String.valueOf(floorContent.getX()));
            width.appendChild(txtWidth);
            root.appendChild(width);
            Element height = document.createElement(DOMConstants.HEIGHT);
            Text txtHeight = document.createTextNode(String.valueOf(floorContent.getY()));
            height.appendChild(txtHeight);
            root.appendChild(height);

            // Image
            if (floorContent.getImageFile() != null) {
                File imgFile = floorContent.getImageFile();
                BufferedImage img = ImageIO.read(imgFile);
                String imgName = floorContent.getImageFile().getName();

                Element image = document.createElement(DOMConstants.IMAGE);

                Element imageName = document.createElement(DOMConstants.NAME);
                image.appendChild(imageName);

                Text txtImage = document.createTextNode(imgName);
                imageName.appendChild(txtImage);

                Element imageID = document.createElement(DOMConstants.IMAGEID);
                image.appendChild(imageID);

                Text txtImageID = document.createTextNode(Integer.toString(pictureID));
                imageID.appendChild(txtImageID);

                root.appendChild(image);
            }

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

            // EmergencyExits
            Element emergencyExits = document.createElement(DOMConstants.EMERGENCYEXITS);
            root.appendChild(emergencyExits);

            Element emergencyExit;
            Element emergencyExitPoint;
            Element emergencyExitPointX;
            Element emergencyExitPointY;
            for (EmergencyExit ee: floorContent.getEmergencyExits()) {
                emergencyExit = document.createElement(DOMConstants.EMERGENCYEXIT);
                emergencyExits.appendChild(emergencyExit);

                // Point
                emergencyExitPoint = document.createElement(DOMConstants.POINT);
                emergencyExit.appendChild(emergencyExitPoint);

                emergencyExitPointX = document.createElement(DOMConstants.X);
                emergencyExitPoint.appendChild(emergencyExitPointX);
                txtPointX = document.createTextNode(((Integer)ee.x).toString());
                emergencyExitPointX.appendChild(txtPointX);

                emergencyExitPointY = document.createElement(DOMConstants.Y);
                emergencyExitPoint.appendChild(emergencyExitPointY);
                txtPointY = document.createTextNode(((Integer)ee.y).toString());
                emergencyExitPointY.appendChild(txtPointY);
            }
           
        } catch (Exception ex) {
			Logger.logger.error("Exception in parse in DOMParser " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
        }
    }

    // Currently saved on disk. TODO: save in database.
    public void write(int buildingID, int floor) {
        try {
            // XML
           Logger.logger.info("Writing in DOMParser");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            File file = new File("floor.xml");
            StreamResult streamResult = new StreamResult(file);
            DOMSource source = new DOMSource(document);
            transformer.transform(source, streamResult);

            // Store in database
            Main.getDataObject().addFloor(buildingID, floor, file);

        } catch (Exception ex) {
			Logger.logger.error("Exception in write in DOMParser " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
        }
    }

    private String getExtension(File file) {
        String ext = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

}
