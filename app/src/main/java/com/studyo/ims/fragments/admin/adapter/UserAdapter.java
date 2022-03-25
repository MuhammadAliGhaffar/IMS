package com.studyo.ims.fragments.admin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;
import com.studyo.ims.R;
import com.studyo.ims.fragments.user.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    private List<ParseObject> list;

    public UserAdapter(List<ParseObject> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        ParseObject object = list.get(position);
        holder.usernameText.setText(object.getString("username"));
        holder.relativeLayout.setOnClickListener(view -> {
            listener.onItemClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //Declarative interface
    private ItemClickListener listener;

    //set method
    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    //Defining interface
    public interface ItemClickListener {
        //Achieve the click method, passing the subscript.
        void onItemClick(ParseObject parseObject);
    }
}

class UserHolder extends RecyclerView.ViewHolder {
    TextView usernameText;
    RelativeLayout relativeLayout;

    public UserHolder(@NonNull View itemView) {
        super(itemView);
        usernameText = itemView.findViewById(R.id.usernameText);
        relativeLayout = itemView.findViewById(R.id.rLL);
    }
}
