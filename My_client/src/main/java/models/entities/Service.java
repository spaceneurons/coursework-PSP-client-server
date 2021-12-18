package models.entities;

import java.util.HashSet;
import java.util.Set;

public class Service {
    private int id;
    private String name;
    private float price;
    private int time;
    private Set<EmployeeService> employees = new HashSet<>();

    public Service(int id, String name, float price, int time, Set<EmployeeService> employees) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.time = time;
        this.employees = employees;
    }

    public Service() {
        this.id = id;
    }

    public Service(int serviceId) {
        id = serviceId;
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

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

    public Set<EmployeeService> getEmployees() {
        return employees;
    }
    public void setEmployees(Set<EmployeeService> employees) {
        this.employees = employees;
    }
}
