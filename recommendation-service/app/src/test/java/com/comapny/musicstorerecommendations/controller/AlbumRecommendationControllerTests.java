package com.comapny.musicstorerecommendations.controller;

import com.comapny.musicstorerecommendations.model.AlbumRecommendation;
import com.comapny.musicstorerecommendations.repository.AlbumRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    AlbumRepository albumRepository;
    private AlbumRecommendation unfinishedInputAlbumRecommendation;
    private String unfinishedInputJSON;

    private AlbumRecommendation inputAlbumRecommendation1;
    private String inputJSON1;

    private AlbumRecommendation inputAlbumRecommendation2;
    private String inputJSON2;

    private AlbumRecommendation inputAlbumRecommendation3;
    private String inputJSON3;

    private List<AlbumRecommendation> allAlbumRecommendations =new ArrayList<>();
    private String allAlbumRecommendationsJSON;

    @Before
    public void setUp() throws JsonProcessingException {
        //unfinished AlbumRecommendation
        unfinishedInputAlbumRecommendation = new AlbumRecommendation();
        unfinishedInputAlbumRecommendation.setAlbumID(null);
        unfinishedInputAlbumRecommendation.setUserID(1);
        unfinishedInputAlbumRecommendation.setLiked(true);
        unfinishedInputJSON = mapper.writeValueAsString(unfinishedInputAlbumRecommendation);

        //AlbumRecommendation with no ID
        inputAlbumRecommendation1 = new AlbumRecommendation();
        inputAlbumRecommendation1.setAlbumID(1);
        inputAlbumRecommendation1.setUserID(1);
        inputAlbumRecommendation1.setLiked(true);
        inputJSON1 = mapper.writeValueAsString(inputAlbumRecommendation1);

        //Same AlbumRecommendation With ID
        inputAlbumRecommendation2 = new AlbumRecommendation();
        inputAlbumRecommendation2.setAlbumRecommendationID(1);
        inputAlbumRecommendation2.setAlbumID(1);
        inputAlbumRecommendation2.setUserID(1);
        inputAlbumRecommendation2.setLiked(true);
        inputJSON2 = mapper.writeValueAsString(inputAlbumRecommendation2);

        //Different AlbumRecommendation with ID

        inputAlbumRecommendation3 = new AlbumRecommendation();
        inputAlbumRecommendation3.setAlbumRecommendationID(2);
        inputAlbumRecommendation3.setAlbumID(2);
        inputAlbumRecommendation3.setUserID(2);
        inputAlbumRecommendation3.setLiked(true);
        inputJSON3 = mapper.writeValueAsString(inputAlbumRecommendation3);

        allAlbumRecommendations.add(inputAlbumRecommendation3);
        allAlbumRecommendations.add(inputAlbumRecommendation2);
        allAlbumRecommendationsJSON =mapper.writeValueAsString(allAlbumRecommendations);
    }

    @Test
    public void shouldReturn422StatusCodeWithInvalidRequestBodyPOSTRequest() throws Exception {
        //Act
        mockMvc.perform(
                        post("/albumRecommendations")
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
                        put("/albumRecommendations")
                                .content(unfinishedInputJSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn404StatusCodeIfRecordNotFound() throws Exception {
        doReturn(Optional.empty()).when(albumRepository).findById(-1);
        mockMvc.perform(get("/albumRecommendations/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404StatusCodeIfConsoleNotFoundForDeletion() throws Exception{
        doReturn(Optional.empty()).when(albumRepository).findById(-1);
        mockMvc.perform(delete("/albumRecommendations/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNewAlbumRecommendationOnPOSTRequest() throws Exception {
        doReturn(inputAlbumRecommendation2).when(albumRepository).save(inputAlbumRecommendation1);
        mockMvc.perform(
                        post("/albumRecommendations")
                                .content(inputJSON1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())                     // ASSERT that we got back 201 Created.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnAlbumRecommendationByID() throws Exception {
        Optional<AlbumRecommendation> optionalAlbumRecommendation = Optional.of(inputAlbumRecommendation2);
        doReturn(optionalAlbumRecommendation).when(albumRepository).findById(1);
        mockMvc.perform(get("/albumRecommendations/1"))
                .andDo(print())
                .andExpect(status().isOk())                     // ASSERT that we got back 200 OK.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnAllAlbumRecommendations() throws Exception {
        doReturn(allAlbumRecommendations).when(albumRepository).findAll();

        mockMvc.perform(get("/albumRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())              // ASSERT (status code is 200)
                .andExpect(jsonPath("$[0]").isNotEmpty());// ASSERT that the JSON array is present and not empty

    }
    @Test
    public void shouldReturn204StatusCodeWithPUTRequest() throws Exception {
        doReturn(inputAlbumRecommendation3).when(albumRepository).save(inputAlbumRecommendation3);
        mockMvc.perform(
                        put("/albumRecommendations")
                                .content(inputJSON3)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent()); // ASSERT that we got back 204 NO CONTENT.

    }
    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        Optional<AlbumRecommendation> optionalAlbumRecommendation = Optional.of(inputAlbumRecommendation2);
        doReturn(optionalAlbumRecommendation).when(albumRepository).findById(1);
        mockMvc.perform(delete("/albumRecommendations/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
