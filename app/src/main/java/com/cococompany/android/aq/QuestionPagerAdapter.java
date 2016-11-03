package com.cococompany.android.aq;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

import com.cococompany.android.aq.fragments.FeedFragment;
import com.cococompany.android.aq.fragments.NotificationsFragment;
import com.cococompany.android.aq.fragments.ProfileFragment;
import com.cococompany.android.aq.fragments.WriteQuestionFragment;

/**
 * Created by Valentin on 03.11.2016.
 */
public class QuestionPagerAdapter extends FragmentPagerAdapter{
    String string1 = "1";
    String string2 = "2";
    public QuestionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return FeedFragment.newInstance(string1, string2);
            case 1:
                return WriteQuestionFragment.newInstance(string1, string2);
            case 2:
                return NotificationsFragment.newInstance(string1, string2);
            case 3:
                return ProfileFragment.newInstance(string1, string2);
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 4;
    }
}
