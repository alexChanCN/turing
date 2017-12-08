package cn.edu.hdu.lab505.tlts.controller.user;

import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.domain.Upload;
import cn.edu.hdu.lab505.tlts.service.IStudentService;
import cn.edu.hdu.lab505.tlts.service.IUploadService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ShineChan on 2017/11/23.
 */
@Path("/file")
@Produces(MediaType.APPLICATION_JSON)
public class FileCtrl {
    //private final String filePath = "C:/upload/";

    @Autowired
    IUploadService uploadService;
    @Autowired
    IStudentService studentService;

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Map<String,String> upload(@FormDataParam("file") InputStream fileInputStream,
                                     @FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
                                     @HeaderParam("token")String openid,
                                     @Context HttpServletRequest request) throws IOException {
        Student student = studentService.getByWeChatId(openid);
        String fileName = contentDispositionHeader.getFileName();
        fileName = new String(fileName.getBytes("ISO-8859-1"),"utf-8");
        /*String prefix = new String(fileName.substring(0,fileName.lastIndexOf(".")).getBytes("ISO-8859-1"),"utf-8");
        String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
        Date date = new Date();
        Long dateString = date.getTime();
        String newFileName = prefix + "_" + dateString + suffix;*/

        String url = request.getServletContext().getRealPath("/") ;
        File file = new File(url + "/file/" + fileName);
        System.out.println(file.getAbsolutePath());
        File parent = file.getParentFile();
        //判断目录是否存在，不在创建
        if(parent!=null&&!parent.exists()){
            parent.mkdirs();
        }
        file.createNewFile();
        OutputStream outputStream = new FileOutputStream(file);
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();

        Upload upload = uploadService.getOneByStudent(student);
        Date date = new Date();
        if(upload != null){//已存在上传记录
            upload.setDatetime(date);
            upload.setStatus(1);
            upload.setFileName(fileName);
        }else {
            upload.setDatetime(date);
            upload.setFileName(fileName);
            upload.setStudent(student);
            upload.setLessonId(student.getLesson().getId());
        }
        uploadService.saveOrUpdate(upload);
        Map<String,String> message= new HashMap<>();
        message.put("fileName",fileName);
        return message;
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Upload getByStudent(@HeaderParam("token")String openid){
        Student student = studentService.getByWeChatId(openid);
        return uploadService.getOneByStudent(student);
    }

   /* @GET
    @Path("newest")
    @Produces(MediaType.APPLICATION_JSON)
    public Upload getOneByStudent(@HeaderParam("token")String openid){
        Student student = studentService.getByWeChatId(openid);
        List<Upload> list = uploadService.getOneByStudent(student);
       *//* int size = list.size();
        return list.get(size-1);*//*
       if (list.size() > 0)
           return list.get(0);
       else
           return null;
    }*/

}
