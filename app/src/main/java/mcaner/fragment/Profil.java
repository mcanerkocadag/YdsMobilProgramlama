package mcaner.fragment;

/**
 * Created by M.Caner on 12.11.2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by M.Caner on 11.11.2016.
 */

public class Profil extends AppCompatActivity {
    Button btnstatisticsgit;
    Button btnayargit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);
        btnstatisticsgit= (Button) findViewById(R.id.btnstatistics);
        btnayargit= (Button) findViewById(R.id.btnsettings);
        btnstatisticsgit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statisticsgit=new Intent(Profil.this,Statistics.class);
                startActivity(statisticsgit);
            }
        });
        btnayargit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ayargit=new Intent(Profil.this,Settings.class);
                startActivity(ayargit);
            }
        });

    }
}