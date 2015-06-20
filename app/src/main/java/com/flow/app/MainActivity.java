package com.flow.app;

import EntityAPI.EventsAPI.EventAPI;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private EventAPI eventAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.flow.app.R.layout.activity_main);

        this.eventAPI = new EventAPI(this);
    }

}
