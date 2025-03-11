package model;

public class Task {
    private Integer ID;
    private Integer arrivalTime;
    private Integer serviceTime;
    private Integer timeWaited;

    public Task(Integer ID, Integer arrivalTime, Integer serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.timeWaited = 0;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
    public void increaseWaitingTime(){
        this.timeWaited++;
    }
    public Integer getWaitingTime(){
        return this.timeWaited;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }
}
