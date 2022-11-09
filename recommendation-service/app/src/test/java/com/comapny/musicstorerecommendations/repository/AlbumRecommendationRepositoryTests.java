package com.comapny.musicstorerecommendations.repository;
//Code referenced from Record-collection Activity from class

import com.comapny.musicstorerecommendations.model.AlbumRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlbumRecommendationRepositoryTests {


    @Autowired
    AlbumRepository albumRepository;


    @Before
    public void setUp() throws Exception {
        albumRepository.deleteAll();
    }

    @Test
    public void addGetDeleteAlbumRecommendation() {

        AlbumRecommendation albumRecommendation = new AlbumRecommendation();
        albumRecommendation.setAlbumID(1);
        albumRecommendation.setUserID(1);
        albumRecommendation.setLiked(true);

        albumRecommendation = albumRepository.save(albumRecommendation);

        Optional<AlbumRecommendation> albumRecommendation1 = albumRepository.findById(albumRecommendation.getAlbumRecommendationID());

        assertEquals(albumRecommendation1.get(), albumRecommendation);

        albumRepository.deleteById(albumRecommendation.getAlbumRecommendationID());

        albumRecommendation1 = albumRepository.findById(albumRecommendation.getAlbumRecommendationID());

        assertFalse(albumRecommendation1.isPresent());

    }

    @Test
    public void getAllAlbumRecommendations() {


        AlbumRecommendation albumRecommendation = new AlbumRecommendation();
        albumRecommendation.setAlbumID(1);
        albumRecommendation.setUserID(1);
        albumRecommendation.setLiked(true);

        albumRecommendation = albumRepository.save(albumRecommendation);

        albumRecommendation = new AlbumRecommendation();
        albumRecommendation.setAlbumID(2);
        albumRecommendation.setUserID(2);
        albumRecommendation.setLiked(false);

        albumRecommendation = albumRepository.save(albumRecommendation);

        List<AlbumRecommendation> aList = albumRepository.findAll();

        assertEquals(aList.size(), 2);

    }

    @Test
    public void updateAlbumRecommendation() {

        AlbumRecommendation albumRecommendation = new AlbumRecommendation();
        albumRecommendation.setAlbumID(1);
        albumRecommendation.setUserID(1);
        albumRecommendation.setLiked(true);
        albumRecommendation = albumRepository.save(albumRecommendation);

        albumRecommendation.setAlbumID(2);
        albumRecommendation.setUserID(3);
        albumRecommendation.setLiked(false);

        albumRepository.save(albumRecommendation);

        Optional<AlbumRecommendation> albumRecommendation1 = albumRepository.findById(albumRecommendation.getAlbumRecommendationID());
        assertEquals(albumRecommendation1.get(), albumRecommendation);

    }

}