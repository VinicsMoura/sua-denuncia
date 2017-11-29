package com.suadenuncia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.suadenuncia.consumer.CivilConsumer;
import com.suadenuncia.pojo.Civil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity implements Validator.ValidationListener {

    @Email(message = "E-mail inválido")
    EditText email;

    @Password(min = 4, scheme = Password.Scheme.ALPHA_NUMERIC, message = "Senha inválida")
    EditText senha;

    @ConfirmPassword(message = "As senhas não coincidem")
    EditText confirmarSenha;

    Button cadastrar;
    RadioButton civil, guarda, secretaria;
    RadioGroup tipoUsuario;
    CivilConsumer civilConsumer;
    Civil civilUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaComponentes();
        final Validator validator = new Validator(this);

        validator.setValidationListener(this);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();


                    civilUser = new Civil();

                    civilUser.setEmail(email.getText().toString());
                    civilUser.setSenha(senha.getText().toString());


                    civilConsumer.postCadastrar(civilUser).enqueue(new Callback<Civil>() {

                        @Override
                        public void onResponse(Call<Civil> call, Response<Civil> response) {

                            if(response.isSuccessful()){
                                civilUser.setId(response.body().getId());
                                Intent itTelaMapa = new Intent(CadastroActivity.this, MapsActivity.class);
                                itTelaMapa.putExtra("civil", civilUser);
                                startActivity(itTelaMapa);
                            }else{
                                Toast.makeText(CadastroActivity.this, "Falha ao cadastrar", Toast.LENGTH_LONG).show();
                                System.out.println(response.errorBody());
                                System.out.println(response.body());
                                System.out.println(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Civil> call, Throwable t) {
                            Toast.makeText(CadastroActivity.this, "Deu muito ruim", Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }

    private void inicializaComponentes(){
        email = (EditText) findViewById(R.id.et_email);
        senha = (EditText) findViewById(R.id.et_senha);
        confirmarSenha = (EditText) findViewById(R.id.et_confirmar_senha);
        cadastrar = (Button) findViewById(R.id.bt_cadastrar);
        civil = (RadioButton) findViewById(R.id.rb_civil);
        guarda = (RadioButton) findViewById(R.id.rb_guarda);
        secretaria = (RadioButton) findViewById(R.id.rb_secretaria);
        tipoUsuario = (RadioGroup) findViewById(R.id.rg_tipo);
        civilConsumer = new CivilConsumer();
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void civilSelecionado(){

    }

    public void guardaSelecionado(){

    }

    public void secretariaSelecionada(){

    }

    @Override
    public void onBackPressed()
    {
        Intent it = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(it);
        finish();
    }
}
