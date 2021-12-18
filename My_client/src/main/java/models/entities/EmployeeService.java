package models.entities;

import java.util.HashSet;
import java.util.Set;


public class EmployeeService {
    private int id;
    private Employee employee;
    private Service service;
    private Set<Appointment> appointments = new HashSet<>();
    public EmployeeService(int id, Employee employee, Service service) {
        this.id = id;
        this.employee = employee;
        this.service = service;
    }

    public EmployeeService() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Service getService() {
        return service;
    }
    public void setService(Service service) {
        this.service = service;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
