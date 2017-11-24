package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.tlts.domain.Answer;
import cn.edu.hdu.lab505.tlts.domain.Student;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface IAnswerService extends ICurdServiceSupport<Answer> {
    List<Answer> findByStudent(Student student);
    List<Answer> findByStudent(String openid);
}
