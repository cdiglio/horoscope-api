package com.cd.horoscope.ai.entity;

import java.util.List;
import java.util.Map;

/**
 * Response JSON object from OpenAi endpoint call
 */
public class Response {

    private String id;
    private String object;
    private int created;
    private String model;
    private Map<String, Integer> usage;
    private List<Choices> choices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Map<String, Integer> getUsage() {
        return usage;
    }

    public void setUsage(Map<String, Integer> usage) {
        this.usage = usage;
    }

    public List<Choices> getChoices() {
        return choices;
    }

    public void setChoices(List<Choices> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", model='" + model + '\'' +
                ", usage=" + usage +
                ", choices=" + choices +
                '}';
    }
}
