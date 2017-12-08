package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Repository
public class LessonDao extends AbstractHibernateCurdDaoSupport<Lesson> implements ILessonDao {
    @Override
    public Lesson getMaxIdLesson() {
        String ql = "from Lesson l order by l.id desc ";
        HibernateTemplate hibernateTemplate=getHibernateTemplate();
        hibernateTemplate.setMaxResults(1);
        List<Lesson> list = (List<Lesson>) hibernateTemplate.find(ql);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<Lesson> getList() {
        return findAll();
    }
}
