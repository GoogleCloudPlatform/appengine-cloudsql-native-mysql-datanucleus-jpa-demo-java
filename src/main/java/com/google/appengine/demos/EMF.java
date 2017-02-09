package com.google.appengine.demos;

import com.google.appengine.api.utils.SystemProperty;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * from:
 * https://cloud.google.com/appengine/docs/java/datastore/jpa/overview
 * see "Getting an EntityManager Instance"
 */
public final class EMF {

    private static final EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("Demo", getEntityManagerProperties());

    public EMF(){}

    public static EntityManagerFactory get() {
        return emfInstance;
    }

    private static Map<String, String> getEntityManagerProperties()
    {
        Map<String, String> properties = new HashMap();
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.GoogleDriver");
            properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url"));
        } else {
            properties.put("javax.persistence.jdbc.driver","com.mysql.jdbc.Driver");
            properties.put("javax.persistence.jdbc.url",System.getProperty("cloudsql.url.dev"));
        }
        return properties;
    }

}
