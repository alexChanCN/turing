package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.domain.Upload;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ShineChan on 2017/11/24.
 */
@Repository
public class UploadDao extends AbstractHibernateCurdDaoSupport<Upload> implements IUploadDao{

    @Override
    public List<Upload> listAll() {
        List<Upload> list = findAll();
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<Upload> getByStudent(Student student) {

        String ql = "from Upload u where u.student=:student";
        /*Upload upload = new Upload();
        upload.setStudent(student);
        List<Upload> list = getHibernateTemplate().findByExample(upload);*/
        List<Upload> list = (List<Upload>) getHibernateTemplate().findByNamedParam(ql,"student",student);
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<Upload> listAllByLessonId(Long lessonId) {
       /* String ql = "from Upload u where u.lessonId=:lessonId";
        List<Upload> list = (List<Upload>) getHibernateTemplate().findByNamedParam(ql,"lessonId",lessonId);*/
        Upload upload = new Upload();
        upload.setLessonId(lessonId);
        List<Upload> list = getHibernateTemplate().findByExample(upload);

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public Long save(Upload upload) {
        Long id = (Long) getHibernateTemplate().save(upload);
        return id;
    }

    @Override
    public void saveOrUpdate(Upload upload) {
        getHibernateTemplate().saveOrUpdate(upload);
    }


}
