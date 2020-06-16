package pl.rupniewski.service_server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIgnoreProperties
@Table(name = "orders")
public class Order extends BaseModel {

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
    })
    @JoinColumn(name = "userId")
    private Users users;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
    })
    @JoinColumn(name = "order_service")
    private Service service;

    @Column(name = "date")
    @JsonFormat(pattern="MMM dd, YYYY HH:mm:ss aa")
    private Date date;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_employee")
    private Employee employee;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "payed")
    private boolean payed;

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    @Override
    public String toString() {
        return "Order{" +
                "users=" + users +
                ", service=" + service +
                ", date=" + date +
                ", employee=" + employee +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", payed=" + payed +
                '}';
    }
}
