package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.dao.IAdminDao;
import cn.edu.hdu.lab505.tlts.dao.IAnswerDao;
import cn.edu.hdu.lab505.tlts.dao.IStudentDao;
import cn.edu.hdu.lab505.tlts.domain.Answer;
import cn.edu.hdu.lab505.tlts.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Service
public class AnswerService extends AbstractCurdServiceSupport<Answer> implements IAnswerService {
    @Autowired
    private IAnswerDao answerDao;
    @Autowired
    private IStudentDao studentDao;

    public IStudentDao getStudentDao() {
        return studentDao;
    }

    @Override
    protected ICurdDaoSupport<Answer> getCurdDao() {
        return answerDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Answer> findByStudent(Student student) {
        return ((IAnswerDao) getCurdDao()).findByStudent(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Answer> findByStudent(String openid) {
        Student student=getStudentDao().getByWeChatId(openid);
        return findByStudent(student);
    }
}
