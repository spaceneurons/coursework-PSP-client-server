package utils;

import models.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil{
    private static SessionFactory sessionFactory;
    private static final String configFileName = "hibernate.cfg.xml";

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Appointment.class);
                configuration.addAnnotatedClass(Client.class);
                configuration.addAnnotatedClass(Employee.class);
                configuration.addAnnotatedClass(EmployeeService.class);
                configuration.addAnnotatedClass(Service.class);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().configure(configFileName);
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}
