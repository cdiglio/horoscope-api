package com.cd.horoscope.rest;

import com.cd.horoscope.ai.OpenAiController;
import com.cd.horoscope.entity.Horoscope;
import com.cd.horoscope.service.HoroscopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * REST service endpoints for horoscope functionality
 *
 * example:
 *  /horoscope/daily/virgo
 *
 */
@RestController
@RequestMapping("/horoscope")
public class HoroscopeRestController {

    private OpenAiController aiService;
    private HoroscopeService service;

    private static final String PROMPT = "write me a daily horoscope for a ";

    private static List<String> signs = new ArrayList<String>(){{
        add("aries");
        add("taurus");
        add("gemini");
        add("cancer");
        add("leo");
        add("virgo");
        add("libra");
        add("scorpius");
        add("sagittarius");
        add("capricorn");
        add("aquarius");
        add("pisces");
    }};

    @Autowired
    public HoroscopeRestController(OpenAiController aiService, HoroscopeService service) {
        this.aiService = aiService;
        this.service = service;
    }

    /**
     * GET used to return inputted sign's horoscope from table. If horoscope is out of date it is regenerated
     * @param sign - path variable of horoscope sign
     * @return JSON response containing horoscope data including the sign, horoscope, and timestamp
     */
    @GetMapping("/daily/{sign}")
    public Horoscope getHoroscope (@PathVariable String sign){
        sign = sign.toLowerCase();

        if (!signs.contains(sign)){ //make sure inputted sign is valid
            throw new RuntimeException("Sign " + sign + " is not valid. Please enter one of the 12 astrology signs");
        }

        Horoscope horoscope = service.getHoroscope(sign);
        //validate the horoscope is for today, if not generate a new one
        horoscope = validateHoroscope(horoscope);

        return horoscope;
    }

    /**
     * GET used to return all horoscopes. Any out of date ones will be regenerated
     * @return JSON response of all horoscopes
     */
    @GetMapping("/daily")
    public List<Horoscope> getHoroscopes (){
        List<Horoscope> horoscopes = service.getHoroscopes();

        //iterate through and regenerate any out of date ones
        for (Horoscope horoscope : horoscopes){
            horoscope = validateHoroscope(horoscope);
        }

        return horoscopes;
    }

    /**
     * DELETE used to clear all stored horoscopes. Table integrity is maintained, only horoscope messages are erased
     * @return success message
     */
    @DeleteMapping("/daily")
    public String deleteHoroscopes (){
        service.deleteHoroscopes();

        return "Success";
    }

    /**
     * Validate if horoscope is out of date by comparing object's stored timestamp to the current one
     * @param horoscope - horoscope object to validate
     * @return up-to-date horoscope object
     */
    private Horoscope validateHoroscope(Horoscope horoscope){
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        //if day is less than current day or missing horoscope, generate new
        if (horoscope.getTimestamp().before(date) || horoscope.getHoroscope().isEmpty()){
            horoscope = generateDailyHoroscope(horoscope, date);
        }
        return horoscope;
    }

    /**
     * Used to generate new horoscope from Open Ai and update the table with the new data
     * @param horoscope - horoscope object to update
     * @param date - current date to update timestamp with
     * @return up-to-date horoscope object
     */
    private Horoscope generateDailyHoroscope(Horoscope horoscope, Date date){
        try {
            //generate new horoscope from OpenAi
            String newHoroscope = aiService.generateResponse(PROMPT + horoscope.getSign());

            //Some of the generations have an Ai comment at the beginning, this is used to find the first mention of the sign, usually at the beginning of the horoscope
            int index = newHoroscope.indexOf(horoscope.getSign().substring(0,1).toUpperCase() + horoscope.getSign().substring(1));
            if (index != -1) {
                newHoroscope = newHoroscope.substring(index, newHoroscope.length());
            }

            //update horoscope and time
            horoscope.setHoroscope(newHoroscope);
            horoscope.setTimestamp(date);

            //update database with new horoscope
            service.setHoroscope(horoscope);

            return horoscope;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
