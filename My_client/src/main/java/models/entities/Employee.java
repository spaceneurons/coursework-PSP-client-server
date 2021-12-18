package models.entities;


import java.util.HashSet;
import java.util.Set;


public class Employee {
    private int id;
    private String name;
    private String surname;
    private Set<EmployeeService> services = new HashSet<>();

    public Employee(int id, String name, String surname, Set<EmployeeService> services) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.services = services;
    }

    public Employee(int id) {
        this.id = id;
    }

    public Employee() {

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

    public Set<EmployeeService> getServices() {
        return services;
    }
    public void setServices(Set<EmployeeService> services) {
        this.services = services;
    }


}

