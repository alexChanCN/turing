package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Attendance;
import cn.edu.hdu.lab505.tlts.domain.Lesson;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface IAttendanceDao extends ICurdDaoSupport<Attendance> {
    Attendance getByLessonAndDate(Lesson lesson, Date date);

    List<Attendance> findByLesson(Lesson lesson);
}
