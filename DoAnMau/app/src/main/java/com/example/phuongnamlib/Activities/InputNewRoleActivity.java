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

import com.example.phuongnamlib.Adapters.RecycleRoleAdapter;
import com.example.phuongnamlib.DAO.RoleManager;
import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Helpers.RecyclerItemClickListener;
import com.example.phuongnamlib.Models.Role;
import com.example.phuongnamlib.R;

import java.util.ArrayList;

public class InputNewRoleActivity extends AppCompatActivity {

    ArrayList<Role> alRole;
    RecyclerView rcvForResultRole;
    RoleManager roleManager;
    SearchView swSearch;
    PhuongNamLibSQLite phuongNamLibSQLite;
    public static final String EXTRA_IDROLE_DATA = "EXTRA_IDROLE_DATA";
    public static final String EXTRA_NAMEROLE_DATA = "EXTRA_NAMEROLE_DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_new_role);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));


        String token = getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE).getString("currentToken", "");
        rcvForResultRole = findViewById(R.id.rcvForResultRole);

        swSearch = findViewById(R.id.swSearchForResultRole);

        phuongNamLibSQLite=new PhuongNamLibSQLite(this);
        roleManager = new RoleManager(phuongNamLibSQLite, token);
        alRole = roleManager.GetAllRole();
        FillRecyclerView();
        RecyclerClickEvent();
    }
    void FillRecyclerView(){
        rcvForResultRole.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvForResultRole.setLayoutManager(manager);
        RecycleRoleAdapter adapter = new RecycleRoleAdapter(alRole,this);
        rcvForResultRole.setAdapter(adapter);
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
        rcvForResultRole.addOnItemTouchListener(new RecyclerItemClickListener(this, rcvForResultRole, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent data = new Intent();
                data.putExtra(EXTRA_NAMEROLE_DATA, alRole.get(position).getName());
                data.putExtra(EXTRA_IDROLE_DATA, alRole.get(position).getId());
                setResult(Activity.RESULT_OK, data);
                finish();
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }
}