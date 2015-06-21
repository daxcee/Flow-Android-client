package Replicator;

import EntityAPI.EventsAPI.EventAPI;
import Utils.AsyncTaskListener;
import Utils.Constants;
import Utils.HTTPClient;
import android.util.Log;

public class RemoteReplicator implements AsyncTaskListener<String>,ReplicatorInterface {

    private HTTPClient pullTask;
    private EventAPI eventAPI;

    public RemoteReplicator(EventAPI eventAPI){
        this.eventAPI = eventAPI;
    }

    @Override
    public void pull() {
        pullTask = new HTTPClient(this);
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
    }
}
