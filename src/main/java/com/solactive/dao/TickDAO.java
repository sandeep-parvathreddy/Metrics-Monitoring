package com.solactive.dao;

/**
 * Created by sandeepreddy on 22/03/20.
 */
public class TickDAO {

    private String instrument;
    private Double price;
    private Long timestamp;

    public TickDAO(String instrument, Double price, Long timestamp) {
        this.instrument = instrument;
        this.price = price;
        this.timestamp = timestamp/1000;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TickDAO{" +
                "instrument='" + instrument + '\'' +
                ", price=" + price +
                ", timestamp=" + timestamp +
                '}';
    }
}
