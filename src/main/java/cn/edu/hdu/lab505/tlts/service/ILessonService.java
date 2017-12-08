package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.ICurdServiceSupport;
import cn.edu.hdu.lab505.tlts.domain.Lesson;

import java.util.List;

/**
 * Created by hhx on 2017/1/10.
 */
public interface ILessonService extends ICurdServiceSupport<Lesson> {

    Lesson getDefaultLesson();

    List<Lesson> getList();
}
