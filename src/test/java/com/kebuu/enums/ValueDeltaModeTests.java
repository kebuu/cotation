package com.kebuu.enums;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueDeltaModeTests {

    @Test
    public void testGetDelta() {
        assertThat(ValueDeltaMode.RAW.getDelta(1, 2)).isEqualTo(1);
        assertThat(ValueDeltaMode.RAW.getDelta(5, -2)).isEqualTo(-7);
        assertThat(ValueDeltaMode.RAW.getDelta(6, 6)).isEqualTo(0);

        assertThat(ValueDeltaMode.PERCENT.getDelta(1, 2)).isEqualTo(100);
        assertThat(ValueDeltaMode.PERCENT.getDelta(5, -2)).isEqualTo(-7.0 / 5.0 * 100.0);
        assertThat(ValueDeltaMode.PERCENT.getDelta(6, 6)).isEqualTo(0);
    }
}
