package cn.edu.hdu.lab505.tlts.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hhx on 2017/1/9.
 */
@Entity
@Table(name = "t_student")
public class Student implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Long studentId;
    @Column(nullable = false)
    private String name;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Lesson lesson;
    @Column(unique = true)
    private String weChatId;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Answer> answerSet = new HashSet<>();


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Quiz> quizSet = new HashSet<>();


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Punch> punchSet = new HashSet<>();

    public Student(Long studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public Student() {
    }

    @JsonIgnore
    public Set<Punch> getPunchSet() {
        return punchSet;
    }

    public void setPunchSet(Set<Punch> punchSet) {
        this.punchSet = punchSet;
    }
    @JsonIgnore
    public Set<Answer> getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(Set<Answer> answerSet) {
        this.answerSet = answerSet;
    }
    @JsonIgnore
    public Set<Quiz> getQuizSet() {
        return quizSet;
    }

    public void setQuizSet(Set<Quiz> quizSet) {
        this.quizSet = quizSet;
    }

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
