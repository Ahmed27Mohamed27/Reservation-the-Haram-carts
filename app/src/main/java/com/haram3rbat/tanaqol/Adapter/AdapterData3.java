package com.haram3rbat.tanaqol.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.haram3rbat.tanaqol.Model.Fetch3;
import com.haram3rbat.tanaqol.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterData3 extends RecyclerView.Adapter<AdapterData3.MyviewHolder> {
    ArrayList<Fetch3> fetches;
    Context context;
    DatabaseReference root, root1;

    public AdapterData3(ArrayList<Fetch3> fetches, Context context) {
        this.fetches = fetches;
        this.context = context;
    }
    public void setFilteredList(List<Fetch3> filteredList){
        this.fetches = (ArrayList<Fetch3>) filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_data3,parent,false);
        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Fetch3 f = fetches.get(position);
        holder.name.setText(f.getName());
        holder.email.setText(f.getEmail());
        holder.password.setText(f.getMobile());
        Glide.with(context).load(f.getImage()).placeholder(R.drawable.logo_bg).into(holder.img1);
        root = FirebaseDatabase.getInstance().getReference()
                .child("Users_Motabara3").child(f.getUid());
        root1 = FirebaseDatabase.getInstance().getReference();
        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap map2 = new HashMap();
                map2.put("name_hag", f.getName_hag());
                map2.put("location_hag", f.getLocation_hag());
                map2.put("numPhone_hag", f.getMobile_hag());
                map2.put("number_old_hag", f.getNumber_old_hag());
                map2.put("sick_hag", f.getSick_hag());
                map2.put("uid", f.getUid_hag());

                root1.child("Users_Motabara3").child(f.getUid()).child("InfoHag").updateChildren(map2);

                HashMap map = new HashMap();
                map.put("ListMotabaraOFF", "done");

                root1.child("Users").child(f.getUid_hag()).updateChildren(map);
                Toast.makeText(context, "تمت الموافقة علي المتطوع وسوف يتم اجراء العملية", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return fetches.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder{
        TextView name,email,password,Accept;
        ImageView img1;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            email = itemView.findViewById(R.id.txtCountry);
            password = itemView.findViewById(R.id.txtId);
            Accept = itemView.findViewById(R.id.Accept);
            img1 = itemView.findViewById(R.id.img1);
        }
    }
}
