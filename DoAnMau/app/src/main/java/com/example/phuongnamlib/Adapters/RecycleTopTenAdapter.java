package com.example.phuongnamlib.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuongnamlib.Models.TopTenBook;
import com.example.phuongnamlib.R;

import java.util.ArrayList;

public class RecycleTopTenAdapter extends RecyclerView.Adapter<RecycleTopTenAdapter.ViewHolder>{
    ArrayList<TopTenBook> alBook;
    Context context;
    public RecycleTopTenAdapter(ArrayList<TopTenBook> alBook, Context context) {
        this.alBook = alBook;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_top_ten_book, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecycleTopTenAdapter.ViewHolder holder, int position) {
        holder.tvId.setText(position+1+"");
        holder.tvBookName.setText(alBook.get(position).getName());
        holder.tvKindName.setText(alBook.get(position).getKind());
        holder.tvBookCount.setText(alBook.get(position).getCount()+"");
    }

    @Override
    public int getItemCount() {
        if(alBook!=null)
            return alBook.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvId;
        TextView tvBookName;
        TextView tvKindName;
        TextView tvBookCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvTop10Id);
            tvBookName= itemView.findViewById(R.id.tvTop10Name);
            tvBookCount = itemView.findViewById(R.id.tvTop10Count);
            tvKindName = itemView.findViewById(R.id.tvTop10Kind);
        }
    }
}
