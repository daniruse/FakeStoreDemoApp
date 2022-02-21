package com.example.FakeStoreDemoApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class OrderProductActivity extends AppCompatActivity {

    Spinner quantitySpinner;
    TextView title;
    TextView description;
    TextView price;
    TextView category;
    TextView rating;
    ImageView image;
    Product selectedProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);

        //this will hide the action bar
        getSupportActionBar().hide();

        //initialize views
        quantitySpinner = findViewById(R.id.spinnerQuantity);
        title = findViewById(R.id.tvSingleProductTitle);
        description = findViewById(R.id.tvSingleProductDescription);
        price = findViewById(R.id.tvSingleProductPrice);
        category = findViewById(R.id.tvSingleProductCategory);
        rating = findViewById(R.id.tvSingleProductRating);
        image = findViewById(R.id.singleProductImage);

        //initialize spinner
        final String[] quantityOptions = {"1", "2", "3", "4", "5"}; //to simplify the scenario, allow purchasing maximum 5 units
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quantityOptions);
        quantitySpinner.setAdapter(spinnerAdapter);

        //get selected product from the other activity
        Intent intent = getIntent();
        selectedProduct =  intent.getParcelableExtra("Product");

        //update UI
        title.setText(selectedProduct.getTitle());
        description.setText(selectedProduct.getDescription());
        price.setText(selectedProduct.getPrice());
        category.setText(selectedProduct.getCategory());
        rating.setText(selectedProduct.getRating()+" ("+selectedProduct.ratingCount+")");

        //download & set image
        try {
            DownloadImageRequest downloadImageRequest = new DownloadImageRequest();
            Bitmap bitmap = downloadImageRequest.execute(selectedProduct.getImage()).get();
            image.setImageBitmap(bitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //when quantity is selected, change price
        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                price.setText(Double.parseDouble(selectedProduct.getPrice().replace("$",""))*Double.parseDouble(quantityOptions[i])+" $");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void order(View view) throws ExecutionException, InterruptedException, JSONException {
        //get current user cart
        //let's suppose the authenticated user has ID 2. Unfortunately, FakeStoreAPI does not provide any user ID after login
        GetCartRequest getCartRequest = new GetCartRequest();
        JSONArray existingProductsArray = getCartRequest.execute("2").get();
        System.out.println("Existing cart: " + existingProductsArray.toString());

        //add product to cart
        JSONObject newProduct = new JSONObject();
        newProduct.put("productId",selectedProduct.id);
        newProduct.put("quantity",quantitySpinner.getSelectedItem().toString());
        existingProductsArray.put(newProduct); //added new product to existing array of products

        //construct new JSON
        JSONObject updateJSON = new JSONObject();
        updateJSON.put("userId",2); //as agreed above
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        updateJSON.put("date",formatter.format(date));
        updateJSON.put("products",existingProductsArray);

        //create new (updated) cart
        //the update cart (PUT/PATCH) request could've been used here instead of creating a new cart
        CreateCartRequest createCartRequest = new CreateCartRequest();
        JSONObject result = createCartRequest.execute(updateJSON).get();
        if(result!=null){
            System.out.println("New cart ID: "+result.get("id").toString());
            System.out.println("New cart content: "+result.get("products"));
            Toast.makeText(getApplicationContext(),"Product added to cart",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Product not added to the cart",Toast.LENGTH_LONG).show();
        }

        //close activity and return to main screen
        finish();
    }
}