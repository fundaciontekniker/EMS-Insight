/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.tekniker.eefrmwrk.cep.entity;

/**
 *
 * @author ikerba
 */
public class NotificationData {
    
    private double message;
    private String timestamp;
    private String topic;
    private String type;
    private String sensor;
    private String provider;
    private String location;
    private String sender;
    private String alert;
    
    public NotificationData(){
    }
    
    public NotificationData (double message, String timestamp, String topic, 
                                String type, String sensor, String provider, 
                                String location, String sender, String alert){
        super();
        this.message = message;
        this.timestamp = timestamp;
        this.topic = topic;
        this.type = type;
        this.sensor = sensor;
        this.provider = provider;
        this.location = location;
        this.sender = sender;
        this.alert = alert;
    }
    
    public void setSensor(String sensor) {
        this.sensor = sensor; //To change body of generated methods, choose Tools | Templates.
    }
 
    public String getSensor() {
        return this.sensor;
    }
    
    public void setMessage(String message) {
    	this.message = Double.parseDouble(message);        
    }
 
    public double getMessage() {
        return this.message;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @param topic the topic to set
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the alert
     */
    public String getAlert() {
        return alert;
    }

    /**
     * @param alert the alert to set
     */
    public void setAlert(String alert) {
        this.alert = alert;
    }
    
}
