package com.boyarskycompany.src.controllers.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * Created by zandr on 26.09.2016.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    static {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void shutDown() {
        sessionFactory.close();
    }

    private HibernateUtil() {
    }

}
