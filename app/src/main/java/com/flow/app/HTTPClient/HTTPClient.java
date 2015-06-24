package com.flow.app.HTTPClient;

import android.app.ProgressDialog;
import android.content.Context;
import com.flow.app.Replicator.AsyncTaskListener;
import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPClient extends AbstractHTTPClient {

    private AsyncTaskListener<String> callback;
    private Context context;
    private ProgressDialog progressDialog;

    public HTTPClient(AsyncTaskListener callback){
        this.callback = callback;
    }
    @Override
    protected void onPreExecute() {
        progressDialog.setTitle("ET is phoning home");
        progressDialog.setMessage("Hold on tight, events are retrieved.");
        progressDialog.setCancelable(false);

        if(!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... urls) {
        String result;

        try {
            URL url = new URL(urls[0]);
            super.httpConnection = (HttpURLConnection) url.openConnection();
            handleResponseCode(getResponseCode());
            result = execute();

            if(!progressDialog.isShowing()) {
                progressDialog.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result != null) {
            callback.onComplete(result,progressDialog);
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

    public void setCaller(Context context){
        this.context = context;
        setProgressHandler(context);
    }

    private void setProgressHandler(Context context){
        progressDialog = new ProgressDialog(context);
    }

    public ProgressDialog getProgressHandler(){
        return progressDialog;
    }

}