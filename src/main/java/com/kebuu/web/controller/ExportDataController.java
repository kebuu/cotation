package com.kebuu.web.controller;

import com.kebuu.builder.impl.CompositeCotationBuilder;
import com.kebuu.dao.CotationRepository;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.service.ExportDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cotations/{code}")
@Slf4j
public class ExportDataController {

    @Autowired private ExportDataService exportDataService;
    @Autowired private CompositeCotationBuilder compositeCotationBuilder;
    @Autowired private CotationRepository cotationRepository;

    @RequestMapping(value = "/csv", method = RequestMethod.GET)
    public String getBuiltCotationsAsCsv(@PathVariable("code") String code) {
        Cotations cotations = getCotations(code);
        return exportDataService.toCsv(compositeCotationBuilder.attributes(), compositeCotationBuilder.basedOn(cotations));
    }

    @RequestMapping(value = "/csv/file", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource getBuiltCotationsAsCsvFile(@PathVariable("code") String code) {
        String fileContent = getBuiltCotationsAsCsv(code);
        return new ByteArrayResource(fileContent.getBytes());
    }

    @RequestMapping(value = "/arff", method = RequestMethod.GET)
    public String getBuiltCotationsAsArff(@PathVariable("code") String code) {
        Cotations cotations = getCotations(code);
        return exportDataService.ToArff(compositeCotationBuilder.attributes(), compositeCotationBuilder.basedOn(cotations));
    }

    @RequestMapping(value = "/arff/file", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource getBuiltCotationsAsArffFile(@PathVariable("code") String code) {
        String fileContent = getBuiltCotationsAsArff(code);
        return new ByteArrayResource(fileContent.getBytes());
    }

    private Cotations getCotations(String code) {
        return new Cotations(cotationRepository.findByCode(code));
    }
}
