package logic;

import model.Server;
import model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t) {
        Integer min = Integer.MAX_VALUE;
        Server minServer = new Server(0);
        for(Server server: servers){
            if(server.getWaitingPeriod() < min){
                min = server.getWaitingPeriod();
                minServer = server;
            }
        }
        minServer.addTask(t);
    }
}
