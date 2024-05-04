import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.NotActiveException;

public class Main {

    /**Use this function to write the header of the problem file.
     * @return file header
     */
    public static String problemFileHeader(){
        return """
%Metamodel
class Map{
    contains MapElement[] elements opposite map
}
abstract class MapElement{
    container Map map opposite elements
}
class Road extends MapElement{
    MapElement successor
    MapElement predecessor
    %contains Lane [] lanes opposite road
    contains LaneSection[] lanesections opposite road
}
class Junction extends MapElement{
    contains Connection[] connections opposite junction
}
class JunctionGroup extends MapElement{
    contains Junction[] junctions
}
class LaneSection{
    container Road road opposite lanesections
    contains Lane[] leftlanes
    contains Lane centerlane
    contains Lane[] rightlanes
}
class Lane{
    Lane laneSuccessor
    Lane lanePredecessor
    LaneType type
}
class Connection{
    container Junction junction opposite connections
    Road incomingRoad
    Road connectingRoad
    contains LaneLink[] links opposite connection
}
class LaneLink{
    container Connection connection opposite links
    Lane from
    Lane to
}
enum LaneType {border, sidewalk, driving, restricted, none}

%Scope
scope node = 10..5000, Map += 0, Junction += 0, Connection += 0, LaneLink += 0, Road += 0, Lane += 0.

%Instace model
Map(map).
""";
    }

    /** Use this function to generate new instances of Road
     * @param id ID of the Road
     * @return Refinery creation text
     */
    public static String createRoad(String id){
        return "Road("+id+").\n";
    }

    /** Use this function to generate new instances of Junction
     * @param id ID of the Junction
     * @return Refinery creation text
     */
    public static String createJunction(String id){
        return "Junction("+id+").\n";
    }

    /** Use this function to set the elements attribute of the Map.
     * @param id ID of the element
     * @return Refinery attribute setter text
     */
    public static String elementsSetter(String id){
        return "elements(map, "+id+").\n";
    }

    /** Use this function to set the successor of a Road
     * @param roadID the road we set the successor for
     * @param successorID the successor
     * @return Refinery attribute setter text
     */
    public static String successorSetter(String roadID, String successorID){
        return "successor("+roadID+", "+successorID+").\n";
    }

    /** Use this function to set the predecessor of a Road
     * @param roadID the road we set the predecessor for
     * @param predecessorID the predecessor
     * @return Refinery attribute setter text
     */
    public static String predecessorSetter(String roadID, String predecessorID){
        return "predecessor("+roadID+", "+predecessorID+").\n";
    }

    static String FILEPATH = "model.problem";

    public static void main(String[] args) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        if (args.length != 1) {
            System.out.println("Invalid arguments!\n" +
                    "Give only one argument: the path to the .xodr file.");
        }

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(args[0]));

            Element rootElement = document.getDocumentElement();

            // 3 main map elements from the file
            NodeList roads = rootElement.getElementsByTagName("road");
            NodeList junctions = rootElement.getElementsByTagName("junction");
            NodeList junctiongroups = rootElement.getElementsByTagName("junctionGroup");

            // LaneLink is missing ID so we generate for it.
            int LaneLinkCounter = 0;

            // Making the file or overrideing it
            File output = new File(FILEPATH);

            FileWriter fileWriter = new FileWriter(output);

            // Writing the metamodel and Map instance see above what we write here.
            fileWriter.write(problemFileHeader());

            // Iterating every road and witing to the file as we progress.
            for (int i = 0; i < roads.getLength(); i++) {
                   Node road = roads.item(i);

                   if(road.getNodeType() == Node.ELEMENT_NODE){
                       Element eroad = (Element) road;

                       // Generating an ID to prefix to child elements.
                       String currentRoadID = "road"+eroad.getAttribute("id");

                       //Creating the road and adding it to the elements of the map.
                       fileWriter.write(createRoad(currentRoadID));

                       fileWriter.write(elementsSetter(currentRoadID));

                       //Getting the links from the current road, but we only use the first one.
                       NodeList links = eroad.getElementsByTagName("link");
                       Element link = (Element)links.item(0);

                       NodeList successors = link.getElementsByTagName("successor");
                       NodeList predecessors = link.getElementsByTagName("predecessor");

                       //Sometimes successor and predecessor are not present we take care of this here.
                       if(successors.getLength() == 1){
                           Element successor = (Element)successors.item(0);
                           String successorID = successor.getAttribute("elementType")+successor.getAttribute("elementId");
                           fileWriter.write(successorSetter(currentRoadID, successorID));
                       }
                       if(predecessors.getLength() == 1){
                           Element predecessor = (Element)predecessors.item(0);
                           String predecessorID = predecessor.getAttribute("elementType")+predecessor.getAttribute("elementId");
                           fileWriter.write(predecessorSetter(currentRoadID, predecessorID));
                       }

                       //Getting the laneSections and processing them
                       NodeList lanesections = eroad.getElementsByTagName("laneSection");
                       for (int j = 0; j < lanesections.getLength(); j++) {
                           Element lanesection = (Element) lanesections.item(j);

                           NodeList leftlanes = lanesection.getElementsByTagName("left");


                           NodeList centerlanes = lanesection.getElementsByTagName("center");
                           NodeList rightlanes = lanesection.getElementsByTagName("right");

                       }
                   }
            }
            
            

            fileWriter.close();
            
            System.out.println("Everything went fine you can find the output here: "+output.getAbsolutePath());

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("File not found.\n Exiting...");
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

    }
}