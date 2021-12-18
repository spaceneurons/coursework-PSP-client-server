package dao;

import interfaces.DAO;
import models.entities.Appointment;
import models.entities.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class AppointmentDao implements DAO {
    @Override
    public void save(Object obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Object obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(obj);
        tx1.commit();
        //session.close();
    }

    @Override
    public void delete(Object obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(obj);
        tx1.commit();
        //session.close();
    }

    @Override
    public Object findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Appointment appointment = session.find(Appointment.class, id);
        session.close();
        return appointment;
    }

    @Override
    public List findAll() {
        Session session =   HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Object> appointments = (List<Object>)session.createQuery("From Appointment").list();
        session.close();
        return appointments;
    }
}
