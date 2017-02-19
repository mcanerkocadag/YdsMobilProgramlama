package mcaner.fragment;

/**
 * Created by M.Caner on 12.11.2016.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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

import static java.lang.System.exit;

public class Login extends AppCompatActivity {
    Button btnLogin,btnRegister;
    EditText edtUname, edtPass;
    ImageView imgTop;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnLogin= (Button) findViewById(R.id.btnlogin);
        btnRegister= (Button) findViewById(R.id.btnregister);
        edtUname = (EditText) findViewById(R.id.edtUname);
        edtPass = (EditText) findViewById(R.id.edtPass);
        imgTop= (ImageView) findViewById(R.id.imgTop);
        imgTop.setImageResource(R.drawable.image);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new myAsyncTask().execute();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(Login.this,Register.class);
                startActivity(register);
            }
        });
    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        JSONObject jsonObject = null;

        String username;
        String password;

        public myAsyncTask() {
            this.dialog = new ProgressDialog(Login.this);
            username = edtUname.getText().toString();
            password = edtPass.getText().toString();
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


            String url = Data.url + "/donemprojesi/users/signin";

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("username", username));
            nameValuePair.add(new BasicNameValuePair("password", password));

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
                String iduser = jsonObject.getString("iduser").toString();
                username = jsonObject.getString("username").toString();
                password = jsonObject.getString("password").toString();
                String email = jsonObject.getString("email").toString();
                String status = jsonObject.getString("status").toString();

                Data.iduser = iduser;
                Data.username = username;
                Data.password = password;
                Data.email = email;
                Data.status = status;

                Intent intent=new Intent(Login.this,Home.class);
                startActivity(intent);

            }
            catch (JSONException e) {

                try{
                    String returnValue;
                    returnValue = jsonObject.getString("return").toString();

                    if(returnValue.equals("wrong password")){
                        Mesaj("Yanlış Şifre!");
                    }
                    else{
                        Mesaj("Lütfen Bilgilerinizi Kontrol Ediniz!");
                    }
                }catch (JSONException er){
                    Mesaj(er.getMessage().toString());
                }
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


