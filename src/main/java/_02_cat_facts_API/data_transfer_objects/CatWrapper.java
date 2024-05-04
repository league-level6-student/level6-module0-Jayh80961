package com.example;

import java.util.Properties;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CatWrapper {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("properties")
    @Expose
    private Properties properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    // Add these getter and setter for the "fact" property
    public String getFact() {
        return properties != null ? properties.getFact() : null;
    }

    public void setFact(String fact) {
        if (properties == null) {
            properties = new Properties();
        }
        properties.setFact(fact);
    }
}
