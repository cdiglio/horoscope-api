package com.cd.horoscope.ai.entity;

import java.util.Map;

/**
 * Sub-class for Response to store actual message response from OpenAi endpoint
 */
public class Choices{

    private Map<String, String> message;
    private String finish_reason;
    private int index;

    public Choices() {
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Choices{" +
                "message=" + message +
                ", finish_reason='" + finish_reason + '\'' +
                ", index=" + index +
                '}';
    }
}
