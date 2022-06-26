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

import com.example.phuongnamlib.Models.CallCard;
import com.example.phuongnamlib.Models.CallcardToItem;
import com.example.phuongnamlib.R;

import java.util.ArrayList;

public class RecycleCallcardAdapter extends RecyclerView.Adapter<RecycleCallcardAdapter.ViewHolder> implements Filterable {
    ArrayList<CallcardToItem> alCallCard;
    ArrayList<CallcardToItem> OldAlCallCard;
    Context context;
    String urlImage;
    public RecycleCallcardAdapter(ArrayList<CallcardToItem> alCallCard, Context context) {
        this.alCallCard = alCallCard;
        this.context = context;
        this.OldAlCallCard = alCallCard;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_callcard, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull RecycleCallcardAdapter.ViewHolder holder, int position) {
        holder.tvIdCallcard.setText(position+1+"");
        holder.tvNameMemberCallcard.setText(alCallCard.get(position).getMemberName()+"");
        holder.tvIdLibrarianCallcard.setText(alCallCard.get(position).getIdLibrarian());
        holder.tvBookNameCallcard.setText(alCallCard.get(position).getBookName());
        holder.tvBeginDate.setText(alCallCard.get(position).getBeginDate());
        holder.tvExpiredsDate.setText(alCallCard.get(position).getExpiresDate());
    }

    @Override
    public int getItemCount() {
        if(alCallCard!=null)
            return alCallCard.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    alCallCard = OldAlCallCard;
                }else{
                    ArrayList<CallcardToItem> list = new ArrayList<>();
                    for(CallcardToItem callcard : OldAlCallCard){
                        if((callcard.getMemberName()+"").toLowerCase().contains(strSearch.toLowerCase())
                            ){
                            list.add(callcard);
                        }
                    }
                    alCallCard = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = alCallCard;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                alCallCard = (ArrayList<CallcardToItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIdCallcard;
        TextView tvNameMemberCallcard;
        TextView tvIdLibrarianCallcard;
        TextView tvBookNameCallcard;
        TextView tvBeginDate;
        TextView tvExpiredsDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdCallcard = itemView.findViewById(R.id.tvIdCallcard);
            tvNameMemberCallcard = itemView.findViewById(R.id.tvIdMemberCallcard);
            tvBookNameCallcard= itemView.findViewById(R.id.tvBookNameCallcard);
            tvBeginDate = itemView.findViewById(R.id.tvBeginDate);
            tvExpiredsDate = itemView.findViewById(R.id.tvExpiredsDate);
            tvIdLibrarianCallcard = itemView.findViewById(R.id.tvIdLibrarianCallcard);
        }
    }
}
