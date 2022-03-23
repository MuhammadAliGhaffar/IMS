package com.studyo.ims.fragments.admin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.studyo.ims.R;


public class DeleteProductFragment extends Fragment {
    private static final int ZXING_CAMERA_PERMISSION = 1;
    TextView codeText;
    private Button scanButton,deleteButton;

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

        if (getArguments() != null){
            Log.d("AliTag",getArguments().getString("amount")+"");
            codeText.setText(getArguments().getString("code"));
        }

        deleteButton.setOnClickListener(view1 -> {

        });

        scanButton.setOnClickListener(view1 -> {
            launchActivity();
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
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
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