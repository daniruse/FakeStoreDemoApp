package com.example.FakeStoreDemoApp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class CreateCartRequest extends AsyncTask<JSONObject,String,JSONObject> {
    @Override
    protected JSONObject doInBackground(JSONObject... jsonObjects) {
        //initialize connection
        URL url = null;
        try {
            url = new URL("https://fakestoreapi.com/carts");

            //open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //set headers
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //send request
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonObjects[0].toString());
            os.flush();
            os.close();

            System.out.println(conn.getResponseCode() + " " + conn.getResponseMessage());
            //read response

            switch (conn.getResponseCode()) {
                case 200:
                    //if status is OK
                {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    String inputLine;
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        stringBuilder.append(inputLine);
                    }
                    in.close();

                    //return JSON result
                    JSONObject jsonResult = new JSONObject(stringBuilder.toString());
                    return jsonResult;
                }

                default:
                    return null;

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
    }

