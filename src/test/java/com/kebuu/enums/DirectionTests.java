package com.kebuu.enums;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectionTests {

    @Test
    public void testFromConsecutiveValues() {
        assertThat(Direction.fromConsecutiveValues(1, 2)).isEqualTo(Direction.UP);
        assertThat(Direction.fromConsecutiveValues(2, 1)).isEqualTo(Direction.DOWN);
        assertThat(Direction.fromConsecutiveValues(1, 1)).isEqualTo(Direction.NONE);

        assertThat(Direction.fromConsecutiveValues(1, 2, 2)).isEqualTo(Direction.NONE);
        assertThat(Direction.fromConsecutiveValues(1, 10, 2)).isEqualTo(Direction.UP);
        assertThat(Direction.fromConsecutiveValues(2, 1, 2)).isEqualTo(Direction.NONE);
        assertThat(Direction.fromConsecutiveValues(10, 1, 2)).isEqualTo(Direction.DOWN);
    }
}
