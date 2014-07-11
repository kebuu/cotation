package com.kebuu.service.impl;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.kebuu.service.DbDataLoaderService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

@Service
public class DbDataLoaderServiceImpl implements DbDataLoaderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @SneakyThrows
    public FileObject loadCotationFile(FileObject fileObject) {
        jdbcTemplate.execute(       "INSERT into cotation(date,start,end,min,max,volume) " +
                                    "SELECT \"date\", \"start\" ,\"end\", \"min\", \"max\", \"volume\" " +
                                    "FROM CSVREAD('" + fileObject.getURL().getFile() + "', 'code,date,start,max,min,end,volume')");
        return fileObject;
    }

    @Override
    @SneakyThrows
    public FileObject aggregateCotationFiles(List<FileObject> fileObjects, FileObject fileObject) {
        OutputStream outputStream = fileObject.getContent().getOutputStream();

        fileObjects.stream()
                .map(currentFileObject -> {
                    try {
                        return Files.toString(new File(fileObject.getURL().getFile()), Charsets.UTF_8);
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                })
                .forEach(fileObjectContent -> {
                    try {
                        IOUtils.write(fileObjectContent, outputStream);
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                });

        return fileObject;
    }

    @Override
    @SneakyThrows
    public FileObject prepareCotationFile(FileObject fileObject) {
        String fileContent = Files.toString(new File(fileObject.getURL().getFile()), Charsets.UTF_8);

        String transformedFileContent = fileContent.replaceAll("(\\d{2})/(\\d{2})/(9\\d)", "19$3-$2-$1");
        transformedFileContent = transformedFileContent.replaceAll("(\\d{2})/(\\d{2})/([0-1]\\d)", "20$3-$2-$1");
        IOUtils.write(transformedFileContent.replaceAll(";", ","), fileObject.getContent().getOutputStream());
        return fileObject;
    }

    @Override
    @SneakyThrows
    public List<FileObject> findCotationFiles() {
        List<FileObject> resultFiles = Lists.newArrayList();
        //VFS.getManager().resolveFile("data/file").findFiles(new FileFilterSelector(fileInfo -> new RegexFileFilter("").fileInfo.getFile().getName()), true, resultFiles);
        return resultFiles;
    }
}
