package program.classes;

import program.interfaces.IDHandler;

public class Link extends IDHandler {
    final Connection connection;
    Lane from;
    Lane to;
    public Link(Connection connection){
        this.connection = connection;
        //TODO
        generateID();
    }
    void generateID(){
        ElementID = connection.getID()+"Link"+atomicInteger.incrementAndGet();
    }

    //TODO
}
