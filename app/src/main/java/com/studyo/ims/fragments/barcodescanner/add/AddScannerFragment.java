package com.studyo.ims.fragments.barcodescanner.add;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.zxing.Result;
import com.studyo.ims.R;
import com.studyo.ims.fragments.barcodescanner.delete.DeleteScannerFragment;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class AddScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private RelativeLayout addScannerFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_scanner, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        addScannerFragment = view.findViewById(R.id.addScannerFragment);
        mScannerView = new ZXingScannerView(getActivity());
        addScannerFragment.addView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        Bundle bundle = new Bundle();
        bundle.putString("code", rawResult.getText());
        Navigation.findNavController(getView()).navigate(R.id.action_addScannerFragment2_to_addProductFragment, bundle);
        Handler handler = new Handler();
        handler.postDelayed(() -> mScannerView.resumeCameraPreview(AddScannerFragment.this), 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}