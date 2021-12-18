package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AppointmentModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty startTime;
    private SimpleStringProperty endTime;
    private SimpleStringProperty date;
    private SimpleStringProperty service;
    private SimpleStringProperty client;

    public AppointmentModel(int id, String startTime, String endTime, String date, String service, String client) {
        this.id = new SimpleIntegerProperty(id);
        this.startTime = new SimpleStringProperty(startTime);
        this.endTime = new SimpleStringProperty(endTime);
        this.date = new SimpleStringProperty(date);
        this.service = new SimpleStringProperty(service);
        this.client = new SimpleStringProperty(client);
    }

    public int getId() {
        return id.getValue().intValue();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getStartTime() {
        return startTime.get();
    }

    public SimpleStringProperty startTimeProperty() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    public String getEndTime() {
        return endTime.get();
    }

    public SimpleStringProperty endTimeProperty() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime.set(endTime);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getService() {
        return service.get();
    }

    public SimpleStringProperty serviceProperty() {
        return service;
    }

    public void setService(String service) {
        this.service.set(service);
    }

    public String getClient() {
        return client.get();
    }

    public SimpleStringProperty clientProperty() {
        return client;
    }

    public void setClient(String client) {
        this.client.set(client);
    }
}
