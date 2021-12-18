package dao;

import interfaces.DAO;
import models.entities.EmployeeService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class EmployeeServiceDao implements DAO {
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
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(EmployeeService.class, id);
    }

    @Override
    public List findAll() {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From EmployeeService", EmployeeService.class).list();
    }
}
