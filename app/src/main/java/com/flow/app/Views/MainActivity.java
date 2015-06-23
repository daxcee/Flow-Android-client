package com.flow.app.Views;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ListView;
import com.flow.app.EntityAPI.EventsAPI.EventAPI;
import com.flow.app.Model.Event;
import com.flow.app.R;
import com.flow.app.Replicator.RemoteReplicator;
import com.flow.app.Utils.Constants;

import java.util.ArrayList;

public class MainActivity extends Activity implements ListViewInterface {

    private EventAPI eventAPI;
    private RemoteReplicator replicator;
    private BroadcastReceiver broadcastReceiver;
    private ArrayList<Event> eventArrayList;
    private EventListAdapter eventListAdapter;
    private final String LOG_TAG = "MainActivity";
    private ListView eventListView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.flow.app.R.layout.activity_main);
        eventAPI = new EventAPI(this);
        replicator = new RemoteReplicator(eventAPI);
        eventArrayList = new ArrayList<Event>(0);
        progressDialog = new ProgressDialog(MainActivity.this);

        initReceivers();

        if(eventAPI.getAll().size() == 0){
            Log.d(LOG_TAG, String.format("No local data available, replicating data from remote: %s",
                    Constants.FlowAPIEndpoints.events));
            setLoadingState();
            pullRemoteData();
        } else {
            eventArrayList = eventAPI.getAll();
            Log.d(LOG_TAG, String.format("Local data is available: %d events stored.", eventArrayList.size()));
        }

        initListView();
    }

    @Override
    public void initReceivers(){
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
            setLoadingState();
            eventAPI.deleteAll();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initListView() {
        eventListAdapter = new EventListAdapter(this, eventArrayList);
        eventListView = (ListView) findViewById(R.id.listView);
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

    @Override
    public void pullRemoteData() {
        replicator.pull();
    }

    @Override
    public void setLoadingState() {
        progressDialog.setTitle("Syncing");
        progressDialog.setMessage("Replicating from remote, hold on tight!");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void refreshListViewData() {
        runOnUiThread(new Runnable() {
            public void run() {
                progressDialog.cancel();
                eventArrayList.clear();
                eventArrayList.addAll(eventAPI.getAll());
                eventListAdapter.notifyDataSetChanged();
                eventListView.invalidateViews();
                eventListView.refreshDrawableState();
                System.out.println("Total event items: " + eventAPI.getAll().size());
            }
        });
    }

}