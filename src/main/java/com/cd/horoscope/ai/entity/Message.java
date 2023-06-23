package com.cd.horoscope.ai.entity;

import java.util.List;

/**
 * Message JSON object for payload to OpenAi endpoint call
 */
public class Message {

    private String model;
    private List<Messages> messages;
    private int max_tokens;
    private double temperature;
    private double top_p;
    private int n;
    private boolean stream;

    public Message(String model, int max_tokens, double temperature, double top_p, int n, boolean stream) {
        this.model = model;
        this.max_tokens = max_tokens;
        this.temperature = temperature;
        this.top_p = top_p;
        this.n = n;
        this.stream = stream;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public double getTop_p() {
        return top_p;
    }

    public void setTop_p(int top_p) {
        this.top_p = top_p;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        return "Message{" +
                "model='" + model + '\'' +
                ", messages=" + messages +
                ", max_tokens=" + max_tokens +
                ", temperature=" + temperature +
                ", top_p=" + top_p +
                ", n=" + n +
                ", stream=" + stream +
                '}';
    }
}
