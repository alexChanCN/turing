package cn.edu.hdu.lab505.tlts.bean;

import cn.edu.hdu.lab505.tlts.domain.Student;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/15.
 */
public class ExcelBean<T> {
    private String[] headers;
    private Date[] dates;
    private List<T[]> values;
    private List<Student> students;

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public Date[] getDates() {
        return dates;
    }

    public void setDates(Date[] dates) {
        this.dates = dates;
    }

    public List<T[]> getValues() {
        return values;
    }

    public void setValues(List<T[]> values) {
        this.values = values;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
