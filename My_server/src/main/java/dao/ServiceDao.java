package dao;

import interfaces.DAO;
import models.entities.Employee;
import models.entities.Service;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class ServiceDao implements DAO {
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
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Object obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public Object findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Service service = session.find(Service.class, id);
        session.close();
        return service;
    }

    @Override
    public List findAll() {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Service", Service.class).list();
    }
}
