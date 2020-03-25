package com.solactive.model;

/**
 * Created by sandeepreddy on 22/03/20.
 */
public class StatisticsDTO {

    private double avg;
    private double max;
    private double min;
    private long count;

    public StatisticsDTO(Double sum, Double max, Double min, long count) {
        if(count!=0) {
            this.avg = sum / count;
            this.max = max;
            this.min = min;
            this.count = count;
        }
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "InstrumentStatistics{" +
                "avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }
}
