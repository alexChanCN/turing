package cn.edu.hdu.lab505.tlts.controller.admin;

import cn.edu.hdu.lab505.tlts.service.ISummaryService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by hhx on 2017/1/15.
 */
@Path("summary")
@Produces("application/vnd.ms-excel;charset=utf-8")
public class SummaryCtrl {
    @Autowired
    private ISummaryService summaryService;

    public ISummaryService getSummaryService() {
        return summaryService;
    }

    @GET
    public Response download(@Context HttpServletResponse response) {
        Workbook wb = getSummaryService().makeSummary();
        String filename = "summary.xlsx";
        OutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filename);
            wb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        java.nio.file.Path path = Paths.get(filename);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response.ResponseBuilder responseBuilder = Response.ok(data);
        try {
            responseBuilder.header("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file = new File(filename);
        file.delete();
        Response r = responseBuilder.build();
        return r;

    }
}
