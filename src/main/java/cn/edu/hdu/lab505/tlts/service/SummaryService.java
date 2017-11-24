package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.bean.ExcelBean;
import cn.edu.hdu.lab505.tlts.dao.IAnswerDao;
import cn.edu.hdu.lab505.tlts.dao.IQuizDao;
import cn.edu.hdu.lab505.tlts.domain.*;
import cn.edu.hdu.lab505.tlts.util.ExcelSupport;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/15.
 */
@Service
public class SummaryService implements ISummaryService {
    private final static int significant_field = Calendar.DAY_OF_MONTH;
    @Autowired
    private IPunchService punchService;

    @Autowired
    private IStudentService studentService;
    @Autowired
    private IAttendanceService attendanceService;
    @Autowired
    private IQuizDao quizDao;
    @Autowired
    private IAnswerDao answerDao;


    public IAnswerDao getAnswerDao() {
        return answerDao;
    }

    public IQuizDao getQuizDao() {
        return quizDao;
    }

    public IPunchService getPunchService() {
        return punchService;
    }

    public IStudentService getStudentService() {
        return studentService;
    }

    public IAttendanceService getAttendanceService() {
        return attendanceService;
    }

    @Override
    @Transactional(readOnly = true)
    public ExcelBean<Integer> makePunchBean() {
        List<Student> students = getStudentService().findDefaultLessonStudent();
        List<Punch> punches = getPunchService().findDefaultLessonPunch();
        List<Attendance> attendances = getAttendanceService().findByDefaultLesson();
        ExcelBean<Integer> excelBean = new ExcelBean<>();
        excelBean.setHeaders(new String[]{"学号", "姓名", "签到日期"});
        Date[] dates = new Date[attendances.size()];
        for (int i = 0; i < attendances.size(); i++) {
            Date truncate = DateUtils.truncate(attendances.get(i).getDate(), significant_field);
            dates[i] = truncate;
        }
        excelBean.setDates(dates);
        excelBean.setStudents(students);
        List<Integer[]> valuesList = new ArrayList<>(students.size());
        for (Student student : students) {
            Integer[] values = new Integer[dates.length];
            for (int i = 0; i < values.length; i++) {
                values[i] = 0;
            }
            for (int i = 0; i < dates.length; i++) {
                for (Punch punch : punches) {
                    Date truncate = DateUtils.truncate(punch.getDate(), significant_field);
                    if (dates[i].compareTo(truncate) == 0 && student.getId() == punch.getStudent().getId()) {
                        values[i] = 1;
                        break;
                    }
                }
            }
            valuesList.add(values);
        }
        excelBean.setValues(valuesList);
        return excelBean;
    }

    @Override
    @Transactional(readOnly = true)
    public ExcelBean<Integer> makeQuizBean() {
        List<Student> students = getStudentService().findDefaultLessonStudent();
        Lesson lesson = students.get(0).getLesson();
        ExcelBean<Integer> excelBean = new ExcelBean<>();
        excelBean.setStudents(students);
        excelBean.setHeaders(new String[]{"学号", "姓名", "课堂成绩"});
        List<Quiz> quizzes = getQuizDao().findByLesson(lesson);
        List<Integer[]> valuesList = new ArrayList<>(students.size());
        for (Student student : students) {
            List<Integer> values = new ArrayList<>();
            for (Quiz quiz : quizzes) {
                if (quiz.getStudent().getId() == student.getId()) {
                    values.add(quiz.getScore());
                }
            }
            valuesList.add(values.toArray(new Integer[values.size()]));
        }
        excelBean.setValues(valuesList);
        return excelBean;
    }

    @Override
    @Transactional(readOnly = true)
    public ExcelBean<String> makeHomeworkBean() {
        List<Student> students = getStudentService().findDefaultLessonStudent();
        Lesson lesson = students.get(0).getLesson();
        List<Answer> answers = getAnswerDao().findByLesson(lesson);
        ExcelBean<String> excelBean = new ExcelBean<>();
        excelBean.setStudents(students);
        excelBean.setHeaders(new String[]{"学号", "姓名", "作业"});
        List<String[]> valuesList = new ArrayList<>(students.size());
        for (Student student : students) {
            List<String> values = new ArrayList<>();
            for (Answer answer : answers) {
                if (answer.getStudent().getId() == student.getId()) {
                    values.add(answer.getContent());
                }
            }
            valuesList.add(values.toArray(new String[values.size()]));
        }
        excelBean.setValues(valuesList);
        return excelBean;
    }

    @Override
    @Transactional(readOnly = true)
    public Workbook makeSummary() {
        ExcelBean<Integer> punchBean = makePunchBean();
        ExcelBean<Integer> quizBean = makeQuizBean();
        ExcelBean<String> homeworkBean = makeHomeworkBean();
        return new ExcelSupport().makeSummary(punchBean, quizBean, homeworkBean);
    }
}
