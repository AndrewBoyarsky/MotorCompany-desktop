package com.boyarskycompany.src.controllers.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;


/**
 * Created by zandr on 26.09.2016.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static void connect() throws IOException {
        Configuration configuration = new Configuration();
        System.out.println(" ");
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    public static void main(String[] args) throws IOException {
        connect();
        System.out.println();
        SessionFactory s = getSessionFactory();
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
