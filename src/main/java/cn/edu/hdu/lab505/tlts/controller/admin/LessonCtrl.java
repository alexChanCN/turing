package cn.edu.hdu.lab505.tlts.controller.admin;

import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.service.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by hhx on 2017/1/14.
 */
@Path("lesson")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LessonCtrl {
    @Autowired
    private ILessonService lessonService;

    public ILessonService getLessonService() {
        return lessonService;
    }

    @POST
    public void add(Lesson lesson) {
        getLessonService().insert(lesson);
    }
}
