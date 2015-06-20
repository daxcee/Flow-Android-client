package EntityAPI.EventsAPI;

import EntityAPI.PersistenceManager;
import Utils.Constants;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import models.Event;
import java.util.*;
import static Utils.Constants.*;
import static Utils.Constants.EventEntityAttributes.*;

public class EventAPI implements EventAPIInterface{

    private PersistenceManager persistenceManager;

    public EventAPI(Context context){
        this.persistenceManager = new PersistenceManager(context);
    }

    @Override
    public void saveEvent(Event event) {
        SQLiteDatabase db = persistenceManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(id.toString(), event.getId());
        values.put(title.toString(), event.getTitle());
        values.put(venue.toString(), event.getVenue());
        values.put(city.toString(), event.getCity());
        values.put(country.toString(), event.getCountry());
        values.put(date.toString(), event.getDateStr());

        db.insert(EventEntityName, null, values);
        db.close();
    }

    @Override
    public Event getById(String idf) {
        SQLiteDatabase db = persistenceManager.getReadableDatabase();

        Cursor cursor = db.query(EventEntityName, new String[] {id.toString(),
                        title.toString(), city.toString() }, id.toString() + "=?",
                new String[] { idf }, null, null, null, null);

        Event event = null;

        if (cursor != null){
            cursor.moveToFirst();
            event = new Event(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2));

            cursor.close();
        }

        return event;
    }

    @Override
    public ArrayList<Event> getSortedByDate() {
        //TODO
        return null;
    }

    @Override
    public ArrayList<Event> getAll() {
        ArrayList<Event> eventsList = new ArrayList<Event>();

        String selectQuery = "SELECT  * FROM " + Constants.EventEntityName;
        SQLiteDatabase db = persistenceManager.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();

                event.setId(cursor.getString(0));
                event.setTitle(cursor.getString(1));
                event.setCity(cursor.getString(2));

                eventsList.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return eventsList;
    }

    @Override
    public void deleteEvent(String id) {
        //TODO
    }

    @Override
    public void deleteAll() {
        //TODO
    }
}