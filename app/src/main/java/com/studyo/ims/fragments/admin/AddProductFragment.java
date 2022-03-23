package com.studyo.ims.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.studyo.ims.R;


public class AddProductFragment extends Fragment {


    private static final String CLASS_NAME = "Items";
    private EditText productNameEditText, categoryEditText, priceEditText;
    private Button addItemButton, scanButton;
    private ParseObject parseObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        parseObject = new ParseObject(CLASS_NAME);
        productNameEditText = view.findViewById(R.id.productNameEditText);
        categoryEditText = view.findViewById(R.id.categoryEditText);
        priceEditText = view.findViewById(R.id.priceEditText);
        addItemButton = view.findViewById(R.id.addItemButton);
        scanButton = view.findViewById(R.id.scanButton);

        addItemButton.setOnClickListener(view1 -> {
            if (productNameEditText.getText().toString().isEmpty() || categoryEditText.getText().toString().isEmpty()|| priceEditText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please Enter all fields", Toast.LENGTH_SHORT).show();
            }else {
                parseObject.put("product_name", productNameEditText.getText().toString());
                parseObject.put("product_price", priceEditText.getText().toString());
                parseObject.put("product_category", categoryEditText.getText().toString());
                parseObject.put("product_qr_code", "14526");
                parseObject.saveInBackground(e -> {
                    if (e != null) {
                        e.printStackTrace();
                        Log.d("AliTag", e.getLocalizedMessage());
                    } else {
                        Navigation.findNavController(getView()).navigate(R.id.action_addProductFragment_to_dashboardFragment);
                        Toast.makeText(getContext(), "Object saved. "+ parseObject.getObjectId(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}