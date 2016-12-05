package com.cococompany.android.aq;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.cococompany.android.aq.adapters.QuestionPagerAdapter;
import com.cococompany.android.aq.utils.UIutils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ContentActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private QuestionPagerAdapter questionPagerAdapter;
    private BottomNavigationView bottomBar;
    public static int currentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        UIutils.setToolbar(R.id.toolbar,this);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentFragment = position;
                if (currentFragment == 0) {
//                    questionPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        viewPager = (ViewPager) findViewById(R.id.pager);
        questionPagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(questionPagerAdapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });
        bottomBar = (BottomNavigationView) findViewById(R.id.bottom_bar);
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.feed_tab:
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.write_question_tab:
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.notifications_tab:
                        viewPager.setCurrentItem(2,false);
                        break;
                    case R.id.profile_tab:
                        viewPager.setCurrentItem(3,false);
                        break;

                }
                return false;
            }

        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        UIutils.setSearchBar(R.id.search, menu,this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
