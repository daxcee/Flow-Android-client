package com.flow.app.EntityAPI;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.flow.app.Model.Event;
import com.flow.app.Replicator.RemoteReplicator;
import com.flow.app.Utils.Constants;
import java.util.ArrayList;
import static com.flow.app.Utils.Constants.EventEntityAttributes.*;
import static com.flow.app.Utils.Constants.EventEntityName;

public class PersistenceManager extends SQLiteOpenHelper {

    private RemoteReplicator remoteReplicator;
    private Context context;
    private ProgressDialog progressDialog;

    SQLiteDatabase db;

    public PersistenceManager(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
        this.remoteReplicator = new RemoteReplicator(this,context);
        this.db = getWritableDatabase();
    }

    /**
     * Creating Tables
     *
     * @param db instance
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String attrTxtType = " TEXT";
        String createTableQuery =
                String.format("Create table %s (%s primary key, %s, %s, %s, %s, %s)",
                    EventEntityName,
                        _id.toString() + " String",
                        title.toString() + attrTxtType,
                        date.toString()+ attrTxtType,
                        city.toString()+ attrTxtType,
                        venue.toString()+ attrTxtType,
                        country.toString() + attrTxtType);

        db.execSQL(createTableQuery);
    }

    /**
     * Upgrading database
     *
     * @param db instance
     * @param oldVersion curr
     * @param newVersion new
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.EventEntityName);
        onCreate(db);
    }

    public void saveEvent(Event event) {
        ContentValues values = new ContentValues();
        values.put(_id.toString(), event.getId());
        values.put(title.toString(), event.getTitle());
        values.put(venue.toString(), event.getVenue());
        values.put(city.toString(), event.getCity());
        values.put(country.toString(), event.getCountry());
        values.put(date.toString(), event.getDateStr());

        db.insert(EventEntityName, null, values);

    }

    public void saveEvents(ArrayList<Event> events) {
        Intent intent = new Intent();
        intent.setAction(Constants.IntentMessages.replication_finished.toString());
        ArrayList<ContentValues> valuesList = new ArrayList<ContentValues>();

        for(Event event:events) {
            if(event.getId() != null) {
                ContentValues values = new ContentValues();
                values.put(title.toString(), event.getTitle());
                values.put(venue.toString(), event.getVenue());
                values.put(city.toString(), event.getCity());
                values.put(country.toString(), event.getCountry());
                values.put(date.toString(), event.getDateStr());

                valuesList.add(values);
            }
        }

        store(valuesList);
    }

    private synchronized void store(final ArrayList<ContentValues> values){
        Thread thread = new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction(Constants.IntentMessages.replication_finished.toString());
                 db = getWritableDatabase();

                for(ContentValues value : values){
                    db.insert(EventEntityName, null, value);
                    Log.e("Saved item: ", String.valueOf(value.get("title")));

                }
                db.close();

                context.sendBroadcast(intent);

                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

            }
        };

        thread.start();
    }

    public Event getById(String idf) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EventEntityName, new String[] {_id.toString(),
                        title.toString(), city.toString() }, _id.toString() + "=?",
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


    public ArrayList<Event> getSortedByDate() {
        //TODO
        return null;
    }


    public ArrayList<Event> getAll() {
        ArrayList<Event> eventsList = new ArrayList<Event>();

        String selectQuery = "SELECT  * FROM " + Constants.EventEntityName;
        db = this.getWritableDatabase();
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

    public void deleteEvent(String id) {
        //TODO
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(String.format("DELETE FROM %s", Constants.EventEntityName));
        db.close();

        Intent intent = new Intent();
        intent.setAction(Constants.IntentMessages.purge_all.toString());
        context.sendBroadcast(intent);
    }


    @Override
    public synchronized void close() {
        if (db != null)
            db.close();

        super.close();

    }

    public RemoteReplicator getReplicatorInstance(){
        return this.remoteReplicator;
    }

    public void setProgresshandler(ProgressDialog progresshandler){
        this.progressDialog = progresshandler;
    }

}
