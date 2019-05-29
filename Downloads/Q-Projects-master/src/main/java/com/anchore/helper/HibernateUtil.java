package com.anchore.helper;



import java.util.logging.Logger;

import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {
	
 private static final Logger LOGGER=Logger.getLogger(HibernateUtil.class.getName());
 private static SessionFactory sessionFactory = buildSessionFactory();

  private static  SessionFactory buildSessionFactory() {
        /*
         * 
         * Load up the configuration using the hibernate.cfg.xml
         */
    	LOGGER.info("######## inside  buildSessionFactory()######");
    	
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        /*
         * Build the registry using the properties in the configuration
         */
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        /*
         * and finally buils the session factory
         */
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static  SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static  void shutdown() {
        getSessionFactory().close();
    }

   
}


