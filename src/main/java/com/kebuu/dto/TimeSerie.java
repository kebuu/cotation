package com.kebuu.dto;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

@Getter
public class TimeSerie<T> {

    private List<T> data = Lists.newArrayList();

    public TimeSerie(List<T> data) {
        this.data = ImmutableList.copyOf(data);
    }
}
