package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Admin;

/**
 * Created by hhx on 2017/1/10.
 */
public interface IAdminDao extends ICurdDaoSupport<Admin> {
    Admin getByAdminIdAndName(Long adminId, String name);
    Admin getByWeChatId(String weChatId);
}
