package cn.edu.hdu.lab505.tlts.util;

import cn.edu.hdu.lab505.tlts.bean.ExcelBean;
import cn.edu.hdu.lab505.tlts.domain.Student;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/11.
 */
public class ExcelSupport {
    private Workbook workbook;

    public List<String[]> readDataForHSSF(InputStream is) throws IOException {
        workbook = new HSSFWorkbook(is);
        return readData(0);
    }

    public List<String[]> readDataForXSSF(InputStream is) throws IOException {
        workbook = new XSSFWorkbook(is);
        return readData(0);
    }

    private Sheet setHeaders(Sheet sheet, String[] headers) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
        return sheet;
    }

    private <T> void writeData(ExcelBean<T> excelBean, Sheet sheet) {
        List<Student> students = excelBean.getStudents();
        for (int i = 0; i < students.size(); i++) {
            T[] values = excelBean.getValues().get(i);
            Row row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(students.get(i).getStudentId());
            row.createCell(1).setCellValue(students.get(i).getName());
            for (int j = 0; j < values.length; j++) {
                T value = values[j];
                if (value instanceof Integer) {
                    row.createCell(2 + j).setCellValue((Integer) value);
                } else if (value instanceof String) {
                    row.createCell(2 + j).setCellValue((String) value);
                }
            }
        }
    }

    public Workbook makeSummary(ExcelBean<Integer> punchBean, ExcelBean<Integer> quizBean, ExcelBean<String> homeworkBean) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet1 = wb.createSheet("签到汇总");
        Sheet sheet2 = wb.createSheet("提问汇总");
        Sheet sheet3 = wb.createSheet("作业汇总");
        sheet1.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet1.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        sheet2.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet2.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        sheet3.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        sheet3.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        int mergedSize = punchBean.getDates().length;

        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 2, ((mergedSize > 0) ? mergedSize : 1) + 2));
        int length = 0;
        for (Integer[] integers : quizBean.getValues()) {
            length = Math.max(length, integers.length - 1);
        }
        sheet2.addMergedRegion(new CellRangeAddress(0, 1, 2, length + 2));
        length = 0;
        for (String[] strings : homeworkBean.getValues()) {
            length = Math.max(length, strings.length - 1);
        }
        sheet3.addMergedRegion(new CellRangeAddress(0, 1, 2, length + 2));
        sheet1 = setHeaders(sheet1, punchBean.getHeaders());
        Row dateRow = sheet1.createRow(1);
        for (int i = 0; i < punchBean.getDates().length; i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String s = simpleDateFormat.format(punchBean.getDates()[i]);
            dateRow.createCell(i + 2).setCellValue(s);
        }
        writeData(punchBean, sheet1);
        sheet2 = setHeaders(sheet2, quizBean.getHeaders());
        writeData(quizBean, sheet2);
        sheet3 = setHeaders(sheet3, homeworkBean.getHeaders());
        writeData(homeworkBean, sheet3);
        return wb;
    }

    private List<String[]> readData(int sheetNo) {
        Sheet sheet1 = workbook.getSheetAt(sheetNo);
        DataFormatter formatter = new DataFormatter();
        List<String[]> list = new ArrayList<>();
        for (Row row : sheet1) {
            String[] strings = new String[2];
            int counter = 0;
            for (Cell cell : row) {
                String text = formatter.formatCellValue(cell);
                strings[counter++] = text;
                if (counter > 1) {
                    break;
                }

            }
            list.add(strings);
        }
        return list;
    }

}
