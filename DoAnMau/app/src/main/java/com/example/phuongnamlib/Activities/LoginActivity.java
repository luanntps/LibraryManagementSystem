 package com.example.phuongnamlib.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Models.Authen;
import com.example.phuongnamlib.Models.Token;
import com.example.phuongnamlib.R;
import com.example.phuongnamlib.DAO.LibrarianManager;
import com.example.phuongnamlib.Services.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 public class LoginActivity extends AppCompatActivity {
    TextView btnLogin;
    EditText edtUsername, edtPassword;
    TextView tvForgetPassword;
    TextView tvNoConnection;
    LinearLayout llIntroduction;
    LibrarianManager librarianManager;

    ProgressBar progressBar;

    SearchView swSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        tvForgetPassword=findViewById(R.id.forgetPassword);
        llIntroduction=findViewById(R.id.llIntroduction);

        progressBar = findViewById(R.id.progressbar);

        swSearch=findViewById(R.id.swSearch);
                
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                llIntroduction.setVisibility(View.GONE);
            }
        }, 2000);

        edtPassword.setOnTouchListener(PasswordTouchListener);

        tvForgetPassword.setOnClickListener(v -> ForgetPassDialog());

        btnLogin.setOnClickListener(v->Login());

    }



    private void Login(){
        String id = edtUsername.getText()+"".trim();
        String password = edtPassword.getText()+"".trim();

        progressBar.setVisibility(View.VISIBLE);

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.GetToken(id, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Authen>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @SuppressLint("CommitPrefEdits")
                    @Override
                    public void onNext(Authen authen) {
                        SharedPreferences sharedPref;
                        SharedPreferences.Editor editor;

                        sharedPref = getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE);
                        editor = sharedPref.edit();
                        editor.putString("currentId", id);
                        editor.putString("currentPassword", password);
                        editor.putString("currentRole", authen.getRole());
                        editor.putString("currentToken","Bearer "+authen.getToken());
                        editor.commit();



                        SharedPreferences checkOffline;
                        checkOffline = getSharedPreferences("Offline", Context.MODE_PRIVATE);
                        editor = checkOffline.edit();
                        editor.putBoolean("isOffline",false);
                        editor.commit();

                        if(authen.getRole().equalsIgnoreCase("user")) {
                            SharedPreferences firstTimePref;

                            firstTimePref = getSharedPreferences("FirstTime", Context.MODE_PRIVATE);
                            boolean isFirstTime = firstTimePref.getBoolean("isFirstTime", true);
                            if (isFirstTime) {

                                editor = firstTimePref.edit();
                                editor.putBoolean("isFirstTime", false);
                                editor.commit();

                                PhuongNamLibSQLite phuongNamLibSQLite = new PhuongNamLibSQLite(LoginActivity.this);
                                phuongNamLibSQLite.InsertDataForTheFirstTimeAsUser(LoginActivity.this,UserMainActivity.class);
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                        if(authen.getRole().equalsIgnoreCase("admin")) {
                            SharedPreferences firstTimePref;

                            firstTimePref = getSharedPreferences("FirstTime", Context.MODE_PRIVATE);
                            boolean isFirstTime = firstTimePref.getBoolean("isFirstTime", true);
                            if (isFirstTime) {

                                editor = firstTimePref.edit();
                                editor.putBoolean("isFirstTime", false);
                                editor.commit();

                                PhuongNamLibSQLite phuongNamLibSQLite = new PhuongNamLibSQLite(LoginActivity.this);
                                phuongNamLibSQLite.InsertDataForTheFirstTimeAsUser(LoginActivity.this,AdminMainActivity.class);
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                    @Override
                    public void onError( Throwable e) {

                        e.printStackTrace();

                        if(e instanceof java.net.UnknownHostException) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            LayoutInflater inflater = getLayoutInflater();
                            View create = inflater.inflate(R.layout.layout_alert_dialog, null);
                            builder.setView(create);
                            AlertDialog NoConnectionDialog = builder.create();
                            NoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            TextView title = create.findViewById(R.id.tvTitleAlert);
                            TextView content = create.findViewById(R.id.tvContentAlert);

                            title.setText(R.string.no_connection_title);
                            content.setText(R.string.no_connection_content);

                            NoConnectionDialog.show();

                            TextView btnOkay = NoConnectionDialog.findViewById(R.id.btnOkay);
                            btnOkay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    SharedPreferences sharedPref;
                                    SharedPreferences.Editor editor;

                                    sharedPref = getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE);
                                    String previuosId = sharedPref.getString("currentId", null);
                                    String previousPassword = sharedPref.getString("currentPassword", null);
                                    String previousRole = sharedPref.getString("currentRole", null);
                                    if (id.equals(previuosId) && password.equals(previousPassword) && previousRole.equalsIgnoreCase("user")) {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập chế độ offline", Toast.LENGTH_SHORT).show();

                                        SharedPreferences checkOffline;
                                        checkOffline = getSharedPreferences("Offline", Context.MODE_PRIVATE);
                                        editor = checkOffline.edit();
                                        editor.putBoolean("isOffline", true);
                                        editor.commit();

                                        startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
                                        progressBar.setVisibility(View.GONE);
                                    } else if (id.equals(previuosId) && password.equals(previousPassword) && previousRole.equalsIgnoreCase("admin")) {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập chế độ offline", Toast.LENGTH_SHORT).show();

                                        SharedPreferences checkOffline;
                                        checkOffline = getSharedPreferences("Offline", Context.MODE_PRIVATE);
                                        editor = checkOffline.edit();
                                        editor.putBoolean("isOffline", true);
                                        editor.commit();

                                        startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        e.printStackTrace();
                                        Toast.makeText(LoginActivity.this, "Không thể đăng nhập", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    NoConnectionDialog.cancel();
                                }
                            });
                        }else if(((HttpException) e).code()==400) {
                            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onComplete() {}
                });
    }

    private void ForgetPassDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        View create=inflater.inflate(R.layout.layout_alert_dialog,null);
        builder.setView(create);
        AlertDialog ForgetPassDialog=builder.create();
        ForgetPassDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView title=create.findViewById(R.id.tvTitleAlert);
        TextView content=create.findViewById(R.id.tvContentAlert);

        title.setText(R.string.forgot_password_title);
        content.setText(R.string.forgot_password_content);
        ForgetPassDialog.show();

        TextView btnOkay=ForgetPassDialog.findViewById(R.id.btnOkay);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgetPassDialog.cancel();
            }
        });
    }


    View.OnTouchListener PasswordTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (edtPassword.getRight() - edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    edtPassword.setSelection(edtPassword.getText().length());
                    return true;
                }
            }
            if(event.getAction() != MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (edtPassword.getRight() - edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    edtPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                    edtPassword.setSelection(edtPassword.getText().length());
                    return true;
                }
            }
            return false;
        }
    };



     boolean doubleBackToExitPressedOnce = false;
     @Override
     public void onBackPressed() {
         if (doubleBackToExitPressedOnce) {
             super.onBackPressed();
         }
         this.doubleBackToExitPressedOnce = true;
         Toast.makeText(this, "Ấn 2 lần để thoát", Toast.LENGTH_SHORT).show();
         new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
             @Override
             public void run() {
                 doubleBackToExitPressedOnce=false;
             }
         }, 1000);
     }
 }