package com.example.FakeStoreDemoApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CartProductAdapter extends ArrayAdapter<CartProduct> {

    private Context context;
    private int resource;
    public CartProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CartProduct> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);
        ImageView imageViewProduct = convertView.findViewById(R.id.productImage);
        TextView tvProductName = convertView.findViewById(R.id.productName);
        TextView tvPrice = convertView.findViewById(R.id.price);
        TextView tvQuantity = convertView.findViewById(R.id.quantity);

        imageViewProduct.setImageBitmap(getItem(position).getImage());
        tvProductName.setText(getItem(position).getName());
        tvPrice.setText(getItem(position).getPrice());
        tvQuantity.setText(getItem(position).getQuantity());



        return convertView;
    }
}
