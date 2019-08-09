package com.guestlogixtest.carlostorres.rickandmortyapp.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;


import com.guestlogixtest.carlostorres.rickandmortyapp.R;
import com.guestlogixtest.carlostorres.rickandmortyapp.controller.remote.Controller;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.adapter.EpisodesAdapter;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Episode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Controller.EpisodeCallbackListener, EpisodesAdapter.EpisodeClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Episode> episodeList = new ArrayList<>();
    private EpisodesAdapter episodesAdapter = new EpisodesAdapter(episodeList, this);
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new Controller();
        controller.setEpisodeCallbacks(MainActivity.this);
        configViews();
        controller.startEpisodeFetching();
    }

    private void configViews() {
        RecyclerView recyclerView = this.findViewById(R.id.list);
        swipeRefreshLayout = this.findViewById(R.id.swipe);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(episodesAdapter);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimaryDark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                controller.startEpisodeFetching();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return (id == R.id.action_settings || super.onOptionsItemSelected(item));
    }

    @Override
    public void onFetchEpisodesProgress(List<Episode> episodes) {
        episodesAdapter.addAllEpisodes(episodes);
    }

    @Override
    public void onFetchEpisodesComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void episodeClicked(Episode episode) {
        Intent i = new Intent(this, CharactersActivity.class);
        i.putExtra("episode", episode);
        startActivity(i);
    }
}
