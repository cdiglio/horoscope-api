package com.cd.horoscope.rest;

import com.cd.horoscope.ai.OpenAiController;
import com.cd.horoscope.service.HoroscopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * REST service endpoints for open ai related functionality
 */
@RestController
@RequestMapping("/openai")
public class AiRestController {

    private static final String USER = "Bearer";
    private HoroscopeService service;
    private OpenAiController aiController;

    @Autowired
    public AiRestController(HoroscopeService service, OpenAiController aiController) {
        this.service = service;
        this.aiController = aiController;
    }

    /**
     * PUT Used to update the open ai credentials
     * @param key - open ai key in a JSON payload
     *            {
     *              "key" : "*key to update table with*"
     *            }
     */
    @PutMapping("/updateKey")
    public void updateAuth (@RequestBody Auth key) {
        try {

            service.setAuth(HttpHeaders.encodeBasicAuth(USER, key.getKey(), null));

        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * POST used to communicate with OpenAi through their REST service
     * @param message - message to send to OpenAi in a JSON message
     *                {
     *                  "message" : "*message to send to open ai*"
     *                }
     * @return JSON message containing open ai returned message
     */
    @PostMapping("/testMessage")
    public TestAi testOpenAi(@RequestBody TestAi message) {
        try {
            String response = aiController.generateResponse(message.getMessage());

            TestAi testResponse = new TestAi();
            testResponse.setMessage(response);

            return testResponse;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Sub classes for JSON payloads used above
     */
    private static class Auth{
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
    private static class TestAi {

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}