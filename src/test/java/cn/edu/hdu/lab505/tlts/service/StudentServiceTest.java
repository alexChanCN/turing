package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
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
public class StudentServiceTest {
    @Autowired
    private IStudentService studentService;

    static String[] files = {"test1.xls", "test.xlsx"};


    @Test
    public void testFindByLesson(){
        Lesson lesson=new Lesson();
        lesson.setId(1L);
        List<Student> list=studentService.findByLesson(lesson);
        System.out.println(list.size());
    }

    @Test
    public void testBatchInsert() {
        InputStream[] inputStreams = new InputStream[files.length];
        for (int i = 0; i < files.length; i++) {
            URL url = StudentServiceTest.class.getClassLoader().getResource(files[i]);
            try {
                inputStreams[i] = new FileInputStream(url.toURI().getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        studentService.batchInsertFromExcel(inputStreams[0], "xls", 1L);
        studentService.batchInsertFromExcel(inputStreams[1], "xlsx", 1L);
    }

    @Test
    public void testFindDefaultLessonPunchDateStudent(){
        List<Student> result=studentService.findDefaultLessonPunchDateStudent();
        System.out.println();
    }

    @Test
    public void testFindAll(){
       /* List<Student> list=studentService.findAll();
        System.out.println();*/
       studentService.deleteById(1L);
    }
}
