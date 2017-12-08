package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.dao.ILessonDao;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Service
public class LessonService extends AbstractCurdServiceSupport<Lesson> implements ILessonService {
    @Autowired
    private ILessonDao lessonDao;

    @Override
    protected ICurdDaoSupport<Lesson> getCurdDao() {
        return lessonDao;
    }

    @Override
    public Lesson getDefaultLesson() {
        return ((ILessonDao) getCurdDao()).getMaxIdLesson();
    }

    @Override
    public List<Lesson> getList() {
        return lessonDao.getList();
    }
}












