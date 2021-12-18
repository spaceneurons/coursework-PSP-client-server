package services;

import dao.UserDao;
import interfaces.DAO;
import interfaces.Service;
import models.entities.User;

import java.util.List;

public class UserService implements Service<User> {
    DAO daoService = new UserDao();

    @Override
    public User findEntity(int id) {
        return (User)daoService.findById(id);
    }

    @Override
    public void saveEntity(User entity) {
        daoService.save(entity);
    }

    @Override
    public void deleteEntity(User entity) {
        daoService.delete(entity);
    }

    @Override
    public void updateEntity(User entity) {
        daoService.update(entity);
    }

    @Override
    public List<User> findAllEntities() {
        return daoService.findAll();
    }
}
