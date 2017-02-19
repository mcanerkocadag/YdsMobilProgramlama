package mcaner.fragment;

/**
 * Created by M.Caner on 12.11.2016.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by M.Caner on 11.11.2016.
 */

public class Examselect extends AppCompatActivity {
    Button btnnormalexam, btnSessionExam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examselect);

        btnnormalexam= (Button) findViewById(R.id.btnnormalexam);
        btnSessionExam = (Button) findViewById(R.id.btnsessionexam);

        btnnormalexam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nxtcategory=new Intent(Examselect.this,Category.class);
                startActivity(nxtcategory);
            }
        });

        btnSessionExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new myAsyncTask().execute();
            }
        });

    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        public myAsyncTask() {
            this.dialog = new ProgressDialog(Examselect.this);

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


            String url = Data.url + "/donemprojesi/exams/search";

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(url);

            HttpResponse response;
            try {
                response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    InputStream instream = entity.getContent();
                    String result = convertStreamToString(instream);

                    try {
                        jsonArray = new JSONArray(result);
                    }catch (Exception e){
                        jsonObject = new JSONObject(result);
                    }

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
                Data.questions.clear();
                Data.fragmentSayfaSayisi=jsonArray.length()-1;
                Data.examname=jsonArray.getJSONObject(0).getString("examname").toString();
                for(int x=1; x<jsonArray.length(); x++){
                    QuestionModel model = new QuestionModel();
                    model.setIdquestion(jsonArray.getJSONObject(x).getString("idquestion").toString());
                    model.setQuestion(jsonArray.getJSONObject(x).getString("question").toString());
                    model.setContent(jsonArray.getJSONObject(x).getString("content").toString());
                    model.setA(jsonArray.getJSONObject(x).getString("a").toString());
                    model.setB(jsonArray.getJSONObject(x).getString("b").toString());
                    model.setC(jsonArray.getJSONObject(x).getString("c").toString());
                    model.setD(jsonArray.getJSONObject(x).getString("d").toString());
                    model.setE(jsonArray.getJSONObject(x).getString("e").toString());
                    model.setAnswer(jsonArray.getJSONObject(x).getString("answer").toString());
                    model.setQuestionType(jsonArray.getJSONObject(x).getString("questionType").toString());
                    model.setStatus(jsonArray.getJSONObject(x).getString("status").toString());

                    Data.questions.add(model);

                }

                for(int x=0; x<Data.userAnswer.length; x++){
                    Data.userAnswer[x]=null;
                }

                Intent nextExamScreen=new Intent(Examselect.this,OturumSinavi.class);
                startActivity(nextExamScreen);

            }
            catch (Exception e) {
                try {
                    String mesaj = jsonObject.getString("return").toString();
                    if(mesaj.equals("null")){
                        Mesaj("Uygun Sınav Bulunamadı!");
                    }else {
                        Mesaj("Bir sonraki en yakın sınav: " + mesaj);
                    }
                } catch (Exception e1) {
                    Mesaj(e1.getMessage());
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