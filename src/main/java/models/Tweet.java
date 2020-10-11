package models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "published_at")
    @CreationTimestamp
    private Date publishedAt;
    private String message;

    private String author;

    public Tweet() {
    }

    public Tweet(String author,  String message) {
        this.author = author;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", publishedAt=" + publishedAt +
                ", message='" + message + '\'' +
                ", author=" + author +
                '}';
    }
}
