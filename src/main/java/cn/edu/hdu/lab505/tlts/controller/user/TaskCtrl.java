package cn.edu.hdu.lab505.tlts.controller.user;

import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.domain.Task;
import cn.edu.hdu.lab505.tlts.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by hhx on 2017/1/12.
 */
@Path("task")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskCtrl {
    @Autowired
    private ITaskService taskService;

    public ITaskService getTaskService() {
        return taskService;
    }

    @GET
    @Path("undone")
    public List<Task> findDefaultLessonTask(@QueryParam("openid")String openid) {
        return getTaskService().findUndoneTask(openid);
    }
}
