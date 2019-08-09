package com.guestlogixtest.carlostorres.rickandmortyapp.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Episode;

public class CharacterPagerAdapter extends FragmentStatePagerAdapter {

    private String[] NAME_PAGES = {"Alive", "Dead"};

    private Episode episode;

    CharacterPagerAdapter(FragmentManager supportFragmentManager, Episode episode) {
        super(supportFragmentManager);
        this.episode = episode;
    }

    @Override
    public Fragment getItem(int position) {
        return ListFragment.newInstance(episode, position);
    }

    @Override public CharSequence getPageTitle(int position) {
        return NAME_PAGES[position];
    }


    @Override
    public int getCount() {
        return 2;
    }
}
