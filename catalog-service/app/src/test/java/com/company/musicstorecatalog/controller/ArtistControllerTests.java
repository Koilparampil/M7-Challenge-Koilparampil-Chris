package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
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
@WebMvcTest(ArtistController.class)
public class ArtistControllerTests {
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    ArtistRepository artistRepository;

    private Artist unfinishedInputArtist;
    private String unfinishedInputJSON;
    private Artist inputArtist1;
    private String inputJSON1;
    private Artist inputArtist2;
    private String inputJSON2;
    private Artist inputArtist3;
    private String inputJSON3;
    private List<Artist> allArtists = new ArrayList<>();
    private String allArtistsJSON;

    @Before
    public void setUp() throws JsonProcessingException {
        //unfinished Artist
        unfinishedInputArtist =new Artist();
        //unfinishedInputArtist.setName("SuperName"); <-- Leave out One Field
        unfinishedInputArtist.setInstagram("@instaHandle");
        unfinishedInputArtist.setTwitter("@tweetHandle");
        unfinishedInputJSON = mapper.writeValueAsString(unfinishedInputArtist);

        //Artist With no ID
        inputArtist1 = new Artist();
        inputArtist1.setName("SuperName");
        inputArtist1.setInstagram("@instaHandle");
        inputArtist1.setTwitter("@tweetHandle");
        inputJSON1= mapper.writeValueAsString(inputArtist1);

        //Artist with ID

        inputArtist2 = new Artist();
        inputArtist2.setId(1);
        inputArtist2.setName("SuperName");
        inputArtist2.setInstagram("@instaHandle");
        inputArtist2.setTwitter("@tweetHandle");
        inputJSON2= mapper.writeValueAsString(inputArtist2);

        //Different Artist with ID
        inputArtist3 = new Artist();
        inputArtist3.setId(3);
        inputArtist3.setName("Sup3rNam3");
        inputArtist3.setInstagram("@instaHandle3");
        inputArtist3.setTwitter("@tweetHandle3");
        inputJSON3= mapper.writeValueAsString(inputArtist3);

        allArtists.add(inputArtist2);
        allArtists.add(inputArtist3);
        allArtistsJSON= mapper.writeValueAsString(allArtists);
    }

    @Test
    public void shouldReturn422StatusCodeWithInvalidRequestBodyPOSTRequest() throws Exception {
        //Act
        mockMvc.perform(
                        post("/artists")
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
                        put("/artists")
                                .content(unfinishedInputJSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    public void shouldReturn404StatusCodeIfRecordNotFound() throws Exception {
        doReturn(Optional.empty()).when(artistRepository).findById(-1);
        mockMvc.perform(get("/albums/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404StatusCodeIfConsoleNotFoundForDeletion() throws Exception{
        doReturn(Optional.empty()).when(artistRepository).findById(-1);
        mockMvc.perform(delete("/albums/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void shouldReturnNewArtistOnPOSTRequest() throws Exception {
        doReturn(inputArtist2).when(artistRepository).save(inputArtist1);
        mockMvc.perform(
                        post("/artists")
                                .content(inputJSON1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())                     // ASSERT that we got back 201 Created.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnArtistByID() throws Exception {
        Optional<Artist> optionalArtist = Optional.of(inputArtist2);
        doReturn(optionalArtist).when(artistRepository).findById(1);
        mockMvc.perform(get("/artists/1"))
                .andDo(print())
                .andExpect(status().isOk())                     // ASSERT that we got back 200 OK.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnAllArtists() throws Exception {
        doReturn(allArtists).when(artistRepository).findAll();

        mockMvc.perform(get("/artists"))
                .andDo(print())
                .andExpect(status().isOk())              // ASSERT (status code is 200)
                .andExpect(jsonPath("$[0]").isNotEmpty());// ASSERT that the JSON array is present and not empty

    }
    @Test
    public void shouldReturn204StatusCodeWithPUTRequest() throws Exception {
        doReturn(inputArtist3).when(artistRepository).save(inputArtist3);
        mockMvc.perform(
                        put("/artists")
                                .content(inputJSON3)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent()); // ASSERT that we got back 204 NO CONTENT.

    }
    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        Optional<Artist> optionalArtist = Optional.of(inputArtist2);
        doReturn(optionalArtist).when(artistRepository).findById(1);
        mockMvc.perform(delete("/artists/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
