package com.suadenuncia.consumer;

import com.suadenuncia.pojo.Denuncia;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DenunciaConsumer {


        private IDenunciaService denunciaService;
        private Retrofit retrofit;

        public DenunciaConsumer(){
            this.retrofit = new Retrofit.
                    Builder().
                    baseUrl(IDenunciaService.URL_BASE).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();

            this.denunciaService = retrofit.create(IDenunciaService.class);

        }

        public Call<Denuncia> postCadastrar(Denuncia denuncia) {
            return this.denunciaService.postCadastrar(denuncia);
        }





}
