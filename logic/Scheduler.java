package logic;

import model.Server;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private ArrayList<Server> servers;
    private Integer maxNrOfServers;
    private Integer maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(Integer maxNrOfServers) {
        servers = new ArrayList<>();
        for(int i = 0; i < maxNrOfServers; i++){
            Server server = new Server(0);
            servers.add(server);
            Thread t1 = new Thread(server);
            t1.start();
        }
    }
    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }
        else{
            strategy = new ConcreteStrategyQueue();
        }
    }
    public void dispatchTask(Task t){
        strategy.addTask(servers, t);
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public void setServers(ArrayList<Server> servers) {
        this.servers = servers;
    }

    public Integer getMaxNrOfServers() {
        return maxNrOfServers;
    }

    public void setMaxNrOfServers(Integer maxNrOfServers) {
        this.maxNrOfServers = maxNrOfServers;
    }

    public Integer getMaxTasksPerServer() {
        return maxTasksPerServer;
    }

    public void setMaxTasksPerServer(Integer maxTasksPerServer) {
        this.maxTasksPerServer = maxTasksPerServer;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
