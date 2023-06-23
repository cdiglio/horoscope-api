package com.cd.horoscope;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Configuration to connect to SQL database
 */
@Configuration
public class HoroscopeDataSource {

    @Value("${horoscope.user}")
    private String HOROSCOPE_USER;
    @Value("${horoscope.pass}")
    private String HOROSCOPE_PASS;
    @Value("${spring.datasource.url}")
    private String DATABASE_URL;

    @Bean
    public DataSource getDataSource(){
        DataSourceBuilder dsb = DataSourceBuilder.create();
        dsb.url(DATABASE_URL);
        dsb.username(HOROSCOPE_USER);
        dsb.password(HOROSCOPE_PASS);
        return dsb.build();
    }
}
