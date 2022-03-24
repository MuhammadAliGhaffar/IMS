package com.studyo.ims.fragments.admin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.studyo.ims.R;


public class AddProductFragment extends Fragment {

    private static final int ZXING_CAMERA_PERMISSION = 1;
    private static final String CLASS_NAME = "Items";
    private EditText productNameEditText, categoryEditText, priceEditText;
    private Button addItemButton, scanButton;
    private ParseObject parseObject;
    private TextView barcodeTextView;

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
        barcodeTextView = view.findViewById(R.id.barcodeTextView);

        if (getArguments() != null) {
            barcodeTextView.setText(getArguments().getString("code"));
            productNameEditText.setText(getArguments().getString("product_name"));
            priceEditText.setText(getArguments().getString("product_price"));
            categoryEditText.setText(getArguments().getString("product_category"));
        }

        scanButton.setOnClickListener(view1 -> {
            launchActivity();
        });

        addItemButton.setOnClickListener(view1 -> {
            if (productNameEditText.getText().toString().isEmpty() || categoryEditText.getText().toString().isEmpty() || priceEditText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please Enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                parseObject.put("product_name", productNameEditText.getText().toString());
                parseObject.put("product_price", priceEditText.getText().toString());
                parseObject.put("product_category", categoryEditText.getText().toString());
                parseObject.put("product_qr_code", barcodeTextView.getText().toString());
                parseObject.saveInBackground(e -> {
                    if (e != null) {
                        e.printStackTrace();
                        Log.d("AliTag", e.getLocalizedMessage());
                    } else {
                        Navigation.findNavController(getView()).navigate(R.id.action_addProductFragment_to_dashboardFragment);
                        Toast.makeText(getContext(), "Object saved. " + parseObject.getObjectId(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void launchActivity() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("product_name", productNameEditText.getText().toString());
            bundle.putString("product_price", priceEditText.getText().toString());
            bundle.putString("product_category", categoryEditText.getText().toString());
            Navigation.findNavController(getView()).navigate(R.id.action_addProductFragment_to_addScannerFragment2, bundle);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Navigation.findNavController(getView()).navigate(R.id.action_addProductFragment_to_addScannerFragment2);
                } else {
                    Toast.makeText(getActivity(), "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}