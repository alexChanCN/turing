package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Repository
public class TaskDao extends AbstractHibernateCurdDaoSupport<Task> implements ITaskDao {
    @Override
    public List<Task> findByLesson(Lesson lesson) {
        String ql = "from Task t where t.lesson=:lesson";
        return (List<Task>) getHibernateTemplate().findByNamedParam(ql, "lesson", lesson);
    }
}
