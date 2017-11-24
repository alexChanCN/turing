package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.tlts.domain.Admin;

/**
 * Created by hhx on 2017/1/10.
 */
public interface IAdminService extends ICurdServiceSupport<Admin> {
    String bind(Admin admin) throws AppException;

    void add(Admin admin);

    void unBind(Admin admin) throws AppException;

    Admin getByWeChatId(String weChatId);
}
