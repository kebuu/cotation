package com.kebuu.web.controller;

import com.kebuu.service.DbDataLoader;
import lombok.SneakyThrows;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbController {

    @Autowired
    private DbDataLoader dbDataLoader;

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @SneakyThrows
    public String loadFileToDb(@RequestBody String filePath) {
        FileObject fileObject = VFS.getManager().resolveFile(filePath);
        dbDataLoader.loadCotationFile(fileObject);
        return "OK";
    }
}
