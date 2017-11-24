package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.dao.IQuizDao;
import cn.edu.hdu.lab505.tlts.domain.Quiz;
import cn.edu.hdu.lab505.tlts.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Service
public class QuizService extends AbstractCurdServiceSupport<Quiz> implements IQuizService {
    @Autowired
    private IQuizDao quizDao;

    @Override
    protected ICurdDaoSupport<Quiz> getCurdDao() {
        return quizDao;
    }

    @Override
    public List<Quiz> findByStudent(Student student) {
        return ((IQuizDao) getCurdDao()).findByStudent(student);
    }



    @Override
    @Transactional
    public void insert(Quiz entity) {
        entity.setDate(new Date());
        super.insert(entity);
    }
}
