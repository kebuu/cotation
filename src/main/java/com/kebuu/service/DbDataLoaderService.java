package com.kebuu.service;

import org.apache.commons.vfs2.FileObject;

import java.util.List;

public interface DbDataLoaderService {

    FileObject loadCotationFile(FileObject fileObject);

    FileObject aggregateCotationFiles(List<FileObject> fileObjects, FileObject fileObject);

    FileObject prepareCotationFile(FileObject fileObject);

    List<FileObject> findCotationFiles();
}
