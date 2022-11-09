package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
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
@WebMvcTest(AlbumController.class)
public class AlbumControllerTests {
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    AlbumRepository albumRepository;
    private Album unfinishedInputAlbum;
    private String unfinishedInputJSON;

    private Album inputAlbum1;
    private String inputJSON1;

    private Album inputAlbum2;
    private String inputJSON2;

    private Album inputAlbum3;
    private String inputJSON3;

    private List<Album> allAlbums =new ArrayList<>();
    private String allAlbumsJSON;

    @Before
    public void setUp() throws JsonProcessingException {
        //unfinished Album
        unfinishedInputAlbum = new Album();
        //inputAlbum.setTitle("ThisIsATitle"); <-- leave out one field.
        unfinishedInputAlbum.setArtistId(1);
        unfinishedInputAlbum.setReleaseDate(LocalDate.of(2012,1,5));
        unfinishedInputAlbum.setLabelId(1);
        unfinishedInputAlbum.setListPrice(new BigDecimal("21.98")) ;
        unfinishedInputJSON = mapper.writeValueAsString(unfinishedInputAlbum);

        //Album with no ID
        inputAlbum1 = new Album();
        inputAlbum1.setTitle("ThisIsATitle");
        inputAlbum1.setArtistId(1);
        inputAlbum1.setReleaseDate(LocalDate.of(2012,1,5));
        inputAlbum1.setLabelId(1);
        inputAlbum1.setListPrice(new BigDecimal("21.98")) ;
        inputJSON1 = mapper.writeValueAsString(inputAlbum1);

        //Same Album With ID
        inputAlbum2 = new Album();
        inputAlbum2.setId(1);
        inputAlbum2.setTitle("ThisIsATitle");
        inputAlbum2.setArtistId(1);
        inputAlbum2.setReleaseDate(LocalDate.of(2012,1,5));
        inputAlbum2.setLabelId(1);
        inputAlbum2.setListPrice(new BigDecimal("21.98")) ;
        inputJSON2 = mapper.writeValueAsString(inputAlbum2);

        //Different Album with ID

        inputAlbum3 = new Album();
        inputAlbum3.setId(2);
        inputAlbum3.setTitle("ThisIsAnotherTitle");
        inputAlbum3.setArtistId(2);
        inputAlbum3.setReleaseDate(LocalDate.of(2017,10,15));
        inputAlbum3.setLabelId(2);
        inputAlbum3.setListPrice(new BigDecimal("15.98")) ;
        inputJSON3 = mapper.writeValueAsString(inputAlbum3);

        allAlbums.add(inputAlbum3);
        allAlbums.add(inputAlbum2);
        allAlbumsJSON =mapper.writeValueAsString(allAlbums);
    }

    @Test
    public void shouldReturn422StatusCodeWithInvalidRequestBodyPOSTRequest() throws Exception {
        //Act
        mockMvc.perform(
                        post("/albums")
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
                        put("/albums")
                                .content(unfinishedInputJSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn404StatusCodeIfRecordNotFound() throws Exception {
        doReturn(Optional.empty()).when(albumRepository).findById(-1);
        mockMvc.perform(get("/albums/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404StatusCodeIfConsoleNotFoundForDeletion() throws Exception{
        doReturn(Optional.empty()).when(albumRepository).findById(-1);
        mockMvc.perform(delete("/albums/-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNewAlbumOnPOSTRequest() throws Exception {
        doReturn(inputAlbum2).when(albumRepository).save(inputAlbum1);
        mockMvc.perform(
                        post("/albums")
                                .content(inputJSON1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())                     // ASSERT that we got back 201 Created.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnAlbumByID() throws Exception {
        Optional<Album> optionalAlbum = Optional.of(inputAlbum2);
        doReturn(optionalAlbum).when(albumRepository).findById(1);
        mockMvc.perform(get("/albums/1"))
                .andDo(print())
                .andExpect(status().isOk())                     // ASSERT that we got back 200 OK.
                .andExpect(content().json(inputJSON2));         // ASSERT that what we're expecting is what we got back.
    }
    @Test
    public void shouldReturnAllAlbums() throws Exception {
        doReturn(allAlbums).when(albumRepository).findAll();

        mockMvc.perform(get("/albums"))
                .andDo(print())
                .andExpect(status().isOk())              // ASSERT (status code is 200)
                .andExpect(jsonPath("$[0]").isNotEmpty());// ASSERT that the JSON array is present and not empty

    }
    @Test
    public void shouldReturn204StatusCodeWithPUTRequest() throws Exception {
        doReturn(inputAlbum3).when(albumRepository).save(inputAlbum3);
        mockMvc.perform(
                        put("/albums")
                                .content(inputJSON3)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent()); // ASSERT that we got back 204 NO CONTENT.

    }
    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        Optional<Album> optionalAlbum = Optional.of(inputAlbum2);
        doReturn(optionalAlbum).when(albumRepository).findById(1);
        mockMvc.perform(delete("/albums/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
