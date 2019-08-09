package com.guestlogixtest.carlostorres.rickandmortyapp;

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
public class EpisodesUnitTest {
    @Test
    public void episodesListTest() throws Exception {
        List<Episode> episodes = new ArrayList<>(3);
        for (int i = 0; i<3; i++) {
            episodes.add(new Episode());
        }
        assertEquals("output should give back 3 results", 3, episodes.size());
    }

    @Test
    public void singleEpisodesTest() throws Exception {
        Episode episode = new Episode();
        episode.setEpisode("S01E01");
        assertEquals("output should give back episode", "S01E01", episode.getEpisode());
    }
}