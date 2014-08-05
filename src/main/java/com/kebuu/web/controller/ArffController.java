package com.kebuu.web.controller;

import com.kebuu.service.ArffService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arff/cotations")
public class ArffController {

    @Autowired
    private ArffService arffService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @SneakyThrows
    public String cotationsAsArff() {
        return arffService.getEnhancedCotationsAsString();
    }
}
