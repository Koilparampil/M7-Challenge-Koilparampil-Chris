package com.comapny.musicstorerecommendations.repository;
//Code referenced from Record-collection Activity from class

import com.comapny.musicstorerecommendations.model.LabelRecommendation;
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
public class LabelRecommendationRepositoryTests {
    
    @Autowired
    LabelRepository labelRepository;

    @Before
    public void setUp() throws Exception {
        labelRepository.deleteAll();
    }

    @Test
    public void addGetDeleteLabelRecommendation() {

        LabelRecommendation labelRecommendation = new LabelRecommendation();
        labelRecommendation.setLabelID(1);
        labelRecommendation.setUserID(1);
        labelRecommendation.setLiked(true);

        labelRecommendation = labelRepository.save(labelRecommendation);

        Optional<LabelRecommendation> labelRecommendation1 = labelRepository.findById(labelRecommendation.getLabelRecommendationID());

        assertEquals(labelRecommendation1.get(), labelRecommendation);

        labelRepository.deleteById(labelRecommendation.getLabelRecommendationID());

        labelRecommendation1 = labelRepository.findById(labelRecommendation.getLabelRecommendationID());

        assertFalse(labelRecommendation1.isPresent());

    }

    @Test
    public void getAllLabelRecommendations() {

        LabelRecommendation labelRecommendation = new LabelRecommendation();
        labelRecommendation.setLabelID(1);
        labelRecommendation.setUserID(1);
        labelRecommendation.setLiked(true);

        labelRecommendation = labelRepository.save(labelRecommendation);

        labelRecommendation = new LabelRecommendation();
        labelRecommendation.setLabelID(2);
        labelRecommendation.setUserID(2);
        labelRecommendation.setLiked(false);

        labelRecommendation = labelRepository.save(labelRecommendation);

        List<LabelRecommendation> aList = labelRepository.findAll();

        assertEquals(aList.size(), 2);

    }

    @Test
    public void updateLabelRecommendation() {

        LabelRecommendation labelRecommendation = new LabelRecommendation();
        labelRecommendation.setLabelID(1);
        labelRecommendation.setUserID(1);
        labelRecommendation.setLiked(true);
        labelRecommendation = labelRepository.save(labelRecommendation);

        labelRecommendation.setLabelID(2);
        labelRecommendation.setUserID(3);
        labelRecommendation.setLiked(false);

        labelRepository.save(labelRecommendation);

        Optional<LabelRecommendation> labelRecommendation1 = labelRepository.findById(labelRecommendation.getLabelRecommendationID());
        assertEquals(labelRecommendation1.get(), labelRecommendation);
    }
}