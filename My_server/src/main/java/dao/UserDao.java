package dao;

import interfaces.DAO;
import models.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;


public class UserDao implements DAO {

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
    public User findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User user = session.find(User.class, id);
        session.close();
        return user;
    }

    @Override
    public List<User> findAll() {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User", User.class).list();
    }
}
