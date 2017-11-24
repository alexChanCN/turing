package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.bean.ExcelBean;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by hhx on 2017/1/15.
 */
public interface ISummaryService {

    ExcelBean<Integer> makePunchBean();

    ExcelBean<Integer> makeQuizBean();

    ExcelBean<String> makeHomeworkBean();

    Workbook makeSummary();
}
