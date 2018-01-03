package com.boyarskycompany.mcis.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;


/**
 * Created by zandr on 26.09.2016.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static void connect(Properties dbProperties) throws HibernateException, InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
        Configuration configuration = new Configuration();
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
        configuration.configure("configuration/hibernate.cfg.xml");
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
        configuration.setProperties(dbProperties);
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
        sessionFactory = configuration.buildSessionFactory();
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutDown() {
        sessionFactory.close();
    }

}
