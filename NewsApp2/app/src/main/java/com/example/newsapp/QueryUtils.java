package com.example.newsapp;

import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    public QueryUtils() {
    }

    public static List<News> FetchNewsData(String requestUrl) {
        URL url = creatURL(requestUrl);
        String jsonResponse = null;
        jsonResponse = makeHttpRequest(url);
        List<News> newsList = extractFeatureFromJson(jsonResponse);
        Log.e("FetchNewsData", "done");

        // Return the list of {@link Earthquake}s
        return newsList;
    }

    private static URL creatURL(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        Log.e("creatURL", "done");
        return url;
    }

    public static List<News> extractFeatureFromJson(String newsjsonResponse) {
        if (TextUtils.isEmpty(newsjsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<News> newsList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject baseJsonResponse = new JSONObject(newsjsonResponse);
            JSONObject baseJsonObject = baseJsonResponse.getJSONObject("response");
            JSONArray newsJsonArray = baseJsonObject.getJSONArray("results");
            for (int i = 0; i < newsJsonArray.length(); i++) {
                JSONObject currentNews = newsJsonArray.getJSONObject(i);
                String id = currentNews.getString("id");
                String type = currentNews.getString("type");
                String webPublicationDate = currentNews.getString("webPublicationDate");
                String webTitle = currentNews.getString("webTitle");
                String webUrl = currentNews.getString("webUrl");
                String sectionName = currentNews.getString("sectionName");
                newsList.add(new News(id, type, webPublicationDate, webTitle, webUrl, sectionName));
                Log.e("extractFeatureFromJson", "successfully done");
            }


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        return newsList;
    }

    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            Log.e("makeHttpRequest", "is Empty");
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.e("makeHttpRequest", "URL connection created");
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "problem in input steam cole", e);
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }

        }

        Log.e("readFromStream", "is done");
        return output.toString();
    }
}

