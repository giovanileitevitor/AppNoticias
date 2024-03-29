package br.applabbs.appnoticias.View;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.applabbs.appnoticias.R;
import br.applabbs.appnoticias.Utils.Utils;

public class MainActivity extends AppCompatActivity {


    private Button btnAtualizar;
    private Button btnAnterior;
    private Button btnProximo;
    private TextView txtRecuperado;
    private TextView txtTituloCard;
    private TextView txtConteudoCard;
    private EditText edtURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnProximo = findViewById(R.id.btnProximo);

        txtRecuperado = findViewById(R.id.txtRecuperado);
        txtTituloCard = findViewById(R.id.txtTituloCard);
        txtConteudoCard = findViewById(R.id.txtConteudoCard);

        edtURL = findViewById(R.id.edtURL);



        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask task = new MyTask();
                String urlAPI = "https://blockchain.info/ticker";
                //String urlDigitada = edtURL.getText().toString().trim();
                String CEPdigitado = edtURL.getText().toString().trim();
                String CEPpadrao = "37540000";



//                if(!Utils.isValidUrl(urlDigitada)){
//                    //carrega a tela hardcoded
//                    Toast.makeText(MainActivity.this, "URL Digitada inválida. \nSerá carregada a tela padrão", Toast.LENGTH_SHORT).show();
//                    task.execute(urlAPI);
//                }
//                else{
//                    //carrega a tela vinda do endereço http
//                    Toast.makeText(MainActivity.this, "Carregando a tela do endereço Santander", Toast.LENGTH_SHORT).show();
//                    task.execute(urlDigitada);
//                }


                if(CEPdigitado.isEmpty()){
                    String urlCEP = "https://viacep.com.br/ws/" + CEPpadrao + "/json/";
                    task.execute(urlCEP);
                }
                else{
                    String urlCEP = "https://viacep.com.br/ws/" + CEPdigitado + "/json/";
                    task.execute(urlCEP);
                }


            }
        });

    }


    class MyTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader =  null;
            StringBuffer buffer = null;

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                //Recupera os dados em Bytes
                inputStream = conexao.getInputStream();

                //Le os dados em Bytes e converte para caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //Objeto utilizado para leitura dos caracteres do InputStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);

                buffer = new StringBuffer();

                String linha = "";

                while((linha = reader.readLine()) != null){
                    buffer.append(linha);
                }


            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }


            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);


            String cep = null;
            String logradouro = null;

            try {
                JSONObject jsonObject = new JSONObject(resultado);
                cep = jsonObject.getString("cep");
                logradouro = jsonObject.getString("logradouro");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            txtTituloCard.setText(cep);
            txtConteudoCard.setText(logradouro);

            txtRecuperado.setText(resultado);
            //btnAtualizar.setFocusable(true);
        }
    }


}
