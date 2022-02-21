package com.example.FakeStoreDemoApp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetCartRequest extends AsyncTask<String,String, JSONArray> {
    @Override
    protected JSONArray doInBackground(String... strings) {

        URL url = null;
        try {
            url = new URL("https://fakestoreapi.com/carts/user/"+strings[0]);
            //let's suppose the authenticated user has ID 2. Unfortunately, FakeStoreAPI does not provide any user ID after login
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
            JSONArray cartsArray = new JSONArray (stringBuilder.toString());
            JSONObject userCartJson = cartsArray.getJSONObject(0);
            //it's known that the array has only one object since the endpoint filters for one user
            //FakeStoreAPI allows users to have multiple carts, but that is not the case and it wouldn't make any sense anyway
            JSONArray cartProducts = userCartJson.getJSONArray("products");

            return cartProducts;
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
