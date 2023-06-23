package com.cd.runner;

import com.cd.horoscope.HoroscopeApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoroscopeApplication.class, args);//Horoscope OpenAi REST Service
	}

}
