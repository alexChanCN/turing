package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.AbstractHibernateCurdDaoSupport;
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
    public Long save(Upload upload) {
        Long id = (Long) getHibernateTemplate().save(upload);
        return id;
    }
}
