package HTTPClient;

import android.os.AsyncTask;
import java.io.*;
import java.net.HttpURLConnection;

abstract class AbstractHTTPClient extends AsyncTask<String, Void, String> {

    protected HttpURLConnection httpConnection;

    protected String execute() throws IOException {
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

    protected void postRequest(String requestBody) throws IOException {
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        httpConnection.setDoOutput(true);

        DataOutputStream outputStream = new DataOutputStream(httpConnection.getOutputStream());
        outputStream.writeBytes(requestBody);
        outputStream.flush();
        outputStream.close();
    }

    protected void disconnect() {
        httpConnection.disconnect();
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
}