package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.tlts.domain.Quiz;
import cn.edu.hdu.lab505.tlts.domain.Student;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface IQuizService extends ICurdServiceSupport<Quiz> {
    List<Quiz> findByStudent(Student student);


}
