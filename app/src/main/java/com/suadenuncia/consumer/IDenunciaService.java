package com.suadenuncia.consumer;

import com.suadenuncia.pojo.Denuncia;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by viniciusmoura on 20/11/17.
 */

public interface IDenunciaService {


    String URL_BASE = "http://192.168.15.7:8081/denuncia/";


    @POST("cadastrar/")
    Call<Denuncia> postCadastrar(@Body Denuncia denuncia);




}
