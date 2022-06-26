package com.example.phuongnamlib.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.AttributionSource;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phuongnamlib.Adapters.RecycleBookAdapter;
import com.example.phuongnamlib.Adapters.RecycleCallcardAdapter;
import com.example.phuongnamlib.Adapters.RecycleKindAdapter;
import com.example.phuongnamlib.Adapters.RecycleMemberAdapter;
import com.example.phuongnamlib.DAO.BookManager;
import com.example.phuongnamlib.DAO.CallCardManager;
import com.example.phuongnamlib.DAO.KindManager;
import com.example.phuongnamlib.DAO.MemberManager;
import com.example.phuongnamlib.Fragments.HomeFragment;
import com.example.phuongnamlib.Fragments.RevenueFragment;
import com.example.phuongnamlib.Fragments.RevenueFragmentYear;
import com.example.phuongnamlib.Fragments.TopBorrowedFragment;
import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Helpers.RecyclerItemClickListener;
import com.example.phuongnamlib.Models.Book;
import com.example.phuongnamlib.Models.BookToItem;
import com.example.phuongnamlib.Models.CallCard;
import com.example.phuongnamlib.Models.CallcardToItem;
import com.example.phuongnamlib.Models.Kind;
import com.example.phuongnamlib.Models.Librarian;
import com.example.phuongnamlib.Models.LibrarianToItem;
import com.example.phuongnamlib.Models.MemberToItem;
import com.example.phuongnamlib.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class UserMainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_BOOK = 1,REQUEST_CODE_MEMBER=2;

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    SearchView searchView;

    String this_librian_id;

    TextView tvOffline;

    LinearLayout btnNewItem;

    SharedPreferences sharedPref;

    PhuongNamLibSQLite phuongNamLibSQLite;
    String token;

    boolean isOffline;

    int pos;

    RelativeLayout contain;
    View recyclerViewLayout;
    RecyclerView recyclerView;

    RelativeLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        container = findViewById(R.id.body_container);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        contain = findViewById(R.id.body_container);

        token = getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE).getString("currentToken", "");
        phuongNamLibSQLite = new PhuongNamLibSQLite(UserMainActivity.this);


        tvOffline = findViewById(R.id.tvOffline);
        SharedPreferences checkOffline;
        checkOffline = getSharedPreferences("Offline", Context.MODE_PRIVATE);
        isOffline = checkOffline.getBoolean("isOffline", false);

        if (isOffline) {
            tvOffline.setVisibility(View.VISIBLE);
            btnNewItem.setVisibility(View.GONE);

        }

        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer_layout);

        searchView = findViewById(R.id.swUserSearch);
        searchView.setQueryHint("Nhập vào để tìm kiếm");

        sharedPref = getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE);
        this_librian_id = sharedPref.getString("currentId", null);
        // CHANGE NAME OF NAVIGATION DRAWER'S HEADER
        View headerView = navigationView.getHeaderView(0);
        TextView tvName = headerView.findViewById(R.id.tvName);
        tvName.setText(this_librian_id);
        // set tool bar
        toolbar = findViewById(R.id.tbToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //set toggle of tool bar for drawer
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //search view



        //SET EVENT CLICK FOR NAVIGATION DRAWER
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ActionMode.Callback callback;
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_callcard: {
                        searchView.setVisibility(View.VISIBLE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        SetViewCallcard();
                        btnNewItem.setOnClickListener(v->ButtonAddCallCard());
                       callback = new ActionMode.Callback() {
                            @Override
                            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                                actionMode.getMenuInflater().inflate(R.menu.menu_context_toolbar, menu);
                                actionMode.setTitle(this_item.getText());
                                return true;
                            }

                            @Override
                            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                                return false;
                            }

                            @Override
                            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.rcv_edit: {
                                        EditCallCard(pos);
                                        actionMode.finish();
                                        break;
                                    }
                                    case R.id.rcv_delete: {
                                        /*librarianManager.DeleteLibrarian(pos);
                                        alLibrarian.remove(alLibrarian.get(pos));
                                        FillRecyclerView();*/
                                        callCardManager.DeleteCallcard(pos);
                                        alCallCardToItem.remove(pos);
                                        recyclerView.setAdapter(adapter);
                                        Toast.makeText(UserMainActivity.this,"Xóa phiếu mượn thành công",Toast.LENGTH_SHORT).show();
                                        actionMode.finish();
                                        }
                                        break;
                                    }
                                return false;
                                }

                            @Override
                            public void onDestroyActionMode(ActionMode actionMode) {
                                actionMode.setTitle("");
                            }
                        };

                        RecyclerClickEvent(callback);
                        return false;
                    }
                    case R.id.nav_book:{
                        searchView.setVisibility(View.VISIBLE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        SetViewBook();
                        callback = new ActionMode.Callback() {
                            @Override
                            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                                actionMode.getMenuInflater().inflate(R.menu.menu_context_toolbar, menu);
                                actionMode.setTitle(this_item.getText());
                                return true;
                            }

                            @Override
                            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                                return false;
                            }

                            @Override
                            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.rcv_edit: {
                                        Toast.makeText(UserMainActivity.this,"Sửa phiếu mượn thành công",Toast.LENGTH_SHORT).show();
                                        actionMode.finish();
                                        break;
                                    }
                                    case R.id.rcv_delete: {
                                        Toast.makeText(UserMainActivity.this,"Xóa phiếu mượn thành công",Toast.LENGTH_SHORT).show();
                                        actionMode.finish();
                                    }
                                    break;
                                }
                                return false;
                            }

                            @Override
                            public void onDestroyActionMode(ActionMode actionMode) {
                                actionMode.setTitle("");
                            }
                        };

                        RecyclerClickEvent(callback);
                        return false;
                    }
                    case R.id.nav_type:{
                        searchView.setVisibility(View.VISIBLE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        SetViewKind();
                        callback = new ActionMode.Callback() {
                            @Override
                            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                                actionMode.getMenuInflater().inflate(R.menu.menu_context_toolbar, menu);
                                actionMode.setTitle(this_item.getText());
                                return true;
                            }

                            @Override
                            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                                return false;
                            }

                            @Override
                            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.rcv_edit: {
                                        Toast.makeText(UserMainActivity.this,"Sửa loại sách thành công",Toast.LENGTH_SHORT).show();
                                        actionMode.finish();
                                        break;
                                    }
                                    case R.id.rcv_delete: {
                                        Toast.makeText(UserMainActivity.this,"Xóa loại sách thành công",Toast.LENGTH_SHORT).show();
                                        actionMode.finish();
                                    }
                                    break;
                                }
                                return false;
                            }

                            @Override
                            public void onDestroyActionMode(ActionMode actionMode) {
                                actionMode.setTitle("");
                            }
                        };

                        RecyclerClickEvent(callback);
                        return false;
                    }
                    case R.id.nav_member: {
                        searchView.setVisibility(View.VISIBLE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        SetViewMember();
                        callback = new ActionMode.Callback() {
                            @Override
                            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                                actionMode.getMenuInflater().inflate(R.menu.menu_context_toolbar, menu);
                                actionMode.setTitle(this_item.getText());
                                return true;
                            }

                            @Override
                            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                                return false;
                            }

                            @Override
                            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.rcv_edit: {
                                        Toast.makeText(UserMainActivity.this,"Sửa thành viên thành công",Toast.LENGTH_SHORT).show();
                                        actionMode.finish();
                                        break;
                                    }
                                    case R.id.rcv_delete: {
                                        Toast.makeText(UserMainActivity.this,"Xóa thành viên thành công",Toast.LENGTH_SHORT).show();
                                        actionMode.finish();
                                    }
                                    break;
                                }
                                return false;
                            }

                            @Override
                            public void onDestroyActionMode(ActionMode actionMode) {
                                actionMode.setTitle("");
                            }
                        };

                        RecyclerClickEvent(callback);
                        return false;
                    }
                    case R.id.nav_revenue_month:{
                        toolbar.setTitle("Thống kê theo 12 tháng");
                        searchView.setVisibility(View.GONE);
                        contain.removeAllViewsInLayout();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new RevenueFragment()).commit();
                        return false;
                    }
                    case R.id.nav_revenue_year:{
                        toolbar.setTitle("Thống kê theo 10 năm");
                        searchView.setVisibility(View.GONE);
                        contain.removeAllViewsInLayout();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new RevenueFragmentYear()).commit();
                        return false;
                    }
                    case R.id.nav_top10:{
                        toolbar.setTitle("Thống kê theo lượt mượn");
                        searchView.setVisibility(View.GONE);
                        contain.removeAllViewsInLayout();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new TopBorrowedFragment() ).commit();
                        return false;
                    }
                    case R.id.nav_logout: {
                        finish();
                    }
                }
                return false;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
        searchView.setVisibility(View.GONE);
        toolbar.setTitle("Trang chủ");
    }
    TextView this_item;
    String thisRcv;
    public void RecyclerClickEvent(ActionMode.Callback callback) {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongItemClick(View view, int position) {
                if(!isOffline && thisRcv.equals("callcard")) {
                    pos = position;
                    this_item = view.findViewById(R.id.tvIdCallcard);
                    recyclerView.startActionMode(callback);
                }
                if(!isOffline && thisRcv.equals("book")) {
                    pos = position;
                    this_item = view.findViewById(R.id.tvBookNameBook);
                    recyclerView.startActionMode(callback);
                }
                if(!isOffline && thisRcv.equals("kind")) {
                    pos = position;
                    this_item = view.findViewById(R.id.tvKindName);
                    recyclerView.startActionMode(callback);
                }
                if(!isOffline && thisRcv.equals("member")) {
                    pos = position;
                    this_item = view.findViewById(R.id.tvMemberName);
                    recyclerView.startActionMode(callback);
                }
            }
        }));
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    String idBookForResult="";
    String idMemberForResult ="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BOOK) {

            // resultCode được set bởi DetailActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if (resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                final String resultBook = data.getStringExtra(InputNewBookActivity.EXTRA_BOOKNAME_DATA);
                idBook.setText(resultBook);
                idBookForResult = resultBook;
            } else {
                // DetailActivity không thành công, không có data trả về.
            }
        }
        if (requestCode == REQUEST_CODE_MEMBER) {

            // resultCode được set bởi DetailActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if (resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                final String resultMember = data.getStringExtra(InputNewMemberActivity.EXTRA_IDMEMBER_DATA);
                idMember.setText(resultMember);
                idMemberForResult=resultMember;
            } else {
                // DetailActivity không thành công, không có data trả về.
            }
        }
    }

    //Callcard
    RecycleCallcardAdapter adapter;
    ArrayList<CallcardToItem> alCallCardToItem;
    ArrayList<CallCard> alCallCard;
    CallCardManager callCardManager;
    void SetViewCallcard() {
        contain.removeAllViewsInLayout();
        View recylerViewLayout = LayoutInflater.from(this).inflate(R.layout.layout_recycler_view, contain);
        recyclerView = recylerViewLayout.findViewById(R.id.rcvRecyclerView);
        btnNewItem = recylerViewLayout.findViewById(R.id.btnNewItem);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        alCallCardToItem = new CallCardManager(phuongNamLibSQLite, token).GetAllCallcardToItem();
        alCallCard = new CallCardManager(phuongNamLibSQLite, token).GetAllCallcard();
        callCardManager= new CallCardManager(phuongNamLibSQLite,token);
        adapter = new RecycleCallcardAdapter(alCallCardToItem, this);
        recyclerView.setAdapter(adapter);
        searchView.setQueryHint("Nhập tên thành viên để tìm kiếm");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
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
        thisRcv="callcard";
    }
    //Book
    RecycleBookAdapter recycleBookAdapter;
    ArrayList<BookToItem> alBookToItem;
    ArrayList<Book> alBook;
    BookManager bookManager;
    void SetViewBook() {
        contain.removeAllViewsInLayout();
        View recylerViewLayout = LayoutInflater.from(this).inflate(R.layout.layout_recycler_view, contain);
        recyclerView = recylerViewLayout.findViewById(R.id.rcvRecyclerView);
        btnNewItem = recylerViewLayout.findViewById(R.id.btnNewItem);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        alBookToItem = new BookManager(phuongNamLibSQLite, token).GetAllBookToItem();
        bookManager= new BookManager(phuongNamLibSQLite,token);
        recycleBookAdapter = new RecycleBookAdapter(alBookToItem, this);
        recyclerView.setAdapter(recycleBookAdapter);
        searchView.setQueryHint("Nhập mã sách để tìm kiếm");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                recycleBookAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recycleBookAdapter.getFilter().filter(s);
                return false;
            }
        });
        thisRcv="book";
    }
    //Kind
    RecycleKindAdapter recycleKindAdapter;
    ArrayList<Kind> alKind;
    KindManager kindManager;
    void SetViewKind() {
        contain.removeAllViewsInLayout();
        View recylerViewLayout = LayoutInflater.from(this).inflate(R.layout.layout_recycler_view, contain);
        recyclerView = recylerViewLayout.findViewById(R.id.rcvRecyclerView);
        btnNewItem = recylerViewLayout.findViewById(R.id.btnNewItem);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        alKind = new KindManager(phuongNamLibSQLite, token).GetAllKind();
        kindManager= new KindManager(phuongNamLibSQLite,token);
        recycleKindAdapter = new RecycleKindAdapter(alKind, this);
        recyclerView.setAdapter(recycleKindAdapter);
        searchView.setQueryHint("Nhập tên loại để tìm kiếm");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                recycleKindAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recycleKindAdapter.getFilter().filter(s);
                return false;
            }
        });
        thisRcv="kind";
    }
    //Member
    RecycleMemberAdapter recycleMemberAdapter;
    ArrayList<MemberToItem> alMember;
    MemberManager memberManager;
    void SetViewMember() {
        contain.removeAllViewsInLayout();
        View recylerViewLayout = LayoutInflater.from(this).inflate(R.layout.layout_recycler_view, contain);
        recyclerView = recylerViewLayout.findViewById(R.id.rcvRecyclerView);
        btnNewItem = recylerViewLayout.findViewById(R.id.btnNewItem);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        alMember = new MemberManager(phuongNamLibSQLite, token).LoadAllMemberToItem();
        memberManager= new MemberManager(phuongNamLibSQLite,token);
        recycleMemberAdapter = new RecycleMemberAdapter(alMember, this);
        recyclerView.setAdapter(recycleMemberAdapter);
        searchView.setQueryHint("Nhập mã số thành viên để tìm kiếm");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                recycleMemberAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recycleMemberAdapter.getFilter().filter(s);
                return false;
            }
        });
        thisRcv="member";
    }
    TextView idBook;
    TextView idMember;
    void ButtonAddCallCard() {

        idBookForResult="";
        idMemberForResult ="";

        AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View create = inflater.inflate(R.layout.layout_create_callcard_dialog, null);
        builder.setView(create);
        AlertDialog CreateCallcardDialog = builder.create();
        CreateCallcardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CreateCallcardDialog.show();

        idMember = create.findViewById(R.id.tvNewCallcardMemberId);
        TextView beginDate = create.findViewById(R.id.tvNewCallcardBeginDate);
        TextView expiresDate = create.findViewById(R.id.tvNewCallcardExpiresDate);
        idBook = create.findViewById(R.id.tvNewCallcardBookName);
        TextView btnOkay = create.findViewById(R.id.btnOkay);
        TextView btnCancel = create.findViewById(R.id.btnCancel);

        long millis=System.currentTimeMillis();

        beginDate.setText(new java.sql.Date(millis)+"");
        expiresDate.setText(new java.sql.Date(millis+604800000)+"");
        idBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(UserMainActivity.this, InputNewBookActivity.class);
                startActivityForResult(intent, REQUEST_CODE_BOOK);
            }
        });
        idMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(UserMainActivity.this, InputNewMemberActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MEMBER);
            }
        });

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookManager bookManager = new BookManager(phuongNamLibSQLite,token);
                if(!idMemberForResult.equals("") && !idBookForResult.equals("")) {
                    int indexSubStringMemberId = idMember.getText().toString().indexOf(":");
                    int memberId = Integer.parseInt(idMember.getText().toString().substring(indexSubStringMemberId + 1).trim());
                    String memberName = idMember.getText().toString().substring(0, indexSubStringMemberId - 4).trim();
                    int bookId = bookManager.GetIdBook(idBook.getText() + "");
                    String begin = beginDate.getText() + "".trim();
                    String expire = expiresDate.getText() + "".trim();

                    callCardManager.CreateNewCallcard(new CallCard(begin, expire, bookId, memberId, this_librian_id));

                    alCallCardToItem.add(new CallcardToItem(begin, expire, idBook.getText() + "", memberName, this_librian_id));
                    adapter = new RecycleCallcardAdapter(alCallCardToItem, UserMainActivity.this);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(UserMainActivity.this, "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                    CreateCallcardDialog.cancel();
                }else{
                    Toast.makeText(UserMainActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateCallcardDialog.cancel();
            }
        });
    }
    void EditCallCard(int pos){

        idBookForResult="";
        idMemberForResult ="";

        AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View edit = inflater.inflate(R.layout.layout_edit_callcard_dialog, null);
        builder.setView(edit);
        AlertDialog EditCallcardDialog = builder.create();
        EditCallcardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditCallcardDialog.show();

        idMember = edit.findViewById(R.id.tvEditCallcardMemberId);
        TextView beginDate = edit.findViewById(R.id.tvEditCallcardBeginDate);
        TextView expiresDate = edit.findViewById(R.id.tvEditCallcardExpiresDate);
        idBook = edit.findViewById(R.id.tvEditCallcardBookName);
        TextView btnOkay = edit.findViewById(R.id.btnOkay);
        TextView btnCancel = edit.findViewById(R.id.btnCancel);

        ArrayList<CallcardToItem> allCallcardToItem = callCardManager.LoadAllCallcardToItem();
        CallcardToItem callcardToItem = allCallcardToItem.get(pos);

        idMember.setText(callcardToItem.getMemberName());
        beginDate.setText(callcardToItem.getBeginDate());
        expiresDate.setText(callcardToItem.getExpiresDate());
        idBook.setText(callcardToItem.getBookName());

        idBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(UserMainActivity.this, InputNewBookActivity.class);
                startActivityForResult(intent, REQUEST_CODE_BOOK);
            }
        });
        idMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(UserMainActivity.this, InputNewMemberActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MEMBER);
            }
        });

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BookManager bookManager = new BookManager(phuongNamLibSQLite,token);

                if(!idMemberForResult.equals("") && !idBookForResult.equals("")) {
                    int indexSubStringMemberId = idMember.getText().toString().indexOf(":");
                    int memberId = Integer.parseInt(idMember.getText().toString().substring(indexSubStringMemberId + 1).trim());
                    String memberName = idMember.getText().toString().substring(0, indexSubStringMemberId-4).trim();
                    int bookId = bookManager.GetIdBook(idBook.getText() + "");
                    String begin = beginDate.getText() + "".trim();
                    String expire = expiresDate.getText() + "".trim();

                    callCardManager.UpdateCallcard(new CallCard(alCallCard.get(pos).getId(),begin, expire, bookId, memberId, this_librian_id));

                    alCallCardToItem.set(pos, new CallcardToItem(begin, expire, idBook.getText() + "", memberName, this_librian_id));
                    adapter = new RecycleCallcardAdapter(alCallCardToItem, UserMainActivity.this);
                    recyclerView.setAdapter(adapter);

                }
                else if(idMemberForResult.equals("")&&!idBookForResult.equals("")) {
                    int bookId = bookManager.GetIdBook(idBook.getText() + "");
                    String begin = beginDate.getText() + "".trim();
                    String expire = expiresDate.getText() + "".trim();
                    String memberName = idMember.getText()+"".trim();
                    
                    callCardManager.UpdateCallcard(new CallCard(alCallCard.get(pos).getId(),begin, expire, bookId, alCallCard.get(pos).getIdMember(), this_librian_id));

                    alCallCardToItem.set(pos, new CallcardToItem(begin, expire, idBook.getText() + "", memberName, this_librian_id));
                    adapter = new RecycleCallcardAdapter(alCallCardToItem, UserMainActivity.this);
                    recyclerView.setAdapter(adapter);

                }
                else if(idMemberForResult.equals("")&&idBookForResult.equals("")){
                }
                EditCallcardDialog.cancel();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCallcardDialog.cancel();
            }
        });
    }

}