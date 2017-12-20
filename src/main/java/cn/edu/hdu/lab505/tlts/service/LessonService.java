package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.dao.ILessonDao;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
    //@Cache()
    @CacheEvict
    public List<Lesson> getList() {
        List<Lesson> list = lessonDao.getList();
        for(Lesson lesson : list){
            System.out.println(lesson);
        }
        //return lessonDao.getList();
        return list;
    }
}












