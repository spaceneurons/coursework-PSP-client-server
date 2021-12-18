package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClientModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty FIO;
    private SimpleStringProperty email;
    private SimpleStringProperty phone;

    public ClientModel(int id, String FIO, String email, String phone) {
        this.id = new SimpleIntegerProperty(id);
        this.FIO = new SimpleStringProperty(FIO);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
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

    public String getFIO() {
        return FIO.get();
    }

    public SimpleStringProperty FIOProperty() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO.set(FIO);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}
