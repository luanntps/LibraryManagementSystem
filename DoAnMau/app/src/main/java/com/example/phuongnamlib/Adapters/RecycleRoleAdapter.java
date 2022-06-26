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

import com.example.phuongnamlib.Models.Role;
import com.example.phuongnamlib.R;

import java.util.ArrayList;

public class RecycleRoleAdapter extends RecyclerView.Adapter<RecycleRoleAdapter.ViewHolder> implements Filterable {
    ArrayList<Role> alRole;
    ArrayList<Role> OldAlRole;
    Context context;
    String urlImage;
    public RecycleRoleAdapter(ArrayList<Role> alRole, Context context) {
        this.alRole = alRole;
        this.context = context;
        this.OldAlRole = alRole;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_role, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecycleRoleAdapter.ViewHolder holder, int position) {
        holder.tvId.setText(position+1+"");
        holder.tvNameRole.setText(alRole.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if(alRole!=null)
            return alRole.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    alRole = OldAlRole;
                }else{
                    ArrayList<Role> list = new ArrayList<>();
                    for(Role role : OldAlRole){
                        if(role.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(role);
                        }
                    }
                    alRole = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = alRole;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                alRole = (ArrayList<Role>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvId;
        TextView tvNameRole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvIdRole);
            tvNameRole= itemView.findViewById(R.id.tvNameRole);
        }
    }
}
