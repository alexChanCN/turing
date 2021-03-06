package cn.edu.hdu.lab505.tlts.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hhx on 2017/1/9.
 */
@Entity
@Table(name = "t_lesson")
public class Lesson implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Student> studentSet = new HashSet<>();
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> taskSet = new HashSet<>();
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attendance> attendanceSet = new HashSet<>();

    public Set<Attendance> getAttendanceSet() {
        return attendanceSet;
    }

    public void setAttendanceSet(Set<Attendance> attendanceSet) {
        this.attendanceSet = attendanceSet;
    }

    public Set<Task> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(Set<Task> taskSet) {
        this.taskSet = taskSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }


}
