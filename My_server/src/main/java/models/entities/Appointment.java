package models.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "appointment", schema = "public")
public class Appointment {
    private int id;
    private java.sql.Time starTime;
    private java.sql.Time endTime;
    private Date date;
    private Client client;
    private EmployeeService service;

    public Appointment() {}

    public Appointment(int id, Time starTime, Time endTime, Date date, Client client, EmployeeService service) {
        this.id = id;
        this.starTime = starTime;
        this.endTime = endTime;
        this.date = date;
        this.client = client;
        this.service = service;
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
    @Column(name = "starttime", length = 45)
    public Time getStarTime() {
        return starTime;
    }
    public void setStarTime(Time starTime) {
        this.starTime = starTime;
    }

    @Column(name = "endtime", length = 45)
    public Time getEndTime() {
        return endTime;
    }
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Column(name = "dat", length = 45)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    public models.entities.Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empl_serv_id", nullable = false)
    public models.entities.EmployeeService getService() {
        return service;
    }
    public void setService(EmployeeService service) {
        this.service = service;
    }
}
