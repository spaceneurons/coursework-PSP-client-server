package services;

import dao.ClientDao;
import interfaces.DAO;
import interfaces.Service;
import models.entities.Client;

import java.util.List;

public class ClientService implements Service<Client> {
    DAO daoService = new ClientDao();

    @Override
    public Client findEntity(int id) { return (Client)daoService.findById(id); }

    @Override
    public void saveEntity(Client entity) {
        daoService.save(entity);
    }

    @Override
    public void deleteEntity(Client entity) {
        daoService.delete(entity);
    }

    @Override
    public void updateEntity(Client entity) {
        daoService.update(entity);
    }

    @Override
    public List<Client> findAllEntities() {
        return daoService.findAll();
    }
}
