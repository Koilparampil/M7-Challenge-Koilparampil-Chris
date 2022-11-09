package com.comapny.musicstorerecommendations.controller;
//Code referenced from Record-collection Activity from class
import com.comapny.musicstorerecommendations.exceptions.NotFoundException;
import com.comapny.musicstorerecommendations.model.ArtistRecommendation;
import com.comapny.musicstorerecommendations.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artistRecommendations")
public class ArtistRecommendationController {
    @Autowired
    ArtistRepository artistRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation addArtistRecommendation(@RequestBody @Valid ArtistRecommendation artistRecommendation) {
        return artistRepository.save(artistRecommendation);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation getArtistRecommendationById(@PathVariable int id) {
        Optional<ArtistRecommendation> returnVal = artistRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new NotFoundException("ArtistRecommendation not found in collection");
        }
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> getArtistRecommendations() {
        return artistRepository.findAll();
    }


    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendation(@RequestBody @Valid ArtistRecommendation artistRecommendation) {
        artistRepository.save(artistRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendation(@PathVariable int id) {
        if(artistRepository.findById(id).isPresent()){
            artistRepository.deleteById(id);
        } else{
            throw new NotFoundException("ArtistRecommendation not found in collection");
        }
    }
}
