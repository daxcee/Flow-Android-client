package com.flow.app.HTTPClient;

import com.flow.app.Replicator.AsyncTaskListener;
import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPClient extends AbstractHTTPClient {

    private AsyncTaskListener<String> callback;

    public HTTPClient(AsyncTaskListener callback){
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result;

        try {
            URL url = new URL(urls[0]);
            super.httpConnection = (HttpURLConnection) url.openConnection();
            handleResponseCode(getResponseCode());
            result = execute();


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result != null) {
            callback.onComplete(result);
        }
    }

    private void handleResponseCode(int responseCode){
        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                Log.e("status: ","200 OK");
                break;
            case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                Log.e("status: ", "GATEWAY TIMEOUT");
                break;
            case HttpURLConnection.HTTP_UNAVAILABLE:
                Log.e("status: ", "HTTP UNAVAILABLE");
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                Log.e("status: ","401 UNAUTHORIZED");
                break;
            default:
                Log.e("status: ", "HTTP errror");
                break; // abort
        }
    }

}