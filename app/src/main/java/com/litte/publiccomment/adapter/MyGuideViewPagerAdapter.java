package com.litte.publiccomment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.litte.publiccomment.fragment_guide.FirstGuideFragment;
import com.litte.publiccomment.fragment_guide.FourGuideFragment;
import com.litte.publiccomment.fragment_guide.SecondGuideFragment;
import com.litte.publiccomment.fragment_guide.ThirdGuideFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by litte on 2018/1/20.
 */

public class MyGuideViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    public MyGuideViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(new FirstGuideFragment());
        fragmentList.add(new SecondGuideFragment());
        fragmentList.add(new ThirdGuideFragment());
        fragmentList.add(new FourGuideFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
