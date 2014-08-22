package com.kebuu.dto.cotation.formatter;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class DateFormatter extends AbstractFormatter<Date> {

    private final String dateFormat;

    public DateFormatter(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String doFormat(Date value) {
        return DateFormatUtils.format(value, dateFormat);
    }
}
