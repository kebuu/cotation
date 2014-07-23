package com.kebuu.domain;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class TimeSeriesCotation {

    private List<Cotation> cotations = Lists.newArrayList();
}
