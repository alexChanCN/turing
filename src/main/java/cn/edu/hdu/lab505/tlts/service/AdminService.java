package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.dao.IAdminDao;
import cn.edu.hdu.lab505.tlts.domain.Admin;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by hhx on 2017/1/10.
 */
@Service
public class AdminService extends AbstractCurdServiceSupport<Admin> implements IAdminService {
    @Autowired
    private IAdminDao adminDao;

    @Override
    protected ICurdDaoSupport<Admin> getCurdDao() {
        return this.adminDao;
    }

    @Override
    @Transactional
    public String bind(Admin admin) throws AppException {
        Admin origin = adminDao.getByAdminIdAndName(admin.getAdminId(), admin.getName());
        if (origin == null) {
            throw new AppException("操作失败，请检查输入是否正确");
        }
        if (!StringUtils.isEmpty(origin.getWeChatId())) {
            throw new AppException("无需重复绑定！");
        }
        origin.setWeChatId(admin.getWeChatId());
        try {
            adminDao.update(origin);
            return "绑定成功!";
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException("该微信号已被绑定！");
        }

    }

    @Override
    @Transactional
    public void add(Admin admin) {
        try {
            adminDao.insert(admin);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException("管理员id重复添加！");
        }

    }

    @Override
    @Transactional
    public void unBind(Admin admin) throws AppException {
        Admin origin = adminDao.getByAdminIdAndName(admin.getAdminId(), admin.getName());
        if (StringUtils.isEmpty(origin.getWeChatId())) {
            throw new AppException("该微信号尚未绑定!");
        }
        origin.setWeChatId(null);
        adminDao.update(origin);
    }

    @Override
    @Transactional(readOnly = true)
    public Admin getByWeChatId(String weChatId) {
        return ((IAdminDao) getCurdDao()).getByWeChatId(weChatId);
    }
}
