package com.example.FakeStoreDemoApp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private int resource;
    private static HashMap<String,Bitmap> images;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) throws ExecutionException, InterruptedException {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;

        //download all images at the beginning
        this.images = new HashMap<String, Bitmap>() {};
        for(int i=0; i<objects.size();i++){
            String imageURL = objects.get(i).getImage();
            DownloadImageRequest downloadImageRequest = new DownloadImageRequest();
            Bitmap image = downloadImageRequest.execute(imageURL).get();
            images.put(imageURL,image);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);

        ImageView imageViewProduct = convertView.findViewById(R.id.productRowImage);
        TextView tvProductName = convertView.findViewById(R.id.productRowName);
        TextView tvPrice = convertView.findViewById(R.id.productRowPrice);
        TextView tvCategory = convertView.findViewById(R.id.productRowCategory);
        TextView tvRating = convertView.findViewById(R.id.productRowRating);

        //download & display image
        DownloadImageRequest downloadImageRequest = new DownloadImageRequest();
        String imageURL = getItem(position).getImage();
        try {
            if(images.get(imageURL)==null) {
                //downloading the images all the time will result in a laggy ListView
                Bitmap image = downloadImageRequest.execute(imageURL).get();
                imageViewProduct.setImageBitmap(image);
                images.put(imageURL, image);
            }
            else{
                imageViewProduct.setImageBitmap(images.get(imageURL));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //display data
        tvProductName.setText(getItem(position).getTitle());
        tvPrice.setText(getItem(position).getPrice());
        tvCategory.setText(getItem(position).getCategory());
        tvRating.setText(getItem(position).getRating());

        return convertView;
    }
}
