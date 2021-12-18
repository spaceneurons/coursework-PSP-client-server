package models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ServiceModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty time;
    private SimpleFloatProperty price;

    public ServiceModel(int id, String name, int time, float price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.time = new SimpleIntegerProperty(time);
        this.price = new SimpleFloatProperty(price);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getTime() {
        return time.get();
    }

    public SimpleIntegerProperty timeProperty() {
        return time;
    }

    public void setTime(int time) {
        this.time.set(time);
    }

    public float getPrice() {
        return price.get();
    }

    public SimpleFloatProperty priceProperty() {
        return price;
    }

    public void setPrice(float price) {
        this.price.set(price);
    }
}
