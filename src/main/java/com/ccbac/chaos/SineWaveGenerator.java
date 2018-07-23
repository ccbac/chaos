package com.ccbac.chaos;

public class SineWaveGenerator extends WaveformGenerator {

    private Double minimum;
    private Double maximum;
    private Double periodSeconds;

    SineWaveGenerator(Double minimum, Double maximum, Double periodSeconds) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.periodSeconds = periodSeconds;
    }

    public Double nextValue() {
        Double secondsElapsed = System.currentTimeMillis() / 1000.0;
        Double radians = ((secondsElapsed % periodSeconds) / periodSeconds) * Math.PI * 2;
        Double cosine = Math.cos(radians);
        Double range = (maximum - minimum);
        return ((cosine / 1) / 2) * range + minimum;
    }

}
