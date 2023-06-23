package com.cd.horoscope.dao;

import com.cd.horoscope.entity.Horoscope;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO class for all SQL communication related to the main horoscope service
 */
@Repository
public class HoroscopeDAO {

    private EntityManager entityManager;

    @Autowired
    public HoroscopeDAO(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

    /**
     * Return a horoscope object from the main SQL table
     * @param sign - the actual sign to retrieve
     * @return - the specific horoscope object
     */
    public Horoscope getHoroscope(String sign) {
        Horoscope horoscope = entityManager.find(Horoscope.class, sign);

        if (horoscope == null){
            throw new RuntimeException("Horoscope for " + sign + " returned null");
        }   //this should never happen, so if it does there's an issue; The SQL table should always have 1 row for every sign

        return horoscope;
    }

    /**
     * Used to get all the horoscopes
     * @return List object of all horoscope objects
     */
    public List<Horoscope> getHoroscopes(){
        TypedQuery<Horoscope> query = entityManager.createQuery("from Horoscope", Horoscope.class);

        return query.getResultList();
    }

    /**
     * Used to update the horoscope records in the table
     * @param horoscope - the horoscope object to update
     */
    public void setHoroscope(Horoscope horoscope) {
        entityManager.merge(horoscope);
    }

    /**
     * Sets all horoscope messages to ''
     */
    public void deleteHoroscope() {
        String table = Horoscope.class.getAnnotation(Table.class).name();
        Query query = entityManager.createNativeQuery("UPDATE " + table + " SET horoscope=''");
        query.executeUpdate();
    }
}
