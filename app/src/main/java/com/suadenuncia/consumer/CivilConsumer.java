package com.suadenuncia.consumer;

import com.suadenuncia.pojo.Civil;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CivilConsumer {


        private ICivilService civilService;
        private Retrofit retrofit;

        public CivilConsumer(){
            this.retrofit = new Retrofit.
                    Builder().
                    baseUrl(ICivilService.URL_BASE).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();

            this.civilService = retrofit.create(ICivilService.class);

        }

        public Call<Civil> postAutentica(String email, String senha) {
            return this.civilService.postAutentica(email, senha);
        }

        public Call<Civil> postCadastrar(Civil civil) {
            return this.civilService.postCadastrar(civil);
        }

}
