package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hhx on 2017/1/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class QuizServiceTest {

    @Autowired
    private IQuizService quizService;
    @Autowired
    private IStudentService studentService;

    @Test
    public void testFindByStudent(){
       // Student s=studentService.get(1L);
        Student s=new Student(1L,null);
        s.setId(1L);
        quizService.findByStudent(s);
    }
}
