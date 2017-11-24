package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Student;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Repository
public class StudentDao extends AbstractHibernateCurdDaoSupport<Student> implements IStudentDao {
    private final static Logger LOGGER = Logger.getLogger(StudentDao.class);

    @Override
    public void batchInsert(List<Student> list) {
        getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session session) throws HibernateException {
                try {
                    int counter = 0;
                    for (Student student : list) {
                        session.save(student);
                        counter++;
                        if (counter == 20) {
                            session.flush();
                            session.clear();
                            counter = 0;
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public List<Student> findByLesson(Lesson lesson) {
        String ql = "from Student s where s.lesson=:lesson";
        return (List<Student>) getHibernateTemplate().findByNamedParam(ql, "lesson", lesson);
    }

    @Override
    public Student getByWeChatId(String weChatId) {
        Student student = new Student();
        student.setWeChatId(weChatId);
        List<Student> list = getHibernateTemplate().findByExample(student);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }

    }

    @Override
    public List<Student> findByPunchDateAndLesson(Date date, Lesson lesson) {
        String ql = "select distinct s from Student s inner join s.punchSet p where DATE(p.date)=DATE(:date) and s.lesson=:lesson order by s.id asc ";
        String[] params = {"date", "lesson"};
        Object[] values = {date, lesson};
        return (List<Student>) getHibernateTemplate().findByNamedParam(ql, params, values);
    }

    @Override
    public Student getByStudentIdAndName(Long studentId, String name) {
        String ql = "from Student s where s.studentId=:studentId and s.name=:name";
        String[] params = {"studentId", "name"};
        Object[] values = {studentId, name};
        List<Student> list = (List<Student>) getHibernateTemplate().findByNamedParam(ql, params, values);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
