package program.classes;

import java.util.ArrayList;

public class Junction extends MapElement{
    ArrayList<Connection> connections = new ArrayList<>();

    public Junction(Map map, String id){
        super(map);
        //TODO
        generateID(id);
    }
    void generateID(String id){
        ElementID = map.getID()+"_"+this.getClass().getSimpleName()+"_"+id;
    }

    //TODO
}
