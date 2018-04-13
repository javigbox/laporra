package com.gboxapps.laporra.api.services;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by Usuario1 on 29/11/2017.
 */

public interface UserApiInterface {

    /**
     * ## Login ##
     * URL: api/user/login
     * Method: POST
     * Params: email, password, arn
     * X-API-KEY: NO
     */
    @FormUrlEncoded
    @POST("/api/user/login")
    void loginUser(@Field("email") String email, @Field("password") String password, Callback<Response> callback);


    /**
     * ## Auto Login ##
     * URL: api/user/autologin
     * Method: POST
     * Params: arn
     * X-API-KEY: SI
     */
    @FormUrlEncoded
    @POST("/api/user/autologin")
    void autoLogin(@Header("X-API-KEY") String token, Callback<Response> callback);


    /**
     * ## Register ##
     * URL: api/user/register
     * Method: POST
     * Params: email, password, name, city, phone
     * X-API-KEY: NO
     */
    @FormUrlEncoded
    @POST("/api/user/register")
    void register(@Field("email") String email, @Field("password") String password, @Field("name") String name, @Field("city") String city, @Field("phone") String phone, Callback<Response> callback);


    /**
     * ## cerrar sesión ##
     * URL: api/user/session_close
     * Method: POST
     * X-API-KEY: SI
     */
    @POST("/api/user/session_close")
    void closeSession(@Header("X-API-KEY") String token, Callback<Response> callback);

    /**
     * ## recuperar cuenta ##
     * URL: api/user/recover
     * Method: POST
     * Params: email
     * X-API-KEY: NO
     */
    @FormUrlEncoded
    @POST("/api/user/recover")
    void recover(@Field("email") String email, Callback<Response> callback);

    /**
     * ## Editar perfil ##
     * URL: api/user/recover
     * Method: POST
     * Params: email, city, phone, email, password, password old
     * X-API-KEY: SI
     */
    @FormUrlEncoded
    @POST("/api/user/profile")
    void editProfile(@Header("X-API-KEY") String token, @Field("name") String name, @Field("city") String city, @Field("phone") String phone,
                     @Field("email") String email, @Field("password") String password, @Field("password_old") String password_old, Callback<Response> callback);

}
