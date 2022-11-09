package com.company.musicstorecatalog.controller;
//Code referenced from Record-collection Activity from class
import com.company.musicstorecatalog.exceptions.NotFoundException;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labels")
public class LabelController {
    @Autowired
    LabelRepository labelRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Label addLabel(@RequestBody @Valid Label label) {
        return labelRepository.save(label);
    }

    @GetMapping("/{id}")
    public Label getLabelById(@PathVariable int id) {
        Optional<Label> returnVal = labelRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new NotFoundException("Label not found in collection");
        }
    }

    @GetMapping()
    public List<Label> getLabels() {
        return labelRepository.findAll();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@RequestBody @Valid Label label) {
        labelRepository.save(label);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable int id) {
        if(labelRepository.findById(id).isPresent()){
            labelRepository.deleteById(id);
        } else{
            throw new NotFoundException("Artist not found in collection");
        }
    }
}
