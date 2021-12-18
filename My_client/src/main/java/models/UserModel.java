package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty login;
    private SimpleStringProperty password;
    private SimpleStringProperty ids;

    public UserModel(int id, String login, String password, String ids) {
        this.id = new SimpleIntegerProperty(id);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.ids = new SimpleStringProperty(ids);
    }

    public UserModel() {

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

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getIds() {
        return ids.get();
    }

    public SimpleStringProperty idsProperty() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids.set(ids);
    }
}
