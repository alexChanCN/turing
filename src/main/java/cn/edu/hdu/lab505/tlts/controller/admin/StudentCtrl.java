package cn.edu.hdu.lab505.tlts.controller.admin;

import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.service.IStudentService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.List;

/**
 * Created by hhx on 2017/1/12.
 */
@Path("student")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentCtrl {
    @Autowired
    private IStudentService studentService;

    public IStudentService getStudentService() {
        return studentService;
    }

    @GET
    public List<Student> findDefaultLessonStudent() {
        return getStudentService().findDefaultLessonStudent();
    }

    @POST
    public void add(Student student) {
        getStudentService().insert(student);
    }

    @DELETE
    @Path("{id:\\d+}")
    public void delete(@PathParam("id") long id) {
        getStudentService().deleteById(id);
    }

    @GET
    @Path("quiz")
    public List<Student> findStudentForQuiz() {
        return getStudentService().findDefaultLessonPunchDateStudent();
    }

    @POST
    @Path("excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void excelUpload(@FormDataParam("file") InputStream is,
                            @FormDataParam("file") FormDataContentDisposition fileDetail) throws AppException {
        String fileName=fileDetail.getFileName();
        String fileType="";
        if(fileName.endsWith("xls")){
            fileType="xls";
        }else if(fileName.endsWith("xlsx")){
            fileType="xlsx";
        }else {
            throw new AppException("文件类型错误");
        }
        getStudentService().batchInsertFromExcelToDefaultLesson(is,fileType);
    }
}
