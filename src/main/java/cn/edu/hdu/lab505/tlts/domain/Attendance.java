package cn.edu.hdu.lab505.tlts.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hhx on 2017/1/10.
 */
@Entity
@Table(name = "t_attendance")
public class Attendance implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Date date;
    @ManyToOne
    private Lesson lesson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
