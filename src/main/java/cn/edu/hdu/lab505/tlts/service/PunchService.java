package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.dao.IAttendanceDao;
import cn.edu.hdu.lab505.tlts.dao.IPunchDao;
import cn.edu.hdu.lab505.tlts.dao.IStudentDao;
import cn.edu.hdu.lab505.tlts.domain.Attendance;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Punch;
import cn.edu.hdu.lab505.tlts.domain.Student;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Service
public class PunchService extends AbstractCurdServiceSupport<Punch> implements IPunchService {
    @Autowired
    private IPunchDao punchDao;
    @Autowired
    private IStudentDao studentDao;
    @Autowired
    private IAttendanceDao attendanceDao;
    @Autowired
    private ILessonService lessonService;


    @Autowired
    private Cache punchTimeCache;

    private final static String punch_time_key = "punch";

    private final static long allow_time = 3 * 60 * 1000;

    private Cache getPunchTimeCache() {
        return punchTimeCache;
    }

    public IAttendanceDao getAttendanceDao() {
        return attendanceDao;
    }

    public ILessonService getLessonService() {
        return lessonService;
    }

    @Override
    protected ICurdDaoSupport<Punch> getCurdDao() {
        return punchDao;
    }

    protected IStudentDao getStudentDao() {
        return studentDao;
    }

    @Transactional
    private void punch(Student student, Date date) throws AppException {
        Punch origin = ((IPunchDao) getCurdDao()).getByStudentAndDate(student, date);
        if (origin != null) {
            throw new AppException("无需重复签到！");
        }
        Punch punch = new Punch();
        punch.setDate(date);
        punch.setStudent(student);
        ((IPunchDao) getCurdDao()).insert(punch);
    }

    @Override
    public String studentPunch(Student student) throws AppException {
        long timestamp = System.currentTimeMillis();
        Element element = getPunchTimeCache().get(punch_time_key);
        if (element != null) {
            long punchTime = (long) element.getObjectValue();
            if ((timestamp - punchTime) <= allow_time) {
                punch(student, new Date());
                return String.valueOf(student.getName() + "同学,你已签到成功!");
            } else {
                throw new AppException("只能在规定的时间内签到。");
            }
        } else {
            throw new AppException("只能在规定的时间内签到。");
        }

    }

    @Override
    @Transactional
    public String studentPunch(String weChatId) throws AppException {
        Student student = getStudentDao().getByWeChatId(weChatId);
        if (student == null) {
            throw new AppException("您尚未绑定,请输入学号姓名进行绑定，如：123456某某");
        }
        return String.valueOf(studentPunch(student));
    }

    @Override
    @Transactional
    public void studentPunch(Student student, Date date) throws AppException {
        punch(student, date);
    }

    @Override
    @Transactional()
    public String letPunch() throws AppException {
        Lesson lesson = getLessonService().getDefaultLesson();
        if (lesson == null) {
            throw new AppException("目前没有任何班级");
        }
        long punchTime = System.currentTimeMillis();
        getPunchTimeCache().put(new Element(punch_time_key, punchTime));
        Date date = new Date();
        Attendance attendance = getAttendanceDao().getByLessonAndDate(lesson, date);
        if (attendance == null) {
            attendance = new Attendance();
            attendance.setDate(date);
            attendance.setLesson(lesson);
            getAttendanceDao().insert(attendance);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String s = simpleDateFormat.format(date);
        return "开始签到时间为：" + s + ",三分钟内允许签到。";
    }

    @Override
    @Transactional(readOnly = true)
    public List<Punch> findByLesson(Lesson lesson) {
        List<Attendance> attendanceList = getAttendanceDao().findByLesson(lesson);
        if (attendanceList.isEmpty()) {
            return new ArrayList<>();
        }
        Date start = attendanceList.get(attendanceList.size() - 1).getDate();
        Date end = attendanceList.get(0).getDate();
        List<Punch> punches = ((IPunchDao) getCurdDao()).findByDate(start, end);
        return punches;
    }

    @Override
    public List<Punch> findDefaultLessonPunch() {
        Lesson lesson = getLessonService().getDefaultLesson();
        return findByLesson(lesson);
    }

    @Override
    @Transactional
    public int deleteByStudentAndDate(Long sid, Date date) {
        Student student = new Student();
        student.setId(sid);
        return ((IPunchDao) getCurdDao()).deleteByStudentAndDate(student, date);
    }
}
