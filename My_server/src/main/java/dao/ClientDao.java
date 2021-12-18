package dao;

import interfaces.DAO;
import models.entities.Client;
import models.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class ClientDao implements DAO {
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
        session.update(obj);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Object obj) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(obj);
        tx1.commit();
    }

    @Override
    public Object findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Client client = session.find(Client.class, id);
        session.close();
        return client;
    }

    @Override
    public List findAll() {
        Session session =   HibernateSessionFactoryUtil.getSessionFactory().openSession();
        return (List<Object>)session.createQuery("From Client").list();
    }
}
