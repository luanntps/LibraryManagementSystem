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

import com.example.phuongnamlib.Models.Kind;
import com.example.phuongnamlib.R;

import java.util.ArrayList;

public class RecycleKindAdapter extends RecyclerView.Adapter<RecycleKindAdapter.ViewHolder> implements Filterable {
    ArrayList<Kind> alKind;
    ArrayList<Kind> OldAlKind;
    Context context;
    String urlImage;
    public RecycleKindAdapter(ArrayList<Kind> alKind, Context context) {
        this.alKind = alKind;
        this.context = context;
        this.OldAlKind = alKind;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_kind, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecycleKindAdapter.ViewHolder holder, int position) {
        holder.tvId.setText(position+1+"");
        holder.tvKindName.setText(alKind.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if(alKind!=null)
            return alKind.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    alKind = OldAlKind;
                }else{
                    ArrayList<Kind> list = new ArrayList<>();
                    for(Kind kind : OldAlKind){
                        if(kind.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(kind);
                        }
                    }
                    alKind = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = alKind;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                alKind = (ArrayList<Kind>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvId;
        TextView tvKindName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvIdKind);
            tvKindName= itemView.findViewById(R.id.tvKindName);
        }
    }
}
