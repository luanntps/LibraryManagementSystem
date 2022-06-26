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

import com.example.phuongnamlib.Models.Member;
import com.example.phuongnamlib.Models.MemberToItem;
import com.example.phuongnamlib.R;

import java.util.ArrayList;

public class RecycleMemberAdapter extends RecyclerView.Adapter<RecycleMemberAdapter.ViewHolder> implements Filterable {
    ArrayList<MemberToItem> alMember;
    ArrayList<MemberToItem> OldAlMember;
    Context context;
    String urlImage;
    public RecycleMemberAdapter(ArrayList<MemberToItem> alMember, Context context) {
        this.alMember = alMember;
        this.context = context;
        this.OldAlMember = alMember;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_member, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecycleMemberAdapter.ViewHolder holder, int position) {
        holder.tvIdentityMember.setText(position+1+"");
        holder.tvIdMember.setText(alMember.get(position).getId()+"");
        holder.tvMemberName.setText(alMember.get(position).getName());
        holder.tvCountTime.setText(alMember.get(position).getCountTime()+"");
        holder.tvMemberType.setText(alMember.get(position).getNameMemberType());
    }

    @Override
    public int getItemCount() {
        if(alMember!=null)
            return alMember.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    alMember = OldAlMember;
                }else{
                    ArrayList<MemberToItem> list = new ArrayList<>();
                    for(MemberToItem member : OldAlMember){
                        if((member.getId()+"").toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(member);
                        }
                    }
                    alMember = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = alMember;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                alMember = (ArrayList<MemberToItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIdentityMember;
        TextView tvIdMember;
        TextView tvMemberName;
        TextView tvCountTime;
        TextView tvMemberType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdMember = itemView.findViewById(R.id.tvIdMember);
            tvIdentityMember = itemView.findViewById(R.id.tvIdentityMember);
            tvMemberName = itemView.findViewById(R.id.tvMemberName);
            tvCountTime = itemView.findViewById(R.id.tvCountTime);
            tvMemberType = itemView.findViewById(R.id.tvMemberType);
        }
    }
}
