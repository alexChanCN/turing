package cn.edu.hdu.lab505.tlts.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ShineChan on 2017/11/24.
 */
@Entity
@Table(name = "t_upload_record")
public class Upload implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    private Date datetime;
    @ManyToOne
    private Student student;
    private Integer status;
    private String fileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
