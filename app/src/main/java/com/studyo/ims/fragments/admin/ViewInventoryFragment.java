package com.studyo.ims.fragments.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.studyo.ims.R;
import com.studyo.ims.fragments.admin.adapter.InventoryAdapter;


public class ViewInventoryFragment extends Fragment {

    private static final String CLASS_NAME = "Items";
    private ParseQuery<ParseObject> parseObject;
    private RecyclerView recyclerViews;
    private InventoryAdapter inventoryAdapter;
    private TextView totalnoitem;
    private ImageView backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_inventory, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        parseObject = ParseQuery.getQuery(CLASS_NAME);
        recyclerViews = view.findViewById(R.id.recyclerViews);
        totalnoitem = view.findViewById(R.id.totalnoitem);
        backButton = view.findViewById(R.id.backButton);

        final ProgressDialog progressDialogg = new ProgressDialog(getContext());
        progressDialogg.setMessage("Please wait fetching all items...");
        progressDialogg.setCanceledOnTouchOutside(false);
        progressDialogg.show();
        parseObject.orderByDescending("createdAt");
        parseObject.findInBackground((objects, e) -> {
            if (e == null) {
                inventoryAdapter = new InventoryAdapter(objects);
                recyclerViews.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerViews.setAdapter(inventoryAdapter);
                totalnoitem.setText(String.valueOf(objects.size()));

            } else {

                Log.d("_debugTag", e.getMessage());
                Log.d("_debugTag", e.getLocalizedMessage());

            }
            progressDialogg.dismiss();
        });

        backButton.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });
    }
}