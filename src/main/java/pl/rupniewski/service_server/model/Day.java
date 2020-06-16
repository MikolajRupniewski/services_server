package pl.rupniewski.service_server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "days")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Day extends BaseModel {

    @Column(name = "open")
    private String open;

    @Column(name = "close")
    private String close;

    public Day(String open, String close) {
        this.open = open;
        this.close = close;
    }

    public Day() {
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "Day{" +
                "open='" + open + '\'' +
                ", close='" + close + '\'' +
                '}';
    }
}
