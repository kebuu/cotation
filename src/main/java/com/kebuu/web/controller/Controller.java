package com.kebuu.web.controller;

import com.kebuu.service.ArffService;
import com.kebuu.service.DbDataLoaderService;
import lombok.SneakyThrows;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private DbDataLoaderService dbDataLoaderService;

    @Autowired
    private ArffService arffService;

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @SneakyThrows
    public String loadFileToDb(@RequestBody String filePath) {
        FileObject fileObject = VFS.getManager().resolveFile(filePath);
        dbDataLoaderService.loadCotationFile(fileObject);
        return "OK";
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @SneakyThrows
    public String exportToArff() {
        return arffService.toArff();
    }
}
