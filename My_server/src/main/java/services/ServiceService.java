package services;

import dao.ServiceDao;
import interfaces.DAO;
import interfaces.Service;

import java.util.List;

public class ServiceService implements Service<models.entities.Service>{
    DAO daoService = new ServiceDao();

    @Override
    public models.entities.Service findEntity(int id) {
        return (models.entities.Service) daoService.findById(id);
    }

    @Override
    public void saveEntity(models.entities.Service entity) {
        daoService.save(entity);
    }

    @Override
    public void deleteEntity(models.entities.Service entity) {
        daoService.delete(entity);
    }

    @Override
    public void updateEntity(models.entities.Service entity) {
        daoService.update(entity);
    }

    @Override
    public List<models.entities.Service> findAllEntities() {
        return daoService.findAll();
    }
}
