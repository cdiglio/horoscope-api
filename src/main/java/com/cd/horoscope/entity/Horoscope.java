package com.cd.horoscope.entity;

import jakarta.persistence.*;

import java.sql.Date;

/**
 * Main horoscope object, same as horoscope table layout
 *
 * sign - the specific astrological sign
 * horoscope - the horoscope message itself
 * timestamp - the date the horoscope was generated
 *
 */
@Entity
@Table(name="horoscopes")
public class Horoscope {

    @Id
    @Column(name="sign")
    private String sign;

    @Column(name="horoscope")
    private String horoscope;

    @Column(name="timestamp")
    private Date timestamp;

    public Horoscope() {
    }

    public Horoscope(String sign, String horoscope, Date timestamp) {
        this.sign = sign;
        this.horoscope = horoscope;
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getHoroscope() {
        return horoscope;
    }

    public void setHoroscope(String horoscope) {
        this.horoscope = horoscope;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Horoscope{" +
                "sign='" + sign + '\'' +
                ", horoscope='" + horoscope + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
