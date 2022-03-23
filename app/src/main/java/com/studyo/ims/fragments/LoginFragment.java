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

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.studyo.ims.R;


public class LoginFragment extends Fragment {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, dontHaveAccountButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        dontHaveAccountButton = view.findViewById(R.id.dontHaveAccountButton);


        loginButton.setOnClickListener(view1 -> {
            if (usernameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please Enter all fields", Toast.LENGTH_SHORT).show();
            } else {

                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), (parseUser, e) -> {
                    final ProgressDialog progressDialogg = new ProgressDialog(getContext());
                    progressDialogg.setMessage("Logging in");
                    progressDialogg.setCanceledOnTouchOutside(false);
                    progressDialogg.show();
                    if (parseUser != null) {
                        Toast.makeText(getContext(), "Successful Login Welcome back " + usernameEditText.getText().toString() + " !", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_dashboardFragment);
                    } else {
                        Toast.makeText(getContext(), "Login Fail " + e.getMessage() + "please re-try", Toast.LENGTH_SHORT).show();
                        ParseUser.logOut();
                    }
                    progressDialogg.dismiss();
                });
            }
        });

        dontHaveAccountButton.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }
}