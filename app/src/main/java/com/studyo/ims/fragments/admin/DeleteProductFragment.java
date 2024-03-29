package com.studyo.ims.fragments.admin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.studyo.ims.R;

import java.util.List;


public class DeleteProductFragment extends Fragment {
    private static final int ZXING_CAMERA_PERMISSION = 1;
    TextView codeText;
    private Button scanButton, deleteButton;
    private ImageView backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_product, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        codeText = view.findViewById(R.id.codeText);
        scanButton = view.findViewById(R.id.scanButton);
        deleteButton = view.findViewById(R.id.deleteButton);
        backButton = view.findViewById(R.id.backButton);

        if (getArguments() != null) {
            codeText.setText(getArguments().getString("code"));
        }

        deleteButton.setOnClickListener(view1 -> {
            if (getArguments() != null) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Items");
                query.whereEqualTo("product_qr_code", getArguments().getString("code"));
                query.findInBackground((objects, e) -> {
                    if (e == null) {
                        for (ParseObject reply : objects) {
                            reply.deleteInBackground();
                        }
                        Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.action_deleteProductFragment_to_dashboardFragment);
                    }
                });
            }

        });

        scanButton.setOnClickListener(view1 -> {
            launchActivity();
        });

        backButton.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });
    }

    public void launchActivity() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Navigation.findNavController(getView()).navigate(R.id.action_deleteProductFragment_to_deleteScannerFragment);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Navigation.findNavController(getView()).navigate(R.id.action_deleteProductFragment_to_deleteScannerFragment);
                } else {
                    Toast.makeText(getActivity(), "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }


}