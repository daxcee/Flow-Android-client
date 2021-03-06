package com.flow.app.EntityAPI.EventsAPI;

import com.flow.app.Model.Event;

import java.util.ArrayList;

public interface EventAPIInterface  {

    void saveEvent(Event event);

    void saveEvents(ArrayList<Event> events);

    Event getById(String id);

    ArrayList<Event> getSortedByDate();

    ArrayList<Event> getAll();

    void deleteEvent(String id);

    void deleteAll();
}
