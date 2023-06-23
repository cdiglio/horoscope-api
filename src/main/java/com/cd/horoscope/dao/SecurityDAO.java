package com.cd.horoscope.dao;

import com.cd.horoscope.entity.Security;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * DAO class for all SQL communications with the Security table, used to store OpenAi credentials
 */
@Repository
public class SecurityDAO {

    private EntityManager entityManager;

    @Autowired
    public SecurityDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Clears the security table of data if there is any and adds the new specified key
     * @param auth - the new auth key to update the table with
     */
    public void setAuth(String auth){

        //clear table of other auths
        String table = Security.class.getAnnotation(Table.class).name();
        Query query = entityManager.createNativeQuery("TRUNCATE TABLE " + table);
        query.executeUpdate();

        //create new auth and add to table
        Security security = new Security();
        security.setKey(auth);

        entityManager.persist(security);
    }

    /**
     * Retrieve OpenAi credentials
     * @return - the OpenAi key
     * @throws Exception
     */
    public String getAuth() throws Exception {
        //should only ever be 1 so get the 1
        Security security = entityManager.find(Security.class, "1");
        if (security == null){
            throw new Exception("No records found in key table");
        }
        return security.getKey();
    }
}
