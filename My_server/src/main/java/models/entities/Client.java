package models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client", schema = "public")
public class Client {
    private int id;
    private String name;
    private String surname;
    private String telephone;
    private String email;
    private User user;
    private transient Set<Appointment> appointments = new HashSet<>();

    public Client() {}

    public Client(int id, String name, String surname, String telephone, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", length = 45)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname", length = 45)
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "telephone", length = 45)
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "email", length = 45)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    public models.entities.User getUser() {
        return user;
    }
    public void setUser(models.entities.User user) {
        this.user = user;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = {CascadeType.ALL})
    public Set<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
