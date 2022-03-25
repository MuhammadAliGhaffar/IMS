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
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.studyo.ims.R;
import com.studyo.ims.fragments.user.model.User;
import com.studyo.ims.fragments.utils.KeyValueStore;


public class LoginFragment extends Fragment {

    private static final String CLASS_NAME = "CustomUser";
    private EditText usernameEditText, passwordEditText;
    private Button loginButton, dontHaveAccountButton;
    ParseQuery<ParseObject> customUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        customUser = ParseQuery.getQuery(CLASS_NAME);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        dontHaveAccountButton = view.findViewById(R.id.dontHaveAccountButton);


        loginButton.setOnClickListener(view1 -> {
            if (usernameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please Enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                final ProgressDialog progressDialogg = new ProgressDialog(getContext());
                progressDialogg.setMessage("Logging in");
                progressDialogg.setCanceledOnTouchOutside(false);
                progressDialogg.show();
                if (usernameEditText.getText().toString().equals("admin") && passwordEditText.getText().toString().equals("admin")) {
                    Toast.makeText(getContext(), "Successful Login Welcome back " + usernameEditText.getText().toString() + " !", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_dashboardFragment);
                    progressDialogg.dismiss();
                } else {
                    customUser.whereEqualTo("username", usernameEditText.getText().toString());
                    customUser.whereEqualTo("password", passwordEditText.getText().toString());
                    customUser.findInBackground((objects, e) -> {
                        if (e == null) {
                            if (objects.size() == 0) {
                                Toast.makeText(getContext(), "Login Fail please re-try", Toast.LENGTH_SHORT).show();
                            } else {
                                for (ParseObject reply : objects) {
                                    KeyValueStore.userDetails(new User(reply.getString("username"),reply.getString("email"),reply.getString("password")));
                                    Toast.makeText(getContext(), "Successful Login Welcome back " + reply.getString("username") + " !", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragmentFragment);
                                }
                            }
                        }
                        progressDialogg.dismiss();
                    });

                }
            }
        });

        dontHaveAccountButton.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }
}