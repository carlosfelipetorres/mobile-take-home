package com.guestlogixtest.carlostorres.rickandmortyapp.model.api;


import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestApiManager
{
    private RickAndMortyApi rickAndMortyApi;
    public static final String BASE_URL = "https://rickandmortyapi.com/api";

    public RickAndMortyApi getRickAndMortyApi()
    {
        if(rickAndMortyApi == null)
        {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(String.class, new StringDesirializer());

            rickAndMortyApi = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .setConverter(new GsonConverter(gsonBuilder.create()))
                    .build()
                    .create(RickAndMortyApi.class);
        }
        return rickAndMortyApi;
    }
}
