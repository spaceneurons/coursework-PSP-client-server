package dao;

import interfaces.DAO;
import models.entities.Client;
import models.entities.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class EmployeeDao implements DAO {
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
        Employee employee = session.find(Employee.class, id);
        session.close();
        return employee;
    }

    @Override
    public List findAll() {
        Session session =   HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Employee> employee = (List<Employee>)session.createQuery("From Employee").list();
        session.close();
        return employee;
    }
}
