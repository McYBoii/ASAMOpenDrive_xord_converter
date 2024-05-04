package program.classes;

import program.interfaces.IDHandler;

public class Link extends IDHandler {
    final Connection connection;
    Lane from;
    Lane to;
    public Link(Connection connection, String id){
        this.connection = connection;
        //TODO
        generateID(id);
    }
    void generateID(String id){
        ElementID = connection.getID()+"_"+this.getClass().getSimpleName()+"_"+id;
    }

    //TODO
}
