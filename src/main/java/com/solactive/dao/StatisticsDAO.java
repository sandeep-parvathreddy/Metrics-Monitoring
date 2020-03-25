package com.solactive.dao;

/**
 * Created by sandeepreddy on 22/03/20.
 */
public class StatisticsDAO {

    private double sum;
    private double max;
    private double min;
    private long count;

    public StatisticsDAO(){
        
    }
    public StatisticsDAO(double sum, double max, double min, long count) {
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double avg) {
        this.sum = avg;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        if(min==0.0){
            return Double.MAX_VALUE;
        }
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
