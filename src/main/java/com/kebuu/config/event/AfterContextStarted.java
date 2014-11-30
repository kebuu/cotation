package com.kebuu.config.event;

import com.kebuu.builder.impl.CompositeCotationBuilder;
import com.kebuu.dao.CotationRepository;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.service.ExportDataService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class AfterContextStarted implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private ExportDataService exportDataService;
    @Autowired private CompositeCotationBuilder compositeCotationBuilder;
    @Autowired private CotationRepository cotationRepository;

    @Override
    @SneakyThrows
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Application Ready");

        Cotations cotations = new Cotations(cotationRepository.findByCode("FR0003500008"));
        String export = exportDataService.ToArff(compositeCotationBuilder.attributes(), compositeCotationBuilder.basedOn(cotations));
        FileUtils.write(new File("target/data.arff"), export);
    }
}
