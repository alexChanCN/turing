package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Punch;
import cn.edu.hdu.lab505.tlts.domain.Student;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Repository
public class PunchDao extends AbstractHibernateCurdDaoSupport<Punch> implements IPunchDao {
    @Override
    public Punch getByStudentAndDate(Student student, Date date) {
        String ql = "from Punch p where p.student=:student and DATE(p.date)=DATE(:date)";
        String[] params = {"student", "date"};
        Object[] values = {student, date};
        List<Punch> list = (List<Punch>) getHibernateTemplate().findByNamedParam(ql, params, values);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<Punch> findByDate(Date start, Date end) {
        String ql = "from Punch p left join fetch p.student s where DATE(p.date) >= DATE(:start) and DATE(p.date) <=DATE(:end)";
        String[] params = {"start", "end"};
        Object[] values = {start, end};

        return (List<Punch>) getHibernateTemplate().findByNamedParam(ql, params, values);
    }

    @Override
    public int deleteByStudentAndDate(Student student, Date date) {
        return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                session.flush();
                String ql = "delete from Punch p where p.student=:student and DATE(p.date)=DATE(:date)";
                Query query = session.createQuery(ql);
                query.setParameter("student", student);
                query.setParameter("date", date);
                query.executeUpdate();
                return new Integer(query.executeUpdate());
            }
        });
    }


}
