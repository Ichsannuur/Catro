package com.ics.catro.API;

import com.ics.catro.Object.Login;
import com.ics.catro.Object.Value;
import com.ics.catro.View.RegisterUser;

import java.sql.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Ichsan.Fatiha on 3/7/2018.
 */

public interface CatroAPI {
    @POST("register_user.php")
    @Multipart
    Call<Value> registerUser(@Part("email") RequestBody email,
                             @Part("username") RequestBody username,
                             @Part("password") RequestBody password,
                             @Part MultipartBody.Part file,
                             @Part("file") RequestBody name,
                             @Part("tgl_lahir") RequestBody tgl_lahir,
                             @Part("no_tlp") RequestBody no_tlp,
                             @Part("jenis_kelamin") RequestBody jenis_kelamin,
                             @Part("tgl_join") RequestBody tgl_join,
                             @Part("time_join") RequestBody time_join);

    @FormUrlEncoded
    @POST("login_user.php")
    Call<Login> login(@Field("email") String email, @Field("password") String password);

    @POST("add_article.php")
    @Multipart
    Call<Value> add_article(@Part("article") RequestBody article,
                            @Part MultipartBody.Part file,
                            @Part("file")RequestBody name,
                            @Part("tgl_posting")RequestBody tgl_posting);

    @GET("show_article_profile.php/email")
    Call<Value> show_article_profile(@Query("email") String email);

    @GET("show_article.php/email")
    Call<Value> show_article(@Query("email") String email);

    @GET("insert_like.php/option/id_article/email/id_like/tgl_like/time_like")
    Call<Value> insert_like(@Query("option") String option,
                            @Query("id_article") String id_article,
                            @Query("email") String email,
                            @Query("id_like") int id_like,
                            @Query("tgl_like") String tgl_like,
                            @Query("time_like") String time_like);
    @FormUrlEncoded
    @POST("search_profile.php")
    Call<Value> search_profile(@Field("search") String search);
}
