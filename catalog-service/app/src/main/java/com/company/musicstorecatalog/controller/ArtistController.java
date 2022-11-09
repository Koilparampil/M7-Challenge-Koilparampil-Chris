package com.company.musicstorecatalog.controller;
//Code referenced from Record-collection Activity from class
import com.company.musicstorecatalog.exceptions.NotFoundException;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    @Autowired
    ArtistRepository artistRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Artist addArtist(@RequestBody @Valid Artist artist) {
        return artistRepository.save(artist);
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Artist getArtistById(@PathVariable int id) {
        Optional<Artist> returnVal = artistRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new NotFoundException("Artist not found in collection");
        }
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getArtists() {
        return artistRepository.findAll();
    }


    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@RequestBody @Valid Artist artist) {
        artistRepository.save(artist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable int id) {
        if(artistRepository.findById(id).isPresent()){
            artistRepository.deleteById(id);
        } else{
            throw new NotFoundException("Artist not found in collection");
        }
    }
}
