package cn.edu.hdu.lab505.tlts.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hhx on 2017/1/10.
 */

/**
 * 学生签到
 */
@Entity
@Table(name = "t_punch")
public class Punch implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Date date;
    @ManyToOne
    private Student student;

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
