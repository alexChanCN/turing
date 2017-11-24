package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.domain.Task;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface ITaskService extends ICurdServiceSupport<Task> {

    List<Task> findDefaultLessonTask();

    void add(Task task) throws AppException;

    List<Task> findUndoneTask(Student student);
    List<Task> findUndoneTask(String wechatId);
}
