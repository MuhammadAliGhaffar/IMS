package com.studyo.ims.fragments.admin;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.studyo.ims.R;


public class DashboardFragment extends Fragment {

    private CardView addItems, deleteItems, scanItems, viewInventory,allUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        addItems = view.findViewById(R.id.addItems);
        deleteItems = view.findViewById(R.id.deleteItems);
        scanItems = view.findViewById(R.id.scanItems);
        viewInventory = view.findViewById(R.id.viewInventory);
        allUsers = view.findViewById(R.id.allUsers);

        addItems.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_addProductFragment);
        });
        deleteItems.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_deleteProductFragment);
        });
        scanItems.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_viewProductFragment);
        });
        viewInventory.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_viewInventoryFragment);
        });
        allUsers.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_allUsersFragment);
        });

    }
}