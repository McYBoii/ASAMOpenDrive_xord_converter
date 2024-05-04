package program.classes;

import program.interfaces.IDHandler;

import java.util.ArrayList;

public class Road extends MapElement{
    MapElement predecessor = null;
    MapElement successor = null;
    ArrayList<Lane> leftLanes = new ArrayList<>();
    Lane centerLane;
    ArrayList<Lane> rightLanes = new ArrayList<>();

    public Road(Map map, String id){
        super(map);
        //TODO
        generateID(id);
    }
    void generateID(String id){
        ElementID = map.getID()+"_"+this.getClass().getSimpleName()+"_"+id;
    }

    //TODO

    public void addLeftLane(Lane lane){
        leftLanes.add(lane);
    }
    public void addRightLane(Lane lane){
        rightLanes.add(lane);
    }

    public void setCenterLane(Lane lane){
        centerLane = lane;
    }
}
