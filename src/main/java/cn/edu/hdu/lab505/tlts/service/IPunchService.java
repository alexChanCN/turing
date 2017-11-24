package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Punch;
import cn.edu.hdu.lab505.tlts.domain.Student;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface IPunchService extends ICurdServiceSupport<Punch> {
    String studentPunch(Student student) throws AppException;

    String studentPunch(String weChatId) throws AppException;

    void studentPunch(Student student, Date date) throws AppException;

    String letPunch() throws AppException;

    List<Punch> findByLesson(Lesson lesson);

    List<Punch> findDefaultLessonPunch();

    int deleteByStudentAndDate(Long sid, Date date);
}
