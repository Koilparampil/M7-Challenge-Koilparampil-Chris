package com.comapny.musicstorerecommendations.controller;
//Code referenced from Record-collection Activity from class
import com.comapny.musicstorerecommendations.exceptions.NotFoundException;
import com.comapny.musicstorerecommendations.model.LabelRecommendation;
import com.comapny.musicstorerecommendations.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labelRecommendations")
public class LabelRecommendationController {
    @Autowired
    LabelRepository labelRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation addLabelRecommendation(@RequestBody @Valid LabelRecommendation labelRecommendation) {
        return labelRepository.save(labelRecommendation);
    }

    @GetMapping("/{id}")
    public LabelRecommendation getLabelRecommendationById(@PathVariable int id) {
        Optional<LabelRecommendation> returnVal = labelRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new NotFoundException("LabelRecommendation not found in collection");
        }
    }

    @GetMapping()
    public List<LabelRecommendation> getLabelRecommendations() {
        return labelRepository.findAll();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendation(@RequestBody @Valid LabelRecommendation labelRecommendation) {
        labelRepository.save(labelRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendation(@PathVariable int id) {
        if(labelRepository.findById(id).isPresent()){
            labelRepository.deleteById(id);
        } else{
            throw new NotFoundException("Artist not found in collection");
        }
    }
}
