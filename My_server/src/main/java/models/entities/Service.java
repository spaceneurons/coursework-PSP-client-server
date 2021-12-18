package models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service", schema = "public")
public class Service {
    private int id;
    private String name;
    private float price;
    private int time;
    private transient Set<EmployeeService> employees = new HashSet<>();

    public Service(int id, String name, float price, int time, Set<EmployeeService> employees) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.time = time;
        this.employees = employees;
    }

    public Service() {
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

    @Column(name = "price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @Column(name = "time", length = 45)
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "service", cascade = {CascadeType.ALL})
    public Set<EmployeeService> getEmployees() {
        return employees;
    }
    public void setEmployees(Set<EmployeeService> employees) {
        this.employees = employees;
    }
}
