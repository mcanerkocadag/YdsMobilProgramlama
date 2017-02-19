package mcaner.fragment;

/**
 * Created by M.Caner on 12.11.2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by M.Caner on 11.11.2016.
 */

public class Home extends AppCompatActivity {
    Button btnsinavbasla;
    Button btnprofilgit;

    @Override
    public void onBackPressed() {
        return ;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        btnsinavbasla= (Button) findViewById(R.id.btnnextcategory);
        btnprofilgit= (Button) findViewById(R.id.btnprofilgit);
        btnsinavbasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent examstart=new Intent(Home.this,Examselect.class);
                startActivity(examstart);

            }
        });
        btnprofilgit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilgit=new Intent(Home.this,Profil.class);
                startActivity(profilgit);
            }
        });

    }
}