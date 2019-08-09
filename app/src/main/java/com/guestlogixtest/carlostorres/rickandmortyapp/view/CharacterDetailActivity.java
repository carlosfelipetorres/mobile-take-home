package com.guestlogixtest.carlostorres.rickandmortyapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guestlogixtest.carlostorres.rickandmortyapp.R;
import com.guestlogixtest.carlostorres.rickandmortyapp.controller.local.CharacterRepository;
import com.guestlogixtest.carlostorres.rickandmortyapp.controller.local.RickAndMortyDatabase;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Character;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Episode;


public class CharacterDetailActivity extends AppCompatActivity {

    private Character current;
    private Episode episode;
    private CharacterRepository characterRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        current = (Character) getIntent().getSerializableExtra("character");
        episode = (Episode) getIntent().getSerializableExtra("episode");
        characterRepository = new CharacterRepository(RickAndMortyDatabase.getDatabase(getApplicationContext()).characterDao());

        setViews();
    }

    private void setViews() {
        TextView name = this.findViewById(R.id.name);
        name.setText(current.getName());
        TextView status = this.findViewById(R.id.status);
        status.setText("Status: " + current.getStatus());
        TextView species = this.findViewById(R.id.species);
        species.setText("Species: " + current.getSpecies());
        TextView gender = this.findViewById(R.id.gender);
        gender.setText("Gender: " + current.getGender());
        ImageView image = this.findViewById(R.id.image);
        ImageView imageDead = this.findViewById(R.id.imageDead);

        Glide.with(this).load(current.getImage()).into(image);
        if (current.getStatus().equals("Dead")){
            imageDead.setVisibility(View.VISIBLE);
        }

        ImageView back = this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button kill = this.findViewById(R.id.kill);
        if (current.getStatus().equals("Dead")) kill.setVisibility(View.GONE);
        kill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setStatus("Dead");
                current.setEpisodeDead(episode.getEpisode());
                characterRepository.insert(current);
                onBackPressed();
            }
        });
    }
}
