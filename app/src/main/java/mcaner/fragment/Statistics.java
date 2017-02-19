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
import android.widget.TextView;
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

public class Statistics extends AppCompatActivity {

    TextView user1,user2,user3,user4,user5,user6,user7,user8,user9,user10;
    TextView dogru1,dogru2,dogru3,dogru4,dogru5,dogru6,dogru7,dogru8,dogru9,dogru10;
    TextView sinav1,sinav2,sinav3,sinav4,sinav5,sinav6,sinav7,sinav8,sinav9,sinav10;
    Button btnMyStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        user1= (TextView) findViewById(R.id.user1);
        user2= (TextView) findViewById(R.id.user2);
        user3= (TextView) findViewById(R.id.user3);
        user4= (TextView) findViewById(R.id.user4);
        user5= (TextView) findViewById(R.id.user5);
        user6= (TextView) findViewById(R.id.user6);
        user7= (TextView) findViewById(R.id.user7);
        user8= (TextView) findViewById(R.id.user8);
        user9= (TextView) findViewById(R.id.user9);
        user10= (TextView) findViewById(R.id.user10);

        dogru1= (TextView) findViewById(R.id.puan1);
        dogru2= (TextView) findViewById(R.id.puan2);
        dogru3= (TextView) findViewById(R.id.puan3);
        dogru4= (TextView) findViewById(R.id.puan4);
        dogru5= (TextView) findViewById(R.id.puan5);
        dogru6= (TextView) findViewById(R.id.puan6);
        dogru7= (TextView) findViewById(R.id.puan7);
        dogru8= (TextView) findViewById(R.id.puan8);
        dogru9= (TextView) findViewById(R.id.puan9);
        dogru10= (TextView) findViewById(R.id.puan10);

        sinav1= (TextView) findViewById(R.id.sinav1);
        sinav2= (TextView) findViewById(R.id.sinav2);
        sinav3= (TextView) findViewById(R.id.sinav3);
        sinav4= (TextView) findViewById(R.id.sinav4);
        sinav5= (TextView) findViewById(R.id.sinav5);
        sinav6= (TextView) findViewById(R.id.sinav6);
        sinav7= (TextView) findViewById(R.id.sinav7);
        sinav8= (TextView) findViewById(R.id.sinav8);
        sinav9= (TextView) findViewById(R.id.sinav9);
        sinav10= (TextView) findViewById(R.id.sinav10);
        btnMyStatistics = (Button) findViewById(R.id.btnMyStatistics);

        new myAsyncTask().execute();

        btnMyStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserAsyncTask().execute();
            }
        });

    }



    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        public myAsyncTask() {
            this.dialog = new ProgressDialog(Statistics.this);

            user1.setText("...");
            user2.setText("...");
            user3.setText("...");
            user4.setText("...");
            user5.setText("...");
            user6.setText("...");
            user7.setText("...");
            user8.setText("...");
            user9.setText("...");
            user10.setText("...");

            dogru1.setText("...");
            dogru2.setText("...");
            dogru3.setText("...");
            dogru4.setText("...");
            dogru5.setText("...");
            dogru6.setText("...");
            dogru7.setText("...");
            dogru8.setText("...");
            dogru9.setText("...");
            dogru10.setText("...");

            sinav1.setText("...");
            sinav2.setText("...");
            sinav3.setText("...");
            sinav4.setText("...");
            sinav5.setText("...");
            sinav6.setText("...");
            sinav7.setText("...");
            sinav8.setText("...");
            sinav9.setText("...");
            sinav10.setText("...");

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


            String url = Data.url + "/donemprojesi/statistics/listall";

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
                List<StatisticsModel> statisticsModelList = new ArrayList<>();
                for(int x=0; x<jsonArray.length(); x++){
                    StatisticsModel model = new StatisticsModel();
                    model.setIdstatistics(jsonArray.getJSONObject(x).getString("idstatistics").toString());
                    model.setUsername(jsonArray.getJSONObject(x).getString("username").toString());
                    model.setExamname(jsonArray.getJSONObject(x).getString("examname").toString());
                    model.setDogru(jsonArray.getJSONObject(x).getString("dogru").toString());
                    model.setStatus(jsonArray.getJSONObject(x).getString("status").toString());

                   statisticsModelList.add(model);

                    switch (x){
                        case 0:{
                            user1.setText(statisticsModelList.get(x).getUsername());
                            dogru1.setText(statisticsModelList.get(x).getDogru());
                            sinav1.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 1:{
                            user2.setText(statisticsModelList.get(x).getUsername());
                            dogru2.setText(statisticsModelList.get(x).getDogru());
                            sinav2.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 2:{
                            user3.setText(statisticsModelList.get(x).getUsername());
                            dogru3.setText(statisticsModelList.get(x).getDogru());
                            sinav3.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 3:{
                            user4.setText(statisticsModelList.get(x).getUsername());
                            dogru4.setText(statisticsModelList.get(x).getDogru());
                            sinav4.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 4:{
                            user5.setText(statisticsModelList.get(x).getUsername());
                            dogru5.setText(statisticsModelList.get(x).getDogru());
                            sinav5.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 5:{
                            user6.setText(statisticsModelList.get(x).getUsername());
                            dogru6.setText(statisticsModelList.get(x).getDogru());
                            sinav6.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 6:{
                            user7.setText(statisticsModelList.get(x).getUsername());
                            dogru7.setText(statisticsModelList.get(x).getDogru());
                            sinav7.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 7:{
                            user8.setText(statisticsModelList.get(x).getUsername());
                            dogru8.setText(statisticsModelList.get(x).getDogru());
                            sinav8.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 8:{
                            user9.setText(statisticsModelList.get(x).getUsername());
                            dogru9.setText(statisticsModelList.get(x).getDogru());
                            sinav9.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 9:{
                            user10.setText(statisticsModelList.get(x).getUsername());
                            dogru10.setText(statisticsModelList.get(x).getDogru());
                            sinav10.setText(statisticsModelList.get(x).getExamname());
                        } break;

                    }


                }


            }
            catch (Exception e) {
                try {
                    String mesaj = jsonObject.getString("return").toString();
                    Mesaj(mesaj);
                } catch (Exception e1) {
                    Mesaj(e1.getMessage());
                }
            }
        }
    }

    private class UserAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        public UserAsyncTask() {
            this.dialog = new ProgressDialog(Statistics.this);

            user1.setText("...");
            user2.setText("...");
            user3.setText("...");
            user4.setText("...");
            user5.setText("...");
            user6.setText("...");
            user7.setText("...");
            user8.setText("...");
            user9.setText("...");
            user10.setText("...");

            dogru1.setText("...");
            dogru2.setText("...");
            dogru3.setText("...");
            dogru4.setText("...");
            dogru5.setText("...");
            dogru6.setText("...");
            dogru7.setText("...");
            dogru8.setText("...");
            dogru9.setText("...");
            dogru10.setText("...");

            sinav1.setText("...");
            sinav2.setText("...");
            sinav3.setText("...");
            sinav4.setText("...");
            sinav5.setText("...");
            sinav6.setText("...");
            sinav7.setText("...");
            sinav8.setText("...");
            sinav9.setText("...");
            sinav10.setText("...");

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


            String url = Data.url + "/donemprojesi/statistics/list";

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
            nameValuePair.add(new BasicNameValuePair("username", Data.username));

            HttpResponse response;
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair,"UTF-8"));
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
                List<StatisticsModel> statisticsModelList = new ArrayList<>();
                for(int x=0; x<jsonArray.length(); x++){
                    StatisticsModel model = new StatisticsModel();
                    model.setIdstatistics(jsonArray.getJSONObject(x).getString("idstatistics").toString());
                    model.setUsername(jsonArray.getJSONObject(x).getString("username").toString());
                    model.setExamname(jsonArray.getJSONObject(x).getString("examname").toString());
                    model.setDogru(jsonArray.getJSONObject(x).getString("dogru").toString());
                    model.setStatus(jsonArray.getJSONObject(x).getString("status").toString());

                    statisticsModelList.add(model);

                    switch (x){
                        case 0:{
                            user1.setText(statisticsModelList.get(x).getUsername());
                            dogru1.setText(statisticsModelList.get(x).getDogru());
                            sinav1.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 1:{
                            user2.setText(statisticsModelList.get(x).getUsername());
                            dogru2.setText(statisticsModelList.get(x).getDogru());
                            sinav2.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 2:{
                            user3.setText(statisticsModelList.get(x).getUsername());
                            dogru3.setText(statisticsModelList.get(x).getDogru());
                            sinav3.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 3:{
                            user4.setText(statisticsModelList.get(x).getUsername());
                            dogru4.setText(statisticsModelList.get(x).getDogru());
                            sinav4.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 4:{
                            user5.setText(statisticsModelList.get(x).getUsername());
                            dogru5.setText(statisticsModelList.get(x).getDogru());
                            sinav5.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 5:{
                            user6.setText(statisticsModelList.get(x).getUsername());
                            dogru6.setText(statisticsModelList.get(x).getDogru());
                            sinav6.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 6:{
                            user7.setText(statisticsModelList.get(x).getUsername());
                            dogru7.setText(statisticsModelList.get(x).getDogru());
                            sinav7.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 7:{
                            user8.setText(statisticsModelList.get(x).getUsername());
                            dogru8.setText(statisticsModelList.get(x).getDogru());
                            sinav8.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 8:{
                            user9.setText(statisticsModelList.get(x).getUsername());
                            dogru9.setText(statisticsModelList.get(x).getDogru());
                            sinav9.setText(statisticsModelList.get(x).getExamname());
                        } break;
                        case 9:{
                            user10.setText(statisticsModelList.get(x).getUsername());
                            dogru10.setText(statisticsModelList.get(x).getDogru());
                            sinav10.setText(statisticsModelList.get(x).getExamname());
                        } break;

                    }


                }


            }
            catch (Exception e) {
                try {
                    String mesaj = jsonObject.getString("return").toString();
                    Mesaj(mesaj);
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