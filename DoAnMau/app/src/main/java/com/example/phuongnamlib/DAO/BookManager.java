package com.example.phuongnamlib.DAO;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Models.Book;
import com.example.phuongnamlib.Models.BookToItem;
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

public class BookManager {
    private PhuongNamLibSQLite phuongNamLibSQLite;
    private String token;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    ArrayList<BookToItem> books;

    public BookManager(PhuongNamLibSQLite phuongNamLibSQLite, String token) {
        this.phuongNamLibSQLite = phuongNamLibSQLite;
        this.token = token;
    }

    public int GetQuantityBook(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select count("+PhuongNamLibSQLite.getKeyBookId()+") "
                +"from "+PhuongNamLibSQLite.getTableBook(),null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public  ArrayList<Book> LoadAllBook(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select *from "+ PhuongNamLibSQLite.getTableBook(),null);
        ArrayList<Book> books=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                books.add(new Book(cursor.getInt(0),cursor.getString(1)
                        ,cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6)));
            }while(cursor.moveToNext());
        }
        return books;
    }

    public  ArrayList<BookToItem> LoadAllBookToItem(){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select "+PhuongNamLibSQLite.getKeyBookId()+","
                +PhuongNamLibSQLite.getKeyBookName()+","
                +PhuongNamLibSQLite.getKeyBookPrice()+","
                +PhuongNamLibSQLite.getKeyBookQuantity()+","
                +PhuongNamLibSQLite.getKeyKindName()+","
                +PhuongNamLibSQLite.getKeyPublisherName()+","
                +PhuongNamLibSQLite.getKeyLanguageName()
                +" from "+ PhuongNamLibSQLite.getTableBook()
                +" inner join "
                +PhuongNamLibSQLite.getTableKind()
                +" on "
                +PhuongNamLibSQLite.getTableBook()+"."+PhuongNamLibSQLite.getKeyKindId()
                +" = "
                +PhuongNamLibSQLite.getTableKind()+"."+PhuongNamLibSQLite.getKeyKindId()
                +" inner join "
                +PhuongNamLibSQLite.getTablePublisher()
                +" on "
                +PhuongNamLibSQLite.getTableBook()+"."+PhuongNamLibSQLite.getKeyPublisherId()
                +" = "
                +PhuongNamLibSQLite.getTablePublisher()+"."+PhuongNamLibSQLite.getKeyPublisherId()
                +" inner join "
                +PhuongNamLibSQLite.getTableLanguage()
                +" on "
                +PhuongNamLibSQLite.getTableBook()+"."+PhuongNamLibSQLite.getKeyLanguageId()
                +" = "
                +PhuongNamLibSQLite.getTableLanguage()+"."+PhuongNamLibSQLite.getKeyLanguageId(),null);
        ArrayList<BookToItem> books=new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                books.add(new BookToItem(cursor.getInt(0),cursor.getString(1)
                        ,cursor.getInt(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5),cursor.getString(6)));
            }while(cursor.moveToNext());
        }
        return books;
    }

    public  int GetIdBook(String BookName){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select "+PhuongNamLibSQLite.getKeyBookId()
                +" from "+ PhuongNamLibSQLite.getTableBook()+" where "
                + PhuongNamLibSQLite.getKeyBookName() +" = "+"'"+BookName+"'",null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }


    public ArrayList<BookToItem> GetAllBookToItem(){
        return LoadAllBookToItem();
    }

    public ArrayList<Book> GetAllBook(){
        return LoadAllBook();
    }

    public void CreateNewBook(Book book){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.CreateBook(token, book)
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
                        values.put(PhuongNamLibSQLite.getKeyBookName(),book.getName());
                        values.put(PhuongNamLibSQLite.getKeyBookPrice(),book.getPrice());
                        values.put(PhuongNamLibSQLite.getKeyBookQuantity(),book.getCopiesQuantity());
                        values.put(PhuongNamLibSQLite.getKeyKindId(),book.getIdKind());
                        values.put(PhuongNamLibSQLite.getKeyPublisherId(),book.getIdPublisher());
                        values.put(PhuongNamLibSQLite.getKeyLanguageId(),book.getIdLanguage());
                        db.insert(PhuongNamLibSQLite.getTableBook(),null,values);
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

    public void DeleteBook( int position){

        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();

        ArrayList<Book> alBooks = LoadAllBook();

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.DeleteBook(token, alBooks.get(position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( Message message) {
                        db.delete(PhuongNamLibSQLite.getTableBook()
                                ,PhuongNamLibSQLite.getKeyBookId()+"=?"
                                ,new String[]{alBooks.get(position).getId()+""});
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(phuongNamLibSQLite.getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() { }
                });
    }

    public void UpdateBook( Book book){
        SQLiteDatabase db=phuongNamLibSQLite.getReadableDatabase();


        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.UpdateBook(token, book)
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
                        values.put(PhuongNamLibSQLite.getKeyBookId(),book.getId());
                        values.put(PhuongNamLibSQLite.getKeyBookName(),book.getName());
                        values.put(PhuongNamLibSQLite.getKeyBookPrice(),book.getPrice());
                        db.update(PhuongNamLibSQLite.getTableBook(),values
                                ,PhuongNamLibSQLite.getKeyBookId()+"=?"
                                ,new String[]{book.getId()+""});

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
