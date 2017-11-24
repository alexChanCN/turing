package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.tlts.domain.Attendance;
import cn.edu.hdu.lab505.tlts.domain.Lesson;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface IAttendanceService extends ICurdServiceSupport<Attendance> {
    List<Attendance> findByLesson(Lesson lesson);
    List<Attendance> findByDefaultLesson();
}
