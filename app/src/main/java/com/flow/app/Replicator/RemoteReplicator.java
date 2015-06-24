package com.flow.app.Replicator;

import android.app.ProgressDialog;
import android.content.Context;
import com.flow.app.EntityAPI.PersistenceManager;
import com.flow.app.HTTPClient.HTTPClient;
import com.flow.app.Model.Event;
import com.flow.app.Utils.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.flow.app.Utils.Constants.EventEntityAttributes.title;

public class RemoteReplicator implements AsyncTaskListener<String>, ReplicatorInterface {

    private PersistenceManager persistenceManager;
    private Context context;
    private HTTPClient pullTask;

    public RemoteReplicator(PersistenceManager persistenceManager, Context context){
        this.persistenceManager = persistenceManager;
        this.context = context;
    }

    @Override
    public void pull() {
        pullTask = new HTTPClient(this);
        String[] params = new String[1];
        params[0] = Constants.FlowAPIEndpoints.events.toString();
        pullTask.setCaller(context);
        pullTask.execute(params);
    }

    public void pull(final String url) {
        HTTPClient pullTask = new HTTPClient(this);
        String[] params = new String[1];
        params[0] = url;

        pullTask.execute(params);
    }

    @Override
    public void push() {
        //TODO
    }

    @Override
    public void onComplete(String result, ProgressDialog progressDialog) {
        processData(result, progressDialog);
    }

    private void processData(String response, ProgressDialog progressDialog){
        JSONArray events;

        try {
            JSONObject jsonObj = new JSONObject(response);
            events = jsonObj.getJSONArray("result");

            if(events != null) {
                ArrayList<Event> eventList = new ArrayList<Event>();

                for (int i = 0; i < events.length(); i++) {
                    JSONObject eventItem = events.getJSONObject(i);

                    Event event = new Event();
                    event.setTitle(eventItem.getString(title.toString()));
                    event.setId(eventItem.getString(Constants.EventEntityAttributes._id.toString()));
                    event.setVenue(eventItem.getString(Constants.EventEntityAttributes.venue.toString()));
                    event.setCity(eventItem.getString(Constants.EventEntityAttributes.city.toString()));
                    event.setCountry(eventItem.getString(Constants.EventEntityAttributes.country.toString()));
                    event.setDate(eventItem.getString(Constants.EventEntityAttributes.date.toString()));

                    eventList.add(event);
                }
                persistenceManager.setProgresshandler(pullTask.getProgressHandler());
                persistenceManager.saveEvents(eventList);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





}
