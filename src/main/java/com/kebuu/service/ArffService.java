package com.kebuu.service;

import com.kebuu.domain.Cotation;
import org.apache.commons.vfs2.FileObject;

import java.util.List;

public interface ArffService {

    void writeToFile(List<Cotation> cotations, FileObject fileObject);
}
