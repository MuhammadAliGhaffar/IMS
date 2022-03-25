package com.studyo.ims.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.studyo.ims.R;


public class RegisterFragment extends Fragment {

    private static final String CLASS_NAME = "CustomUser";
    private EditText emailEditText, passwordEditText, usernameEditText;
    private Button registerButton;
    ParseObject customUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        customUser = new ParseObject(CLASS_NAME);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        registerButton = view.findViewById(R.id.registerButton);


        registerButton.setOnClickListener(view1 -> {
            if (emailEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty() || usernameEditText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please Enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                final ProgressDialog progressDialog1 = new ProgressDialog(getContext());
                progressDialog1.setMessage("Signing up " + usernameEditText.getText().toString());
                progressDialog1.setCanceledOnTouchOutside(false);
                progressDialog1.show();
                customUser.put("username", usernameEditText.getText().toString());
                customUser.put("email", emailEditText.getText().toString());
                customUser.put("password", passwordEditText.getText().toString());
                customUser.saveInBackground(e -> {
                    if (e == null){
                        Toast.makeText(getContext(), "Account Created Successfully please verify your email before Login", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_loginFragment);
                    } else {
                        Toast.makeText(getContext(), "Error Account Creation failed account could not be created :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog1.dismiss();
                });
            }
        });
    }
}