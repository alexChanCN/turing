package cn.edu.hdu.lab505.tlts.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hhx on 2017/1/9.
 */
@Entity
@Table(name = "t_answer")
public class Answer implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Lob
    private String content;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Student student;
    @ManyToOne(optional = false)
    private Task task;

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

    @JsonIgnore
    public Student getStudent() {
        return student;
    }

    @JsonProperty
    public void setStudent(Student student) {
        this.student = student;
    }

    @JsonIgnore
    public Task getTask() {
        return task;
    }

    @JsonProperty
    public void setTask(Task task) {
        this.task = task;
    }
}
