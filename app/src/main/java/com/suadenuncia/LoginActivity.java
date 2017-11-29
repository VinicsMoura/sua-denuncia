package com.suadenuncia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.suadenuncia.consumer.CivilConsumer;
import com.suadenuncia.pojo.Civil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Email(message = "E-mail inválido")
    EditText email;

    @Password(min = 4, scheme = Password.Scheme.ALPHA_NUMERIC, message = "Senha inválida")
    EditText senha;

    Button entrar, cadastreSe;
    private Civil civil;
    private SharedPreferences spLogin;
    private SharedPreferences.Editor editor;
    public static final String NOME_ARQUIVO = "arquivo_login";
    private CivilConsumer civilConsumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializaComponentes();

        if(this.verificaJaLogou()) {
            chamaMapsActivity();
        }

        this.entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                civil.setEmail(email.getText().toString());
                civil.setSenha(senha.getText().toString());
                autenticaWS(civil.getEmail(),civil.getSenha());

            }
        });

        cadastreSe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    private void chamaMapsActivity() {
        Intent itTelaLogado = new Intent(LoginActivity.this, MapsActivity.class);
        Bundle parametros = new Bundle();
        parametros.putSerializable("civil", civil);
        itTelaLogado.putExtras(parametros);
        startActivity(itTelaLogado);
        finish();
    }

    private boolean verificaJaLogou() {
        boolean logou = false;
        String login = this.spLogin.getString("login", null);
        if(login!=null) {
            this.civil.setEmail(login);
            logou = true;
        }
        return logou;
    }

    private Civil autenticaWS(String email, String senha) {
        Log.i("debug","entrou AutenticaWS");

        this.civilConsumer.postAutentica(email, senha).enqueue(new Callback<Civil>() {
            @Override
            public void onResponse(Call<Civil> call, Response<Civil> response) {
                Log.i("debug","entrou OnResponse");
                civil = response.body();
                if(response.isSuccessful() && civil != null) {
                    editor.putString("email", civil.getEmail());
                    editor.commit();
                    chamaMapsActivity();
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jObjError.getString("errorMessage"), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<Civil> call, Throwable t) {
                Log.i("debug","erro");
                t.printStackTrace();

            }
        });


        return civil;
    }

    private void inicializaComponentes(){
        email = (EditText) findViewById(R.id.et_email);
        senha = (EditText) findViewById(R.id.et_senha);
        entrar = (Button) findViewById(R.id.bt_entrar);
        cadastreSe = (Button) findViewById(R.id.bt_cadastre_se);
        this.civil = new Civil();
        this.spLogin = super.getApplicationContext().getSharedPreferences(NOME_ARQUIVO,MODE_APPEND);
        this.editor = this.spLogin.edit();
        this.civilConsumer = new CivilConsumer();
    }
}
