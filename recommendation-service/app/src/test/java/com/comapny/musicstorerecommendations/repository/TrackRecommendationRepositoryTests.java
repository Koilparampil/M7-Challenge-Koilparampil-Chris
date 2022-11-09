package com.comapny.musicstorerecommendations.repository;
//Code referenced from Record-collection Activity from class

import com.comapny.musicstorerecommendations.model.TrackRecommendation;

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
public class TrackRecommendationRepositoryTests {

    @Autowired
    TrackRepository trackRepository;
    


    @Before
    public void setUp() throws Exception {
        trackRepository.deleteAll();
    }

    @Test
    public void addGetDeleteTrackRecommendation() {


        TrackRecommendation trackRecommendation = new TrackRecommendation();
        trackRecommendation.setTrackID(1);
        trackRecommendation.setUserID(1);
        trackRecommendation.setLiked(true);

        trackRecommendation = trackRepository.save(trackRecommendation);

        Optional<TrackRecommendation> trackRecommendation1 = trackRepository.findById(trackRecommendation.getTrackRecommendationID());

        assertEquals(trackRecommendation1.get(), trackRecommendation);

        trackRepository.deleteById(trackRecommendation.getTrackRecommendationID());

        trackRecommendation1 = trackRepository.findById(trackRecommendation.getTrackRecommendationID());

        assertFalse(trackRecommendation1.isPresent());
    }

    @Test
    public void updateTrackRecommendation() {


        TrackRecommendation trackRecommendation = new TrackRecommendation();
        trackRecommendation.setTrackID(1);
        trackRecommendation.setUserID(1);
        trackRecommendation.setLiked(true);
        trackRecommendation = trackRepository.save(trackRecommendation);

        trackRecommendation.setTrackID(2);
        trackRecommendation.setUserID(3);
        trackRecommendation.setLiked(false);

        trackRepository.save(trackRecommendation);

        Optional<TrackRecommendation> trackRecommendation1 = trackRepository.findById(trackRecommendation.getTrackRecommendationID());
        assertEquals(trackRecommendation1.get(), trackRecommendation);

    }

    @Test
    public void getAllTrackRecommendations() {

        TrackRecommendation trackRecommendation = new TrackRecommendation();
        trackRecommendation.setTrackID(1);
        trackRecommendation.setUserID(1);
        trackRecommendation.setLiked(true);

        trackRecommendation = trackRepository.save(trackRecommendation);

        trackRecommendation = new TrackRecommendation();
        trackRecommendation.setTrackID(2);
        trackRecommendation.setUserID(2);
        trackRecommendation.setLiked(false);

        trackRecommendation = trackRepository.save(trackRecommendation);

        List<TrackRecommendation> aList = trackRepository.findAll();

        assertEquals(aList.size(), 2);

    }


}