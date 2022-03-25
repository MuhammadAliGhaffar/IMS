package com.studyo.ims.fragments.user;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;
import com.studyo.ims.R;

public class HomeFragmentFragment extends Fragment {

    private ParseUser user;
    private TextView usernameText;
    private CardView purchaseItems,searchItems,purchaseHistory,viewAllItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        user = ParseUser.getCurrentUser();
        usernameText = view.findViewById(R.id.usernameText);
        purchaseItems = view.findViewById(R.id.purchaseItems);
        searchItems = view.findViewById(R.id.searchItems);
        purchaseHistory = view.findViewById(R.id.purchaseHistory);
        viewAllItems = view.findViewById(R.id.viewAllItems);

        usernameText.setText("Welcome "+user.getString("username"));

        purchaseItems.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragmentFragment_to_purchaseItemFragment);
        });
        searchItems.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragmentFragment_to_viewProductFragment);
        });
        purchaseHistory.setOnClickListener(view1 -> {

        });
        viewAllItems.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragmentFragment_to_viewInventoryFragment);
        });

    }
}