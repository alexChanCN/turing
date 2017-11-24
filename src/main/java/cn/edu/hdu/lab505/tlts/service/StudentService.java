package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.dao.ILessonDao;
import cn.edu.hdu.lab505.tlts.dao.IStudentDao;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.util.ExcelSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Service
public class StudentService extends AbstractCurdServiceSupport<Student> implements IStudentService {
    private final static Logger LOGGER = Logger.getLogger(StudentService.class);
    @Autowired
    private IStudentDao studentDao;
    @Autowired
    private ILessonDao lessonDao;

    public ILessonDao getLessonDao() {
        return lessonDao;
    }

    @Override
    protected ICurdDaoSupport<Student> getCurdDao() {
        return studentDao;
    }

    @Override
    @Transactional
    public void batchInsertFromExcel(InputStream inputStream, String fileType, Long lessonId) {
        ExcelSupport excelSupport = new ExcelSupport();
        List<String[]> data = null;
        try {
            if ("xls".equals(fileType)) {
                data = excelSupport.readDataForHSSF(inputStream);
            } else if ("xlsx".equals(fileType)) {
                data = excelSupport.readDataForXSSF(inputStream);
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
        List<Student> list = new ArrayList<>();
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        if (data != null) {
            for (String[] strings : data) {
                if (!StringUtils.isEmpty(strings[0]) && !StringUtils.isEmpty(strings[1])) {
                    Long studentId = Long.valueOf(strings[0]);
                    String name = strings[1];
                    Student student = new Student(studentId, name);
                    student.setLesson(lesson);
                    list.add(student);
                }

            }
            ((IStudentDao) getCurdDao()).batchInsert(list);
        }

    }

    @Override
    @Transactional
    public void batchInsertFromExcelToDefaultLesson(InputStream inputStream, String fileType) throws AppException {
        Lesson lesson = getLessonDao().getMaxIdLesson();
        if (lesson == null) {
            throw new AppException("还没有添加班级");
        }
        batchInsertFromExcel(inputStream, fileType, lesson.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByLesson(Lesson lesson) {
        return ((IStudentDao) getCurdDao()).findByLesson(lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findDefaultLessonStudent() {
        Lesson lesson = getLessonDao().getMaxIdLesson();
        return findByLesson(lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findDefaultLessonPunchDateStudent() {
        Lesson lesson = getLessonDao().getMaxIdLesson();
        return ((IStudentDao) getCurdDao()).findByPunchDateAndLesson(new Date(), lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getByWeChatId(String weChatId) {
        return ((IStudentDao) getCurdDao()).getByWeChatId(weChatId);
    }

    @Override
    @Transactional
    public String bind(Student student) throws AppException {
        Student origin = ((IStudentDao) getCurdDao()).getByStudentIdAndName(student.getStudentId(), student.getName());
        if (origin == null) {
            throw new AppException("绑定失败，找不到信息，请检查学号和姓名是否输入正确，如：123456某某");
        }
        if (!StringUtils.isEmpty(origin.getWeChatId())) {
            throw new AppException(origin.getName() + "同学，你已经绑定成功，无需重复绑定！");
        }
        origin.setWeChatId(student.getWeChatId());
        ((IStudentDao) getCurdDao()).update(origin);
        return origin.getName() + "同学，绑定成功!";
    }

    @Transactional
    @Override
    public void insert(Student entity) {
        Lesson lesson = getLessonDao().getMaxIdLesson();
        entity.setLesson(lesson);
        super.insert(entity);
    }
}
