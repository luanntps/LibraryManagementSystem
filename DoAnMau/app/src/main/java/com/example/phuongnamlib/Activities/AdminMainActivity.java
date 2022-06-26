package com.example.phuongnamlib.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phuongnamlib.Adapters.RecycleLibrarianAdapter;
import com.example.phuongnamlib.DAO.LibrarianManager;
import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Helpers.RecyclerItemClickListener;
import com.example.phuongnamlib.Models.Librarian;
import com.example.phuongnamlib.Models.LibrarianToItem;
import com.example.phuongnamlib.R;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {
    ArrayList<LibrarianToItem> alLibrarian;
    RecyclerView rcvLibrarian;
    LibrarianManager librarianManager;
    SearchView swSearch;
    LinearLayout btnNewUser;
    LinearLayout btnLogout;
    TextView tvOffline;
    static int pos;

    private static final int REQUEST_CODE_ROLE = 1;

    PhuongNamLibSQLite phuongNamLibSQLite;

    static TextView this_librarian;

    SharedPreferences checkOffline;
    Boolean isOffline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        tvOffline = findViewById(R.id.tvOffline);
        btnNewUser = findViewById(R.id.btnNewUser);
        btnLogout = findViewById(R.id.btnLogout);

        checkOffline = getSharedPreferences("Offline", Context.MODE_PRIVATE);
        isOffline = checkOffline.getBoolean("isOffline",false);

        if(isOffline){
            tvOffline.setVisibility(View.VISIBLE);
            btnNewUser.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);
        }

        String token = getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE).getString("currentToken", "");
        rcvLibrarian = findViewById(R.id.rcvLibrarian);
        RecyclerClickEvent();

        btnNewUser.setOnClickListener(v -> DialogCreateUser());
        btnLogout.setOnClickListener(v -> DialogLogout());

        swSearch = findViewById(R.id.swSearch);

        phuongNamLibSQLite=new PhuongNamLibSQLite(this);
        librarianManager = new LibrarianManager(phuongNamLibSQLite, token);

        alLibrarian = librarianManager.LoadAllLibrarianToItem();
        FillRecyclerView();

    }

    void FillRecyclerView(){
        rcvLibrarian.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvLibrarian.setLayoutManager(manager);
        RecycleLibrarianAdapter adapter = new RecycleLibrarianAdapter(alLibrarian,this);
        rcvLibrarian.setAdapter(adapter);
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


    void DialogEditUser(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminMainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View edit = inflater.inflate(R.layout.layout_edit_user_dialog, null);
        builder.setView(edit);
        AlertDialog EditUserDialog = builder.create();
        EditUserDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditUserDialog.show();

        TextView btnOkay = EditUserDialog.findViewById(R.id.btnOkay);
        TextView btnCancel = EditUserDialog.findViewById(R.id.btnCancel);
        TextView tvId = EditUserDialog.findViewById(R.id.tvEditUserId);
        tvRoleForResult = EditUserDialog.findViewById(R.id.tvEditLibrarianRoleName);

        EditText edtName = EditUserDialog.findViewById(R.id.edtEditUserName);
        EditText edtPassword = EditUserDialog.findViewById(R.id.tvEditUserPassword);

        alLibrarian = librarianManager.LoadAllLibrarianToItem();
        LibrarianToItem librarian = alLibrarian.get(position);
        
        tvId.setText(librarian.getId());
        edtName.setText(librarian.getName());
        edtPassword.setText(librarian.getPassword());
        tvRoleForResult.setText(librarian.getNameRole());

        tvRoleForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(AdminMainActivity.this, InputNewRoleActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ROLE);
            }
        });

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if(idRoleForResult==0 && !name.equals("") && !password.equals("")) {
                    ArrayList<Librarian> librarians = librarianManager.GetAllLibrarian();
                    Librarian inputLibrarian = new Librarian(librarians.get(position).getId(), name, password, librarians.get(position).getIdRole());
                    librarianManager.UpdateLibrarian(inputLibrarian);
                    LibrarianToItem librarianToItem = new LibrarianToItem(librarian.getId(), name, password, librarian.getNameRole());
                    alLibrarian.set(pos,librarianToItem);
                    FillRecyclerView();
                    EditUserDialog.cancel();
                }else if(name.equals("")||password.equals("")) {
                    Toast.makeText(AdminMainActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if(idRoleForResult!=0 && !name.equals("") && !password.equals("")){
                    Librarian inputLibrarian = new Librarian(librarian.getId(), name, password, idRoleForResult);
                    librarianManager.UpdateLibrarian(inputLibrarian);
                    LibrarianToItem librarianToItem = new LibrarianToItem(librarian.getId(), name, password, nameRoleForResult);
                    alLibrarian.set(pos,librarianToItem);
                    FillRecyclerView();
                    EditUserDialog.cancel();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditUserDialog.cancel();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    void DialogCreateUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminMainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View create = inflater.inflate(R.layout.layout_create_user_dialog, null);
        builder.setView(create);
        AlertDialog CreateUserDialog = builder.create();
        CreateUserDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CreateUserDialog.show();

        TextView btnOkay = CreateUserDialog.findViewById(R.id.btnOkay);
        TextView btnCancel = CreateUserDialog.findViewById(R.id.btnCancel);
        TextView tvId = CreateUserDialog.findViewById(R.id.tvNewUserId);
        EditText edtName = CreateUserDialog.findViewById(R.id.edtNewUserName);
        TextView tvPassword = CreateUserDialog.findViewById(R.id.tvNewUserPassword);
        tvRoleForResult = CreateUserDialog.findViewById(R.id.tvNewLibrarianRoleName);

        tvPassword.setText(librarianManager.AutoGenerateLibrarianPassword());

        tvPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    tvPassword.setTransformationMethod(new PasswordTransformationMethod());
                    return true;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    tvPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                    return true;
                }
                return false;
            }
        });

        tvId.setText(librarianManager.AutoGenerateLibrarianId());

        tvRoleForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(AdminMainActivity.this, InputNewRoleActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ROLE);
            }
        });

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = tvId.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                String password = tvPassword.getText().toString().trim();

                if(idRoleForResult!=0 && !name.equals("")) {
                    Librarian librarian = new Librarian(id, name, password, idRoleForResult);
                    LibrarianToItem librarianToItem = new LibrarianToItem(id, name, password, nameRoleForResult);
                    librarianManager.CreateNewLibrarian(librarian);
                    alLibrarian.add(librarianToItem);
                    FillRecyclerView();
                    CreateUserDialog.cancel();
                }else{
                    Toast.makeText(AdminMainActivity.this,"Vui lòng nhập đủ thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateUserDialog.cancel();
            }
        });
    }

    void DialogLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminMainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View create = inflater.inflate(R.layout.layout_warning_dialog, null);
        builder.setView(create);
        AlertDialog WaringDialog = builder.create();
        WaringDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WaringDialog.show();

        TextView tvTitle = WaringDialog.findViewById(R.id.tvTitle);
        TextView tvContent = WaringDialog.findViewById(R.id.tvContent);
        TextView btnOkay = WaringDialog.findViewById(R.id.btnOkay);
        TextView btnCancel = WaringDialog.findViewById(R.id.btnCancel);

        tvTitle.setText("");
        tvContent.setText("Thoát khỏi Admin?");
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaringDialog.cancel();
            }
        });
    }

    public void RecyclerClickEvent() {
        rcvLibrarian.addOnItemTouchListener(new RecyclerItemClickListener(this, rcvLibrarian, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongItemClick(View view, int position) {
                if(!isOffline) {
                    pos = position;
                    this_librarian = view.findViewById(R.id.tvUsername);
                    rcvLibrarian.startActionMode(callback);
                }
            }
        }));
    }

    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater().inflate(R.menu.menu_context_toolbar, menu);
            actionMode.setTitle(this_librarian.getText());
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
                    DialogEditUser(pos);
                    actionMode.finish();
                    break;
                }
                case R.id.rcv_delete: {
                    String currentLibrarianId = getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE).getString("currentId", "");
                    if (librarianManager.GetAllLibrarian().get(pos).getId().equalsIgnoreCase(currentLibrarianId)) {
                        Toast.makeText(AdminMainActivity.this,"Không thể xóa admin đang đăng nhập",Toast.LENGTH_SHORT).show();
                    }else {
                        librarianManager.DeleteLibrarian(pos);
                        alLibrarian.remove(alLibrarian.get(pos));
                        FillRecyclerView();
                        actionMode.finish();
                    }
                    break;
                }
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }
    };

    TextView tvRoleForResult;
    String nameRoleForResult;
    int idRoleForResult;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ROLE) {

            // resultCode được set bởi DetailActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if (resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                final String nameRole = data.getStringExtra(InputNewRoleActivity.EXTRA_NAMEROLE_DATA);
                final int idRole = data.getIntExtra(InputNewRoleActivity.EXTRA_IDROLE_DATA,0);
                // Sử dụng kết quả result
                nameRoleForResult = nameRole;
                tvRoleForResult.setText(nameRole);
                idRoleForResult = idRole;
            } else {
                // DetailActivity không thành công, không có data trả về.
            }
        }
    }
}