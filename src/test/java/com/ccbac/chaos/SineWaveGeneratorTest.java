package com.ccbac.chaos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SineWaveGeneratorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testPositiveRange() {
        SineWaveGenerator generator = new SineWaveGenerator(10.0, 20.0, 12.0);
        assertEquals(20.0, generator.nextValue(0), 0.001);
        assertEquals(15.0, generator.nextValue(3000), 0.001);
        assertEquals(10.0, generator.nextValue(6000), 0.001);
        assertEquals(15.0, generator.nextValue(9000), 0.001);
        assertEquals(20.0, generator.nextValue(12000), 0.001);
        assertEquals(18.535, generator.nextValue(13500), 0.001);
        assertEquals(15.0, generator.nextValue(15000), 0.001);
    }

    @Test
    void testStraddlingRange() {
        SineWaveGenerator generator = new SineWaveGenerator(-10.0, 10.0, 12.0);
        assertEquals(10.0, generator.nextValue(0), 0.001);
        assertEquals(0.0, generator.nextValue(3000), 0.001);
        assertEquals(-10.0, generator.nextValue(6000), 0.001);
        assertEquals(0.0, generator.nextValue(9000), 0.001);
        assertEquals(10.0, generator.nextValue(12000), 0.001);
        assertEquals(0.0, generator.nextValue(15000), 0.001);
    }

    @Test
    void testNegativeRange() {
        SineWaveGenerator generator = new SineWaveGenerator(-20.0, -10.0, 12.0);
        assertEquals(-10.0, generator.nextValue(0), 0.001);
        assertEquals(-15.0, generator.nextValue(3000), 0.001);
        assertEquals(-20.0, generator.nextValue(6000), 0.001);
        assertEquals(-15.0, generator.nextValue(9000), 0.001);
        assertEquals(-10.0, generator.nextValue(12000), 0.001);
        assertEquals(-15.0, generator.nextValue(15000), 0.001);
    }
}
