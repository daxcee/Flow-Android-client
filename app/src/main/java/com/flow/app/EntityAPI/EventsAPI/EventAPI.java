package com.flow.app.EntityAPI.EventsAPI;

import android.content.Context;
import com.flow.app.EntityAPI.PersistenceManager;
import com.flow.app.Model.Event;
import java.util.ArrayList;

public class EventAPI implements EventAPIInterface {

    private PersistenceManager persistenceManager;
    private boolean pullFromRemote;

    public EventAPI(Context context){
        this.persistenceManager = new PersistenceManager(context);
        pullFromRemote = false;
    }

    @Override
    public void saveEvent(Event event) {
       persistenceManager.saveEvent(event);
    }

    @Override
    public void saveEvents(ArrayList<Event> events) {
        persistenceManager.saveEvents(events);
    }

    @Override
    public Event getById(String id) {
        return persistenceManager.getById(id);
    }

    @Override
    public ArrayList<Event> getSortedByDate() {
        //TODO
        return persistenceManager.getSortedByDate();
    }

    @Override
    public ArrayList<Event> getAll() {
        if(!isPullingFromRemote() && persistenceManager.getAll().size()== 0) { //pretty ugly, for now it will do FIXME
                setPullingFromRemote(true);
                persistenceManager.getReplicatorInstance().pull();
        }

       return persistenceManager.getAll();
    }

    @Override
    public void deleteEvent(String id) {
        //TODO
        persistenceManager.deleteEvent(id);
    }

    @Override
    public void deleteAll() {
         persistenceManager.deleteAll();
    }

    private void setPullingFromRemote(Boolean b){
        pullFromRemote = b;
    }

    private boolean isPullingFromRemote(){
        return pullFromRemote;
    }
}