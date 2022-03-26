package com.studyo.ims.fragments.user.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;
import com.studyo.ims.R;

import java.util.List;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryHolder> {

    private List<ParseObject> list;

    public PurchaseHistoryAdapter(List<ParseObject> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PurchaseHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_history_item_row, parent, false);
        return new PurchaseHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseHistoryHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.productNameText.setText("Product Name :"+object.getString("product_name"));
        holder.productCategoryText.setText("Product Price :"+object.getString("product_price"));
        holder.productPriceText.setText("Product Category :"+object.getString("product_category"));
        holder.productScanText.setText("Product BAR/QR Code :"+object.getString("product_qr_code"));
        holder.dateTextView.setText("Purchased Date :"+object.getString("date"));
        holder.timeTextView.setText("Time :"+object.getString("time"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class PurchaseHistoryHolder extends RecyclerView.ViewHolder {
    TextView productNameText, productCategoryText, productPriceText, productScanText,dateTextView,timeTextView;


    public PurchaseHistoryHolder(@NonNull View itemView) {
        super(itemView);
        productNameText = itemView.findViewById(R.id.productNameText);
        productCategoryText = itemView.findViewById(R.id.productCategoryText);
        productPriceText = itemView.findViewById(R.id.productPriceText);
        productScanText = itemView.findViewById(R.id.productScanText);
        dateTextView = itemView.findViewById(R.id.dateTextView);
        timeTextView = itemView.findViewById(R.id.timeTextView);
    }
}

