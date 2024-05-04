package program.classes;

import program.interfaces.IDHandler;

import java.util.ArrayList;

public class Connection extends IDHandler {
    final Junction junction;
    Road incomingRoad;
    Road connectingRoad;

    ArrayList<Link> links = new ArrayList<>();
    public Connection(Junction junction, String id){
        this.junction = junction;
        //TODO
        generateID(id);
    }
    void generateID(String id){
        ElementID = junction.getID()+"_"+this.getClass().getSimpleName()+"_"+id;
    }


    //TODO
}
