package com.suadenuncia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.suadenuncia.consumer.DenunciaConsumer;
import com.suadenuncia.pojo.Civil;
import com.suadenuncia.pojo.Denuncia;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DenunciaActivity extends AppCompatActivity {

    private EditText titulo, descricao;
    private AutoCompleteTextView categoria;
    private Button cadDenuncia;
    private DenunciaConsumer denunciaConsumer;
    private Denuncia denuncia;
    private String[] categorias = {"Estacionamento em local proibido", "Estacionamento indevido em local preferencial", "Poluição sonora"};

    private Civil civil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        inicializaComponentes();

        cadDenuncia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                denuncia = new Denuncia();
                denuncia.setNome(titulo.getText().toString());
                denuncia.setDescricao(descricao.getText().toString());
                denuncia.setCategoria(categoria.getText().toString());
                denuncia.setCivil(civil);
                denuncia.setLongitude((float) getIntent().getDoubleExtra("longitude", 0));
                denuncia.setLatitude((float) getIntent().getDoubleExtra("latitude", 0));
                denunciaConsumer.postCadastrar(denuncia).enqueue(new Callback<Denuncia>() {
                    @Override
                    public void onResponse(Call<Denuncia> call, Response<Denuncia> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(DenunciaActivity.this, "Denúncia cadastrada", Toast.LENGTH_LONG).show();
                            Intent itTelaMapa = new Intent(DenunciaActivity.this, MapsActivity.class);
                            itTelaMapa.putExtra("civil", civil);
                            startActivity(itTelaMapa);
                            finish();
                        }else{
                            Toast.makeText(DenunciaActivity.this, "Falha ao cadastrar", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Denuncia> call, Throwable t) {
                        Toast.makeText(DenunciaActivity.this, "Denúncia cadastrada", Toast.LENGTH_LONG).show();
                        System.out.println(t.getStackTrace());
                        System.out.println(t.getCause());
                        System.out.println(t.getMessage());
                    }
                });
            }
        });

    }

    public void inicializaComponentes() {
        this.titulo = findViewById(R.id.et_titulo);
        this.descricao = findViewById(R.id.et_descricao);
        this.cadDenuncia = findViewById(R.id.bt_cadDenuncia);
        this.categoria = findViewById(R.id.actv_categoria);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.categorias);
        this.categoria.setAdapter(adapter);
        this.denunciaConsumer = new DenunciaConsumer();
        this.civil = (Civil) getIntent().getSerializableExtra("civil");
        this.civil.getId();
        this.civil.getEmail();
    }

    @Override
    public void onBackPressed()
    {
        Intent it = new Intent(DenunciaActivity.this, MapsActivity.class);
        startActivity(it);
        finish();
    }
}
