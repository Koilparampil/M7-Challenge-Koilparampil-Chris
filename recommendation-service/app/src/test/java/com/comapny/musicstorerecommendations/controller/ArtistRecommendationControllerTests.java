package com.comapny.musicstorerecommendations.controller;

import com.comapny.musicstorerecommendations.model.ArtistRecommendation;
import com.comapny.musicstorerecommendations.repository.ArtistRepository;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    ArtistRepository artistRepository;

    private ArtistRecommendation unfinishedInputArtistRecommendation;
    private String unfinishedInputJSON;
    private ArtistRecommendation inputArtistRecommendation1;
    private String inputJSON1;
    private ArtistRecommendation inputArtistRecommendation2;
    private String inputJSON2;
    private ArtistRecommendation inputArtistRecommendation3;
    private String inputJSON3;
    private List<ArtistRecommendation> allArtistRecommendations = new ArrayList<>();
    private String allArtistRecommendationsJSON;

    @Before
    public void setUp() throws JsonProcessingException {
        //unfinished ArtistRecommendation
        unfinishedInputArtistRecommendation =new ArtistRecommendation();
        unfinishedInputArtistRecommendation.setArtistID(null);
        unfinishedInputArtistRecommendation.setUserID(1);
        unfinishedInputArtistRecommendation.setLiked(true);
        unfinishedInputJSON = mapper.writeValueAsString(unfinishedInputArtistRecommendation);

        //ArtistRecommendation With no ID
        inputArtistRecommendation1 = new ArtistRecommendation();
        inputArtistRecommendation1.setArtistID(1);
        inputArtistRecommendation1.setUserID(1);
        inputArtistRecommendation1.setLiked(true);
        inputJSON1= mapper.writeValueAsString(inputArtistRecommendation1);

        //ArtistRecommendation with ID

        inputArtistRecommendation2 = new ArtistRecommendation();
        inputArtistRecommendation2.setArtistRecommendationID(1);
        inputArtistRecommendation2.setArtistID(1);
        inputArtistRecommendation2.setUserID(1);
        inputArtistRecommendation2.setLiked(true);
        inputJSON2= mapper.writeValueAsString(inputArtistRecommendation2);

        //Different ArtistRecommendation with ID
        inputArtistRecommendation3 = new ArtistRecommendation();
        inputArtistRecommendation3.setArtistRecommendationID(2);
        inputArtistRecommendation3.setArtistID(2);
        inputArtistRecommendation3.setUserID(2);
        inputArtistRecommendation3.setLiked(false);
        inputJSON3= mapper.writeValueAsString(inputArtistRecommendation3);

        allArtistRecommendations.add(inputArtistRecommendation2);
        allArtistRecommendations.add(inputArtistRecommendation3);
        allArtistRecommendationsJSON= mapper.writeValueAsString(allArtistRecommendations);
    }

    @Test
    public void shouldReturn422StatusCodeWithInvalidRequestBodyPOSTRequest() throws Exception {
        //Act
        mockMvc.perform(
                        post("/artistRecommendations")
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
                        put("/artistRecommendations")
                                .content(unfinishedInputJSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    public void shouldReturn404StatusCodeIfRecordNotFound() throws Exception {
        doReturn(Optional.empty()).when(artistRepository).findById(-1);
        mockMvc.perform(get("/artistRecommendations/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404StatusCodeIfConsoleNotFoundForDeletion() throws Exception{
        doReturn(Optional.empty()).when(artistRepository).findById(-1);
        mockMvc.perform(delete("/artistRecommendations/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void shouldReturnNewArtistRecommendationOnPOSTRequest() throws Exception {
        doReturn(inputArtistRecommendation2).when(artistRepository).save(inputArtistRecommendation1);
        mockMvc.perform(
                        post("/artistRecommendations")
                                .content(inputJSON1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())                     // ASSERT that we got back 201 Created.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnArtistRecommendationByID() throws Exception {
        Optional<ArtistRecommendation> optionalArtistRecommendation = Optional.of(inputArtistRecommendation2);
        doReturn(optionalArtistRecommendation).when(artistRepository).findById(1);
        mockMvc.perform(get("/artistRecommendations/1"))
                .andDo(print())
                .andExpect(status().isOk())                     // ASSERT that we got back 200 OK.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnAllArtistRecommendations() throws Exception {
        doReturn(allArtistRecommendations).when(artistRepository).findAll();

        mockMvc.perform(get("/artistRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())              // ASSERT (status code is 200)
                .andExpect(jsonPath("$[0]").isNotEmpty());// ASSERT that the JSON array is present and not empty

    }
    @Test
    public void shouldReturn204StatusCodeWithPUTRequest() throws Exception {
        doReturn(inputArtistRecommendation3).when(artistRepository).save(inputArtistRecommendation3);
        mockMvc.perform(
                        put("/artistRecommendations")
                                .content(inputJSON3)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent()); // ASSERT that we got back 204 NO CONTENT.

    }
    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        Optional<ArtistRecommendation> optionalArtistRecommendation = Optional.of(inputArtistRecommendation2);
        doReturn(optionalArtistRecommendation).when(artistRepository).findById(1);
        mockMvc.perform(delete("/artistRecommendations/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
