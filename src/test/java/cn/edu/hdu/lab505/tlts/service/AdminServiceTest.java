package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.domain.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hhx on 2017/1/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AdminServiceTest {
    @Autowired
    private IAdminService adminService;

    @Test
    //@Rollback(false)
    public void testAdd() throws AppException {
        Admin admin = new Admin("hhxx", 1234L, null);
        adminService.add(admin);

    }

    @Test
    public void testBind() {
        Admin admin = new Admin("hhx", 12L, "fjsdfs");
        try {
            adminService.bind(admin);
        } catch (AppException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testUnBind() throws AppException {
        Admin admin = new Admin("hhx", 123L, "fjsdfs");
        adminService.unBind(admin);
    }


}
