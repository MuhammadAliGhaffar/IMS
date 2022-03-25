package com.studyo.ims;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.studyo.ims.fragments.utils.KeyValueStore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KeyValueStore.initPref(this);
    }
}