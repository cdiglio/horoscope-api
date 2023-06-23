package com.cd.horoscope.service;

import com.cd.horoscope.dao.HoroscopeDAO;
import com.cd.horoscope.dao.SecurityDAO;
import com.cd.horoscope.entity.Horoscope;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class used to consolidate DAOs
 */
@Service
public class HoroscopeService {

    private SecurityDAO securityDAO;
    private HoroscopeDAO horoscopeDAO;

    @Autowired
    public HoroscopeService(SecurityDAO securityDAO, HoroscopeDAO horoscopeDAO) {
        this.securityDAO = securityDAO;
        this.horoscopeDAO = horoscopeDAO;
    }

    @Transactional
    public void setAuth(String auth){
        securityDAO.setAuth(auth);
    }

    public String getAuth() throws Exception {
        return securityDAO.getAuth();
    }

    public Horoscope getHoroscope(String sign) {
        return horoscopeDAO.getHoroscope(sign);
    }

    public List<Horoscope> getHoroscopes() {
        return horoscopeDAO.getHoroscopes();
    }

    @Transactional
    public void setHoroscope(Horoscope horoscope) {
        horoscopeDAO.setHoroscope(horoscope);
    }

    @Transactional
    public void deleteHoroscopes() {
        horoscopeDAO.deleteHoroscope();
    }
}