package com.comapny.musicstorerecommendations.controller;

import com.comapny.musicstorerecommendations.model.LabelRecommendation;
import com.comapny.musicstorerecommendations.repository.LabelRepository;
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
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    LabelRepository labelRepository;

    private LabelRecommendation unfinishedInputLabelRecommendation;
    private String unfinishedInputJSON;
    private LabelRecommendation inputLabelRecommendation1;
    private String inputJSON1;
    private LabelRecommendation inputLabelRecommendation2;
    private String inputJSON2;
    private LabelRecommendation inputLabelRecommendation3;
    private String inputJSON3;
    private List<LabelRecommendation> allLabelRecommendations = new ArrayList<>();
    private String allLabelRecommendationsJSON;

    @Before
    public void setUp() throws JsonProcessingException {
        //unfinished LabelRecommendation
        unfinishedInputLabelRecommendation =new LabelRecommendation();
        unfinishedInputLabelRecommendation.setLabelID(null); //<-- making this null to simulate emptiness
        unfinishedInputLabelRecommendation.setUserID(1);
        unfinishedInputLabelRecommendation.setLiked(true);
        unfinishedInputJSON = mapper.writeValueAsString(unfinishedInputLabelRecommendation);

        //LabelRecommendation With no ID
        inputLabelRecommendation1 = new LabelRecommendation();
        inputLabelRecommendation1.setLabelID(1);
        inputLabelRecommendation1.setUserID(1);
        inputLabelRecommendation1.setLiked(true);
        inputJSON1= mapper.writeValueAsString(inputLabelRecommendation1);

        //LabelRecommendation with ID

        inputLabelRecommendation2 = new LabelRecommendation();
        inputLabelRecommendation2.setLabelRecommendationID(1);
        inputLabelRecommendation2.setLabelID(1);
        inputLabelRecommendation2.setUserID(1);
        inputLabelRecommendation2.setLiked(true);
        inputJSON2= mapper.writeValueAsString(inputLabelRecommendation2);

        //Different LabelRecommendation with ID
        inputLabelRecommendation3 = new LabelRecommendation();
        inputLabelRecommendation3.setLabelRecommendationID(2);
        inputLabelRecommendation3.setLabelID(2);
        inputLabelRecommendation3.setUserID(2);
        inputLabelRecommendation3.setLiked(false);
        inputJSON3= mapper.writeValueAsString(inputLabelRecommendation3);

        allLabelRecommendations.add(inputLabelRecommendation2);
        allLabelRecommendations.add(inputLabelRecommendation3);
        allLabelRecommendationsJSON= mapper.writeValueAsString(allLabelRecommendations);
    }
    @Test
    public void shouldReturn422StatusCodeWithInvalidRequestBodyPOSTRequest() throws Exception {
        //Act
        mockMvc.perform(
                        post("/labelRecommendations")
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
                        put("/labelRecommendations")
                                .content(unfinishedInputJSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    public void shouldReturn404StatusCodeIfRecordNotFound() throws Exception {
        doReturn(Optional.empty()).when(labelRepository).findById(-1);
        mockMvc.perform(get("/labelRecommendations/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404StatusCodeIfConsoleNotFoundForDeletion() throws Exception{
        doReturn(Optional.empty()).when(labelRepository).findById(-1);
        mockMvc.perform(delete("/labelRecommendations/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void shouldReturnNewLabelRecommendationOnPOSTRequest() throws Exception {
        doReturn(inputLabelRecommendation2).when(labelRepository).save(inputLabelRecommendation1);
        mockMvc.perform(
                        post("/labelRecommendations")
                                .content(inputJSON1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())                     // ASSERT that we got back 201 Created.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnLabelRecommendationByID() throws Exception {
        Optional<LabelRecommendation> optionalLabelRecommendation = Optional.of(inputLabelRecommendation2);
        doReturn(optionalLabelRecommendation).when(labelRepository).findById(1);
        mockMvc.perform(get("/labelRecommendations/1"))
                .andDo(print())
                .andExpect(status().isOk())                     // ASSERT that we got back 200 OK.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnAllLabelRecommendations() throws Exception {
        doReturn(allLabelRecommendations).when(labelRepository).findAll();

        mockMvc.perform(get("/labelRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())              // ASSERT (status code is 200)
                .andExpect(jsonPath("$[0]").isNotEmpty());// ASSERT that the JSON array is present and not empty

    }
    @Test
    public void shouldReturn204StatusCodeWithPUTRequest() throws Exception {
        doReturn(inputLabelRecommendation3).when(labelRepository).save(inputLabelRecommendation3);
        mockMvc.perform(
                        put("/labelRecommendations")
                                .content(inputJSON3)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent()); // ASSERT that we got back 204 NO CONTENT.

    }
    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        Optional<LabelRecommendation> optionalLabelRecommendation = Optional.of(inputLabelRecommendation2);
        doReturn(optionalLabelRecommendation).when(labelRepository).findById(1);
        mockMvc.perform(delete("/labelRecommendations/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
