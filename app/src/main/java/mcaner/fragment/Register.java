package mcaner.fragment;

/**
 * Created by M.Caner on 12.11.2016.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    EditText edtRname, edtRpass, edtRemail;
    Button btnRegisterfinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        edtRname = (EditText) findViewById(R.id.edtRname);
        edtRpass = (EditText) findViewById(R.id.edtRpass);
        edtRemail = (EditText) findViewById(R.id.edtRemail);
        btnRegisterfinish = (Button) findViewById(R.id.btnRegisterfinish);

        btnRegisterfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtRname.getText().length()<4) {Mesaj("Kullanıcı Adınız En Az 4 Karakterden Oluşmalı!");}
                else if(edtRpass.getText().length()<6) {Mesaj("Şifreniz En Az 6 Karakterden Oluşmalı!");}
                else if(edtRemail.getText().length()<6) {Mesaj("Geçersiz Email Bilgileri!");}
                else {
                    new myAsyncTask().execute();
                }
            }
        });

    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        JSONObject jsonObject = null;

        String username;
        String password;
        String email;

        public myAsyncTask() {
            this.dialog = new ProgressDialog(Register.this);
            username = edtRname.getText().toString();
            password = edtRpass.getText().toString();
            email = edtRemail.getText().toString();

        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {


            String url = Data.url + "/donemprojesi/users/register";

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
            nameValuePair.add(new BasicNameValuePair("username", username));
            nameValuePair.add(new BasicNameValuePair("password", password));
            nameValuePair.add(new BasicNameValuePair("email", email));

            HttpResponse response;
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair,"UTF-8"));
                response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    InputStream instream = entity.getContent();
                    String result = convertStreamToString(instream);

                    jsonObject = new JSONObject(result);

                    instream.close();
                }

            } catch (ClientProtocolException e) {
                Mesaj(e.getMessage());
            } catch (UnsupportedEncodingException e) {
                Mesaj(e.getMessage());
            } catch (IOException e) {
                Mesaj(e.getMessage());
            } catch (JSONException e) {
                Mesaj(e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void str) {
            if (dialog.isShowing())
                dialog.dismiss();

            try {

                String returnValue;
                returnValue = jsonObject.getString("return").toString();

                if(!returnValue.equals("success")){
                    Mesaj("Kayıtlı Kullanıcı Adı veya Email Girdiniz!");
                }else {
                    Mesaj("Kayıt Başarılı");
                    Intent register=new Intent(Register.this,Login.class);
                    startActivity(register);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        }

    private static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void Mesaj(String s) {

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}