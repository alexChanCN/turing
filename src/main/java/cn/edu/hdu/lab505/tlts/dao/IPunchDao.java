package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Punch;
import cn.edu.hdu.lab505.tlts.domain.Student;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface IPunchDao extends ICurdDaoSupport<Punch> {
    Punch getByStudentAndDate(Student student, Date date);

    List<Punch> findByDate(Date start, Date end);

    int deleteByStudentAndDate(Student student, Date date);
}
