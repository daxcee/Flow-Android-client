package com.flow.app;

import EntityAPI.EventsAPI.EventAPI;
import Replicator.RemoteReplicator;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private EventAPI eventAPI;
    private RemoteReplicator replicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.flow.app.R.layout.activity_main);

        this.eventAPI = new EventAPI(this);
        this.replicator = new RemoteReplicator(eventAPI);

        getEvents();
    }

    private void getEvents(){
        replicator.pull();
    }

}
