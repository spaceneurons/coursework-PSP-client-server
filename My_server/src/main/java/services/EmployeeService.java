package services;

import dao.EmployeeDao;
import interfaces.DAO;
import interfaces.Service;
import models.entities.Employee;

import java.util.List;

public class EmployeeService implements Service<Employee>{
    DAO daoService = new EmployeeDao();
    @Override
    public Employee findEntity(int id) {
        return (Employee)daoService.findById(id) ;
    }

    @Override
    public void saveEntity(Employee entity) {
        daoService.save(entity);
    }

    @Override
    public void deleteEntity(Employee entity) {
            daoService.delete(entity);
    }

    @Override
    public void updateEntity(Employee entity) {
        daoService.update(entity);
    }

    @Override
    public List<Employee> findAllEntities() {
       return daoService.findAll();
    }
}
