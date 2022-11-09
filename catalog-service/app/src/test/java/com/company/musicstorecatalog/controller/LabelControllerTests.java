package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LabelController.class)
public class LabelControllerTests {
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    LabelRepository labelRepository;

    private Label unfinishedInputLabel;
    private String unfinishedInputJSON;
    private Label inputLabel1;
    private String inputJSON1;
    private Label inputLabel2;
    private String inputJSON2;
    private Label inputLabel3;
    private String inputJSON3;
    private List<Label> allLabels = new ArrayList<>();
    private String allLabelsJSON;

    @Before
    public void setUp() throws JsonProcessingException {
        //unfinished Label
        unfinishedInputLabel =new Label();
        //unfinishedInputLabel.setName("SuperName"); <-- Leave out One Field
        unfinishedInputLabel.setWebsite("www.instaHandle.com");
        unfinishedInputJSON = mapper.writeValueAsString(unfinishedInputLabel);

        //Label With no ID
        inputLabel1 = new Label();
        inputLabel1.setName("SuperName");
        inputLabel1.setWebsite("www.instaHandle.com");
        inputJSON1= mapper.writeValueAsString(inputLabel1);

        //Label with ID

        inputLabel2 = new Label();
        inputLabel2.setId(1);
        inputLabel2.setName("SuperName");
        inputLabel2.setWebsite("www.instaHandle.com");
        inputJSON2= mapper.writeValueAsString(inputLabel2);

        //Different Label with ID
        inputLabel3 = new Label();
        inputLabel3.setId(3);
        inputLabel3.setName("Sup3rNam3");
        inputLabel3.setWebsite("www.instaHandle3.com");
        inputJSON3= mapper.writeValueAsString(inputLabel3);

        allLabels.add(inputLabel2);
        allLabels.add(inputLabel3);
        allLabelsJSON= mapper.writeValueAsString(allLabels);
    }
    @Test
    public void shouldReturn422StatusCodeWithInvalidRequestBodyPOSTRequest() throws Exception {
        //Act
        mockMvc.perform(
                        post("/labels")
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
                        put("/labels")
                                .content(unfinishedInputJSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    public void shouldReturn404StatusCodeIfRecordNotFound() throws Exception {
        doReturn(Optional.empty()).when(labelRepository).findById(-1);
        mockMvc.perform(get("/labels/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404StatusCodeIfConsoleNotFoundForDeletion() throws Exception{
        doReturn(Optional.empty()).when(labelRepository).findById(-1);
        mockMvc.perform(delete("/labels/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void shouldReturnNewArtistOnPOSTRequest() throws Exception {
        doReturn(inputLabel2).when(labelRepository).save(inputLabel1);
        mockMvc.perform(
                        post("/labels")
                                .content(inputJSON1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())                     // ASSERT that we got back 201 Created.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnArtistByID() throws Exception {
        Optional<Label> optionalLabel = Optional.of(inputLabel2);
        doReturn(optionalLabel).when(labelRepository).findById(1);
        mockMvc.perform(get("/labels/1"))
                .andDo(print())
                .andExpect(status().isOk())                     // ASSERT that we got back 200 OK.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnAllArtists() throws Exception {
        doReturn(allLabels).when(labelRepository).findAll();

        mockMvc.perform(get("/labels"))
                .andDo(print())
                .andExpect(status().isOk())              // ASSERT (status code is 200)
                .andExpect(jsonPath("$[0]").isNotEmpty());// ASSERT that the JSON array is present and not empty

    }
    @Test
    public void shouldReturn204StatusCodeWithPUTRequest() throws Exception {
        doReturn(inputLabel3).when(labelRepository).save(inputLabel3);
        mockMvc.perform(
                        put("/labels")
                                .content(inputJSON3)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent()); // ASSERT that we got back 204 NO CONTENT.

    }
    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        Optional<Label> optionalLabel = Optional.of(inputLabel2);
        doReturn(optionalLabel).when(labelRepository).findById(1);
        mockMvc.perform(delete("/labels/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
