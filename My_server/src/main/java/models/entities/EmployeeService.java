package models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employeeservice", schema = "public")
public class EmployeeService {
    private int id;
    private Employee employee;
    private Service service;
    private transient Set<Appointment> appointments = new HashSet<>();
    public EmployeeService(int id, Employee employee, Service service) {
        this.id = id;
        this.employee = employee;
        this.service = service;
    }

    public EmployeeService() {
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = false)
    public Service getService() {
        return service;
    }
    public void setService(Service service) {
        this.service = service;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "service", cascade = {CascadeType.ALL})
    public Set<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
