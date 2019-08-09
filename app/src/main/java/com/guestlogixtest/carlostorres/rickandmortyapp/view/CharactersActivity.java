package com.guestlogixtest.carlostorres.rickandmortyapp.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guestlogixtest.carlostorres.rickandmortyapp.R;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Episode;

public class CharactersActivity extends AppCompatActivity {

    private Episode episode;
    private ViewPager pager;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);

        episode = (Episode) getIntent().getSerializableExtra("episode");
        setViews();
        setViewPagerInfo();
    }

    private void setViews() {
        TextView chapter = this.findViewById(R.id.episode);
        TextView nameEpisode = this.findViewById(R.id.nameEpisode);
        chapter.setText(episode.getEpisode());
        nameEpisode.setText(episode.getName());
        ImageView back = this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setViewPagerInfo() {
        pager = this.findViewById(R.id.viewPager);
        tab = this.findViewById(R.id.tab);
        CharacterPagerAdapter adapter = new CharacterPagerAdapter(getSupportFragmentManager(), episode);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager, true);
    }
}
