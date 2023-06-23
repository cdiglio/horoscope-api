package com.cd.horoscope.ai;

import com.cd.horoscope.ai.entity.Choices;
import com.cd.horoscope.ai.entity.Message;
import com.cd.horoscope.ai.entity.Messages;
import com.cd.horoscope.ai.entity.Response;
import com.cd.horoscope.service.HoroscopeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to handle the communication and response to the OpenAi chat completions REST API
 */
@Controller
public class OpenAiController {

    private static final String OPEN_AI_URL = "https://api.openai.com/v1/chat/completions";
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private HoroscopeService service;

    //Message constants; view OpenAi documentation for exact arg meanings
    private static final String MODEL = "gpt-3.5-turbo"; //The actual model we want to use, can be changed to certain models
    private static final String ROLE = "user";
    private static final int MAX_TOKENS = 2048;
    private static final double TEMP = 0.2;
    private static final double TOP_P = 1;
    private static final int N = 1;
    private static final boolean STREAM = false;

    //Response constants
    private static final String CONTENT = "content";

    @Autowired
    public OpenAiController(HoroscopeService service){
        this.service = service;
    }

    @PostConstruct
    private void init(){
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            headers.setBasicAuth(service.getAuth());//get credentials for communication to OpenAi
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Used to call the OpenAi chat completions REST service
     * @param prompt - the message to ask OpenAi
     * @return - the response from OpenAi
     * @throws Exception
     */
    public String generateResponse(String prompt) throws Exception {
        Message message = new Message(MODEL, MAX_TOKENS, TEMP, TOP_P, N, STREAM);//The main message object containing all the config data

        Messages message1 = new Messages(ROLE, prompt);//The actual message object to ask OpenAi
        List<Messages> messages = new ArrayList<Messages>();//Payload can handle multiple questions be we only want to ask 1
        messages.add(message1);
        message.setMessages(messages);

        try {

            Response response = callOpenAi(message);//The actual call method to open ai
            List<Choices> choices = response.getChoices();//Get us the response

            if (choices.size() > 1){
                throw new Exception("More than one response from OpenAi");//We only want to send one message so we only want one response
            }

            return choices.get(0).getMessage().get(CONTENT);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * The actual HTTP call to OpenAi's REST service
     * @param message - our payload object
     * @return - response object from the call
     * @throws Exception
     */
    private Response callOpenAi(Message message) throws Exception {
        HttpEntity<String> request =
                new HttpEntity<String>(convertJson(message), headers);

        try {
            return restTemplate.postForObject(OPEN_AI_URL, request, Response.class);//The specific endpoint is expecting a POST
        } catch (HttpClientErrorException | HttpServerErrorException httpE){
            if (HttpStatus.TOO_MANY_REQUESTS.equals(httpE.getStatusCode())){//The level of access the typical user has to OpenAi is 3 calls every minute, with a 20 sec cooldown before another request is allowed
                Thread.sleep(20000);//rest for 20 sec to allow another request
                return callOpenAi(message);//retry
            }
            else{
                throw new Exception(httpE.getMessage());
            }
        }
    }

    /**
     * Take our payload object and convert it to JSON string
     * @param message - payload object to convert
     * @return - JSON string
     * @throws JsonProcessingException
     */
    private String convertJson(Message message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        return objectMapper.writeValueAsString(message);
    }





}
