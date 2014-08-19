package com.kebuu.dto.cotation.value;

import lombok.Value;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

@Value
public class DateCotationValue implements CotationValue {

    private Date value;

    @Override
    public String getValueAsText() {
        return DateFormatUtils.ISO_DATE_FORMAT.format(value);
    }
}
