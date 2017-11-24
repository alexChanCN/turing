package cn.edu.hdu.lab505.tlts.controller.admin;

import cn.edu.hdu.lab505.tlts.domain.Answer;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by hhx on 2017/1/15.
 */
@Path("answer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnswerCtrl {

    @Autowired
    private IAnswerService answerService;

    public IAnswerService getAnswerService() {
        return answerService;
    }

    @GET
    public List<Answer> getByStudent(@QueryParam("sid")Long id){
        Student student=new Student();
        student.setId(id);
        return getAnswerService().findByStudent(student);
    }
}
