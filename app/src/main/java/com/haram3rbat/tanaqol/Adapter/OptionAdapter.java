package com.haram3rbat.tanaqol.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haram3rbat.tanaqol.Model.UserModel;
import com.haram3rbat.tanaqol.R;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder>{

    private Context context;
    List<UserModel> list;


    public OptionAdapter(Context context, List<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        UserModel user = list.get(position);

        holder.name_user.setText(user.getName());
        holder.email_user.setText(user.getEmail());
        holder.password_user.setText(user.getPassword());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_user, email_user, password_user;
        ImageView image_user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            password_user=itemView.findViewById(R.id.password_user);
            email_user=itemView.findViewById(R.id.email_user);
            name_user=itemView.findViewById(R.id.name_user);
        }

    }

}
