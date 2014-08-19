package com.kebuu.dto.cotation.value;

import com.kebuu.utils.DecimalFormatUtils;
import lombok.Value;

@Value
public class DoubleCotationValue implements CotationValue {

    private Double value;

    @Override
    public String getValueAsText() {
        return DecimalFormatUtils.getDefault().format(value);
    }
}
