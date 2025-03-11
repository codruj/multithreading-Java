package model;


import logic.SimulationManager;

import java.lang.reflect.AnnotatedType;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    public Integer peak;
    public static Integer timeWaitedTotal = 0;
    public Server(int w) {
        waitingPeriod = new AtomicInteger(w);
        tasks = new LinkedBlockingQueue<Task>();
    }

    public void addTask(Task newTask){
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }
    @Override
    public void run() {
        while (true) {
            synchronized (SimulationManager.lock) {
                try {
                    SimulationManager.lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(SimulationManager.finished) break;
            if (!tasks.isEmpty()) {
                for(Task task: tasks){
                    task.increaseWaitingTime();
                }
                Task currentTask = tasks.peek();
                Integer serviceTime = currentTask.getServiceTime()-1;
                currentTask.setServiceTime(serviceTime);
                if (currentTask.getServiceTime() == 0) {
                    timeWaitedTotal += currentTask.getWaitingTime();
                    tasks.poll();
                }
                waitingPeriod.decrementAndGet();
            }
        }
    }

    public Task[] getTasks() {
        Task[] getter = new Task[tasks.size()];
        int i = 0;
        for(Task task: tasks){
            getter[i] = task;
            i++;
        }
        return getter;
    }

    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public Integer getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

}
