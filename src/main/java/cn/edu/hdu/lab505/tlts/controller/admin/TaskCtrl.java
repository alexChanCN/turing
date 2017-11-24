package cn.edu.hdu.lab505.tlts.controller.admin;

import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.domain.Task;
import cn.edu.hdu.lab505.tlts.service.ITaskService;
import org.apache.poi.ss.formula.functions.T;
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

    @POST
    public void add(Task task) throws AppException {
        getTaskService().add(task);
    }

    @DELETE
    @Path("{id:\\d+}")
    public void delete(@PathParam("id") long id) {
        getTaskService().deleteById(id);
    }

    @PUT
    public void update(Task task) {
        getTaskService().saveOrUpdate(task);
    }

    @GET
    public List<Task> getDefaultLessonTask(){
        return getTaskService().findDefaultLessonTask();
    }
}
