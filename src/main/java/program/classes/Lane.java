package program.classes;

import program.interfaces.IDHandler;

import java.util.ArrayList;

public class Lane extends IDHandler {
    final Road road;

    LaneType laneType;

    public Lane(Road road, String id){
        this.road = road;
        //TODO
        generateID(id);
    }
    void generateID(String id){
        ElementID = road.getID()+"_"+this.getClass().getSimpleName()+"_"+id;
    }

    //TODO
}
