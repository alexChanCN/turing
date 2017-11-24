package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Student;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface IStudentService extends ICurdServiceSupport<Student> {

    void batchInsertFromExcel(InputStream inputStream, String fileType, Long lessonId);

    void batchInsertFromExcelToDefaultLesson(InputStream inputStream, String fileType) throws AppException;

    List<Student> findByLesson(Lesson lesson);

    List<Student> findDefaultLessonStudent();

    List<Student> findDefaultLessonPunchDateStudent();

    Student getByWeChatId(String weChatId);

    String bind(Student student) throws AppException;
}
