package com.example.phuongnamlib.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.phuongnamlib.Adapters.RecycleBookAdapter;
import com.example.phuongnamlib.Adapters.RecycleLibrarianAdapter;
import com.example.phuongnamlib.DAO.BookManager;
import com.example.phuongnamlib.DAO.LibrarianManager;
import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Helpers.RecyclerItemClickListener;
import com.example.phuongnamlib.Models.BookToItem;
import com.example.phuongnamlib.Models.Librarian;
import com.example.phuongnamlib.R;

import java.util.ArrayList;

public class InputNewBookActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_BOOK = 1;

    ArrayList<BookToItem> alBook;
    RecyclerView rcvForResultBook;
    BookManager bookManager;
    SearchView swSearch;
    PhuongNamLibSQLite phuongNamLibSQLite;
    public static final String EXTRA_BOOKNAME_DATA = "EXTRA_BOOKNAME_DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_new_book);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        String token = getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE).getString("currentToken", "");
        rcvForResultBook = findViewById(R.id.rcvForResultBook);

        swSearch = findViewById(R.id.swSearchForResultBook);

        phuongNamLibSQLite=new PhuongNamLibSQLite(this);
        bookManager = new BookManager(phuongNamLibSQLite, token);
        alBook = bookManager.GetAllBookToItem();
        FillRecyclerView();
        RecyclerClickEvent();
    }
    void FillRecyclerView(){
        rcvForResultBook.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvForResultBook.setLayoutManager(manager);
        RecycleBookAdapter adapter = new RecycleBookAdapter(alBook,this);
        rcvForResultBook.setAdapter(adapter);
        swSearch.setQueryHint("Nhập vào để tìm kiếm");
        swSearch.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }
    public void RecyclerClickEvent() {
        rcvForResultBook.addOnItemTouchListener(new RecyclerItemClickListener(this, rcvForResultBook, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Intent data = new Intent();
                data.putExtra(EXTRA_BOOKNAME_DATA, alBook.get(position).getName());
                setResult(Activity.RESULT_OK, data);
                finish();
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }

}