package com.guestlogixtest.carlostorres.rickandmortyapp.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.guestlogixtest.carlostorres.rickandmortyapp.R;
import com.guestlogixtest.carlostorres.rickandmortyapp.controller.local.CharacterRepository;
import com.guestlogixtest.carlostorres.rickandmortyapp.controller.local.RickAndMortyDatabase;
import com.guestlogixtest.carlostorres.rickandmortyapp.controller.remote.Controller;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.adapter.CharactersAdapter;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Character;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Episode;

import java.util.ArrayList;
import java.util.List;

public class CharactersActivity extends AppCompatActivity implements Controller.CharacterCallbackListener, CharactersAdapter.CharacterClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Character> characterList = new ArrayList<>();
    private CharactersAdapter charactersAdapter = new CharactersAdapter(characterList, this);
    private Controller controller;
    private Episode episode;
    private CharacterRepository characterRepository;
    private List<Character> savedCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        controller = new Controller();
        controller.setCharacterCallbacks(CharactersActivity.this);
        configViews();
        characterRepository = new CharacterRepository(RickAndMortyDatabase.getDatabase(getApplicationContext()).characterDao());
        episode = (Episode) getIntent().getSerializableExtra("episode");


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

    @Override
    protected void onResume() {
        controller.startCharacterFetching(episode.getCharacters());
        super.onResume();
    }

    private void configViews() {
        RecyclerView recyclerView = this.findViewById(R.id.list);
        swipeRefreshLayout = this.findViewById(R.id.swipe);

        recyclerView.setLayoutManager(new LinearLayoutManager(CharactersActivity.this));
        recyclerView.setAdapter(charactersAdapter);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimaryDark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                controller.startCharacterFetching(episode.getCharacters());
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
    public void onFetchCharactersProgress(List<Character> characters) {
        for (Character character: characters) {
            savedCharacters = characterRepository.charactersById(character.getId().toString(), episode.getEpisode());
            if (savedCharacters.size() > 0) {
                character.setStatus(savedCharacters.get(0).getStatus());
            }
        }
        charactersAdapter.addAllEpisodes(characters);
    }

    @Override
    public void onFetchCharactersComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void characterClicked(Character character) {
        Intent i = new Intent(this, CharacterDetailActivity.class);
        i.putExtra("character", character);
        i.putExtra("episode", episode);
        startActivity(i);
    }
}
