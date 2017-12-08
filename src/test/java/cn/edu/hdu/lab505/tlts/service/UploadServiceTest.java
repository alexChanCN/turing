package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.dao.IUploadDao;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.domain.Upload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by hhx on 2017/1/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UploadServiceTest {
    @Autowired
    private IUploadDao uploadDao;



    @Test
    public void testFindByStudent(){
        Student student = new Student();
        student.setId(53l);
        List<Upload> uploads = uploadDao.listAllByStudent(student);
        for (Upload upload:uploads) {
            System.out.println(upload);
        }
    }

    @Test
    public void testFindByLesson(){
        List<Upload> uploads = uploadDao.listAllByLessonId(3l);
        for (Upload upload:uploads) {
            System.out.println(upload);
        }
    }


}
