package program.classes;

import com.sun.tools.attach.AgentInitializationException;
import program.interfaces.IDHandler;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;

public final class Map extends IDHandler{

    private static Map INSTANCE;

    private Map(){
        generateID("");
    }

    public static Map getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new Map();
        }

        return INSTANCE;
    }
    ArrayList<MapElement> mapElements = new ArrayList<>();

    public void addMapElement(MapElement mapElement){
        mapElements.add(mapElement);
    }

    public ArrayList<MapElement> getElements(){
        return mapElements;
    }

    void generateID(String id){
        ElementID = "map";
    }
}
