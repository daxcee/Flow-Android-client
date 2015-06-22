package com.flow.app;

import EntityAPI.EventsAPI.EventAPI;
import Replicator.RemoteReplicator;
import Utils.Constants;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import models.Event;

import java.util.ArrayList;

public class MainActivity extends Activity  {

    private EventAPI eventAPI;
    private RemoteReplicator replicator;
    private BroadcastReceiver broadcastReceiver;
    private ArrayList<Event> eventArrayList;
    private ListAdapter eventListAdapter;
    private final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.flow.app.R.layout.activity_main);
        eventAPI = new EventAPI(this);
        replicator = new RemoteReplicator(eventAPI);
        eventArrayList = new ArrayList<Event>(0);

        initReceivers();

        if(eventAPI.getAll().size() == 0){
            Log.d(LOG_TAG, String.format("No local data available, replicating data from remote: %s",
                    Constants.FlowAPIEndpoints.events));
            pullRemoteData();
        } else {
            eventArrayList = eventAPI.getAll();
            Log.d(LOG_TAG, String.format("Local data is available: %d events stored.", eventArrayList.size()));
        }

        initListView();
    }

    private void initReceivers(){
        IntentFilter replicateFilter = new IntentFilter(Constants.IntentMessages.replication_finished.toString());
        IntentFilter purgefilter = new IntentFilter(Constants.IntentMessages.purge_all.toString());

        broadcastReceiver =  new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refreshListViewData();
            }
        };

        registerReceiver(broadcastReceiver, replicateFilter);
        registerReceiver(broadcastReceiver, purgefilter);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Receivers are not registered")) {
                Log.e(LOG_TAG, "Tried to unregister the receivers whilest not beeing registered..");
            } else {
                throw e;
            }
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            eventAPI.deleteAll();
            eventArrayList.clear();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initListView() {
        eventListAdapter = new EventListAdapter(this, eventArrayList);
        ListView eventListView = (ListView) findViewById(R.id.listView);
        eventListView.setAdapter(eventListAdapter);

        eventListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //TODO show selected item details
                        //Event selectedEvent = (Event) adapterView.getItemAtPosition(i);
                    }
                }
        );
    }

    private void pullRemoteData() {
        replicator.pull();
    }

    private void refreshListViewData() {

        synchronized(this){
            eventArrayList = eventAPI.getAll();
            eventListAdapter = new EventListAdapter(this, eventArrayList);

            //TODO notify all to trigger listview refresh
        }
    }
}