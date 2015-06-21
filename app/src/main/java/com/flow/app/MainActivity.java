package com.flow.app;

import EntityAPI.EventsAPI.EventAPI;
import Replicator.RemoteReplicator;
import Utils.Constants;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import models.Event;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private EventAPI eventAPI;
    private RemoteReplicator replicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.flow.app.R.layout.activity_main);

        this.eventAPI = new EventAPI(this);
        this.replicator = new RemoteReplicator(eventAPI);

        pullRemoteData();
    }

    private void pullRemoteData() {
        replicator.pull();
    }

    private void getEvents(){
        ArrayList<Event> events = eventAPI.getAll();
        for(Event event : events) {
            Log.e("retrieved item: ", event.getTitle());
        }
    }

}
