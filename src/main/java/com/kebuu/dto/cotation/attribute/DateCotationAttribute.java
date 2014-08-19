package com.kebuu.dto.cotation.attribute;

import com.kebuu.constant.Constant;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DateCotationAttribute extends CotationAttribute {

    private String dateFormat;

    public DateCotationAttribute(String name, String dateFormat) {
        super(name);
        this.dateFormat = dateFormat;
    }

    public DateCotationAttribute(String name) {
        this(name, DateFormatUtils.ISO_DATE_FORMAT.getPattern());
    }

    @Override
    public String getArffType() {
        return Constant.ARFF_TYPE_DATE + " " + dateFormat;
    }
}
