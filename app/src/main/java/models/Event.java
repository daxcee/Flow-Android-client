package models;

import android.net.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

//https://github.com/srmds/Flow-API/blob/master/models/event.js
public class Event {

    private String id;
    private String date;
    private String title;
    private String venue;
    private String city;
    private String country;

    public Event(){}

    public Event(String eventId, String title, String city){
        setId(eventId);
        setTitle(title);
        setCity(city);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDateStr() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        SimpleDateFormat targetFormat = new SimpleDateFormat("E dd MMM yyyy", Locale.US); //Woe 15 Mei 2015
        String formattedDate = null;
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(getDateStr());
            formattedDate = targetFormat.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        System.out.println(formattedDate);

        return convertedDate;
    }

}
