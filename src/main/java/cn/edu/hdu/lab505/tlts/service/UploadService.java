package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.dao.IUploadDao;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.domain.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ShineChan on 2017/11/24.
 */
@Service
public class UploadService implements IUploadService {

    @Autowired
    IUploadDao uploadDao;

    @Override
    public List<Upload> listAll() {
        return uploadDao.listAll();
    }

    @Override
    public Upload getOneByStudent(Student student) {
        List<Upload> list = uploadDao.getByStudent(student);
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public List<Upload> listAllByLessonId(Long id) {
        return uploadDao.listAllByLessonId(id);
    }

    @Transactional
    @Override
    public Long save(Upload upload) {
        return uploadDao.save(upload);
    }

    @Transactional
    @Override
    public void saveOrUpdate(Upload upload) {
        uploadDao.saveOrUpdate(upload);
    }


}
