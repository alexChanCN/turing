package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.dao.IAnswerDao;
import cn.edu.hdu.lab505.tlts.dao.ILessonDao;
import cn.edu.hdu.lab505.tlts.dao.IStudentDao;
import cn.edu.hdu.lab505.tlts.dao.ITaskDao;
import cn.edu.hdu.lab505.tlts.domain.Answer;
import cn.edu.hdu.lab505.tlts.domain.Lesson;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
@Service
public class TaskService extends AbstractCurdServiceSupport<Task> implements ITaskService {
    @Autowired
    private ITaskDao taskDao;
    @Autowired
    private ILessonDao lessonDao;
    @Autowired
    private IStudentDao studentDao;
    @Autowired
    private IAnswerDao answerDao;

    protected IAnswerDao getAnswerDao() {
        return answerDao;
    }

    protected IStudentDao getStudentDao() {
        return studentDao;
    }

    protected ILessonDao getLessonDao() {
        return lessonDao;
    }

    @Override
    protected ICurdDaoSupport<Task> getCurdDao() {
        return taskDao;
    }

    @Override
    @Transactional
    public List<Task> findDefaultLessonTask() {
        Lesson lesson = getLessonDao().getMaxIdLesson();
        return ((ITaskDao) getCurdDao()).findByLesson(lesson);
    }

    @Override
    @Transactional
    public void add(Task entity) throws AppException {
        Lesson lesson = getLessonDao().getMaxIdLesson();
        if (lesson == null) {
            throw new AppException("还没有添加班级");
        }
        entity.setLesson(lesson);
        insert(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findUndoneTask(Student student) {
        List<Task> tasks=((ITaskDao)getCurdDao()).findByLesson(student.getLesson());
        List<Answer> answers=getAnswerDao().findByStudent(student);
        List<Task> undone=new ArrayList<>();
        for(Task task:tasks){
            Long taskId=task.getId();
            boolean flag=false;
            for(Answer answer:answers){
                if(taskId==answer.getTask().getId()){
                    flag=true;
                    break;
                }
            }
            if(flag==false){
                undone.add(task);
            }
        }
        return undone;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findUndoneTask(String wechatId) {
      Student student=getStudentDao().getByWeChatId(wechatId);
      return findUndoneTask(student);
    }
}
