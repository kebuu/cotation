package com.kebuu.web.controller;

import com.kebuu.dao.EnhancedCotationRepository;
import com.kebuu.domain.EnhancedCotation;
import com.kebuu.service.ArffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arff/cotations")
public class ArffController {

    @Autowired private ArffService arffService;
    @Autowired private EnhancedCotationRepository enhancedCotationRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String cotationsAsArff() {
        return arffService.enhancedCotationsToArff(enhancedCotationRepository.findAll());
    }

    @RequestMapping(value = "/timeserie", method = RequestMethod.GET)
    public String timeSerieCotationsAsArff(@RequestParam("elementsInSerie") int elementsInSerie) {
        Iterable<EnhancedCotation> enhancedCotations = enhancedCotationRepository.findAll(new Sort(Sort.Direction.ASC, "date"));
        return arffService.timeSeriesToArff(enhancedCotations, elementsInSerie);
    }
}
