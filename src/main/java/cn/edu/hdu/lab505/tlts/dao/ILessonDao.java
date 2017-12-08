package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Lesson;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface ILessonDao extends ICurdDaoSupport<Lesson> {
    Lesson getMaxIdLesson();

    List<Lesson> getList();
}
