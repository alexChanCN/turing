package cn.edu.hdu.lab505.tlts.controller.admin;

import cn.edu.hdu.lab505.tlts.domain.Quiz;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.service.IQuizService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by hhx on 2017/1/13.
 */
@Path("quiz")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuizCtrl {

    @Autowired
    private IQuizService quizService;

    public IQuizService getQuizService() {
        return quizService;
    }

    @GET
    @Path("student/{id:\\d+}")
    public List<Quiz> findByStudent(@PathParam("id") Long id) {
        Student student = new Student();
        student.setId(id);
        return getQuizService().findByStudent(student);
    }

    @POST
    public void add(Quiz quiz) {
        getQuizService().insert(quiz);
    }

    @DELETE
    @Path("{id:\\d+}")
    public void delete(@PathParam("id") Long id) {
        getQuizService().deleteById(id);
    }
}
