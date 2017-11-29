package com.suadenuncia.consumer;

import com.suadenuncia.pojo.Civil;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICivilService {


    String URL_BASE = "http://192.168.15.7:8081/civil/";

    @POST("civil/{email}/{senha}")
    Call<Civil> postAutentica(@Path("email") String email, @Path("senha") String senha);

    @POST("cadastrar/")
    Call<Civil> postCadastrar(@Body Civil civil);


}
