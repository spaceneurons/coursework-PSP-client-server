package models.entities;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
    private int id;
    private Time starTime;
    private Time endTime;
    private Date date;
    private Client client;
    private EmployeeService service;

    public Appointment(int id) { this.id = id; }

    public Appointment(int id, Time starTime, Time endTime, Date date, Client client, EmployeeService service) {
        this.id = id;
        this.starTime = starTime;
        this.endTime = endTime;
        this.date = date;
        this.client = client;
        this.service = service;
    }

    public Appointment() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Time getStarTime() {
        return starTime;
    }

    public void setStarTime(Time starTime) {
        this.starTime = starTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public models.entities.Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public models.entities.EmployeeService getService() {
        return service;
    }
    public void setService(EmployeeService service) {
        this.service = service;
    }
}
