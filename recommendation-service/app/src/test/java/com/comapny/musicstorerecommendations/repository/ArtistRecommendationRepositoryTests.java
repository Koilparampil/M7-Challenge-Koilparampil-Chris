package com.comapny.musicstorerecommendations.repository;
//Code referenced from Record-collection Activity from class

import com.comapny.musicstorerecommendations.model.ArtistRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ArtistRecommendationRepositoryTests {

    @Autowired
    ArtistRepository artistRepository;

    @Before
    public void setUp() throws Exception {
        artistRepository.deleteAll();
    }

    @Test
    public void addGetDeleteArtistRecommendation() {

        ArtistRecommendation artistRecommendation = new ArtistRecommendation();
        artistRecommendation.setArtistID(1);
        artistRecommendation.setUserID(1);
        artistRecommendation.setLiked(true);

        artistRecommendation = artistRepository.save(artistRecommendation);

        Optional<ArtistRecommendation> artistRecommendation1 = artistRepository.findById(artistRecommendation.getArtistRecommendationID());

        assertEquals(artistRecommendation1.get(), artistRecommendation);

        artistRepository.deleteById(artistRecommendation.getArtistRecommendationID());

        artistRecommendation1 = artistRepository.findById(artistRecommendation.getArtistRecommendationID());

        assertFalse(artistRecommendation1.isPresent());
    }

    @Test
    public void updateArtistRecommendation() {

        ArtistRecommendation artistRecommendation = new ArtistRecommendation();
        artistRecommendation.setArtistID(1);
        artistRecommendation.setUserID(1);
        artistRecommendation.setLiked(true);
        artistRecommendation = artistRepository.save(artistRecommendation);

        artistRecommendation.setArtistID(2);
        artistRecommendation.setUserID(3);
        artistRecommendation.setLiked(false);

        artistRepository.save(artistRecommendation);

        Optional<ArtistRecommendation> artistRecommendation1 = artistRepository.findById(artistRecommendation.getArtistRecommendationID());
        assertEquals(artistRecommendation1.get(), artistRecommendation);
    }

    @Test
    public void getAllArtistRecommendations() {

        ArtistRecommendation artistRecommendation = new ArtistRecommendation();
        artistRecommendation.setArtistID(1);
        artistRecommendation.setUserID(1);
        artistRecommendation.setLiked(true);

        artistRecommendation = artistRepository.save(artistRecommendation);

        artistRecommendation = new ArtistRecommendation();
        artistRecommendation.setArtistID(2);
        artistRecommendation.setUserID(2);
        artistRecommendation.setLiked(false);

        artistRecommendation = artistRepository.save(artistRecommendation);

        List<ArtistRecommendation> aList = artistRepository.findAll();

        assertEquals(aList.size(), 2);

    }

}