package com.example.phuongnamlib.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuongnamlib.Adapters.RecycleMemberAdapter;
import com.example.phuongnamlib.DAO.MemberManager;
import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Helpers.RecyclerItemClickListener;
import com.example.phuongnamlib.Models.Member;
import com.example.phuongnamlib.Models.MemberToItem;
import com.example.phuongnamlib.R;

import java.util.ArrayList;

public class InputNewMemberActivity extends AppCompatActivity {

    ArrayList<MemberToItem> alMember;
    RecyclerView rcvForResultMember;
    MemberManager memberManager;
    SearchView swSearch;
    PhuongNamLibSQLite phuongNamLibSQLite;
    public static final String EXTRA_IDMEMBER_DATA = "EXTRA_IDMEMBER_DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_new_member);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        String token = getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE).getString("currentToken", "");
        rcvForResultMember = findViewById(R.id.rcvForResultMember);

        swSearch = findViewById(R.id.swSearchForResultMember);

        phuongNamLibSQLite=new PhuongNamLibSQLite(this);
        memberManager = new MemberManager(phuongNamLibSQLite, token);
        alMember = memberManager.LoadAllMemberToItem();
        FillRecyclerView();
        RecyclerClickEvent();
    }
    void FillRecyclerView(){
        rcvForResultMember.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvForResultMember.setLayoutManager(manager);
        RecycleMemberAdapter adapter = new RecycleMemberAdapter(alMember,this);
        rcvForResultMember.setAdapter(adapter);
        swSearch.setQueryHint("Nhập vào để tìm kiếm");
        swSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        rcvForResultMember.addOnItemTouchListener(new RecyclerItemClickListener(this, rcvForResultMember, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Intent data = new Intent();
                data.putExtra(EXTRA_IDMEMBER_DATA, alMember.get(position).getName()+", ID: "+alMember.get(position).getId());
                setResult(Activity.RESULT_OK, data);
                finish();
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }

}