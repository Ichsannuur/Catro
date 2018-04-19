package com.ics.catro.API;

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
    @FormUrlEncoded
    @POST("register_user.php")
    Call<Value> registerUser(@Field("email") String email,
                             @Field("username") String username,
                             @Field("password") String password,
                             @Field("tgl_lahir") String tgl_lahir,
                             @Field("no_tlp") String no_tlp,
                             @Field("jenis_kelamin") String jenis_kelamin);

    @FormUrlEncoded
    @POST("login_user.php")
    Call<Value> login(@Field("email") String email,@Field("password") String password);

    @POST("add_article.php")
    @Multipart
    Call<Value> add_article(@Part("article") RequestBody article,
                            @Part MultipartBody.Part file, @Part("file")RequestBody name,
                            @Part("tgl_posting")RequestBody tgl_posting);

    @GET("show_article_profile.php/email")
    Call<Value> show_article_profile(@Query("email") String email);

    @GET("show_article.php/email")
    Call<Value> show_article(@Query("email") String email);

    @GET("insert_like.php/option/id_article/email/id_like")
    Call<Value> insert_like(@Query("option") String option,@Query("id_article") String id_article,@Query("email") String email,@Query("id_like") int id_like);
}
