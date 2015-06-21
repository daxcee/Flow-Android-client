package Replicator;

import Utils.Constants;
import Utils.HTTPClient;

public class RemoteReplicator implements ReplicatorInterface {

    private HTTPClient pullTask;

    @Override
    public void pull() {
        pullTask = new HTTPClient();
        String[] params = new String[1];
        params[0] = Constants.FlowAPIEndpoints.events.toString();

        pullTask.execute(params);
    }

    @Override
    public void push() {
        //TODO
    }
}
