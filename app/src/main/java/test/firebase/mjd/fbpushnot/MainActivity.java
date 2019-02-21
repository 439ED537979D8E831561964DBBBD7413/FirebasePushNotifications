package test.firebase.mjd.fbpushnot;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView mProfileLabel;
    private TextView mUsersLabel;
    private TextView mNotificationLabel;

    private ViewPager mMainPager;

    private PagerViewAdapter mPagerViewApadter;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            sendToLogin();
        }
    }
    private void sendToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mProfileLabel = (TextView) findViewById(R.id.profileLabel);
        mUsersLabel = (TextView) findViewById(R.id.usersLabel);
        mNotificationLabel = (TextView) findViewById(R.id.notificationsLabel);

        mMainPager = (ViewPager) findViewById(R.id.mainPager);

        mPagerViewApadter = new PagerViewAdapter(getSupportFragmentManager());
        mMainPager.setAdapter(mPagerViewApadter);
        mMainPager.setOffscreenPageLimit(2);  //loading issue - changing tabs 

        mProfileLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(0);
            }
        });
        mUsersLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(1);
            }
        });
        mNotificationLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(2);
            }
        });

        mMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                changeTabs(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void changeTabs(int i) {
        if(i == 0) {
            mProfileLabel.setTextColor(getColor(R.color.textTabBright));
            mProfileLabel.setTextSize(22);

            mUsersLabel.setTextColor(getColor(R.color.textTabLight));
            mUsersLabel.setTextSize(18);

            mNotificationLabel.setTextColor(getColor(R.color.textTabLight));
            mNotificationLabel.setTextSize(18);
        }
        if(i == 1) {
            mProfileLabel.setTextColor(getColor(R.color.textTabLight));
            mProfileLabel.setTextSize(18);

            mUsersLabel.setTextColor(getColor(R.color.textTabBright));
            mUsersLabel.setTextSize(22);

            mNotificationLabel.setTextColor(getColor(R.color.textTabLight));
            mNotificationLabel.setTextSize(18);
        }
        if(i == 2) {
            mProfileLabel.setTextColor(getColor(R.color.textTabLight));
            mProfileLabel.setTextSize(18);

            mUsersLabel.setTextColor(getColor(R.color.textTabLight));
            mUsersLabel.setTextSize(18);

            mNotificationLabel.setTextColor(getColor(R.color.textTabBright));
            mNotificationLabel.setTextSize(22);
        }

    }
}
