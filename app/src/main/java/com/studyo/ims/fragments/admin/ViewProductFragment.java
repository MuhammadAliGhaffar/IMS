package com.studyo.ims.fragments.admin;

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
import com.studyo.ims.R;


public class ViewProductFragment extends Fragment {

    private static final int ZXING_CAMERA_PERMISSION = 1;
    private TextView productNameText,productCategoryText,productPriceText,productScanText,codeText;
    private Button scanButton,viewButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_product, container, false);
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
                            productNameText.setText("Product Name :"+reply.getString("product_name"));
                            productCategoryText.setText("Product Category :"+reply.getString("product_category"));
                            productPriceText.setText("Product Price :"+reply.getString("product_price"));
                            productScanText.setText("Product BAR/QR Code :"+reply.getString("product_qr_code"));
                        }
                    }
                });
            }else {
                Toast.makeText(getContext(), "Please scan first", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void launchActivity() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Navigation.findNavController(getView()).navigate(R.id.action_viewProductFragment_to_viewScannerFragment);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Navigation.findNavController(getView()).navigate(R.id.action_viewProductFragment_to_viewScannerFragment);
                } else {
                    Toast.makeText(getActivity(), "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }


}