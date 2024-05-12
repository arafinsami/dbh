package com.dbh.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaConfig {

    public static final String PERSISTANCE_UNIT = "persistence-unit";

    private static final EntityManagerFactory factory;

    static {
        factory = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT);
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
