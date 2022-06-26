package com.example.phuongnamlib.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.phuongnamlib.Activities.AdminMainActivity;
import com.example.phuongnamlib.Activities.LoginActivity;
import com.example.phuongnamlib.Activities.UserMainActivity;
import com.example.phuongnamlib.Models.Author;
import com.example.phuongnamlib.Models.AuthorManager;
import com.example.phuongnamlib.Models.Book;
import com.example.phuongnamlib.Models.CallCard;
import com.example.phuongnamlib.Models.Kind;
import com.example.phuongnamlib.Models.Language;
import com.example.phuongnamlib.Models.Librarian;
import com.example.phuongnamlib.Models.Member;
import com.example.phuongnamlib.Models.MemberType;
import com.example.phuongnamlib.Models.Publisher;
import com.example.phuongnamlib.Models.Role;
import com.example.phuongnamlib.Services.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhuongNamLibSQLite extends SQLiteOpenHelper {
    SQLiteDatabase db=this.getReadableDatabase();
    private static final String DATABASE_NAME="PhuongNamLib";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_AUTHOR="Author";
    private static final String KEY_AUTHOR_ID="ID_Author";
    private static final String KEY_AUTHOR_NAME="Name_Author";
    private static final String KEY_AUTHOR_DOB="Dob_Author";

    private static final String TABLE_KIND = "Kind";
    private static final String KEY_KIND_ID = "ID_Kind";
    private static final String KEY_KIND_NAME = "Name_Kind";

    private static final String TABLE_LANGUAGE = "Language";
    private static final String KEY_LANGUAGE_ID = "ID_Language";
    private static final String KEY_LANGUAGE_NAME = "Name_Language";

    private static final String TABLE_BOOK = "Book";
    private static final String KEY_BOOK_ID = "ID_Book";
    private static final String KEY_BOOK_NAME = "Name_Book";
    private static final String KEY_BOOK_PRICE = "Price";
    private static final String KEY_BOOK_QUANTITY = "CopiesQuantity";

    private static final String TABLE_PUBLISHER = "Publisher";
    private static final String KEY_PUBLISHER_ID = "ID_Publisher";
    private static final String KEY_PUBLISHER_NAME = "Name_Publisher";
    private static final String KEY_PUBLISHER_LOCATION = "Location_Publisher";

    private static final String TABLE_CALLCARD = "CallCard";
    private static final String KEY_CALLCARD_ID = "ID_CallCard";
    private static final String KEY_CALLCARD_BEGINDATE = "BeginDate";
    private static final String KEY_CALLCARD_EXPIRESDATE = "ExpiresDate";

    private static final String TABLE_LIBRARIAN = "Librarian";
    private static final String KEY_LIBRARIAN_ID = "ID_Librarian";
    private static final String KEY_LIBRARIAN_NAME = "Name_Librarian";
    private static final String KEY_LIBRARIAN_PASSWORD = "Password_Librarian";

    private static final String TABLE_MEMBER = "Member";
    private static final String KEY_MEMBER_ID = "ID_Member";
    private static final String KEY_MEMBER_NAME = "Name_Member";
    private static final String KEY_MEMBER_COUNTTIME = "CountTime";

    private static final String TABLE_ROLE = "Role";
    private static final String KEY_ROLE_ID = "ID_Role";
    private static final String KEY_ROLE_NAME = "Name_Role";

    private static final String TABLE_MEMBERTYPE = "MemberType";
    private static final String KEY_MEMBERTYPE_ID = "ID_MemberType";
    private static final String KEY_MEMBERTYPE_NAME = "Name_MemberType";
    private static final String KEY_MEMBERTYPE_DISCOUNT = "Discount_MemberType";

    private static final String TABLE_AUTHORMANAGER = "AuthorManager";

    Context context;

    public PhuongNamLibSQLite(@Nullable Context context) {
        super(context,DATABASE_NAME , null, DATABASE_VERSION);
    }


    /*public PhuongNamLibSQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sqlKind="create table "+TABLE_KIND+"("
                +KEY_KIND_ID+" integer primary key autoincrement "+","
                +KEY_KIND_NAME+" text"+")";
        db.execSQL(sqlKind);

        String sqlRole="create table "+TABLE_ROLE+"("
                +KEY_ROLE_ID+" integer primary key autoincrement "+","
                +KEY_ROLE_NAME+" text"+")";
        db.execSQL(sqlRole);



        String sqlLanguage="create table "+TABLE_LANGUAGE+"("
                +KEY_LANGUAGE_ID+" integer primary key autoincrement "+","
                +KEY_LANGUAGE_NAME+" text"+")";
        db.execSQL(sqlLanguage);

        String sqlMemberType="create table "+TABLE_MEMBERTYPE+"("
                +KEY_MEMBERTYPE_ID+" integer primary key autoincrement "+","
                +KEY_MEMBERTYPE_NAME+" text"+","
                +KEY_MEMBERTYPE_DISCOUNT+" integer"+")";
        db.execSQL(sqlMemberType);

        String sqlMember="create table "+TABLE_MEMBER+"("
                +KEY_MEMBER_ID+" integer primary key autoincrement "+","
                +KEY_MEMBER_NAME+" text" +","
                +KEY_MEMBER_COUNTTIME+" integer"+","
                +KEY_MEMBERTYPE_ID+" text" +")";
        db.execSQL(sqlMember);

        String sqlAuthor="create table "+TABLE_AUTHOR+"("
                +KEY_AUTHOR_ID+" integer primary key autoincrement "+","
                +KEY_AUTHOR_NAME+" text" +","
                +KEY_AUTHOR_DOB+" text" +")";
        db.execSQL(sqlAuthor);



        String sqlLibrarian="create table "+TABLE_LIBRARIAN+"("
                +KEY_LIBRARIAN_ID+" text primary key "+","
                +KEY_LIBRARIAN_NAME+" text" +","
                +KEY_LIBRARIAN_PASSWORD+" text"+","
                +KEY_ROLE_ID+" integer" +")";
        db.execSQL(sqlLibrarian);

        String sqlPublisher="create table "+TABLE_PUBLISHER+"("
                +KEY_PUBLISHER_ID+" integer primary key autoincrement "+","
                +KEY_PUBLISHER_NAME+" text" +","
                +KEY_PUBLISHER_LOCATION+" text" +")";
        db.execSQL(sqlPublisher);

        String sqlBook="create table "+TABLE_BOOK+"("
                +KEY_BOOK_ID+" integer primary key autoincrement "+","
                +KEY_BOOK_NAME+" text" +","
                +KEY_BOOK_PRICE+" integer" +","
                +KEY_BOOK_QUANTITY+" integer" +","
                +KEY_KIND_ID+" integer" +","
                +KEY_PUBLISHER_ID+" integer" +","
                +KEY_LANGUAGE_ID+" integer"+")";
        db.execSQL(sqlBook);

        String sqlCallCard="create table "+TABLE_CALLCARD+"("
                +KEY_CALLCARD_ID+" integer primary key autoincrement "+","
                +KEY_CALLCARD_BEGINDATE+" text" +","
                +KEY_CALLCARD_EXPIRESDATE+" text" +","
                +KEY_BOOK_ID+" integer" +","
                +KEY_MEMBER_ID+" integer" +","
                +KEY_LIBRARIAN_ID+" text" +")";
        db.execSQL(sqlCallCard);

        String sqlAuthorManager="create table "+TABLE_AUTHORMANAGER+"("
                +KEY_BOOK_ID+" integer " +","
                +KEY_AUTHOR_ID+" integer "+","+"primary key"+"("+KEY_BOOK_ID+","+KEY_AUTHOR_ID+")"+")";
        db.execSQL(sqlAuthorManager);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("drop table if exists "+TABLE_AUTHORMANAGER);

        db.execSQL("drop table if exists "+TABLE_AUTHOR);

        db.execSQL("drop table if exists "+TABLE_PUBLISHER);

        db.execSQL("drop table if exists "+TABLE_LANGUAGE);

        db.execSQL("drop table if exists "+TABLE_ROLE);

        db.execSQL("drop table if exists " +TABLE_BOOK);

        db.execSQL("drop table if exists " +TABLE_KIND);

        db.execSQL("drop table if exists " +TABLE_CALLCARD);

        db.execSQL("drop table if exists " +TABLE_MEMBER);

        db.execSQL("drop table if exists " +TABLE_LIBRARIAN);

        db.execSQL("drop table if exists "+TABLE_MEMBERTYPE);
        onCreate(db);
    }

    static int tablesHadInserted=0;
    boolean completement=false;;
    static int tablesForUserToInsert=11;
    public void InsertDataForTheFirstTimeAsUser(Context context, Class c){
        String token = context.getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE).getString("currentToken", "");
        //load dữ liệu từ sv vào bảng callcard
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.GetAllCallcard(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<CallCard>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<CallCard> CallcardList) {
                        for(int i=0; i < CallcardList.size(); i++){
                            ContentValues values=new ContentValues();
                            values.put(PhuongNamLibSQLite.getKeyCallcardId(),CallcardList.get(i).getId());
                            values.put(PhuongNamLibSQLite.getKeyMemberId(),CallcardList.get(i).getIdMember());
                            values.put(PhuongNamLibSQLite.getKeyBookId(),CallcardList.get(i).getIdBook());
                            values.put(PhuongNamLibSQLite.getKeyLibrarianId(),CallcardList.get(i).getIdLibrarian());
                            values.put(PhuongNamLibSQLite.getKeyCallcardBegindate(),CallcardList.get(i).getBeginDate());
                            values.put(PhuongNamLibSQLite.getKeyCallcardExpiresdate(),CallcardList.get(i).getExpiresDate());
                            db.insert(PhuongNamLibSQLite.getTableCallcard(),null,values);
                        }
                        tablesHadInserted=tablesHadInserted+1; if(tablesHadInserted==tablesForUserToInsert) completement=true;
                        if(completement==true) {

                            context.startActivity(new Intent(context, c));
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError( Throwable e) {
                        Toast.makeText(context,"failed callcard",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
        //load dữ liệu từ sv vào bảng Book
        requestInterface.GetAllBook(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Book>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<Book> BookList) {
                        ArrayList<Book> books=BookList;
                        for(int i=0; i < BookList.size(); i++){
                            ContentValues values=new ContentValues();
                            values.put(PhuongNamLibSQLite.getKeyBookId(),BookList.get(i).getId());
                            values.put(PhuongNamLibSQLite.getKeyBookName(),BookList.get(i).getName());
                            values.put(PhuongNamLibSQLite.getKeyBookPrice(),BookList.get(i).getPrice());
                            values.put(PhuongNamLibSQLite.getKeyBookQuantity(),BookList.get(i).getCopiesQuantity());
                            values.put(PhuongNamLibSQLite.getKeyKindId(),BookList.get(i).getIdKind());
                            values.put(PhuongNamLibSQLite.getKeyPublisherId(),BookList.get(i).getIdPublisher());
                            values.put(PhuongNamLibSQLite.getKeyLanguageId(),BookList.get(i).getIdLanguage());
                            db.insert(PhuongNamLibSQLite.getTableBook(),null,values);
                        }
                        tablesHadInserted=tablesHadInserted+1; if(tablesHadInserted==tablesForUserToInsert) completement=true;
                        if(completement==true) {
                            context.startActivity(new Intent(context, c));
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError( Throwable e) {
                        Toast.makeText(context,"failed book",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
        //load dữ liệu từ sv vào bảng Kind
        requestInterface.GetAllKind(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Kind>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<Kind> KindList) {
                        for(int i=0; i < KindList.size(); i++){
                            ContentValues values=new ContentValues();
                            values.put(PhuongNamLibSQLite.getKeyKindId(),KindList.get(i).getId());
                            values.put(PhuongNamLibSQLite.getKeyKindName(),KindList.get(i).getName());
                            db.insert(PhuongNamLibSQLite.getTableKind(),null,values);
                        }
                        tablesHadInserted=tablesHadInserted+1; if(tablesHadInserted==tablesForUserToInsert) completement=true;
                        if(completement==true) {
                            context.startActivity(new Intent(context, c));
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError( Throwable e) {
                        Toast.makeText(context,"failed kind",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
        //load dữ liệu từ sv vào bảng Member
        requestInterface.GetAllMember(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Member>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<Member> MemberList) {
                        for(int i=0; i < MemberList.size(); i++){
                            ContentValues values=new ContentValues();
                            values.put(PhuongNamLibSQLite.getKeyMemberId(),MemberList.get(i).getId());
                            values.put(PhuongNamLibSQLite.getKeyMembertypeId(),MemberList.get(i).getIdMemberType());
                            values.put(PhuongNamLibSQLite.getKeyMemberName(),MemberList.get(i).getName());
                            values.put(PhuongNamLibSQLite.getKeyMemberCounttime(),MemberList.get(i).getCountTime());
                            db.insert(PhuongNamLibSQLite.getTableMember(),null,values);
                        }
                        tablesHadInserted=tablesHadInserted+1; if(tablesHadInserted==tablesForUserToInsert) completement=true;
                        if(completement==true) {
                            context.startActivity(new Intent(context, c));
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError( Throwable e) {
                        Toast.makeText(context,"failed member",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
        //load data từ sv vào bảng librarian
        requestInterface.GetAllUser(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Librarian>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<Librarian> LibrarianList) {
                        for(int i=0; i < LibrarianList.size(); i++){
                            ContentValues values=new ContentValues();
                            values.put(PhuongNamLibSQLite.getKeyLibrarianId(),LibrarianList.get(i).getId());
                            values.put(PhuongNamLibSQLite.getKeyLibrarianName(),LibrarianList.get(i).getName());
                            values.put(PhuongNamLibSQLite.getKeyLibrarianPassword(),LibrarianList.get(i).getPassword());
                            values.put(PhuongNamLibSQLite.getKeyRoleId(),LibrarianList.get(i).getIdRole());
                            db.insert(PhuongNamLibSQLite.getTableLibrarian(),null,values);
                        }
                        tablesHadInserted=tablesHadInserted+1; if(tablesHadInserted==tablesForUserToInsert) completement=true;
                        if(completement==true) {
                            context.startActivity(new Intent(context, c));
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError( Throwable e) {
                        Toast.makeText(context,"failed librarian",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
        //load data từ sv vào bảng author
        requestInterface.GetAllAuthor(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Author>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<Author> AuthorList) {
                        for(int i=0; i < AuthorList.size(); i++){
                            ContentValues values=new ContentValues();
                            values.put(PhuongNamLibSQLite.getKeyAuthorId(),AuthorList.get(i).getId());
                            values.put(PhuongNamLibSQLite.getKeyAuthorName(),AuthorList.get(i).getName());
                            values.put(PhuongNamLibSQLite.getKeyAuthorDob(),AuthorList.get(i).getDob());
                            db.insert(PhuongNamLibSQLite.getTableAuthor(),null,values);
                        }
                        tablesHadInserted=tablesHadInserted+1; if(tablesHadInserted==tablesForUserToInsert) completement=true;
                        if(completement==true) {
                            context.startActivity(new Intent(context, c));
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError( Throwable e) {
                        Toast.makeText(context,"failed author",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
        //load data từ sv vào bảng role
        requestInterface.GetAllRole(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Role>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<Role> RoleList) {
                        for(int i=0; i < RoleList.size(); i++){
                            ContentValues values=new ContentValues();
                            values.put(PhuongNamLibSQLite.getKeyRoleId(),RoleList.get(i).getId());
                            values.put(PhuongNamLibSQLite.getKeyRoleName(),RoleList.get(i).getName());
                            db.insert(PhuongNamLibSQLite.getTableRole(),null,values);
                        }
                        tablesHadInserted=tablesHadInserted+1; if(tablesHadInserted==tablesForUserToInsert) completement=true;
                        if(completement==true) {
                            context.startActivity(new Intent(context, c));
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError( Throwable e) {
                        Toast.makeText(context,"failed role",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
        //load data từ sv vào bảng membertype
        requestInterface.GetAllMemberType(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<MemberType>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<MemberType> MemberTypeList) {
                        for(int i=0; i < MemberTypeList.size(); i++){
                            ContentValues values=new ContentValues();
                            values.put(PhuongNamLibSQLite.getKeyMembertypeId(),MemberTypeList.get(i).getId());
                            values.put(PhuongNamLibSQLite.getKeyMembertypeName(),MemberTypeList.get(i).getName());
                            values.put(PhuongNamLibSQLite.getKeyMembertypeDiscount(),MemberTypeList.get(i).getDiscountPercents());
                            db.insert(PhuongNamLibSQLite.getTableMembertype(),null,values);
                        }
                        tablesHadInserted=tablesHadInserted+1; if(tablesHadInserted==tablesForUserToInsert) completement=true;
                        if(completement==true) {
                            context.startActivity(new Intent(context, c));
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError( Throwable e) {
                        Toast.makeText(context,"failed membertype",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
        //load data từ sv vào bảng publisher
        requestInterface.GetAllPublisher(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Publisher>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<Publisher> publisherList) {
                        for(int i=0; i < publisherList.size(); i++){
                            ContentValues values=new ContentValues();
                            values.put(PhuongNamLibSQLite.getKeyPublisherId(),publisherList.get(i).getId());
                            values.put(PhuongNamLibSQLite.getKeyPublisherName(),publisherList.get(i).getName());
                            values.put(PhuongNamLibSQLite.getKeyPublisherLocation(),publisherList.get(i).getLocation());
                            db.insert(PhuongNamLibSQLite.getTablePublisher(),null,values);
                        }
                        tablesHadInserted=tablesHadInserted+1; if(tablesHadInserted==tablesForUserToInsert) completement=true;
                        if(completement==true) {
                            context.startActivity(new Intent(context, c));
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError( Throwable e) {
                        Toast.makeText(context,"failed publisher",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
        //load data từ sv vào bảng language
        requestInterface.GetAllLanguage(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Language>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<Language> LanguageList) {
                        for(int i=0; i < LanguageList.size(); i++){
                            ContentValues values=new ContentValues();
                            values.put(PhuongNamLibSQLite.getKeyLanguageId(),LanguageList.get(i).getId());
                            values.put(PhuongNamLibSQLite.getKeyLanguageName(),LanguageList.get(i).getName());
                            db.insert(PhuongNamLibSQLite.getTableLanguage(),null,values);
                        }
                        tablesHadInserted=tablesHadInserted+1; if(tablesHadInserted==tablesForUserToInsert) completement=true;
                        if(completement==true) {
                            context.startActivity(new Intent(context, c));
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError( Throwable e) {
                        Toast.makeText(context,"failed language",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
        //load data từ sv vào bảng authormanager
        requestInterface.GetAllAuthorManager(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<AuthorManager>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<AuthorManager> authorManagerList) {
                        for(int i=0; i < authorManagerList.size(); i++){
                            ContentValues values=new ContentValues();
                            values.put(PhuongNamLibSQLite.getKeyAuthorId(),authorManagerList.get(i).getIdAuthor());
                            values.put(PhuongNamLibSQLite.getKeyBookId(),authorManagerList.get(i).getIdBook());
                            db.insert(PhuongNamLibSQLite.getTableAuthormanager(),null,values);
                        }
                        tablesHadInserted=tablesHadInserted+1; if(tablesHadInserted==tablesForUserToInsert) completement=true;
                        if(completement==true) {
                            context.startActivity(new Intent(context, c));
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError( Throwable e) {
                        Toast.makeText(context,"failed authormanager",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
    }



    public static String getTableBook() {
        return TABLE_BOOK;
    }

    public static String getKeyBookId() {
        return KEY_BOOK_ID;
    }

    public static String getKeyBookName() {
        return KEY_BOOK_NAME;
    }

    public static String getKeyBookPrice() {
        return KEY_BOOK_PRICE;
    }

    public static String getTableKind() {
        return TABLE_KIND;
    }

    public static String getKeyKindId() {
        return KEY_KIND_ID;
    }

    public static String getKeyKindName() {
        return KEY_KIND_NAME;
    }

    public static String getTableCallcard() {
        return TABLE_CALLCARD;
    }

    public static String getKeyCallcardId() {
        return KEY_CALLCARD_ID;
    }

    public static String getKeyCallcardBegindate() {
        return KEY_CALLCARD_BEGINDATE;
    }

    public static String getKeyCallcardExpiresdate() {
        return KEY_CALLCARD_EXPIRESDATE;
    }

    public static String getTableMember() {
        return TABLE_MEMBER;
    }

    public static String getKeyMemberId() {
        return KEY_MEMBER_ID;
    }

    public static String getKeyMemberName() {
        return KEY_MEMBER_NAME;
    }

    public static String getKeyMemberCounttime() {
        return KEY_MEMBER_COUNTTIME;
    }

    public static String getTableLibrarian() {
        return TABLE_LIBRARIAN;
    }

    public static String getKeyLibrarianId() {
        return KEY_LIBRARIAN_ID;
    }

    public static String getKeyLibrarianName() {
        return KEY_LIBRARIAN_NAME;
    }

    public static String getKeyLibrarianPassword() {
        return KEY_LIBRARIAN_PASSWORD;
    }

    public static String getTableAuthor() {
        return TABLE_AUTHOR;
    }

    public static String getKeyAuthorId() {
        return KEY_AUTHOR_ID;
    }

    public static String getKeyAuthorName() {
        return KEY_AUTHOR_NAME;
    }

    public static String getKeyAuthorDob() {
        return KEY_AUTHOR_DOB;
    }

    public static String getTableLanguage() {
        return TABLE_LANGUAGE;
    }

    public static String getKeyLanguageId() {
        return KEY_LANGUAGE_ID;
    }

    public static String getKeyLanguageName() {
        return KEY_LANGUAGE_NAME;
    }

    public static String getKeyBookQuantity() {
        return KEY_BOOK_QUANTITY;
    }

    public static String getTablePublisher() {
        return TABLE_PUBLISHER;
    }

    public static String getKeyPublisherId() {
        return KEY_PUBLISHER_ID;
    }

    public static String getKeyPublisherName() {
        return KEY_PUBLISHER_NAME;
    }

    public static String getKeyPublisherLocation() {
        return KEY_PUBLISHER_LOCATION;
    }

    public static String getTableRole() {
        return TABLE_ROLE;
    }

    public static String getKeyRoleId() {
        return KEY_ROLE_ID;
    }

    public static String getKeyRoleName() {
        return KEY_ROLE_NAME;
    }

    public static String getTableMembertype() {
        return TABLE_MEMBERTYPE;
    }

    public static String getKeyMembertypeId() {
        return KEY_MEMBERTYPE_ID;
    }

    public static String getKeyMembertypeName() {
        return KEY_MEMBERTYPE_NAME;
    }

    public static String getKeyMembertypeDiscount() {
        return KEY_MEMBERTYPE_DISCOUNT;
    }

    public static String getTableAuthormanager() {
        return TABLE_AUTHORMANAGER;
    }

    public Context getContext() {
        return context;
    }
}
