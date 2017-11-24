package cn.edu.hdu.lab505.tlts.controller.user;

import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.dao.IStudentDao;
import cn.edu.hdu.lab505.tlts.domain.Answer;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
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
    @Autowired
    private IStudentDao studentDao;

    public IStudentDao getStudentDao() {
        return studentDao;
    }

    public IAnswerService getAnswerService() {
        return answerService;
    }
    @GET
    @Path("student")
    public List<Answer> getStudentAnswers(@QueryParam("openid")String openid){
       return getAnswerService().findByStudent(openid);
    }

    @POST
    public void add(Answer answer,@HeaderParam("token")String openid){
        Student student=getStudentDao().getByWeChatId(openid);
        answer.setStudent(student);
        getAnswerService().insert(answer);
    }


    @DELETE
    @Path("{id:\\d+}")
    public void delete(@PathParam("id")Long id,@HeaderParam("token")String openId) throws AppException {
        Student student=getStudentDao().getByWeChatId(openId);
        String error="删除失败！";
        if(student==null){
            throw new AppException(error);
        }
        Answer answer=getAnswerService().get(id);
        if(answer.getStudent().getId()!=student.getId()){
            throw new AppException(error);
        }
        getAnswerService().deleteById(id);
    }
}
