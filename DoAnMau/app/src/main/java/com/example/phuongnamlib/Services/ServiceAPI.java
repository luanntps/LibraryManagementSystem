package com.example.phuongnamlib.Services;



import com.example.phuongnamlib.Models.Authen;
import com.example.phuongnamlib.Models.Author;
import com.example.phuongnamlib.Models.AuthorManager;
import com.example.phuongnamlib.Models.Book;
import com.example.phuongnamlib.Models.BorrowedKind;
import com.example.phuongnamlib.Models.CallCard;
import com.example.phuongnamlib.Models.Kind;
import com.example.phuongnamlib.Models.Language;
import com.example.phuongnamlib.Models.Librarian;
import com.example.phuongnamlib.Models.Member;
import com.example.phuongnamlib.Models.MemberType;
import com.example.phuongnamlib.Models.Message;
import com.example.phuongnamlib.Models.Publisher;
import com.example.phuongnamlib.Models.Revenue;
import com.example.phuongnamlib.Models.Role;
import com.example.phuongnamlib.Models.Token;
import com.example.phuongnamlib.Models.TopTenBook;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.Request;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAPI {
    String BASE_SERVICE="https://doanmau.luanntps19425.tokyo/";


    @GET("api/authentication")
    Observable<Authen> GetToken(@Query("ID") String id, @Query("password") String password);
    //user
    @GET("api/all-librarian")
    Observable<ArrayList<Librarian>> GetAllUser(@Header("Authorization") String token);
    @POST("api/add-librarian")
    Observable<Message> CreateUser(@Header("Authorization") String token, @Body Librarian librarian);

    @POST("api/delete-librarian")
    Observable<Message> DeleteUser(@Header("Authorization") String token,@Body Librarian librarian);

    @POST("api/update-librarian")
    Observable<Message> UpdateUser(@Header("Authorization") String token,@Body Librarian librarian);
    //callcard
    @GET("api/all-callcard")
    Observable<ArrayList<CallCard>> GetAllCallcard(@Header("Authorization") String token);
    @POST("api/add-callcard")
    Observable<Message> CreateCallcard(@Header("Authorization") String token, @Body CallCard callcard);

    @POST("api/delete-callcard")
    Observable<Message> DeleteCallcard(@Header("Authorization") String token,@Body CallCard callcard);

    @POST("api/update-callcard")
    Observable<Message> UpdateCallcard(@Header("Authorization") String token,@Body CallCard callcard);
    //book
    @GET("api/all-book")
    Observable<ArrayList<Book>> GetAllBook(@Header("Authorization") String token);
    @POST("api/add-book")
    Observable<Message> CreateBook(@Header("Authorization") String token, @Body Book book);

    @POST("api/delete-book")
    Observable<Message> DeleteBook(@Header("Authorization") String token,@Body Book book);

    @POST("api/update-book")
    Observable<Message> UpdateBook(@Header("Authorization") String token,@Body Book book);
    //kind
    @GET("api/all-kind")
    Observable<ArrayList<Kind>> GetAllKind(@Header("Authorization") String token);
    @POST("api/add-kind")
    Observable<Message> CreateKind(@Header("Authorization") String token, @Body Kind kind);

    @POST("api/delete-kind")
    Observable<Message> DeleteKind(@Header("Authorization") String token,@Body Kind kind);

    @POST("api/update-kind")
    Observable<Message> UpdateKind(@Header("Authorization") String token,@Body Kind kind);
    //member
    @GET("api/all-member")
    Observable<ArrayList<Member>> GetAllMember(@Header("Authorization") String token);
    @POST("api/add-member")
    Observable<Message> CreateMember(@Header("Authorization") String token, @Body Member member);

    @POST("api/delete-member")
    Observable<Message> DeleteMember(@Header("Authorization") String token,@Body Member member);

    @POST("api/update-member")
    Observable<Message> UpdateMember(@Header("Authorization") String token,@Body Member member );

    @GET("api/all-membertype")
    Observable<ArrayList<MemberType>> GetAllMemberType(@Header("Authorization") String token);

    @GET("api/all-publisher")
    Observable<ArrayList<Publisher>> GetAllPublisher(@Header("Authorization") String token);

    @GET("api/all-author")
    Observable<ArrayList<Author>> GetAllAuthor(@Header("Authorization") String token);

    @GET("api/all-authormanager")
    Observable<ArrayList<AuthorManager>> GetAllAuthorManager(@Header("Authorization") String token);

    @GET("api/all-role")
    Observable<ArrayList<Role>> GetAllRole(@Header("Authorization") String token);

    @GET("api/all-language")
    Observable<ArrayList<Language>> GetAllLanguage(@Header("Authorization") String token);

    @GET("api/revenue-by-month")
    Observable<ArrayList<Revenue>> GetAllRevenueByMonth(@Header("Authorization") String token);

    @GET("api/revenue-by-year")
    Observable<ArrayList<Revenue>> GetAllRevenueByYear(@Header("Authorization") String token);

    @GET("api/top-borrowed")
    Observable<ArrayList<BorrowedKind>> GetAllTopBorrowed(@Header("Authorization") String token);

    @GET("api/top-10-book")
    Observable<ArrayList<TopTenBook>> GetAllTopTen(@Header("Authorization") String token);
}
