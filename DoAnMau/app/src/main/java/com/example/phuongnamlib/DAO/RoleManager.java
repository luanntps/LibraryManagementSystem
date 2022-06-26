package com.example.phuongnamlib.DAO;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Models.Role;
import com.example.phuongnamlib.Models.Message;
import com.example.phuongnamlib.Services.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoleManager {
    PhuongNamLibSQLite phuongNamLibSQLite;
    String token;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    ArrayList<Role> roles;

    public RoleManager(PhuongNamLibSQLite phuongNamLibSQLite, String token) {
        this.phuongNamLibSQLite = phuongNamLibSQLite;
        this.token = token;
    }

    public  ArrayList<Role> LoadAllRole(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ PhuongNamLibSQLite.getTableRole(),null);
        ArrayList<Role> roles=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                roles.add(new Role(cursor.getInt(0),cursor.getString(1)));
            }while(cursor.moveToNext());
        }
        return roles;
    }

    public ArrayList<Role> GetAllRole(){
        return LoadAllRole();
    }
}
