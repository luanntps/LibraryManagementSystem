package com.example.phuongnamlib.DAO;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Models.CallCard;
import com.example.phuongnamlib.Models.CallcardToItem;
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

public class CallCardManager {
    PhuongNamLibSQLite phuongNamLibSQLite;
    String token;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    ArrayList<CallcardToItem> callcards;

    public CallCardManager(PhuongNamLibSQLite phuongNamLibSQLite, String token) {
        this.phuongNamLibSQLite = phuongNamLibSQLite;
        this.token = token;
    }

    public  ArrayList<CallCard> LoadAllCallcard(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ PhuongNamLibSQLite.getTableCallcard(),null);
        ArrayList<CallCard> callcards=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                callcards.add(new CallCard(cursor.getInt(0),cursor.getString(1)
                        ,cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5)));
            }while(cursor.moveToNext());
        }
        return callcards;
    }

    public int GetQuantityCallcard(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select count("+PhuongNamLibSQLite.getKeyCallcardId()+") "
                +"from "+PhuongNamLibSQLite.getTableCallcard(),null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public  ArrayList<CallcardToItem> LoadAllCallcardToItem(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select "+PhuongNamLibSQLite.getKeyCallcardId()+","
                +PhuongNamLibSQLite.getKeyCallcardBegindate()+","
                +PhuongNamLibSQLite.getKeyCallcardExpiresdate()+","
                +PhuongNamLibSQLite.getKeyBookName()+","
                +PhuongNamLibSQLite.getKeyMemberName()+","
                +PhuongNamLibSQLite.getKeyLibrarianId()
                +" from "+ PhuongNamLibSQLite.getTableCallcard()
                +" inner join "
                +PhuongNamLibSQLite.getTableBook()
                +" on "
                +PhuongNamLibSQLite.getTableCallcard()+"."+PhuongNamLibSQLite.getKeyBookId()
                +" = "
                +PhuongNamLibSQLite.getTableBook()+"."+PhuongNamLibSQLite.getKeyBookId()
                +" inner join "
                +PhuongNamLibSQLite.getTableMember()
                +" on "
                +PhuongNamLibSQLite.getTableCallcard()+"."+PhuongNamLibSQLite.getKeyMemberId()
                +" = "
                +PhuongNamLibSQLite.getTableMember()+"."+PhuongNamLibSQLite.getKeyMemberId(),null);
        ArrayList<CallcardToItem> callcards=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                callcards.add(new CallcardToItem(cursor.getInt(0),cursor.getString(1)
                        ,cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
            }while(cursor.moveToNext());
        }
        return callcards;
    }

    public ArrayList<CallcardToItem> GetAllCallcardToItem(){
        return LoadAllCallcardToItem();
    }

    public ArrayList<CallCard> GetAllCallcard(){
        return LoadAllCallcard();
    }

    public void CreateNewCallcard(CallCard callcard){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.CreateCallcard(token, callcard)
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
                        values.put(PhuongNamLibSQLite.getKeyMemberId(),callcard.getIdMember());
                        values.put(PhuongNamLibSQLite.getKeyBookId(),callcard.getIdBook());
                        values.put(PhuongNamLibSQLite.getKeyLibrarianId(),callcard.getIdLibrarian());
                        values.put(PhuongNamLibSQLite.getKeyCallcardBegindate(),callcard.getBeginDate());
                        values.put(PhuongNamLibSQLite.getKeyCallcardExpiresdate(),callcard.getExpiresDate());
                        db.insert(PhuongNamLibSQLite.getTableCallcard(),null,values);
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

    public void DeleteCallcard( int position){

        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();

        ArrayList<CallCard> alCallcards = LoadAllCallcard();

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.DeleteCallcard(token, alCallcards.get(position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( Message message) {
                        db.delete(PhuongNamLibSQLite.getTableCallcard()
                                ,PhuongNamLibSQLite.getKeyCallcardId()+"=?"
                                ,new String[]{alCallcards.get(position).getId()+""});
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(phuongNamLibSQLite.getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() { }
                });
    }

    public void UpdateCallcard( CallCard callcard){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();


        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.UpdateCallcard(token, callcard)
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
                        values.put(PhuongNamLibSQLite.getKeyMemberId(),callcard.getIdMember());
                        values.put(PhuongNamLibSQLite.getKeyBookId(),callcard.getIdBook());
                        values.put(PhuongNamLibSQLite.getKeyLibrarianId(),callcard.getIdLibrarian());
                        values.put(PhuongNamLibSQLite.getKeyCallcardBegindate(),callcard.getBeginDate());
                        values.put(PhuongNamLibSQLite.getKeyCallcardExpiresdate(),callcard.getExpiresDate());
                        db.update(PhuongNamLibSQLite.getTableCallcard(),values
                                ,PhuongNamLibSQLite.getKeyCallcardId()+"=?"
                                ,new String[]{callcard.getId()+""});

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
