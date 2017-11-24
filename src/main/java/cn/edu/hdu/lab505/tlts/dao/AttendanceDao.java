package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Attendance;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Repository
public class AttendanceDao extends AbstractHibernateCurdDaoSupport<Attendance> implements IAttendanceDao {
    @Override
    public Attendance getByLessonAndDate(Lesson lesson, Date date) {
        String ql = "from Attendance a where a.lesson=:lesson and DATE(a.date)=DATE(:date)";
        String[] params = {"lesson", "date"};
        Object[] values = {lesson, date};
        List<Attendance> list = (List<Attendance>) getHibernateTemplate().findByNamedParam(ql, params, values);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<Attendance> findByLesson(Lesson lesson) {
        String ql = "from Attendance a where a.lesson=:lesson order by a.date desc";
        return (List<Attendance>) getHibernateTemplate().findByNamedParam(ql,"lesson",lesson);
    }
}
