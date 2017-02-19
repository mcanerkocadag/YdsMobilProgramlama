package mcaner.fragment;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    CountDownTimer MyTimer;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnexamfinish= (Button) findViewById(R.id.btnExamFinish);

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
                Intent ınto1 = new Intent(MainActivity.this, ExamScore.class);
                startActivity(ınto1);
            }




        }.start();

        btnexamfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTimer.cancel();
                Data.time = time;
                Intent examfinish=new Intent(MainActivity.this,ExamScore.class);
                startActivity(examfinish);
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
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            TextView titleTw = (TextView) rootView.findViewById(R.id.titleTw);
            TextView contentTw = (TextView) rootView.findViewById(R.id.contentTw);
            TextView questionTw = (TextView) rootView.findViewById(R.id.questionTw);
            TextView aTw = (TextView) rootView.findViewById(R.id.aTw);
            TextView bTw = (TextView) rootView.findViewById(R.id.bTw);
            TextView cTw = (TextView) rootView.findViewById(R.id.cTw);
            TextView dTw = (TextView) rootView.findViewById(R.id.dTw);
            TextView eTw = (TextView) rootView.findViewById(R.id.eTw);
            final RadioButton aBtn = (RadioButton) rootView.findViewById(R.id.aBtn);
            final RadioButton bBtn = (RadioButton) rootView.findViewById(R.id.bBtn);
            final RadioButton cBtn = (RadioButton) rootView.findViewById(R.id.cBtn);
            final RadioButton dBtn = (RadioButton) rootView.findViewById(R.id.dBtn);
            final RadioButton eBtn = (RadioButton) rootView.findViewById(R.id.eBtn);

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
}
