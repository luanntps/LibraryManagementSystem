package com.example.phuongnamlib.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phuongnamlib.Models.LibrarianToItem;
import com.example.phuongnamlib.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleLibrarianAdapter extends RecyclerView.Adapter<RecycleLibrarianAdapter.ViewHolder> implements Filterable {
    ArrayList<LibrarianToItem> alLibrarianToItem;
    ArrayList<LibrarianToItem> OldAlLibrarianToItem;
    Context context;
    String urlImage;
    public RecycleLibrarianAdapter(ArrayList<LibrarianToItem> alLibrarianToItem, Context context) {
        this.alLibrarianToItem = alLibrarianToItem;
        this.context = context;
        this.OldAlLibrarianToItem = alLibrarianToItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_librarian, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecycleLibrarianAdapter.ViewHolder holder, int position) {
        holder.tvIdentity.setText(position+1+"");
        holder.tvUsername.setText(alLibrarianToItem.get(position).getId());
        holder.tvName.setText(alLibrarianToItem.get(position).getName());
        holder.tvPassword.setText(alLibrarianToItem.get(position).getPassword());
        holder.tvRole.setText(alLibrarianToItem.get(position).getNameRole());
        holder.btnShowPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    holder.tvPassword.setTransformationMethod(new PasswordTransformationMethod());
                    return true;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.tvPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(alLibrarianToItem!=null)
            return alLibrarianToItem.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    alLibrarianToItem = OldAlLibrarianToItem;
                }else{
                    ArrayList<LibrarianToItem> list = new ArrayList<>();
                    for(LibrarianToItem librarian : OldAlLibrarianToItem){
                        if(librarian.getId().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(librarian);
                        }
                    }
                    alLibrarianToItem = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = alLibrarianToItem;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                alLibrarianToItem = (ArrayList<LibrarianToItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIdentity;
        TextView tvUsername;
        TextView tvName;
        TextView tvPassword;
        TextView tvRole;
        ImageButton btnShowPassword;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdentity = itemView.findViewById(R.id.tvIdentity);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvName= itemView.findViewById(R.id.tvName);
            tvPassword = itemView.findViewById(R.id.tvPassword);
            btnShowPassword = itemView.findViewById(R.id.btnShowPassword);
            tvRole = itemView.findViewById(R.id.tvRole);
        }
    }
}
