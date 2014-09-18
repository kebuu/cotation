package com.kebuu.enums;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueComparisonPositionTests {

    @Test
    public void testFromConsecutiveValues() {
        assertThat(ValueComparisonPosition.from(1, 2)).isEqualTo(ValueComparisonPosition.FIRST_VALUE_LOWER);
        assertThat(ValueComparisonPosition.from(2, 1)).isEqualTo(ValueComparisonPosition.FIRST_VALUE_UPPER);
        assertThat(ValueComparisonPosition.from(1, 1)).isEqualTo(ValueComparisonPosition.EQUAL_VALUES);

        assertThat(ValueComparisonPosition.from(1, 2, 2)).isEqualTo(ValueComparisonPosition.EQUAL_VALUES);
        assertThat(ValueComparisonPosition.from(1, 10, 2)).isEqualTo(ValueComparisonPosition.FIRST_VALUE_LOWER);
        assertThat(ValueComparisonPosition.from(2, 1, 2)).isEqualTo(ValueComparisonPosition.EQUAL_VALUES);
        assertThat(ValueComparisonPosition.from(10, 1, 2)).isEqualTo(ValueComparisonPosition.FIRST_VALUE_UPPER);
    }
}
