package EntityAPI.EventsAPI;


import models.Event;

import java.util.ArrayList;

public interface EventAPIInterface {

    void saveEvent(Event event);

    Event getById(String id);

    ArrayList<Event> getSortedByDate();

    ArrayList<Event> getAll();

    void deleteEvent(String id);

    void deleteAll();
}
