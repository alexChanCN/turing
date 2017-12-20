package cn.edu.hdu.lab505.tlts.controller.admin;

import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.service.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.print.DocFlavor;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GET
    @Path("list")
    public Map<Long, String> getList() {
        List<Lesson> list = lessonService.getList();
        Map<Long, String> map = new HashMap<>();
        for (Lesson lesson : list) {
            Long id = lesson.getId();
            String name = lesson.getName();
            map.put(id, name);
        }
        return map;
    }

    @GET
    public Map<String, Object> get() {
        Lesson lesson = lessonService.getDefaultLesson();
        Map<String, Object> map = new HashMap<>();
        map.put("id", lesson.getId());
        map.put("name", lesson.getName());
        return map;
    }
}
