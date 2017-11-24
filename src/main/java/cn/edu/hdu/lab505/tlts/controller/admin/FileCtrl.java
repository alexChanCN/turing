package cn.edu.hdu.lab505.tlts.controller.admin;

import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.domain.Upload;
import cn.edu.hdu.lab505.tlts.service.IStudentService;
import cn.edu.hdu.lab505.tlts.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * Created by ShineChan on 2017/11/23.
 */
@Path("/file")
@Produces(MediaType.APPLICATION_JSON)
public class FileCtrl {

    @Autowired
    IUploadService uploadService;
    @Autowired
    IStudentService studentService;
    @GET
    @Path("list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Upload> listAllByStudent(@PathParam("id")Long id){
        Student student = studentService.get(id);
        return uploadService.listAll(student);
    }

    @GET
    @Path("newest/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Upload getOneByStudent(@PathParam("id")Long id){
        Student student = studentService.get(id);
        List<Upload> list = uploadService.listAll(student);
        int size = list.size();
        return list.get(size-1);
    }

    /*private static final String filepath = "E:/circulation-checking-rest/src/resources/download/test1.uml";
    private static final String serverLocation = "E:/circulation-checking-rest/src/resources/upload/";
    @GET
    @Path("download")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile() {

        File file = new File(filepath);
        if (file.isFile() && file.exists()) {
            String mt = new MimetypesFileTypeMap().getContentType(file);
            String fileName = file.getName();

            return Response
                    .ok(file, mt)
                    .header("Content-disposition",
                            "attachment;filename=" + fileName)
                    .header("ragma", "No-cache")
                    .header("Cache-Control", "no-cache").build();

        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("下载失败，未找到该文件").build();
        }
    }*/

}
