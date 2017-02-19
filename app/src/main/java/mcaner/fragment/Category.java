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

public class Category extends AppCompatActivity {
    Button btnexamstart;
    EditText KelimeBilgisiEdt, DilBilgisiEdt, ClozeTestEdt, CümleTamamlamaEdt, İngilizceTürkceEdt, TürkceİngilizceEdt,
            OkumaParcalarıEdt, DiyalogTamamlamaEdt, YakinAnlamEdt, ParagrafTamamlamaEdt, AnlamBütünlügünüBozanEdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        KelimeBilgisiEdt = (EditText) findViewById(R.id.KelimeBilgisiEdt);
        DilBilgisiEdt = (EditText) findViewById(R.id.DilBilgisiEdt);
        ClozeTestEdt = (EditText) findViewById(R.id.ClozeTestEdt);
        CümleTamamlamaEdt = (EditText) findViewById(R.id.CümleTamamlamaEdt);
        İngilizceTürkceEdt = (EditText) findViewById(R.id.İngilizceTürkceEdt);
        TürkceİngilizceEdt = (EditText) findViewById(R.id.TürkceİngilizceEdt);
        OkumaParcalarıEdt = (EditText) findViewById(R.id.OkumaParcalarıEdt);
        DiyalogTamamlamaEdt = (EditText) findViewById(R.id.DiyalogTamamlamaEdt);
        YakinAnlamEdt = (EditText) findViewById(R.id.YakinAnlamEdt);
        ParagrafTamamlamaEdt = (EditText) findViewById(R.id.ParagrafTamamlamaEdt);
        AnlamBütünlügünüBozanEdt = (EditText) findViewById(R.id.AnlamBütünlügünüBozanEdt);

        btnexamstart= (Button) findViewById(R.id.btnexamstart);
        btnexamstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    if(KelimeBilgisiEdt.getText().length()==0){           KelimeBilgisiEdt.setText("0"); }
                    if(DilBilgisiEdt.getText().length()==0){              DilBilgisiEdt.setText("0"); }
                    if(ClozeTestEdt.getText().length()==0){               ClozeTestEdt.setText("0"); }
                    if(CümleTamamlamaEdt.getText().length()==0){          CümleTamamlamaEdt.setText("0"); }
                    if(İngilizceTürkceEdt.getText().length()==0){         İngilizceTürkceEdt.setText("0"); }
                    if(TürkceİngilizceEdt.getText().length()==0){         TürkceİngilizceEdt.setText("0"); }
                    if(OkumaParcalarıEdt.getText().length()==0){          OkumaParcalarıEdt.setText("0"); }
                    if(DiyalogTamamlamaEdt.getText().length()==0){        DiyalogTamamlamaEdt.setText("0"); }
                    if(YakinAnlamEdt.getText().length()==0){              YakinAnlamEdt.setText("0"); }
                    if(ParagrafTamamlamaEdt.getText().length()==0){       ParagrafTamamlamaEdt.setText("0"); }
                    if(AnlamBütünlügünüBozanEdt.getText().length()==0){   AnlamBütünlügünüBozanEdt.setText("0"); }


                int soruSayisi = Integer.parseInt(KelimeBilgisiEdt.getText().toString())
                        + Integer.parseInt(DilBilgisiEdt.getText().toString())
                        + Integer.parseInt(ClozeTestEdt.getText().toString())
                        + Integer.parseInt(CümleTamamlamaEdt.getText().toString())
                        + Integer.parseInt(İngilizceTürkceEdt.getText().toString())
                        + Integer.parseInt(TürkceİngilizceEdt.getText().toString())
                        + Integer.parseInt(OkumaParcalarıEdt.getText().toString())
                        + Integer.parseInt(DiyalogTamamlamaEdt.getText().toString())
                        + Integer.parseInt(YakinAnlamEdt.getText().toString())
                        + Integer.parseInt(ParagrafTamamlamaEdt.getText().toString())
                        + Integer.parseInt(AnlamBütünlügünüBozanEdt.getText().toString());
                if(soruSayisi>80 || soruSayisi<1){//80den fazla soru girilemez
                    //Mesaj("80 Soruya Tamamlayınız!");
                    Mesaj("1'den Az, 80'den Fazla Soru Girilemez!");
                }else {
                    new myAsyncTask().execute();
                }
                }catch (Exception e){
                    Mesaj("Error!");
                }

            }
        });

    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        JSONArray jsonArray = null;

        String counts;

        public myAsyncTask() {
            this.dialog = new ProgressDialog(Category.this);
            counts = KelimeBilgisiEdt.getText().toString()
            + "," + DilBilgisiEdt.getText().toString()
            + "," + ClozeTestEdt.getText().toString()
            + "," + CümleTamamlamaEdt.getText().toString()
            + "," + İngilizceTürkceEdt.getText().toString()
            + "," + TürkceİngilizceEdt.getText().toString()
            + "," + OkumaParcalarıEdt.getText().toString()
            + "," + DiyalogTamamlamaEdt.getText().toString()
            + "," + YakinAnlamEdt.getText().toString()
            + "," + ParagrafTamamlamaEdt.getText().toString()
            + "," + AnlamBütünlügünüBozanEdt.getText().toString();

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


            String url = Data.url + "/donemprojesi/questions/getq";

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
            nameValuePair.add(new BasicNameValuePair("counts", counts));

            HttpResponse response;
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair,"UTF-8"));
                response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    InputStream instream = entity.getContent();
                    String result = convertStreamToString(instream);

                    jsonArray = new JSONArray(result);

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
                Data.fragmentSayfaSayisi=jsonArray.length();
                for(int x=0; x<jsonArray.length(); x++){
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

                Intent nextExamScreen=new Intent(Category.this,MainActivity.class);
                startActivity(nextExamScreen);

            }
            catch (JSONException e) {
                Mesaj(e.getMessage().toString());
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