package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
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
@WebMvcTest(TrackController.class)
public class TrackControllerTests {

    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    TrackRepository trackRepository;

    private Track unfinishedInputTrack;
    private String unfinishedInputJSON;
    private Track inputTrack1;
    private String inputJSON1;
    private Track inputTrack2;
    private String inputJSON2;
    private Track inputTrack3;
    private String inputJSON3;
    private List<Track> allTracks = new ArrayList<>();
    private String allTracksJSON;

    @Before
    public void setUp() throws JsonProcessingException {
        //Unfinished Track
        unfinishedInputTrack = new Track();
        unfinishedInputTrack.setAlbumId(1);
        //unfinishedInputTrack.setTitle("Title 1");
        unfinishedInputTrack.setRuntime(170);
        unfinishedInputJSON = mapper.writeValueAsString(unfinishedInputTrack);

        //Track with no ID
        inputTrack1 = new Track();
        inputTrack1.setAlbumId(1);
        inputTrack1.setTitle("Title 1");
        inputTrack1.setRuntime(170);
        inputJSON1=mapper.writeValueAsString(inputTrack1);

        //Track with ID
        inputTrack2 = new Track();
        inputTrack2.setId(1);
        inputTrack2.setAlbumId(1);
        inputTrack2.setTitle("Title 1");
        inputTrack2.setRuntime(170);
        inputJSON2=mapper.writeValueAsString(inputTrack2);

        //Different Track with ID
        inputTrack3 = new Track();
        inputTrack3.setId(3);
        inputTrack3.setAlbumId(1);
        inputTrack3.setTitle("Title 1");
        inputTrack3.setRuntime(170);
        inputJSON3=mapper.writeValueAsString(inputTrack3);

        allTracks.add(inputTrack2);
        allTracks.add(inputTrack3);
        allTracksJSON= mapper.writeValueAsString(allTracks);
    }
    @Test
    public void shouldReturn422StatusCodeWithInvalidRequestBodyPOSTRequest() throws Exception {
        //Act
        mockMvc.perform(
                        post("/tracks")
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
                        put("/tracks")
                                .content(unfinishedInputJSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    public void shouldReturn404StatusCodeIfRecordNotFound() throws Exception {
        doReturn(Optional.empty()).when(trackRepository).findById(-1);
        mockMvc.perform(get("/tracks/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404StatusCodeIfConsoleNotFoundForDeletion() throws Exception{
        doReturn(Optional.empty()).when(trackRepository).findById(-1);
        mockMvc.perform(delete("/tracks/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNewArtistOnPOSTRequest() throws Exception {
        doReturn(inputTrack2).when(trackRepository).save(inputTrack1);
        mockMvc.perform(
                        post("/tracks")
                                .content(inputJSON1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())                     // ASSERT that we got back 201 Created.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnArtistByID() throws Exception {
        Optional<Track> optionalTrack = Optional.of(inputTrack2);
        doReturn(optionalTrack).when(trackRepository).findById(1);
        mockMvc.perform(get("/tracks/1"))
                .andDo(print())
                .andExpect(status().isOk())                     // ASSERT that we got back 200 OK.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnAllArtists() throws Exception {
        doReturn(allTracks).when(trackRepository).findAll();

        mockMvc.perform(get("/tracks"))
                .andDo(print())
                .andExpect(status().isOk())              // ASSERT (status code is 200)
                .andExpect(jsonPath("$[0]").isNotEmpty());// ASSERT that the JSON array is present and not empty

    }
    @Test
    public void shouldReturn204StatusCodeWithPUTRequest() throws Exception {
        doReturn(inputTrack3).when(trackRepository).save(inputTrack3);
        mockMvc.perform(
                        put("/tracks")
                                .content(inputJSON3)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent()); // ASSERT that we got back 204 NO CONTENT.

    }
    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        Optional<Track> optionalTrack = Optional.of(inputTrack2);
        doReturn(optionalTrack).when(trackRepository).findById(1);
        mockMvc.perform(delete("/tracks/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
