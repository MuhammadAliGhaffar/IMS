package com.studyo.ims.fragments.admin;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.studyo.ims.R;
import com.studyo.ims.fragments.user.model.User;
import com.studyo.ims.fragments.utils.KeyValueStore;


public class AddUserBalanceFragment extends Fragment {

    private static final String CLASS_NAME = "CustomUser";
    private TextView usernameText;
    private EditText balanceEditText;
    private Button submitButton;
    private ImageView backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user_balance, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        usernameText = view.findViewById(R.id.usernameText);
        balanceEditText = view.findViewById(R.id.balanceEditText);
        submitButton = view.findViewById(R.id.submitButton);
        backButton = view.findViewById(R.id.backButton);

        if (getArguments() != null) {
            usernameText.setText(getArguments().getString("username"));
        }

        submitButton.setOnClickListener(view1 -> {
            if (balanceEditText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Enter some balance first", Toast.LENGTH_SHORT).show();
            } else {
                if (getArguments() != null) {
                    final ProgressDialog progressDialogg = new ProgressDialog(getContext());
                    progressDialogg.setMessage("Please wait updating balance...");
                    progressDialogg.setCanceledOnTouchOutside(false);
                    progressDialogg.show();
                    ParseQuery<ParseObject> updateQuery = ParseQuery.getQuery(CLASS_NAME);
                    updateQuery.getInBackground(getArguments().getString("objectId"), (data, error) -> {
                        if (error == null) {
                            data.put("balance",balanceEditText.getText().toString() );
                            data.saveInBackground();
                            Toast.makeText(getContext(), "Successful balance added", Toast.LENGTH_SHORT).show();
                            progressDialogg.dismiss();
                            Navigation.findNavController(view).navigateUp();
                        }
                    });
                }

            }
        });

        backButton.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });


    }
}