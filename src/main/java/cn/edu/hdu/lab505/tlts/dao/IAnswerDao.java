package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Answer;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Student;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface IAnswerDao extends ICurdDaoSupport<Answer> {
    List<Answer> findByStudent(Student student);

    List<Answer> findByLesson(Lesson lesson);
}
