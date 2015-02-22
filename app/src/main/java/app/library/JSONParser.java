package app.library;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Not for public use
 * Created by FAIZ on 19-02-2015.
 */
public class JSONParser {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {
    }

    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params, String httpRequestType) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient(); // Making HTTP request defaultHttpClient
            HttpResponse response;
            HttpEntity entity;
            if (httpRequestType.equals("get") && params == null) {
                HttpGet httpGet = new HttpGet(url);
                response = httpClient.execute(httpGet);
                entity = response.getEntity();
                is = entity.getContent();
            } else if (httpRequestType.equals("post") && params != null) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                response = httpClient.execute(httpPost);
                entity = response.getEntity();
                is = entity.getContent();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            is.close();
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj; // return JSON String
    }
}
