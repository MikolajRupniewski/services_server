package pl.rupniewski.service_server.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message extends BaseModel{

    @ManyToOne(cascade = {
            CascadeType.PERSIST
    })
    @JoinColumn(name = "message_author")
    private Users author;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    @JsonFormat(pattern="MMM dd, YYYY HH:mm:ss aa")
    private Date date;

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "author=" + author +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
