package com.kebuu.dto.cotation.attribute;

import com.kebuu.constant.Constant;
import com.kebuu.dto.cotation.formatter.DateFormatter;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class DateCotationAttribute extends AbstractAttribute<Date> {

    private final String arffType;

    public DateCotationAttribute(String name, String dateFormat) {
        super(name);
        formatter = new DateFormatter(dateFormat);
        arffType = Constant.ARFF_TYPE_DATE + " " + dateFormat;
    }

    public DateCotationAttribute(String name) {
        this(name, DateFormatUtils.ISO_DATE_FORMAT.getPattern());
    }

    @Override
    public String getArffType() {
        return arffType;
    }
}
