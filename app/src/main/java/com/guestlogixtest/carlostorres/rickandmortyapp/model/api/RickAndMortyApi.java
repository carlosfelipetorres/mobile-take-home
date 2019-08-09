package com.guestlogixtest.carlostorres.rickandmortyapp.model.api;


import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Character;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Data;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Episode;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface RickAndMortyApi
{
    @GET("/episode")
    void getEpisodes(Callback<Data<Episode>> episodes);

    @GET("/character/{ids}")
    void getCharacter(@Path("ids") String ids, Callback<List<Character>> characters);
}
