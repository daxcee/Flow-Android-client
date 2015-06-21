package Utils;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPClient extends AsyncTask<String, Void, String> {

    HttpURLConnection httpConnection;

    @Override
    protected String doInBackground(String... urls) {
        String result;

        try {
            URL url = new URL(urls[0]);
            httpConnection = (HttpURLConnection) url.openConnection();
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
            Log.e("res: ", result);
        }
    }

    private String execute() throws IOException {
        BufferedReader reader = null;
        InputStream inputStream = null;
        String result = null;

        httpConnection.connect();

        try {
            inputStream = httpConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            result = sb.toString();

        } finally {
            if (inputStream != null)
                inputStream.close();

            if (reader != null)
                reader.close();
        }

        return result;
    }

    protected int getResponseCode() {
        int responseCode = 0;
        try {
            responseCode = httpConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseCode;
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