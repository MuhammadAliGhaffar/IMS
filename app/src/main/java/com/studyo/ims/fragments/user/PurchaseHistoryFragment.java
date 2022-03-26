package com.studyo.ims.fragments.user;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.studyo.ims.R;
import com.studyo.ims.fragments.admin.adapter.UserAdapter;
import com.studyo.ims.fragments.user.adapter.PurchaseHistoryAdapter;
import com.studyo.ims.fragments.utils.KeyValueStore;

import java.util.List;


public class PurchaseHistoryFragment extends Fragment {

    private RecyclerView recyclerViews;
    private PurchaseHistoryAdapter purchaseHistoryAdapter;
    private ImageView backButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_history, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        recyclerViews = view.findViewById(R.id.recyclerViews);
        backButton = view.findViewById(R.id.backButton);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PurchaseHistory");
        query.whereEqualTo("username", KeyValueStore.getUserDetails().getUsername());
        final ProgressDialog progressDialogg = new ProgressDialog(getContext());
        progressDialogg.setMessage("Please wait fetching all purchase history...");
        progressDialogg.setCanceledOnTouchOutside(false);
        progressDialogg.show();
        query.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects.size() == 0){
                    Toast.makeText(getContext(), "No Purchases found", Toast.LENGTH_SHORT).show();
                }else {
                    purchaseHistoryAdapter = new PurchaseHistoryAdapter(objects);
                    recyclerViews.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViews.setAdapter(purchaseHistoryAdapter);
                }

            } else {
                Log.d("_debugTag", "Error: " + e.getMessage());
            }
            progressDialogg.dismiss();
        });

        backButton.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });
    }
}