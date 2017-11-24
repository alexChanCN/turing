package cn.edu.hdu.lab505.tlts.controller.admin;

import cn.edu.hdu.lab505.tlts.domain.Upload;
import cn.edu.hdu.lab505.tlts.service.IUploadService;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    private final String filePath = "C:/upload/";
    @Autowired
    IUploadService uploadService;

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public @ResponseBody Map<String,String> upload(@FormDataParam("file") InputStream fileInputStream,
                                                   @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws IOException {

        String fileName = contentDispositionHeader.getFileName();
        String t=contentDispositionHeader.getName();
        //System.out.println(fileName+" "+t);
        //File file = new File("h:/upload/" + fileName);
        File file = new File(filePath + fileName);
        File parent = file.getParentFile();
        //判断目录是否存在，不在创建
        if(parent!=null&&!parent.exists()){
            parent.mkdirs();
        }
        file.createNewFile();
        OutputStream outpuStream = new FileOutputStream(file);
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = fileInputStream.read(bytes)) != -1) {
            outpuStream.write(bytes, 0, read);
        }
        outpuStream.flush();
        outpuStream.close();
        fileInputStream.close();
        Upload upload = new Upload();
        upload.setDatetime(new Date());
        upload.setFileName(fileName);
        //upload.setStudent();
        uploadService.save(upload);
        Map<String,String> message= new HashMap<>();
        message.put("fileName",fileName);
        return message;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Upload> listAll(){
       return uploadService.listAll();
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
