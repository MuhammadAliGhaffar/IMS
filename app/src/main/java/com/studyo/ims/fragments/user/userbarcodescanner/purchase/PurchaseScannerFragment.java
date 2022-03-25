package com.studyo.ims.fragments.user.userbarcodescanner.purchase;

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
import com.studyo.ims.fragments.admin.adminbarcodescanner.add.AddScannerFragment;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class PurchaseScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private RelativeLayout purchaseScannerFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_purchase_scanner, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        purchaseScannerFragment = view.findViewById(R.id.purchaseScannerFragment);
        mScannerView = new ZXingScannerView(getActivity());
        purchaseScannerFragment.addView(mScannerView);
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
        Navigation.findNavController(getView()).navigate(R.id.action_purchaseScannerFragment_to_purchaseItemFragment, bundle);
        Handler handler = new Handler();
        handler.postDelayed(() -> mScannerView.resumeCameraPreview(PurchaseScannerFragment.this), 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}