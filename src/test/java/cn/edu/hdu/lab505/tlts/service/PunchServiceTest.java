package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.domain.Punch;
import cn.edu.hdu.lab505.tlts.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PunchServiceTest {
    @Autowired
    private IPunchService punchService;

    @Test
    public void testStudentPunch() {
        Student student = new Student();
        student.setId(2L);
        try {
            punchService.studentPunch(student,new Date());
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPunchByWeChatId() throws InterruptedException {
        try {
            punchService.letPunch();
        } catch (AppException e) {
            e.printStackTrace();
        }
        Thread.sleep(1000*60*4);
        try {
            punchService.studentPunch("hhh");
        } catch (AppException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testLetPunch(){
        try {
            punchService.letPunch();
        } catch (AppException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFindDefaultLessonPunch(){
        List<Punch> list=punchService.findDefaultLessonPunch();
        System.out.println();
    }
}
