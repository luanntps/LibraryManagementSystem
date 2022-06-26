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

import com.example.phuongnamlib.Models.Book;
import com.example.phuongnamlib.Models.BookToItem;
import com.example.phuongnamlib.R;

import java.util.ArrayList;

public class RecycleBookAdapter extends RecyclerView.Adapter<RecycleBookAdapter.ViewHolder> implements Filterable {
    ArrayList<BookToItem> alBook;
    ArrayList<BookToItem> OldAlBook;
    Context context;
    String urlImage;
    public RecycleBookAdapter(ArrayList<BookToItem> alBook, Context context) {
        this.alBook = alBook;
        this.context = context;
        this.OldAlBook = alBook;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecycleBookAdapter.ViewHolder holder, int position) {
        holder.tvId.setText(position+1+"");
        holder.tvBookName.setText(alBook.get(position).getName());
        holder.tvBookPrice.setText(alBook.get(position).getPrice()+"");
        holder.tvKindName.setText(alBook.get(position).getNameKind());
    }

    @Override
    public int getItemCount() {
        if(alBook!=null)
            return alBook.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    alBook = OldAlBook;
                }else{
                    ArrayList<BookToItem> list = new ArrayList<>();
                    for(BookToItem book : OldAlBook){
                        if(book.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(book);
                        }
                    }
                    alBook = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = alBook;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                alBook = (ArrayList<BookToItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvId;
        TextView tvBookName;
        TextView tvBookPrice;
        TextView tvKindName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvIdBook);
            tvBookName= itemView.findViewById(R.id.tvBookNameBook);
            tvBookPrice = itemView.findViewById(R.id.tvBookPrice);
            tvKindName = itemView.findViewById(R.id.tvBookKindBook);
        }
    }
}
