package com.jonfp.joystick;

import android.os.AsyncTask;

import com.albroco.barebonesdigest.DigestAuthentication;
import com.albroco.barebonesdigest.DigestChallengeResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequester extends AsyncTask<String, Void, String> {
    public static final String REQUEST_METHOD = "GET";

    @Override
    protected String doInBackground(String... params){
        String stringUrl = params[0];
        String password = params[1];
        String result = "Unsuccessful";

        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                DigestAuthentication auth = DigestAuthentication.fromResponse(connection);
                auth.username("root").password(password);
                if (!auth.canRespond()) {
                    result = "Unsuccessful";
                    return result;
                }

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty(DigestChallengeResponse.HTTP_HEADER_AUTHORIZATION,
                        auth.getAuthorizationForRequest(REQUEST_METHOD, connection.getURL().getPath()));
                if(connection.getResponseCode() == 204)
                    result = "Successful";
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }
}