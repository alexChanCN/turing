package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.dao.IAttendanceDao;
import cn.edu.hdu.lab505.tlts.dao.ILessonDao;
import cn.edu.hdu.lab505.tlts.domain.Attendance;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Service
public class AttendanceService extends AbstractCurdServiceSupport<Attendance> implements IAttendanceService {
    @Autowired
    private IAttendanceDao attendanceDao;
    @Autowired
    private ILessonDao lessonDao;

    public ILessonDao getLessonDao() {
        return lessonDao;
    }

    @Override
    protected ICurdDaoSupport<Attendance> getCurdDao() {
        return attendanceDao;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Attendance> findByLesson(Lesson lesson) {
        return ((IAttendanceDao)getCurdDao()).findByLesson(lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attendance> findByDefaultLesson() {
        Lesson lesson=getLessonDao().getMaxIdLesson();
        return findByLesson(lesson);
    }
}
