package cn.edu.hdu.lab505.tlts.util;

import cn.edu.hdu.lab505.tlts.common.AppException;

/**
 * Created by hhx on 2017/1/16.
 */
public class Test {
    @org.junit.Test
    public void e(){
        try {
            throw new AppException("233");
        } catch (AppException e) {
            System.out.println(e.getMessage());
        }
    }
}
