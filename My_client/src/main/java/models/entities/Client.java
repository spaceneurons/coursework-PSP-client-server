package models.entities;

import java.util.HashSet;
import java.util.Set;

public class Client {
    private int id;
    private String name;
    private String surname;
    private String telephone;
    private String email;
    private User user;
    private Set<Appointment> appointments = new HashSet<>();

    public Client(int id) { this.id = id; }

    public Client(int id, String name, String surname, String telephone, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.email = email;
    }

    public Client() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public models.entities.User getUser() {
        return user;
    }
    public void setUser(models.entities.User user) {
        this.user = user;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
