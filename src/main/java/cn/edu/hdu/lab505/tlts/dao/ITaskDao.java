package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Task;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface ITaskDao extends ICurdDaoSupport<Task> {

    List<Task> findByLesson(Lesson lesson);
}
