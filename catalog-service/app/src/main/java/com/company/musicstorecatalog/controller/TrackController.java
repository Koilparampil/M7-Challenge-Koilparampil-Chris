package com.company.musicstorecatalog.controller;
//Code referenced from Record-collection Activity from class
import com.company.musicstorecatalog.exceptions.NotFoundException;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tracks")
public class TrackController {
    @Autowired
    TrackRepository trackRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Track addTrack(@RequestBody @Valid Track track) {
        return trackRepository.save(track);
    }


    @GetMapping("/{id}")
    public Track getTrackById(@PathVariable int id) {
        Optional<Track> returnVal = trackRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new NotFoundException("Label not found in collection");
        }
    }

    @GetMapping()
    public List<Track> getTracks() {
        return trackRepository.findAll();
    }


    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrack(@RequestBody @Valid Track track) {
        trackRepository.save(track);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrack(@PathVariable int id) {
        if(trackRepository.findById(id).isPresent()){
            trackRepository.deleteById(id);
        } else{
            throw new NotFoundException("Artist not found in collection");
        }
    }
}
