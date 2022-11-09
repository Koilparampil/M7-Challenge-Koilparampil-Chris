package com.comapny.musicstorerecommendations.controller;
//Code referenced from Record-collection Activity from class
import com.comapny.musicstorerecommendations.exceptions.NotFoundException;
import com.comapny.musicstorerecommendations.model.TrackRecommendation;
import com.comapny.musicstorerecommendations.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trackRecommendations")
public class TrackRecommendationController {
    @Autowired
    TrackRepository trackRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation addTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRecommendation) {
        return trackRepository.save(trackRecommendation);
    }


    @GetMapping("/{id}")
    public TrackRecommendation getTrackRecommendationById(@PathVariable int id) {
        Optional<TrackRecommendation> returnVal = trackRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new NotFoundException("Label not found in collection");
        }
    }

    @GetMapping()
    public List<TrackRecommendation> getTrackRecommendations() {
        return trackRepository.findAll();
    }


    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRecommendation) {
        trackRepository.save(trackRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendation(@PathVariable int id) {
        if(trackRepository.findById(id).isPresent()){
            trackRepository.deleteById(id);
        } else{
            throw new NotFoundException("Artist not found in collection");
        }
    }
}
