package models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee", schema = "public")
public class Employee {
    private int id;
    private String name;
    private String surname;
    private transient Set<EmployeeService> services = new HashSet<>();

    public Employee(int id, String name, String surname, Set<EmployeeService> services) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.services = services;
    }

    public Employee() {
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee", cascade = {CascadeType.ALL})
    public Set<EmployeeService> getServices() {
        return services;
    }
    public void setServices(Set<EmployeeService> services) {
        this.services = services;
    }
}

