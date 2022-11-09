package com.company.musicstorecatalog.controller;
//Code referenced from Record-collection Activity from class
import com.company.musicstorecatalog.exceptions.NotFoundException;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    AlbumRepository albumRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Album addANewAlbum(@RequestBody  @Valid Album album) {
        return albumRepository.save(album);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Album lookUpAlbumById(@PathVariable int id) {
        Optional<Album> returnVal = albumRepository.findById(id);
        if(returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new NotFoundException("Albums not found in collection");
        }
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAnAlbum(@RequestBody @Valid Album album) {
        albumRepository.save(album);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable int id) {
        if(albumRepository.findById(id).isPresent()){
        albumRepository.deleteById(id);}
        else{
            throw new NotFoundException("Album not found in collection");
        }
    }
}
