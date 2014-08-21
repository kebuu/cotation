package com.kebuu.dto.cotation.formatter;

import com.kebuu.utils.DecimalFormatUtils;

public class DoubleFormatter extends AbstractFormatter<Double> {    

    @Override
    public String doFormat(Double value) {
        return DecimalFormatUtils.getDefault().format(value);
    }
}
