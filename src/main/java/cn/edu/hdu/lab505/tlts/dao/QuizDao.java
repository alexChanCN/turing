package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Quiz;
import cn.edu.hdu.lab505.tlts.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Repository
public class QuizDao extends AbstractHibernateCurdDaoSupport<Quiz> implements IQuizDao {
    @Autowired
    private IStudentDao studentDao;

    public IStudentDao getStudentDao() {
        return studentDao;
    }

    @Override
    public List<Quiz> findByStudent(Student student) {
        String ql = "from Quiz q where q.student=:student";
        return (List<Quiz>) getHibernateTemplate().findByNamedParam(ql, "student", student);
    }

    @Override
    public List<Quiz> findByLesson(Lesson lesson) {
        List<Student> students = getStudentDao().findByLesson(lesson);
        String ql = "from Quiz q where q.student in :students";
        return (List<Quiz>) getHibernateTemplate().findByNamedParam(ql, "students", students);
    }
}
