package com.cd.horoscope.entity;

import jakarta.persistence.*;

/**
 * Object used for storing OpenAi credentials, matches SQL table layout
 *
 * id - id used by table
 * key - OpenAi key
 */
@Entity
@Table(name="openai")
public class Security {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private String id;

    @Column(name="openkey")
    private String key;

    public Security() {
    }

    public Security(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Security{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
