package pl.rupniewski.service_server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee extends BaseModel{

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Day monday;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Day tuesday;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Day wednesday;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Day thursday;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Day friday;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Day saturday;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Day sunday;

    public Day getMonday() {
        return monday;
    }

    public void setMonday(Day monday) {
        this.monday = monday;
    }

    public Day getTuesday() {
        return tuesday;
    }

    public void setTuesday(Day tuesday) {
        this.tuesday = tuesday;
    }

    public Day getWednesday() {
        return wednesday;
    }

    public void setWednesday(Day wednesday) {
        this.wednesday = wednesday;
    }

    public Day getThursday() {
        return thursday;
    }

    public void setThursday(Day thursday) {
        this.thursday = thursday;
    }

    public Day getFriday() {
        return friday;
    }

    public void setFriday(Day friday) {
        this.friday = friday;
    }

    public Day getSaturday() {
        return saturday;
    }

    public void setSaturday(Day saturday) {
        this.saturday = saturday;
    }

    public Day getSunday() {
        return sunday;
    }

    public void setSunday(Day sunday) {
        this.sunday = sunday;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                '}';
    }
}
