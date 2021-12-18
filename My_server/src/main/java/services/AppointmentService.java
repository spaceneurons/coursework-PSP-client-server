package services;

import dao.AppointmentDao;
import interfaces.DAO;
import interfaces.Service;
import models.entities.Appointment;

import java.util.List;

public class AppointmentService implements Service<Appointment>{
    DAO daoService = new AppointmentDao();

    @Override
    public Appointment findEntity(int id) {
        return (Appointment)daoService.findById(id);
    }

    @Override
    public void saveEntity(Appointment entity) {
        daoService.save(entity);
    }

    @Override
    public void deleteEntity(Appointment entity) {
        daoService.delete(entity);
    }

    @Override
    public void updateEntity(Appointment entity) {
        daoService.update(entity);
    }

    @Override
    public List<Appointment> findAllEntities() {
        return daoService.findAll();
    }
}
