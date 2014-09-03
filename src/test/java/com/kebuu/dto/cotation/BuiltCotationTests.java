package com.kebuu.dto.cotation;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BuiltCotationTests {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BuiltCotation builtCotation;

    @Before
    public void beforeBuiltCotationTests() {
        builtCotation = new BuiltCotation(new Cotation());
    }

    @Test
    public void testCannotAddSameAttributeTwice() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("All cotationValues should reference a different attribute name");

        SimpleCotationValue<Double> cotationValue1 = new SimpleCotationValue<Double>(new RealCotationAttribute("attributeName1"));
        builtCotation.withAdditionalValues(cotationValue1, cotationValue1);
    }
}
