package com.kebuu.enums;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CrossingValuesStatusTests {

    @Test
    public void testFromValues() {
        assertThat(CrossingValuesStatus.fromValues(1.0, 2.0, 4.0, 3.0)).isEqualTo(CrossingValuesStatus.FIRST_MAY_CROSS_UP_TOMORROW);
        assertThat(CrossingValuesStatus.fromValues(-1.0, -2.0, -4.0, -3.0)).isEqualTo(CrossingValuesStatus.FIRST_MAY_CROSS_DOWN_TOMORROW);
        assertThat(CrossingValuesStatus.fromValues(1.0, 2.0, 4.0, 3.9)).isEqualTo(CrossingValuesStatus.FIRST_MAY_CROSS_UP_AFTER_TOMORROW);
        assertThat(CrossingValuesStatus.fromValues(-1.0, -2.0, -4.0, -3.9)).isEqualTo(CrossingValuesStatus.FIRST_MAY_CROSS_DOWN_AFTER_TOMORROW);
        assertThat(CrossingValuesStatus.fromValues(1.0, 2.0, 4.0, 1.0)).isEqualTo(CrossingValuesStatus.FIRST_CROSSING_UP);
        assertThat(CrossingValuesStatus.fromValues(-1.0, -2.0, -4.0, -1.0)).isEqualTo(CrossingValuesStatus.FIRST_CROSSING_DOWN);
        assertThat(CrossingValuesStatus.fromValues(1.0, 2.0, 3.0, 4.0)).isEqualTo(CrossingValuesStatus.NOT_CROSSING);
        assertThat(CrossingValuesStatus.fromValues(1.0, 2.0, 3.0, 5.0)).isEqualTo(CrossingValuesStatus.NOT_CROSSING);
    }
}
