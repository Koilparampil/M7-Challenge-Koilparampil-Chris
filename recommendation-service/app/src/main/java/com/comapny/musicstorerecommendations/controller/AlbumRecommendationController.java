package com.comapny.musicstorerecommendations.controller;
//Code referenced from Record-collection Activity from class
import com.comapny.musicstorerecommendations.exceptions.NotFoundException;
import com.comapny.musicstorerecommendations.model.AlbumRecommendation;
import com.comapny.musicstorerecommendations.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albumRecommendations")
public class AlbumRecommendationController {
    @Autowired
    AlbumRepository albumRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation addANewAlbumRecommendation(@RequestBody  @Valid AlbumRecommendation albumRecommendation) {
        return albumRepository.save(albumRecommendation);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation lookUpAlbumRecommendationById(@PathVariable int id) {
        Optional<AlbumRecommendation> returnVal = albumRepository.findById(id);
        if(returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new NotFoundException("AlbumRecommendations not found in collection");
        }
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getAllAlbumRecommendations() {
        return albumRepository.findAll();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAnAlbumRecommendation(@RequestBody @Valid AlbumRecommendation albumRecommendation) {
        albumRepository.save(albumRecommendation);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendation(@PathVariable int id) {
        if(albumRepository.findById(id).isPresent()){
        albumRepository.deleteById(id);}
        else{
            throw new NotFoundException("AlbumRecommendation not found in collection");
        }
    }
}
