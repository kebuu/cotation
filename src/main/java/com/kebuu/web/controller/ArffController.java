package com.kebuu.web.controller;

import com.kebuu.builder.impl.MultiCotationBuilder;
import com.kebuu.dao.CotationRepository;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.service.ArffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arff/cotations")
public class ArffController {

    @Autowired private ArffService arffService;
    @Autowired private MultiCotationBuilder multiCotationBuilder;
    @Autowired private CotationRepository cotationRepository;

    @RequestMapping(value = "/built", method = RequestMethod.GET)
    public String getbuildCotations() {
        Cotations cotations = new Cotations(cotationRepository.findAll());
        return arffService.ToArff(multiCotationBuilder.attributes(), multiCotationBuilder.basedOn(cotations));
    }

    @RequestMapping(value = "/built/file", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource getbuildCotationsAsFile() {
        String fileContent = getbuildCotations();
        return new ByteArrayResource(fileContent.getBytes());
    }
}
