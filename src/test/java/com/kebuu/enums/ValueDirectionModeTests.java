package com.kebuu.enums;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueDirectionModeTests {

    @Test
    public void testGetDelta() {
        assertThat(ValueDirectionMode.RAW.getDirection(1, 2)).isEqualTo(Direction.UP);
        assertThat(ValueDirectionMode.RAW.getDirection(1, -2)).isEqualTo(Direction.DOWN);
        assertThat(ValueDirectionMode.RAW.getDirection(1, -2, 2)).isEqualTo(Direction.DOWN);
        assertThat(ValueDirectionMode.RAW.getDirection(1, -2, 4)).isEqualTo(Direction.NONE);

        assertThat(ValueDirectionMode.PERCENT.getDirection(1, -2)).isEqualTo(Direction.DOWN);
        assertThat(ValueDirectionMode.PERCENT.getDirection(1, 1.1)).isEqualTo(Direction.UP);
        assertThat(ValueDirectionMode.PERCENT.getDirection(1, 1.1, 5)).isEqualTo(Direction.UP);
        assertThat(ValueDirectionMode.PERCENT.getDirection(1, 1.1, 11)).isEqualTo(Direction.NONE);
    }
}
