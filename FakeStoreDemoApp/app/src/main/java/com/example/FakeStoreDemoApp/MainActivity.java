package com.example.FakeStoreDemoApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ListView listViewProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this will hide the action bar
        getSupportActionBar().hide();

        listViewProducts = findViewById(R.id.listViewProductsMenu);
        final ArrayList<Product> products = new ArrayList<>();

        //get products from API
        try {
            GetAllProductsRequest getAllProductsRequest = new GetAllProductsRequest();
            JSONArray productsJSONArray = getAllProductsRequest.execute("10").get();
            for(int i=0;i<productsJSONArray.length();i++) {
                //for each product retrieved, download the image and add it to the collection
                JSONObject jsonObjectProduct = productsJSONArray.getJSONObject(i);

                //download image
                DownloadImageRequest downloadImageRequest = new DownloadImageRequest();

                //get rating
                JSONObject jsonObjectRating = jsonObjectProduct.getJSONObject("rating");

                //add to collection
                products.add(new Product(jsonObjectProduct.get("image").toString(),jsonObjectProduct.get("id").toString(),jsonObjectProduct.get("title").toString(),jsonObjectProduct.get("price").toString()+" $",jsonObjectProduct.get("description").toString(),jsonObjectProduct.get("category").toString(),jsonObjectRating.get("count").toString(),jsonObjectRating.get("rate").toString()+"★"));
                //this could've been simplified by getting directly an object of type Product from the API, but it's ok as long as it's a POC
            }

            //display products
            ProductAdapter productAdapter = new ProductAdapter(this,R.layout.product_row,products);
            listViewProducts.setAdapter(productAdapter);
            listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //when the user clicks an item, go to the product page (order page)

                    //get product
                    Product selectedProduct = (Product) adapterView.getItemAtPosition(i);

                    //start the new activity
                    Intent intent = new Intent (MainActivity.this, OrderProductActivity.class);
                    intent.putExtra("Product",selectedProduct);
                    startActivity(intent);
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openCart(View view) {
        Intent intent = new Intent(this, DisplayCartActivity.class);
        startActivity(intent);
    }
}