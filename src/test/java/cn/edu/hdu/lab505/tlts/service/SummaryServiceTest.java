package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.bean.ExcelBean;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hhx on 2017/1/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SummaryServiceTest {
    @Autowired
    private ISummaryService summaryService;

    @Test
    public void testMakePunchBean() {
        ExcelBean<Integer> result = summaryService.makePunchBean();
        System.out.println();
    }

    @Test
    public void testMakeQuizBean() {
        ExcelBean<Integer> result = summaryService.makeQuizBean();
        System.out.println();
    }

    @Test
    public void testMakeHomeworkBean() {
        ExcelBean<String> result = summaryService.makeHomeworkBean();
        System.out.println();
    }

    @Test
    public void testSpendTime() {
        summaryService.makePunchBean();
        summaryService.makeQuizBean();
        summaryService.makeHomeworkBean();
    }

    @Test
    public void testMakeSummary() throws IOException {
        Workbook wb = summaryService.makeSummary();
        FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
        wb.write(fileOut);
        fileOut.close();
    }
}
