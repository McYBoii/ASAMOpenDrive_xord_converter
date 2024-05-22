import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
    Lane successorlane
    Lane predecessorlane
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
enum LaneType {border, sidewalk, driving, biking, parking, restricted, none}

class Car{
    Lane position
}

%Predications



%Scope

scope Map += 0, Junction += 0, Connection += 0, Link += 0, Road += 0, Lane += 0.

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

    /** Use this function to generate new instances of Junction
     * @param id ID of the Junction
     * @return Refinery creation text
     */
    public static String createJunctionGroup(String id){
        return "JunctionGroup("+id+").\n";
    }

    /** Use this function to generate new instances of Lane
     * @param id ID of the Lane
     * @return Refinery creation text
     */
    public static String createLane(String id){
        return "Lane("+id+").\n";
    }

    /** Use this function to set the type of a Lane
     * @param laneID Lane to set the type
     * @param type type [border, sidewalk, driving, restricted, none]
     * @return Refinery attribute setter text
     */
    public static String laneTypeSetter(String laneID, String type){
        return "type("+laneID+", "+type+").\n";
    }

    /** Use this function to set the elements attribute of the Map.
     * @param id ID of the element
     * @return Refinery attribute setter text
     */
    public static String addElements(String id){
        return "elements(map, "+id+").\n";
    }

    /** Use this function to set the junctions attribute of a JunctionGroup.
     * @param junctiongroupID The JunctionGroup we add the Junctions
     * @param junctionID ID of the Junction
     * @return Refinery attribute setter text
     */
    public static String addJunctions(String junctiongroupID, String junctionID){
        return "junctions("+junctiongroupID+", "+junctionID+").\n";
    }

    /** Use this function to add Connections to a Junction
     * @param junctionID Junction we add the Connection to
     * @param connectionID The Connection we add
     * @return Refinery attribute setter text
     */
    public static String addConnections(String junctionID, String connectionID){
        return "connections("+junctionID+", "+connectionID+").\n";
    }

    /** Use this function to set the incomingRoad for a Connection
     * @param connectionID Connection to add the Road
     * @param roadID IncomingRoad
     * @return Refinery attribute setter text
     */
    public static String incomingRoadSetter(String connectionID, String roadID){
        return "incomingRoad("+connectionID+", "+roadID+").\n";
    }

    /** Use this function to set the connectingRoad for a Connection
     * @param connectionID Connection to add the Road
     * @param roadID ConnectingROad
     * @return Refinery attribute setter text
     */
    public static String connectingRoadSetter(String connectionID, String roadID){
        return "connectingRoad("+connectionID+", "+roadID+").\n";
    }

    /** Use this function to add Links to a Connection
     * @param connectionID Connection to add the Link
     * @param lanelinkID Link to add
     * @return Refinery attribute setter text
     */
    public static String addLaneLink(String connectionID, String lanelinkID){
        return "links("+connectionID+", "+lanelinkID+").\n";
    }

    /** Use this function to set the To lane on a link
     * @param linkID Link to set the Lane
     * @param laneID Lane to set up
     * @return Refinery attibute setter text
     */
    public static String toSetter(String linkID, String laneID){
        return "to("+linkID+", "+laneID+").\n";
    }

    /** Use this function to set the From lane on a link
     * @param linkID Link to set the Lane
     * @param laneID Lane to set up
     * @return Refinery attibute setter text
     */
    public static String fromSetter(String linkID, String laneID){
        return "from("+linkID+", "+laneID+").\n";
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

    /** Use this function to add a lanesection to a Road
     * @param roadID Road that recieves the LaneSection
     * @param lanesectionID LaneSection to add
     * @return Refinery attribute setter text
     */
    public static String addLaneSection(String roadID, String lanesectionID){
        return "lanesections("+roadID+", "+lanesectionID+").\n";
    }
    /** use this function to add Left Lanes to a LaneSection
     * @param lanesectionID LaneSection recieving the Lane
     * @param laneID Lane to add
     * @return Refinery attribute setter text
     */
    public static String addLeftLane(String lanesectionID, String laneID){
        return "leftlanes("+lanesectionID+", "+laneID+").\n";
    }
    /** use this function to set the Center Lane for a LaneSection
     * @param lanesectionID LaneSection recieving the Lane
     * @param laneID Lane to add
     * @return Refinery attribute setter text
     */
    public static String centerLaneSetter(String lanesectionID, String laneID){
        return "centerlane("+lanesectionID+", "+laneID+").\n";
    }
    /** use this function to add Right Lanes to a LaneSection
     * @param lanesectionID LaneSection recieving the Lane
     * @param laneID Lane to add
     * @return Refinery attribute setter text
     */
    public static String addRightLane(String lanesectionID, String laneID){
        return "rightlanes("+lanesectionID+", "+laneID+").\n";
    }

    /** Refinery does not support '-' character in names. This function converts negative id's to '_n' annotated id's for example: -1 to _n1
     * @param id id to convert
     * @return converted id
     */
    public static String negaviteIDconverter(String id){
        return (id.replace("-", "_n"));
    }

    static String FILEPATH = "model.problem";


    ///EXPERIMENTAL REGION FOR DEBUGING



    ///==!==!==!==!==!==!==

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

            // Making the file or overrideing it
            File output = new File(FILEPATH);

            FileWriter fileWriter = new FileWriter(output);

            // Writing the metamodel and Map instance see above what we write here.
            fileWriter.write(problemFileHeader());

            // Iterating every road and witing to the file as we progress.
            for (int i = 0; i < roads.getLength(); i++) {
                Element road =(Element) roads.item(i);

                // Generating an ID to prefix to child elements.
                String roadID = "road"+road.getAttribute("id");

                //Creating the road and adding it to the elements of the map.
                fileWriter.write(createRoad(roadID));

                fileWriter.write(addElements(roadID));

                //Getting the links from the current road, but we only use the first one.
                NodeList links = road.getElementsByTagName("link");
                // safe, link tags are always present even if empty
                Element link = (Element)links.item(0);

                NodeList successors = link.getElementsByTagName("successor");
                NodeList predecessors = link.getElementsByTagName("predecessor");

                //Sometimes successor and predecessor are not present we take care of this here.
                String successorID = null;
                if(successors.getLength() == 1){
                    Element successor = (Element)successors.item(0);
                    successorID= successor.getAttribute("elementType")+successor.getAttribute("elementId");
                    fileWriter.write(successorSetter(roadID, successorID));
                }
                String predecessorID = null;
                if(predecessors.getLength() == 1){
                    Element predecessor = (Element)predecessors.item(0);
                    predecessorID = predecessor.getAttribute("elementType")+predecessor.getAttribute("elementId");
                    fileWriter.write(predecessorSetter(roadID, predecessorID));
                }

                //Getting the laneSections and processing them
                NodeList lanesections = road.getElementsByTagName("laneSection");
                //Lane sections dont have ID's, we generate for them from the cycle
                for (int j = 0; j < lanesections.getLength(); j++) {
                    Element lanesection = (Element) lanesections.item(j);
                    String lanesectionID = roadID+"lanesection"+j;

                    NodeList lanes = lanesection.getElementsByTagName("lane");

                    for (int k = 0; k < lanes.getLength(); k++) {
                        Element lane = (Element)lanes.item(k);
                        int id = Integer.parseInt(lane.getAttribute("id"));
                        if(0 < id){// left lanes
                            String laneID = lanesectionID+"lane"+lane.getAttribute("id");
                            fileWriter.write(createLane(laneID));
                            fileWriter.write(laneTypeSetter(laneID, lane.getAttribute("type")));
                            fileWriter.write(addLeftLane(lanesectionID, laneID));
                        }else if(0 == id){//center lane
                            String laneID = lanesectionID+"lane"+lane.getAttribute("id");
                            fileWriter.write(createLane(laneID));
                            fileWriter.write(laneTypeSetter(laneID, lane.getAttribute("type")));
                            fileWriter.write(centerLaneSetter(lanesectionID, laneID));
                        }else if(0 > id){//right lanes, also converting
                            String laneID = lanesectionID+"lane"+negaviteIDconverter(lane.getAttribute("id"));
                            fileWriter.write(createLane(laneID));
                            fileWriter.write(laneTypeSetter(laneID, lane.getAttribute("type")));
                            fileWriter.write(addRightLane(lanesectionID, laneID));
                        }
                    }
                }
            }

            // Iterating every junction and processing them
            for (int i = 0; i < junctions.getLength(); i++) {
                Element junction = (Element) junctions.item(i);
                // Generating an ID to prefix to child elements.
                String junctionID = "junction"+junction.getAttribute("id");

                fileWriter.write(createJunction(junctionID));
                fileWriter.write(addElements(junctionID));

                NodeList connections = junction.getElementsByTagName("connection");
                for (int j = 0; j < connections.getLength(); j++) {
                    Element connection = ((Element)connections.item(j));
                    String connectionID = junctionID+"connection"+connection.getAttribute("id");
                    fileWriter.write(addConnections(junctionID, connectionID));

                    String incomingroad = connection.getAttribute("incomingRoad");
                    String connectingroad = connection.getAttribute("connectingRoad");

                    fileWriter.write(incomingRoadSetter(connectionID, "road"+incomingroad));
                    fileWriter.write(connectingRoadSetter(connectionID, "road"+connectingroad));

                    NodeList links = connection.getElementsByTagName("laneLink");
                    for (int k = 0; k < links.getLength(); k++) {
                        Element link = (Element) links.item(k);
                        String linkID = connectionID+"link"+k;
                        fileWriter.write(addLaneLink(connectionID, linkID));

                        fileWriter.write(toSetter(linkID, "road"+connectingroad+"lanesection0lane"+negaviteIDconverter(link.getAttribute("to"))));
                        fileWriter.write(fromSetter(linkID, "road"+incomingroad+"lanesection0lane"+negaviteIDconverter(link.getAttribute("from"))));

                    }

                }

            }
            // Iterataing every JunctionGroups
            for (int i = 0; i < junctiongroups.getLength(); i++) {
                Element junctiongroup = (Element) junctiongroups.item(i);
                String junctiongroupID = "junctiongroup"+junctiongroup.getAttribute("id");

                fileWriter.write(createJunctionGroup(junctiongroupID));
                fileWriter.write(addElements(junctiongroupID));

                NodeList references = junctiongroup.getElementsByTagName("junctionReference");

                for (int j = 0; j < references.getLength(); j++) {
                    Element reference = (Element) references.item(j);

                    fileWriter.write(addJunctions(junctiongroupID, "junction"+reference.getAttribute("junction")));
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