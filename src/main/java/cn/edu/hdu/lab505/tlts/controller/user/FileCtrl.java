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
        String prefix = new String(fileName.substring(0,fileName.lastIndexOf(".")).getBytes("ISO-8859-1"),"utf-8");
        System.out.println(prefix);
        String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
        Date date = new Date();
        Long dateString = date.getTime();
        String newFileName = prefix + "_" + dateString + suffix;
        //String path  = request.getContextPath();
        String url = request.getServletContext().getRealPath("/") ;
        File file = new File(url + "/file/" + newFileName);
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

        Upload upload = new Upload();
        upload.setDatetime(date);
        upload.setFileName(newFileName);
        upload.setStudent(student);
        uploadService.save(upload);
        Map<String,String> message= new HashMap<>();
        message.put("fileName",newFileName);
        return message;
    }
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Upload> listAllByStudent(@HeaderParam("token")String openid){
        Student student = studentService.getByWeChatId(openid);
        return uploadService.listAll(student);
    }

    @GET
    @Path("newest")
    @Produces(MediaType.APPLICATION_JSON)
    public Upload getOneByStudent(@HeaderParam("token")String openid){
        Student student = studentService.getByWeChatId(openid);
        List<Upload> list = uploadService.listAll(student);
        int size = list.size();
        return list.get(size-1);
    }

}
