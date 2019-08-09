package com.guestlogixtest.carlostorres.rickandmortyapp;

import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Character;
import com.guestlogixtest.carlostorres.rickandmortyapp.model.pojo.Episode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CharactersUnitTest {
    @Test
    public void characterListTest() throws Exception {
        List<Character> characters = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            characters.add(new Character());
        }
        assertEquals("output should give back 3 results", 3, characters.size());
    }

    @Test
    public void singleCharacterTest() throws Exception {
        Character character = new Character();
        character.setStatus("Alive");
        assertEquals("output should give back character status", "Alive", character.getStatus());
    }
}