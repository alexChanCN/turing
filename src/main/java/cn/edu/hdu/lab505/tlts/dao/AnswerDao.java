package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Answer;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Repository
public class AnswerDao extends AbstractHibernateCurdDaoSupport<Answer> implements IAnswerDao {
    @Autowired
    private IStudentDao studentDao;

    public IStudentDao getStudentDao() {
        return studentDao;
    }

    @Override
    public List<Answer> findByStudent(Student student) {
        String ql = "from Answer a where a.student=:student";
        return (List<Answer>) getHibernateTemplate().findByNamedParam(ql, "student", student);

    }

    @Override
    public List<Answer> findByLesson(Lesson lesson) {
        List<Student> students = getStudentDao().findByLesson(lesson);
        String ql = "from Answer a where a.student in :students";
        return (List<Answer>) getHibernateTemplate().findByNamedParam(ql, "students", students);
    }
}
