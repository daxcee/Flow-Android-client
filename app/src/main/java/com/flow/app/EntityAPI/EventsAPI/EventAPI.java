package com.flow.app.EntityAPI.EventsAPI;

import com.flow.app.EntityAPI.PersistenceManager;
import com.flow.app.Utils.Constants;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.flow.app.Model.Event;
import java.util.ArrayList;
import static com.flow.app.Utils.Constants.EventEntityAttributes.*;
import static com.flow.app.Utils.Constants.EventEntityName;


public class EventAPI implements EventAPIInterface {

    private PersistenceManager persistenceManager;
    private Context context;

    public EventAPI(Context context){
        this.context = context;
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
    public void saveEvents(ArrayList<Event> events) {
        SQLiteDatabase db = persistenceManager.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(Event event:events) {

            values.put(id.toString(), event.getId());
            values.put(title.toString(), event.getTitle());
            values.put(venue.toString(), event.getVenue());
            values.put(city.toString(), event.getCity());
            values.put(country.toString(), event.getCountry());
            values.put(date.toString(), event.getDateStr());

            db.insert(EventEntityName, null, values);

            Log.e("Saved item: ", event.getTitle());


        }
        Intent intent = new Intent();
        intent.setAction(Constants.IntentMessages.replication_finished.toString());
        context.sendBroadcast(intent);

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
                event.setCity(cursor.getString(3));
                event.setVenue(cursor.getString(4));
                event.setDate(cursor.getString(2));

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
        SQLiteDatabase db = persistenceManager.getWritableDatabase();
        db.execSQL(String.format("DELETE FROM %s", Constants.EventEntityName));
        db.close();

        Intent intent = new Intent();
        intent.setAction(Constants.IntentMessages.purge_all.toString());
        context.sendBroadcast(intent);
    }
}