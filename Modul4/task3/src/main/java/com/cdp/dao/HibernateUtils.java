package com.cdp.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by Paladin on 22.06.2014.
 */
public class HibernateUtils {

    public static EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("task3").createEntityManager();
    }
}
