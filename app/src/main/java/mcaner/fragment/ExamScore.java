package mcaner.fragment;

/**
 * Created by M.Caner on 12.11.2016.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by M.Caner on 11.11.2016.
 */

public class ExamScore extends AppCompatActivity {

    TextView tvDogru,tvYanlis,tvBos,tvSure;
    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examfinish);

        tvDogru = (TextView) findViewById(R.id.tvDogru);
        tvYanlis= (TextView) findViewById(R.id.tvYanlis);
        tvBos= (TextView) findViewById(R.id.tvBos);
        tvSure= (TextView) findViewById(R.id.tvSure);
        btnHome = (Button) findViewById(R.id.btnAnaSayfa);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(ExamScore.this,Home.class);
                startActivity(gohome);
            }
        });

        new myAsyncTask().execute();

    }
    @Override
    public void onBackPressed() {
        return;
    }
    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        int dogru,yanlis,bos;

        public myAsyncTask() {
            dogru=0;
            yanlis=0;
            bos=0;
        }

        @Override
        protected Void doInBackground(Void... params) {
            for(int x=0; x<Data.questions.size(); x++) {
                if (Data.questions.get(x).getAnswer().equals(Data.userAnswer[x])) {
                    dogru++;
                }
                else if(Data.userAnswer[x]==null){
                    bos++;
                }
                else {
                    yanlis++;
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void str) {

            try {
                tvDogru.setText(""+dogru);
                tvYanlis.setText(""+yanlis);
                tvBos.setText(""+bos);
                tvSure.setText(Data.time);

            }catch (Exception e) {
                Mesaj(e.getMessage().toString());
            }


        }
    }


    private void Mesaj(String s) {

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}