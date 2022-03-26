package com.studyo.ims.fragments.user;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.studyo.ims.R;
import com.studyo.ims.fragments.user.model.User;
import com.studyo.ims.fragments.utils.KeyValueStore;

public class HomeFragment extends Fragment {

    private static final String CLASS_NAME = "CustomUser";
    private TextView usernameText, balanceText;
    private CardView purchaseItems, searchItems, purchaseHistory, viewAllItems;
    ParseQuery<ParseObject> customUser;
    private ImageView logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        customUser = ParseQuery.getQuery(CLASS_NAME);
        usernameText = view.findViewById(R.id.usernameText);
        purchaseItems = view.findViewById(R.id.purchaseItems);
        searchItems = view.findViewById(R.id.searchItems);
        purchaseHistory = view.findViewById(R.id.purchaseHistory);
        viewAllItems = view.findViewById(R.id.viewAllItems);
        balanceText = view.findViewById(R.id.balanceText);
        logoutButton = view.findViewById(R.id.logoutButton);


        customUser.whereEqualTo("username", KeyValueStore.getUserDetails().getUsername());
        customUser.whereEqualTo("email", KeyValueStore.getUserDetails().getEmail());
        customUser.whereEqualTo("password", KeyValueStore.getUserDetails().getPassword());
        customUser.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects.size() == 0) {
                    Toast.makeText(getContext(), "Error Unable to Fetched", Toast.LENGTH_SHORT).show();
                } else {
                    for (ParseObject reply : objects) {
                        usernameText.setText("Welcome " + reply.getString("username"));
                        balanceText.setText("Balance :" + reply.getString("balance"));
                        KeyValueStore.setUserBalance(reply.getString("balance"));
                    }
                }
            }
        });

        purchaseItems.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragmentFragment_to_purchaseItemFragment);
        });
        searchItems.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragmentFragment_to_viewProductFragment);
        });
        purchaseHistory.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragmentFragment_to_purchaseHistoryFragment);
        });
        viewAllItems.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragmentFragment_to_viewInventoryFragment);
        });

        logoutButton.setOnClickListener(view1 -> {
            KeyValueStore.clearPref();
            Navigation.findNavController(getView()).navigateUp();
        });

    }
}