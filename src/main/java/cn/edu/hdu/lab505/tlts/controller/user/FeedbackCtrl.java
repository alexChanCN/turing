package cn.edu.hdu.lab505.tlts.controller.user;

import cn.edu.hdu.lab505.tlts.domain.Feedback;
import cn.edu.hdu.lab505.tlts.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

    @POST
    public void add(Feedback feedback) {
        getFeedbackService().insert(feedback);
    }
}
