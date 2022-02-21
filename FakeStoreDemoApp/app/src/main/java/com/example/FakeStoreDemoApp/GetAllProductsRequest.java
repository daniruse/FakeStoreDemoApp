package com.example.FakeStoreDemoApp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAllProductsRequest extends AsyncTask<String,String, JSONArray> {
    @Override
    protected JSONArray doInBackground(String... strings) {

        String URLString = "https://fakestoreapi.com/products?limit="+strings[0]; //limit the number of products for demo purposes

        URL url = null;
        try {
            url = new URL(URLString);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            //print response code & message
            System.out.println(http.getResponseCode() + " " + http.getResponseMessage());

            //read response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            http.getInputStream()));
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            in.close();

            //print response
            System.out.println(stringBuilder.toString());
            http.disconnect();

            //read the result
            JSONArray productsArray = new JSONArray (stringBuilder.toString());
            for(int i=0;i<productsArray.length();i++) {
                //output to console all product titles
                System.out.println(productsArray.getJSONObject(i).get("title").toString());
            }
            return productsArray;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
