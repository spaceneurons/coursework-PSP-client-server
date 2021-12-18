package services;

import dao.EmployeeDao;
import dao.EmployeeServiceDao;
import interfaces.DAO;
import interfaces.Service;

import java.util.List;

public class EmployeeServService implements Service<models.entities.EmployeeService>{
    DAO daoService = new EmployeeServiceDao();

    @Override
    public models.entities.EmployeeService findEntity(int id) {
        return (models.entities.EmployeeService) daoService.findById(id);
    }

    @Override
    public void saveEntity(models.entities.EmployeeService entity) {
        daoService.save(entity);
    }

    @Override
    public void deleteEntity(models.entities.EmployeeService entity) {
        daoService.delete(entity);
    }

    @Override
    public void updateEntity(models.entities.EmployeeService entity) {
        daoService.update(entity);
    }

    @Override
    public List<models.entities.EmployeeService> findAllEntities() {
        return daoService.findAll();
    }
}
