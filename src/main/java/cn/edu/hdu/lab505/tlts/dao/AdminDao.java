package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by hhx on 2017/1/10.
 */
@Repository
public class AdminDao extends AbstractHibernateCurdDaoSupport<Admin> implements IAdminDao {
    @Override
    public Admin getByAdminIdAndName(Long adminId, String name) {
        List<Admin> list = getHibernateTemplate().findByExample(new Admin(name, adminId, null));
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public Admin getByWeChatId(String weChatId) {
        List<Admin> list = getHibernateTemplate().findByExample(new Admin(null, null, weChatId));
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
