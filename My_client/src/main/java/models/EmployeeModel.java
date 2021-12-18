package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty surname;
    private SimpleStringProperty services;

    public EmployeeModel(int id, String name, String surname, String services) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.services = new SimpleStringProperty(services);
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

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getServices() {
        return services.get();
    }

    public SimpleStringProperty servicesProperty() {
        return services;
    }

    public void setServices(String services) {
        this.services.set(services);
    }
}
