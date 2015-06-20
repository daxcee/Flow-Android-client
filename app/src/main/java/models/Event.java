package models;

import java.util.Date;

//https://github.com/srmds/Flow-API/blob/master/models/event.js
public class Event {

    private String id;
    private Date date;
    private String title;
    private String venue;
    private String city;
    private String country;

    public Event(){}

    public Event(String eventId){}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date createFormattedDate(String date){
        return null;
    }

    public String getFormattedDate(Date date) {
        return "";
    }

}
