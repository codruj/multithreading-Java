package logic;

import model.Server;
import model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t) {
        Integer min = Integer.MAX_VALUE;
        Server minServer = new Server(0);
        for(Server server: servers){
            if(server.getTasks().length < min){
                min = server.getTasks().length;
                minServer = server;
            }
        }
        minServer.addTask(t);
    }
}
