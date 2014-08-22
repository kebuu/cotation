package com.kebuu.dto.cotation;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;

import java.util.List;
import java.util.Optional;

public class BuiltCotation {

    private final Cotation baseCotation;
    private List<CotationValue> values = Lists.newArrayList();

    public BuiltCotation(Cotation baseCotation) {
        this.baseCotation = baseCotation;
    }

    public int getPosition() {
        return baseCotation.getPosition();
    }

    public List<CotationValue> getValues() {
        return ImmutableList.copyOf(values);
    }

    public Cotation getBaseCotation() {
        return baseCotation;
    }

    public BuiltCotation withAdditionalValues(CotationValue... cotationValues) {
        return withAdditionalValues(Lists.newArrayList(cotationValues));
    }

    public BuiltCotation withAdditionalValues(List<CotationValue> cotationValues) {
        values.addAll(cotationValues);
        return this;
    }

    public <T> Optional<CotationValue<T>> getValueByAttribute(CotationAttribute<T> attribute) {
        return values.stream()
            .filter(cotationValue -> cotationValue.getAttribute().getName().equals(attribute.getName()))
            .map(cotationValue -> (CotationValue<T>) cotationValue)
            .findFirst();
    }

    public static BuiltCotation merge(BuiltCotation builtCotation1, BuiltCotation builtCotation2) {
        Preconditions.checkArgument(builtCotation1.getBaseCotation().equals(builtCotation2.getBaseCotation()), "Both parameters have to be built on the same base cotation");

        List<CotationValue> mergedCotationValues = Lists.newArrayList(Iterables.concat(builtCotation1.getValues(), builtCotation2.getValues()));
        return new BuiltCotation(builtCotation1.getBaseCotation()).withAdditionalValues(mergedCotationValues);
    }
}
