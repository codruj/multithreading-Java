package logic;

import GUI.View;
import model.Server;
import model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationManager implements Runnable{
    private View view;
    public Integer timeLimit;
    public Integer maxProcessingTime;
    public Integer minProcessingTime;
    public Integer nrOfServers;
    public Integer nrOfClients;
    public Integer maxArrivalTime;
    public Integer minArrivalTime;

    public static boolean finished = false;
    private Scheduler scheduler;
    private List<Task> generatedTasks = new ArrayList<Task>();
    public static final Object lock = new Object();
    Integer size = 0;
    Integer maxBusy = 0;Integer timePeak = 0; Integer totalService = 0;
    int currentTime = 0;
    public SimulationManager(View view){
        this.view = view;
        this.timeLimit = view.getSimulationTime();
        this.nrOfClients = view.getNrClients();
        this.nrOfServers = view.getNrQueues();
        this.minArrivalTime= view.getMinArrivalTime();
        this.maxArrivalTime = view.getMaxArrivalTime();
        this.minProcessingTime = view.getMinServiceTime();
        this.maxProcessingTime = view.getMaxServiceTime();
        scheduler = new Scheduler(nrOfServers);
        if(view.getSelectedStrategy().equals("Queue Strategy")){
            scheduler.changeStrategy(SelectionPolicy.SHORTEST_QUEUE);
        } else{
             scheduler.changeStrategy(SelectionPolicy.SHORTEST_TIME);
      }
        generatedTasks = generateNRandomTasks();
        for(Task task: generatedTasks){
            totalService += task.getServiceTime();
        }
        size = generatedTasks.size();
    }

    private void buildingOutput(StringBuilder out){
        out.append("\nTime "+ currentTime + "\nWaiting clients: ");
        for(Task task: generatedTasks){
            out.append(" (" + task.getID() + ", " + task.getArrivalTime() + ", " + task.getServiceTime() + ") ");
        }
        out.append("\n");
        int index = 1; int out1 = 0;
        for (Server server : scheduler.getServers()) {
            out.append("Queue " + index + " ");
            if(server.getTasks().length == 0)
                out.append("closed");
            for (Task task : server.getTasks())
                out.append(" (" + task.getID() + ", " + task.getArrivalTime() + ", " + task.getServiceTime() + ") ");
            if (server.getTasks().length == 0) {
                out1++;
                if (out1 == nrOfServers)
                    break;
            }
            out.append("\n");
            index++;
        }
    }
    public void appendAverages(StringBuilder out){
        out.delete(0, out.length());
        out.append("\n\nPeak hour: "+ timePeak);
        out.append("\nAverage waiting time: "+ String.format("%.2f",Server.timeWaitedTotal*1.0/size));
        out.append("\nAverage service time: "+ String.format("%.2f",totalService*1.0/size));
    }
    private void peakHour(Scheduler sch){
        Integer peak = 0;
        for(Server server: sch.getServers()){
            peak += server.getTasks().length;
        }
        if(maxBusy < peak){
            maxBusy = peak;
            timePeak = currentTime;
        }
    }
    private void printTxt(String str) throws IOException {
        FileWriter write = new FileWriter("logofevents.txt", true);
        write.write(str);
        write.close();
    }
    private static void newTxt() throws IOException {
        FileWriter write = new FileWriter("logofevents.txt");
        write.close();
    }
    @Override
    public void run() {
        while (currentTime <= timeLimit) {
            StringBuilder output = new StringBuilder();
            Iterator<Task> iterator = generatedTasks.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(task); iterator.remove();
                }
            }
            synchronized (lock){
                lock.notifyAll();
            }
            peakHour(scheduler); buildingOutput(output);
            if(currentTime == timeLimit)
                appendAverages(output);
            System.out.println(output.toString()); view.printInView(output.toString());
            try { printTxt(output.toString());}
            catch (IOException e) {throw new RuntimeException(e);}
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        finished = true;
        synchronized (lock){
            lock.notifyAll();
        } }


    private ArrayList<Task> generateNRandomTasks(){
        ArrayList<Task> generatedTasks1 = new ArrayList<>();
        Random random = new Random();
        for(int i = 1; i <= nrOfClients; i++){
            Integer processingTime = random.nextInt(minProcessingTime, maxProcessingTime);
            Integer arrivalTime = random.nextInt(minArrivalTime, maxArrivalTime);
            generatedTasks1.add(new Task(i, arrivalTime, processingTime));
        }
        generatedTasks1.sort(Comparator.comparing(Task::getArrivalTime));
        return generatedTasks1;
    }


    public static void start(View view){
        SimulationManager controller = new SimulationManager(view);
        Thread t = new Thread(controller);
        t.start();
    }

    public static void main(String[] args) throws IOException {
        newTxt();
        View view = new View();
    }

}
