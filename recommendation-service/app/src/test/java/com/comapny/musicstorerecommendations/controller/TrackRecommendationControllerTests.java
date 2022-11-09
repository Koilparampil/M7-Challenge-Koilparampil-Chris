package com.comapny.musicstorerecommendations.controller;

import com.comapny.musicstorerecommendations.model.TrackRecommendation;
import com.comapny.musicstorerecommendations.repository.TrackRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    TrackRepository trackRepository;

    private TrackRecommendation unfinishedInputTrackRecommendation;
    private String unfinishedInputJSON;
    private TrackRecommendation inputTrackRecommendation1;
    private String inputJSON1;
    private TrackRecommendation inputTrackRecommendation2;
    private String inputJSON2;
    private TrackRecommendation inputTrackRecommendation3;
    private String inputJSON3;
    private List<TrackRecommendation> allTrackRecommendations = new ArrayList<>();
    private String allTrackRecommendationsJSON;

    @Before
    public void setUp() throws JsonProcessingException {
        //Unfinished TrackRecommendation
        unfinishedInputTrackRecommendation = new TrackRecommendation();
        unfinishedInputTrackRecommendation.setTrackID(null);
        unfinishedInputTrackRecommendation.setUserID(1);
        unfinishedInputTrackRecommendation.setLiked(true);
        unfinishedInputJSON = mapper.writeValueAsString(unfinishedInputTrackRecommendation);

        //TrackRecommendation with no ID
        inputTrackRecommendation1 = new TrackRecommendation();
        inputTrackRecommendation1.setTrackID(1);
        inputTrackRecommendation1.setUserID(1);
        inputTrackRecommendation1.setLiked(true);
        inputJSON1=mapper.writeValueAsString(inputTrackRecommendation1);

        //TrackRecommendation with ID
        inputTrackRecommendation2 = new TrackRecommendation();
        inputTrackRecommendation2.setTrackRecommendationID(1);
        inputTrackRecommendation2.setTrackID(1);
        inputTrackRecommendation2.setUserID(1);
        inputTrackRecommendation2.setLiked(true);
        inputJSON2=mapper.writeValueAsString(inputTrackRecommendation2);

        //Different TrackRecommendation with ID
        inputTrackRecommendation3 = new TrackRecommendation();
        inputTrackRecommendation3.setTrackRecommendationID(2);
        inputTrackRecommendation3.setTrackID(2);
        inputTrackRecommendation3.setUserID(2);
        inputTrackRecommendation3.setLiked(false);
        inputJSON3=mapper.writeValueAsString(inputTrackRecommendation3);

        allTrackRecommendations.add(inputTrackRecommendation2);
        allTrackRecommendations.add(inputTrackRecommendation3);
        allTrackRecommendationsJSON= mapper.writeValueAsString(allTrackRecommendations);
    }
    @Test
    public void shouldReturn422StatusCodeWithInvalidRequestBodyPOSTRequest() throws Exception {
        //Act
        mockMvc.perform(
                        post("/trackRecommendations")
                                .content(unfinishedInputJSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn422StatusCodeWithInvalidRequestBodyPUTRequest() throws Exception {
        //Act
        mockMvc.perform(
                        put("/trackRecommendations")
                                .content(unfinishedInputJSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    public void shouldReturn404StatusCodeIfRecordNotFound() throws Exception {
        doReturn(Optional.empty()).when(trackRepository).findById(-1);
        mockMvc.perform(get("/trackRecommendations/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404StatusCodeIfConsoleNotFoundForDeletion() throws Exception{
        doReturn(Optional.empty()).when(trackRepository).findById(-1);
        mockMvc.perform(delete("/trackRecommendations/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNewTrackOnPOSTRequest() throws Exception {
        doReturn(inputTrackRecommendation2).when(trackRepository).save(inputTrackRecommendation1);
        mockMvc.perform(
                        post("/trackRecommendations")
                                .content(inputJSON1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())                     // ASSERT that we got back 201 Created.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnTrackByID() throws Exception {
        Optional<TrackRecommendation> optionalTrackRecommendation = Optional.of(inputTrackRecommendation2);
        doReturn(optionalTrackRecommendation).when(trackRepository).findById(1);
        mockMvc.perform(get("/trackRecommendations/1"))
                .andDo(print())
                .andExpect(status().isOk())                     // ASSERT that we got back 200 OK.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnAllTracks() throws Exception {
        doReturn(allTrackRecommendations).when(trackRepository).findAll();

        mockMvc.perform(get("/trackRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())              // ASSERT (status code is 200)
                .andExpect(jsonPath("$[0]").isNotEmpty());// ASSERT that the JSON array is present and not empty

    }
    @Test
    public void shouldReturn204StatusCodeWithPUTRequest() throws Exception {
        doReturn(inputTrackRecommendation3).when(trackRepository).save(inputTrackRecommendation3);
        mockMvc.perform(
                        put("/trackRecommendations")
                                .content(inputJSON3)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent()); // ASSERT that we got back 204 NO CONTENT.

    }
    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        Optional<TrackRecommendation> optionalTrackRecommendation = Optional.of(inputTrackRecommendation2);
        doReturn(optionalTrackRecommendation).when(trackRepository).findById(1);
        mockMvc.perform(delete("/trackRecommendations/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
