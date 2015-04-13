package com.example.ariel;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;

public class Utility {
    private static final String LOG_TAG = Utility.class.getSimpleName();

    public static String getJsonStringFromNetwork() {
        Log.d(LOG_TAG, "Starting network connection");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {

            URL url = new URL("http://api.randomuser.me/?results=50");
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
                return "";
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0)
                return "";

            Log.d(LOG_TAG, buffer.toString());
            return buffer.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    e.printStackTrace();
                }
            }
        }

        return "";
    }


    public static JSONArray parseFixtureJson(String fixtureJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<String> result = new ArrayList<>();

        final String LIST = "results";
        final String USER = "user";
        final String NAME = "name";

        final String TITLE = "title";
        final String FIRST = "first";
        final String LAST = "last";

        final String PHONE = "phone";
        final String USERNAME = "username";
        final String EMAIL =  "email";
        final  String DOB =  "dob";

        JSONArray fixturesArray = jsonObject.getJSONArray(LIST);

//        for (int i = 0; i < fixturesArray.length(); i++) {
//            String nameComplete;
//            String phone;
//
//            String homeTeam;
//            String awayTeam;
//            int homeScore;
//            int awayScore;
//            JSONObject matchObject = fixturesArray.getJSONObject(i);
//            JSONObject resultObject = matchObject.getJSONObject(USER);
//            JSONObject resultObjectName = resultObject.getJSONObject(NAME);
//
//            nameComplete = resultObjectName.getString(TITLE)+" "+resultObjectName.getString(FIRST);
//            phone = resultObject.getString(PHONE);
//
//            homeTeam = resultObject.getString(USERNAME);
//            awayTeam = resultObject.getString(EMAIL);
//            homeScore = resultObject.getInt(DOB);
//            awayScore = resultObject.getInt(DOB);
//
//            String resultString = new Formatter().format("%s: %s", nameComplete, phone).toString();
//            result.add(resultString);
//        }
        return fixturesArray;// result.toArray(new String[result.size()]);
    }
}
