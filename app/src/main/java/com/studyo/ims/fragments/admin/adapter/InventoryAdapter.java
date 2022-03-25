package com.studyo.ims.fragments.admin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;
import com.studyo.ims.R;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryHolder> {

    private List<ParseObject> list;

    public InventoryAdapter(List<ParseObject> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public InventoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new InventoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.productNameText.setText("Product Name :"+object.getString("product_name"));
        holder.productCategoryText.setText("Product Price :"+object.getString("product_price"));
        holder.productPriceText.setText("Product Category :"+object.getString("product_category"));
        holder.productScanText.setText("Product BAR/QR Code :"+object.getString("product_qr_code"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class InventoryHolder extends RecyclerView.ViewHolder {
    TextView productNameText, productCategoryText, productPriceText, productScanText;


    public InventoryHolder(@NonNull View itemView) {
        super(itemView);
        productNameText = itemView.findViewById(R.id.productNameText);
        productCategoryText = itemView.findViewById(R.id.productCategoryText);
        productPriceText = itemView.findViewById(R.id.productPriceText);
        productScanText = itemView.findViewById(R.id.productScanText);
    }
}
