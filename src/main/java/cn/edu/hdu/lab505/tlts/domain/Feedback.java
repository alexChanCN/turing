package cn.edu.hdu.lab505.tlts.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hhx on 2017/1/12.
 */
@Entity
@Table(name = "t_feedback")
public class Feedback implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String content;
    @Column
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Feedback() {
    }

    public Feedback(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
