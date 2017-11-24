package cn.edu.hdu.lab505.tlts.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by hhx on 2017/1/11.
 */
public class ExcelHelperTest {
    String excel = "test1.xls";

    @Test
    public void testExcel() {
        try {
            URL url = ExcelHelperTest.class.getClassLoader().getResource(excel);
            InputStream inputStream = new FileInputStream(url.toURI().getPath());
            ExcelSupport excelHelper = new ExcelSupport();
            List<String[]> list = excelHelper.readDataForHSSF(inputStream);
            for (String[] strings : list) {
                for (String s : strings) {
                    System.out.print(s);
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test() throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet1 = wb.createSheet("new sheet");
        Sheet sheet2 = wb.createSheet("second sheet");
        Sheet sheet3 = wb.createSheet("third sheet");
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 2, 5));
        Row row = sheet1.createRow((short) 0);
        Cell cell = row.createCell(0);
        Cell cell2 = row.createCell(1);
        Cell cell3 = row.createCell(2);
        cell.setCellValue(1);
        cell2.setCellValue(1);
        cell3.setCellValue(433);
        CellStyle cellType=cell3.getCellStyle();
        cellType.setAlignment(HorizontalAlignment.CENTER);
        cell3.setCellStyle(cellType);
        FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
        wb.write(fileOut);
        fileOut.close();

    }
}
