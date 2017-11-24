package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Student;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface IStudentDao extends ICurdDaoSupport<Student> {
    void batchInsert(List<Student> list);

    List<Student> findByLesson(Lesson lesson);

    Student getByWeChatId(String weChatId);

    List<Student> findByPunchDateAndLesson(Date date, Lesson lesson);

    Student getByStudentIdAndName(Long studentId, String name);

}
