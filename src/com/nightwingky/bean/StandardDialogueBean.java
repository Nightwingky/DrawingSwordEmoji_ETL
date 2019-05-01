package com.nightwingky.bean;


public class StandardDialogueBean {

    private String log_id;
    private double variance;
    private double average;
    private double min;
    private String words;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "StandardDialogueBean{" +
                "log_id='" + log_id + '\'' +
                ", variance=" + variance +
                ", average=" + average +
                ", min=" + min +
                ", words='" + words + '\'' +
                '}';
    }
}
