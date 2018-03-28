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

/**
 * Created by Ichsan.Fatiha on 3/7/2018.
 */

public interface CatroAPI {
    @FormUrlEncoded
    @POST("register_user.php")
    Call<Value> registerUser(@Field("email") String email,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("login_user.php")
    Call<Value> login(@Field("email") String email,@Field("password") String password);

    @POST("add_article.php")
    @Multipart
    Call<Value> add_article(@Part("article") RequestBody article,
                            @Part MultipartBody.Part file, @Part("file")RequestBody name,
                            @Part("tgl_posting")RequestBody tgl_posting);

    @GET("show_article_profile.php")
    Call<Value> show_article_profile();

    @GET("show_article.php")
    Call<Value> show_article();

    @POST("insert_like")
    Call<Value> insert_like(@Field("id_article") String id_article,@Field("email") String email);
}
