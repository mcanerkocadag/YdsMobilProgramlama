package mcaner.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.RadioButton;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class OturumSinavi extends AppCompatActivity {

    CountDownTimer MyTimer;
    String time;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oturum_sinavi);

        final Button btnexamfinish= (Button) findViewById(R.id.btnExamFinish2);

        MyTimer = new CountDownTimer(Data.fragmentSayfaSayisi*135000,1000) {
            public void onTick(long millisUntilFinished) {

                time = "" + millisUntilFinished/60000 + ":" + (millisUntilFinished%60000)/1000;
                btnexamfinish.setText("Sınavı Bitir   " + time);

                // Burada timer içerisinde çalıştırılmak istenen kodlar yer almalı

            }

            @Override
            public void onFinish() {
                start();

                MyTimer.cancel();
                Data.time = time;
                new myAsyncTask().execute();
            }

        }.start();

        btnexamfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTimer.cancel();
                Data.time = time;
                new myAsyncTask().execute();
                //Intent examfinish=new Intent(OturumSinavi.this,ExamScore.class);
                //startActivity(examfinish);
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }
    @Override
    public void onBackPressed() {
        return;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return Data.fragmentSayfaSayisi;
        }
    }


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString("idquestion", Data.questions.get(sectionNumber-1).getIdquestion());
            args.putString("question", Data.questions.get(sectionNumber-1).getQuestion());
            args.putString("content", Data.questions.get(sectionNumber-1).getContent());
            args.putString("a", Data.questions.get(sectionNumber-1).getA());
            args.putString("b", Data.questions.get(sectionNumber-1).getB());
            args.putString("c", Data.questions.get(sectionNumber-1).getC());
            args.putString("d", Data.questions.get(sectionNumber-1).getD());
            args.putString("e", Data.questions.get(sectionNumber-1).getE());
            args.putString("answer", Data.questions.get(sectionNumber-1).getAnswer());
            args.putString("questionType", Data.questions.get(sectionNumber-1).getQuestionType());
            args.putString("status", Data.questions.get(sectionNumber-1).getStatus());
            args.putString("soruNo","" + sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_oturum_sinavi, container, false);

            TextView titleTw = (TextView) rootView.findViewById(R.id.titleTw2);
            TextView contentTw = (TextView) rootView.findViewById(R.id.contentTw2);
            TextView questionTw = (TextView) rootView.findViewById(R.id.questionTw2);
            TextView aTw = (TextView) rootView.findViewById(R.id.aTw2);
            TextView bTw = (TextView) rootView.findViewById(R.id.bTw2);
            TextView cTw = (TextView) rootView.findViewById(R.id.cTw2);
            TextView dTw = (TextView) rootView.findViewById(R.id.dTw2);
            TextView eTw = (TextView) rootView.findViewById(R.id.eTw2);
            final RadioButton aBtn = (RadioButton) rootView.findViewById(R.id.aBtn2);
            final RadioButton bBtn = (RadioButton) rootView.findViewById(R.id.bBtn2);
            final RadioButton cBtn = (RadioButton) rootView.findViewById(R.id.cBtn2);
            final RadioButton dBtn = (RadioButton) rootView.findViewById(R.id.dBtn2);
            final RadioButton eBtn = (RadioButton) rootView.findViewById(R.id.eBtn2);

            titleTw.setText(getArguments().getString("soruNo"));
            contentTw.setText(getArguments().getString("content"));
            questionTw.setText(getArguments().getString("question"));
            aTw.setText(getArguments().getString("a"));
            bTw.setText(getArguments().getString("b"));
            cTw.setText(getArguments().getString("c"));
            dTw.setText(getArguments().getString("d"));
            eTw.setText(getArguments().getString("e"));

            aBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bBtn.setChecked(false);
                    cBtn.setChecked(false);
                    dBtn.setChecked(false);
                    eBtn.setChecked(false);
                    Data.userAnswer[Integer.parseInt(getArguments().getString("soruNo"))-1]="a";
                }
            });
            bBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aBtn.setChecked(false);
                    cBtn.setChecked(false);
                    dBtn.setChecked(false);
                    eBtn.setChecked(false);
                    Data.userAnswer[Integer.parseInt(getArguments().getString("soruNo"))-1]="b";
                }
            });
            cBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bBtn.setChecked(false);
                    aBtn.setChecked(false);
                    dBtn.setChecked(false);
                    eBtn.setChecked(false);
                    Data.userAnswer[Integer.parseInt(getArguments().getString("soruNo"))-1]="c";
                }
            });
            dBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bBtn.setChecked(false);
                    cBtn.setChecked(false);
                    aBtn.setChecked(false);
                    eBtn.setChecked(false);
                    Data.userAnswer[Integer.parseInt(getArguments().getString("soruNo"))-1]="d";

                }
            });
            eBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bBtn.setChecked(false);
                    cBtn.setChecked(false);
                    dBtn.setChecked(false);
                    aBtn.setChecked(false);
                    Data.userAnswer[Integer.parseInt(getArguments().getString("soruNo"))-1]="e";
                }
            });

            return rootView;
        }
    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        JSONObject jsonObject = null;

        String username;
        String examname;
        int dogru;

        public myAsyncTask() {
            this.dialog = new ProgressDialog(OturumSinavi.this);
            username = Data.username;
            examname = Data.examname;
            dogru=0;
            for(int x=0; x<Data.questions.size(); x++) {
                if (Data.questions.get(x).getAnswer().equals(Data.userAnswer[x])) {
                    dogru++;
                }
                else if(Data.userAnswer[x]==null){
                    //bos++;
                }
                else {
                    //yanlis++;
                }
            }
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


            String url = Data.url + "/donemprojesi/statistics/add";

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
            nameValuePair.add(new BasicNameValuePair("username", username));
            nameValuePair.add(new BasicNameValuePair("examname", examname));
            nameValuePair.add(new BasicNameValuePair("dogru", ""+dogru));

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

                if(returnValue.equals("success")){
                    Intent intent=new Intent(OturumSinavi.this,ExamScore.class);
                    startActivity(intent);
                }else{
                    Mesaj(returnValue);
                    Intent intent=new Intent(OturumSinavi.this,ExamScore.class);
                    startActivity(intent);
                }

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
