package cn.edu.hdu.lab505.tlts.controller.admin;

import cn.edu.hdu.lab505.tlts.domain.Feedback;
import cn.edu.hdu.lab505.tlts.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by hhx on 2017/1/12.
 */
@Path("feedback")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedbackCtrl {
    @Autowired
    private IFeedbackService feedbackService;

    public IFeedbackService getFeedbackService() {
        return feedbackService;
    }

    @GET
    public List<Feedback> findAll() {
        return getFeedbackService().findAll();
    }

    @DELETE
    @Path("{id:\\d+}")
    public void delete(@PathParam("id") Long id) {
        getFeedbackService().deleteById(id);
    }

}
