package com.example.phuongnamlib.DAO;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Models.Member;
import com.example.phuongnamlib.Models.MemberToItem;
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

public class MemberManager {
    PhuongNamLibSQLite phuongNamLibSQLite;
    String token;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    ArrayList<Member> members;

    public MemberManager(PhuongNamLibSQLite phuongNamLibSQLite, String token) {
        this.phuongNamLibSQLite = phuongNamLibSQLite;
        this.token = token;
    }

    public int GetQuantityMember(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select count("+PhuongNamLibSQLite.getKeyMemberId()+") "
                +"from "+PhuongNamLibSQLite.getTableMember(),null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public  ArrayList<Member> LoadAllMember(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ PhuongNamLibSQLite.getTableMember(),null);
        ArrayList<Member> members=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                members.add(new Member(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }while(cursor.moveToNext());
        }
        return members;
    }

    public  ArrayList<MemberToItem> LoadAllMemberToItem(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select "+PhuongNamLibSQLite.getKeyMemberId()+","
                +PhuongNamLibSQLite.getKeyMemberName()+","
                +PhuongNamLibSQLite.getKeyMemberCounttime()+","
                +PhuongNamLibSQLite.getKeyMembertypeName()
                +" from "+ PhuongNamLibSQLite.getTableMember()
                +" inner join "+ PhuongNamLibSQLite.getTableMembertype()
                +" on "+PhuongNamLibSQLite.getTableMember()+"."+PhuongNamLibSQLite.getKeyMembertypeId()
                +" = "+PhuongNamLibSQLite.getTableMembertype()+"."+PhuongNamLibSQLite.getKeyMembertypeId(),null);
        ArrayList<MemberToItem> members=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                members.add(new MemberToItem(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3)));
            }while(cursor.moveToNext());
        }
        return members;
    }

    public ArrayList<Member> GetAllMember(){
        return LoadAllMember();
    }

    public void CreateNewMember(Member member){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.CreateMember(token, member)
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
                        values.put(PhuongNamLibSQLite.getKeyMemberName(),member.getName());
                        values.put(PhuongNamLibSQLite.getKeyMemberCounttime(),member.getCountTime());
                        values.put(PhuongNamLibSQLite.getKeyMembertypeId(),member.getIdMemberType());
                        db.insert(PhuongNamLibSQLite.getTableMember(),null,values);
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

    public void DeleteMember( int position){

        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();

        ArrayList<Member> members = LoadAllMember();

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.DeleteMember(token, members.get(position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( Message message) {
                        db.delete(PhuongNamLibSQLite.getTableMember()
                                ,PhuongNamLibSQLite.getKeyMemberId()+"=?"
                                ,new String[]{members.get(position).getId()+""});
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(phuongNamLibSQLite.getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() { }
                });
    }

    public void UpdateMember( Member member){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();


        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.UpdateMember(token, member)
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

                        values.put(PhuongNamLibSQLite.getKeyMemberName(),member.getName());
                        db.update(PhuongNamLibSQLite.getTableMember(),values
                                ,PhuongNamLibSQLite.getKeyMemberId()+"=?"
                                ,new String[]{member.getId()+""});

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
