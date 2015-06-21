package Replicator;

import EntityAPI.EventsAPI.EventAPI;
import Utils.Constants;
import HTTPClient.HTTPClient;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.Event;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RemoteReplicator implements AsyncTaskListener<String>, ReplicatorInterface {

    private EventAPI eventAPI;

    public RemoteReplicator(EventAPI eventAPI){
        this.eventAPI = eventAPI;
    }

    @Override
    public void pull() {
        HTTPClient pullTask = new HTTPClient(this);
        String[] params = new String[1];
        params[0] = Constants.FlowAPIEndpoints.events.toString();

        pullTask.execute(params);
    }

    @Override
    public void push() {
        //TODO
    }

    @Override
    public void onComplete(String result) {
        Log.e("FINAL RES: ", result);
        processData(result);
    }

    private void processData(String response){

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd-mm-yyyy");
        Gson gson = gsonBuilder.create();

        Type listType = new TypeToken<ArrayList<Event>>(){}.getType();
        ArrayList<Event> events = gson.fromJson(response, listType);

        eventAPI.saveEvents(events);
    }
}
