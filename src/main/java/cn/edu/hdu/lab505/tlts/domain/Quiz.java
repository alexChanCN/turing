package cn.edu.hdu.lab505.tlts.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hhx on 2017/1/9.
 */
@Entity
@Table(name = "t_quiz")
public class Quiz implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Integer score;
    @Column(nullable = false)
    private Date date;
    @ManyToOne(optional = false)
    private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
