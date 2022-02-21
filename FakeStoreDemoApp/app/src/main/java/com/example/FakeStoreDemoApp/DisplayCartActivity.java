package com.example.FakeStoreDemoApp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DisplayCartActivity extends AppCompatActivity {

    ListView listViewProducts;
    TextView textViewTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cart);

        //this will hide the action bar
        getSupportActionBar().hide();

        listViewProducts = findViewById(R.id.listViewProducts);
        textViewTotal = findViewById(R.id.textViewTotal);
        //create data
        ArrayList<CartProduct> arrayCartProducts = new ArrayList<>();

        try {


            //get cart items for user with ID 2
            GetCartRequest getCartRequest = new GetCartRequest();
            //let's suppose the authenticated user has ID 2. Unfortunately, FakeStoreAPI does not provide any user ID after login
            JSONArray productsArray = getCartRequest.execute("2").get();

            //initialize total sum
            double totalSum = 0;

            //iterate through products in the cart
            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject cartItem = productsArray.getJSONObject(i);
                GetProductRequest getProductRequest = new GetProductRequest();
                JSONObject productDetails = getProductRequest.execute(cartItem.get("productId").toString()).get();
                //download image
                DownloadImageRequest downloadImageRequest = new DownloadImageRequest();
                Bitmap image = downloadImageRequest.execute(productDetails.get("image").toString()).get();
                arrayCartProducts.add(new CartProduct(image,productDetails.get("title").toString(),cartItem.get("quantity").toString()+" pc",productDetails.get("price")+"$/pc"));
                totalSum+= Double.parseDouble(productDetails.get("price").toString())*Double.parseDouble(cartItem.get("quantity").toString());
            }
            CartProductAdapter cartProductAdapter = new CartProductAdapter(this,R.layout.cart_list_row,arrayCartProducts);
            listViewProducts.setAdapter(cartProductAdapter);
            textViewTotal.setText("Total: "+totalSum+" $");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void finish(View view) {
        //there's no endpoint to order, so this button just returns to the main screen and simulates order placement
        Toast.makeText(getApplicationContext(),"Order placed successfully",Toast.LENGTH_LONG).show();
        finish();

    }
}