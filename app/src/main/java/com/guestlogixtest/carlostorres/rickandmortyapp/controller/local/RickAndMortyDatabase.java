package com.guestlogixtest.carlostorres.rickandmortyapp.controller.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Character;

@Database(entities = {Character.class}, version = 1, exportSchema = false)
public abstract class RickAndMortyDatabase extends RoomDatabase {

    public abstract CharacterDao characterDao();

    private static RickAndMortyDatabase INSTANCE = null;

    public static RickAndMortyDatabase getDatabase(Context context) {
        RickAndMortyDatabase tempInstance = INSTANCE;
        if (tempInstance != null) {
            return tempInstance;
        }

        synchronized(context) {
            RickAndMortyDatabase instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RickAndMortyDatabase.class,
                    "database")
                    .allowMainThreadQueries()
                    .build();
            INSTANCE = instance;
            return instance;
        }
    }
}