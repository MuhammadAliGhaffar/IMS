package com.studyo.ims.fragments.admin;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.studyo.ims.R;
import com.studyo.ims.fragments.admin.adapter.InventoryAdapter;
import com.studyo.ims.fragments.admin.adapter.UserAdapter;


public class AllUsersFragment extends Fragment {

    private static final String CLASS_NAME = "CustomUser";
    private ParseQuery<ParseObject> parseObject;
    private RecyclerView recyclerViews;
    private UserAdapter userAdapter;
    private ImageView backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_users, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        backButton = view.findViewById(R.id.backButton);
        parseObject = ParseQuery.getQuery(CLASS_NAME);
        final ProgressDialog progressDialogg = new ProgressDialog(getContext());
        progressDialogg.setMessage("Please wait fetching all users...");
        progressDialogg.setCanceledOnTouchOutside(false);
        progressDialogg.show();
        recyclerViews = view.findViewById(R.id.recyclerViews);
        parseObject.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects.size() == 0){
                    Toast.makeText(getContext(), "No User found", Toast.LENGTH_SHORT).show();
                }else {
                    userAdapter = new UserAdapter(objects);
                    recyclerViews.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViews.setAdapter(userAdapter);
                }
                progressDialogg.dismiss();
            }

            userAdapter.setListener(parseObject -> {
                Bundle bundle = new Bundle();
                bundle.putString("username", parseObject.getString("username"));
                bundle.putString("password", parseObject.getString("password"));
                bundle.putString("balance", parseObject.getString("balance"));
                bundle.putString("objectId", parseObject.getObjectId());

                Navigation.findNavController(getView()).navigate(R.id.action_allUsersFragment_to_addUserBalanceFragment,bundle);
            });
        });

        backButton.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });
    }
}