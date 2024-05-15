package com.dbh.utils;

import com.dbh.entity.Employee;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HibernateConfig {

    private static SessionFactory sessionFactory = null;

    static {
        try {
            loadSessionFactory();
        } catch (Exception e) {
            log.error("Exception while initializing hibernate util.. ");
        }
    }

    public static void loadSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("/hibernate.cfg.xml");
        configuration.addAnnotatedClass(Employee.class);
        ServiceRegistry srvcReg = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        sessionFactory = configuration.buildSessionFactory(srvcReg);
    }

    public static Session getSession() throws HibernateException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
        if (session == null) {
            log.error("session is discovered null");
        }
        return session;
    }
}