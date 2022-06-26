package com.example.phuongnamlib.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.phuongnamlib.Adapters.RecycleLibrarianAdapter;
import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Models.Librarian;
import com.example.phuongnamlib.Models.LibrarianToItem;
import com.example.phuongnamlib.Models.Message;
import com.example.phuongnamlib.Models.Token;
import com.example.phuongnamlib.Services.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LibrarianManager {
    PhuongNamLibSQLite phuongNamLibSQLite;
    String token;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    ArrayList<Librarian> librarians;

    public LibrarianManager(PhuongNamLibSQLite phuongNamLibSQLite, String token) {
        this.phuongNamLibSQLite = phuongNamLibSQLite;
        this.token = token;
    }

    public  ArrayList<Librarian> LoadAllLibrarian(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ PhuongNamLibSQLite.getTableLibrarian(),null);
        ArrayList<Librarian> librarians=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                librarians.add(new Librarian(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3)));
            }while(cursor.moveToNext());
        }
        return librarians;
    }

    public  ArrayList<LibrarianToItem> LoadAllLibrarianToItem(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("" +
                "select "+PhuongNamLibSQLite.getKeyLibrarianId()+","
                +PhuongNamLibSQLite.getKeyLibrarianName()+","
                +PhuongNamLibSQLite.getKeyLibrarianPassword()+","
                +PhuongNamLibSQLite.getKeyRoleName()
                +" from "+ PhuongNamLibSQLite.getTableLibrarian()
                +" inner join "+ PhuongNamLibSQLite.getTableRole()
                +" on "+PhuongNamLibSQLite.getTableLibrarian()+"."+PhuongNamLibSQLite.getKeyRoleId()
                +" = "+PhuongNamLibSQLite.getTableRole()+"."+PhuongNamLibSQLite.getKeyRoleId(),null);
        ArrayList<LibrarianToItem> librarians=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                librarians.add(new LibrarianToItem(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while(cursor.moveToNext());
        }
        return librarians;
    }

    public ArrayList<Librarian> GetAllLibrarian(){
        return LoadAllLibrarian();
    }

    public void CreateNewLibrarian(Librarian librarian){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.CreateUser(token, librarian)
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
                        values.put(PhuongNamLibSQLite.getKeyLibrarianId(),librarian.getId());
                        values.put(PhuongNamLibSQLite.getKeyLibrarianName(),librarian.getName());
                        values.put(PhuongNamLibSQLite.getKeyLibrarianPassword(),librarian.getPassword());
                        values.put(PhuongNamLibSQLite.getKeyRoleId(),librarian.getIdRole());
                        db.insert(PhuongNamLibSQLite.getTableLibrarian(),null,values);
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

    public void DeleteLibrarian( int position){

        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();

        ArrayList<Librarian> librarians = LoadAllLibrarian();

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.DeleteUser(token, librarians.get(position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( Message message) {
                        db.delete(PhuongNamLibSQLite.getTableLibrarian()
                                ,PhuongNamLibSQLite.getKeyLibrarianId()+"=?"
                                ,new String[]{librarians.get(position).getId()});
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(phuongNamLibSQLite.getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() { }
                });
    }

    public void UpdateLibrarian( Librarian librarian){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();


        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.UpdateUser(token, librarian)
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
                        values.put(PhuongNamLibSQLite.getKeyLibrarianId(),librarian.getId());
                        values.put(PhuongNamLibSQLite.getKeyLibrarianName(),librarian.getName());
                        values.put(PhuongNamLibSQLite.getKeyLibrarianPassword(),librarian.getPassword());
                        values.put(PhuongNamLibSQLite.getKeyRoleId(),librarian.getIdRole());
                        db.update(PhuongNamLibSQLite.getTableLibrarian(),values
                                ,PhuongNamLibSQLite.getKeyLibrarianId()+"=?"
                                ,new String[]{librarian.getId()});

                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(phuongNamLibSQLite.getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() { }
                });
    }

    public String AutoGenerateLibrarianId(){
        String id="";
        int idFooter=Integer.parseInt(LoadAllLibrarian().get((LoadAllLibrarian().size()-1)).getId().substring(5));
        if(idFooter<10){
            id="PS1940"+(idFooter+1);
        }else if(idFooter>=10){
            id="PS194"+(idFooter+1);
        }
        return id;
    }
    public String AutoGenerateLibrarianPassword(){
        String password="";
        int passwordFooter=Integer.parseInt(LoadAllLibrarian().get((LoadAllLibrarian().size()-1)).getId().substring(5));
        if(passwordFooter<10){
            password="fpt"+"1940"+(passwordFooter+1);
        }else if(passwordFooter>=10){
            password="fpt"+"194"+(passwordFooter+1);
        }
        return password;
    }

}
