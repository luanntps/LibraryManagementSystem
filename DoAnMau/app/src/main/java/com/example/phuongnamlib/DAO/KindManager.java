package com.example.phuongnamlib.DAO;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Models.Kind;
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

public class KindManager {
    PhuongNamLibSQLite phuongNamLibSQLite;
    String token;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    ArrayList<Kind> kinds;

    public KindManager(PhuongNamLibSQLite phuongNamLibSQLite, String token) {
        this.phuongNamLibSQLite = phuongNamLibSQLite;
        this.token = token;
    }

    public int GetQuantityKind(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select count("+PhuongNamLibSQLite.getKeyKindId()+") "
                +"from "+PhuongNamLibSQLite.getTableKind(),null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public  ArrayList<Kind> LoadAllKind(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ PhuongNamLibSQLite.getTableKind(),null);
        ArrayList<Kind> kinds=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                kinds.add(new Kind(cursor.getInt(0),cursor.getString(1)));
            }while(cursor.moveToNext());
        }
        return kinds;
    }

    public ArrayList<Kind> GetAllKind(){
        return LoadAllKind();
    }

    public void CreateNewKind(Kind kind){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.CreateKind(token, kind)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( Message message) {
                        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
                        ContentValues values=new ContentValues();
                        values.put(PhuongNamLibSQLite.getKeyKindName(),kind.getName());
                        db.insert(PhuongNamLibSQLite.getTableKind(),null,values);
                    }
                    @Override

                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("AAA","LOI NE");
                        Toast.makeText(phuongNamLibSQLite.getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
    }

    public void DeleteKind( int position){

        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();

        ArrayList<Kind> kinds = LoadAllKind();

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.DeleteKind(token, kinds.get(position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( Message message) {
                        db.delete(PhuongNamLibSQLite.getTableKind()
                                ,PhuongNamLibSQLite.getKeyKindId()+"=?"
                                ,new String[]{kinds.get(position).getId()+""});
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(phuongNamLibSQLite.getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() { }
                });
    }

    public void UpdateKind( Kind kind){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();


        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.UpdateKind(token, kind)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(Message message) {
                        ContentValues values=new ContentValues();
                        values.put(PhuongNamLibSQLite.getKeyKindName(),kind.getName());
                        db.update(PhuongNamLibSQLite.getTableKind(),values
                                ,PhuongNamLibSQLite.getKeyKindId()+"=?"
                                ,new String[]{kind.getId()+""});

                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(phuongNamLibSQLite.getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() { }
                });
    }
}
