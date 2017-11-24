package cn.edu.hdu.lab505.tlts.controller.admin;

import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.domain.Attendance;
import cn.edu.hdu.lab505.tlts.domain.Punch;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.service.IAttendanceService;
import cn.edu.hdu.lab505.tlts.service.IPunchService;
import cn.edu.hdu.lab505.tlts.service.IStudentService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by hhx on 2017/1/12.
 */
@Path("punch")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PunchCtrl {
    private final static int significant_field = Calendar.DAY_OF_MONTH;
    @Autowired
    private IPunchService punchService;

    @Autowired
    private IStudentService studentService;
    @Autowired
    private IAttendanceService attendanceService;

    public IStudentService getStudentService() {
        return studentService;
    }

    public IAttendanceService getAttendanceService() {
        return attendanceService;
    }

    public IPunchService getPunchService() {
        return punchService;
    }

    @DELETE
    @Path("{id:\\d+}/{date}")
    public void delete(@PathParam("id") Long id, @PathParam("date") Long timestamp) {
        getPunchService().deleteByStudentAndDate(id, new Date(timestamp));
    }

    @POST
    public void add(Punch punch) throws AppException {
        getPunchService().studentPunch(punch.getStudent(), punch.getDate());
    }

    @GET
    @Path("view")
    public PunchView getPunchView() {
        List<Student> students = getStudentService().findDefaultLessonStudent();
        List<Punch> punches = getPunchService().findDefaultLessonPunch();
        List<Attendance> attendances = getAttendanceService().findByDefaultLesson();
        PunchView punchView = new PunchView();
        Date date0 = null;
        Date date1 = null;
        Date date2 = null;
        if (!attendances.isEmpty()) {
            date0 = attendances.get(0).getDate();
        }
        if (attendances.size() > 1) {
            date1 = attendances.get(1).getDate();
        }
        if (attendances.size() > 2) {
            date2 = attendances.get(2).getDate();
        }
        if (date0 == null && date1 == null && date2 == null) {
            return punchView;
        }
        Date[] dates = {date0, date1, date2};
        for (int i = 0; i < dates.length; i++) {
            if (dates[i] == null) {
                continue;
            }
            Date truncate = DateUtils.truncate(dates[i], significant_field);
            dates[i] = truncate;
        }
        punchView.setDates(dates);

        for (Student student : students) {
            int[] status = {-1, -1, -1};
            for (int i = 0; i < dates.length; i++) {
                boolean flag = false;
                if (dates[i] == null) {
                    continue;
                }
                for (Punch punch : punches) {
                    Date truncate = DateUtils.truncate(punch.getDate(), significant_field);
                    if (dates[i].compareTo(truncate) == 0 && student.getId() == punch.getStudent().getId()) {
                        status[i] = 1;
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    status[i] = 0;
                }
            }
            punchView.add(new Data(status, student));
        }
        return punchView;
    }

    public class Data {
        int[] status;
        Student student;

        public Data(int[] status, Student student) {
            this.status = status;
            this.student = student;
        }

        public int[] getStatus() {
            return status;
        }

        public void setStatus(int[] status) {
            this.status = status;
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }
    }

    public class PunchView {
        Date[] dates;
        List<Data> list = new ArrayList<>();


        public void add(Data data) {
            list.add(data);
        }


        public Date[] getDates() {
            return dates;
        }

        public void setDates(Date[] dates) {
            this.dates = dates;
        }

        public List<Data> getList() {
            return list;
        }

        public void setList(List<Data> list) {
            this.list = list;
        }
    }
}
