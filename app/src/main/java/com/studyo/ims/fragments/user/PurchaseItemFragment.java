package com.studyo.ims.fragments.user;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.studyo.ims.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class PurchaseItemFragment extends Fragment {

    private static final int ZXING_CAMERA_PERMISSION = 1;
    private TextView productNameText, productCategoryText, productPriceText, productScanText, codeText;
    private Button scanButton, viewButton, purchaseButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_item, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        productNameText = view.findViewById(R.id.productNameText);
        productCategoryText = view.findViewById(R.id.productCategoryText);
        productPriceText = view.findViewById(R.id.productPriceText);
        productScanText = view.findViewById(R.id.productScanText);
        codeText = view.findViewById(R.id.codeText);
        scanButton = view.findViewById(R.id.scanButton);
        viewButton = view.findViewById(R.id.viewButton);
        purchaseButton = view.findViewById(R.id.purchaseButton);

        if (getArguments() != null) {
            codeText.setText(getArguments().getString("code"));
        }

        scanButton.setOnClickListener(view1 -> {
            launchActivity();
        });

        viewButton.setOnClickListener(view1 -> {
            if (getArguments() != null) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Items");
                query.whereEqualTo("product_qr_code", getArguments().getString("code"));
                query.findInBackground((objects, e) -> {
                    if (e == null) {
                        for (ParseObject reply : objects) {
                            productNameText.setText("Product Name :" + reply.getString("product_name"));
                            productCategoryText.setText("Product Category :" + reply.getString("product_category"));
                            productPriceText.setText("Product Price :" + reply.getString("product_price"));
                            productScanText.setText("Product BAR/QR Code :" + reply.getString("product_qr_code"));
                        }
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please scan first", Toast.LENGTH_SHORT).show();
            }

        });

        purchaseButton.setOnClickListener(view1 -> {
            if (productNameText.getText().toString().equals("Product Name :") &&
                    productCategoryText.getText().toString().equals("Product Category :") &&
                    productPriceText.getText().toString().equals("Product Price :") &&
                    productScanText.getText().toString().equals("Product Price :")
            ) {
                Toast.makeText(getContext(), "Please scan first", Toast.LENGTH_SHORT).show();
            }else {
                if (getArguments() != null) {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Items");
                    query.whereEqualTo("product_qr_code", getArguments().getString("code"));
                    query.findInBackground((objects, e) -> {
                        if (e == null) {
                            for (ParseObject reply : objects) {
                                ParseUser user = ParseUser.getCurrentUser();

                                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                ParseObject purchaseHistory = new ParseObject("PurchaseHistory");
                                purchaseHistory.put("username", user.getUsername());
                                purchaseHistory.put("product_name", reply.getString("product_name"));
                                purchaseHistory.put("product_category", reply.getString("product_category"));
                                purchaseHistory.put("product_price", reply.getString("product_price"));
                                purchaseHistory.put("product_qr_code", reply.getString("product_qr_code"));
                                purchaseHistory.put("date", currentDate);
                                purchaseHistory.put("time", currentTime);
                                purchaseHistory.saveInBackground();
                                int balance = Integer.parseInt(user.getString("balance"));
                                int productPrice = Integer.parseInt(reply.getString("product_price"));
                                int amount = balance - productPrice;
                                user.add("balance", String.valueOf(amount));
                                Toast.makeText(getContext(), "Item Purchased", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "Scan First", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void launchActivity() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Navigation.findNavController(getView()).navigate(R.id.action_purchaseItemFragment_to_purchaseScannerFragment);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Navigation.findNavController(getView()).navigate(R.id.action_purchaseItemFragment_to_purchaseScannerFragment);
                } else {
                    Toast.makeText(getActivity(), "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}