package com.studyo.ims.fragments.admin.adminbarcodescanner.view;

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

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ViewScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private RelativeLayout viewScannerFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_scanner, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        viewScannerFragment = view.findViewById(R.id.viewScannerFragment);
        mScannerView = new ZXingScannerView(getActivity());
        viewScannerFragment.addView(mScannerView);
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
        Navigation.findNavController(getView()).navigate(R.id.action_viewScannerFragment_to_viewProductFragment, bundle);
        Handler handler = new Handler();
        handler.postDelayed(() -> mScannerView.resumeCameraPreview(ViewScannerFragment.this), 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}