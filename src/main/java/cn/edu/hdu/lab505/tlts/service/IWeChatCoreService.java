package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.IWeChatService;

/**
 * Created by hhx on 2017/1/16.
 */
public interface IWeChatCoreService extends IWeChatService {
    String BASE_PATH = "www.renjiwulian.com/turing/index.html";
    String ANSWER_PATH = BASE_PATH + "#/answer";
    String CHECK_IN_PATH = BASE_PATH + "#/checkin";
    String FEEDBACK_CONTENT_PATH = BASE_PATH + "#/feedback/content";
    String FEEDBACK_EDIT_PATH = BASE_PATH + "#/feedback/edit";
    String QUIZ_LIST_PATH = BASE_PATH + "#quiz/list";
    String STUDENT_LIST_PATH = BASE_PATH + "#/student/list";
    String TASK_LIST_PATH = BASE_PATH + "#/task";
    String SUMMARY_PATH = "www.renjiwulian.com/turing/admin/summary";
    String SLOGAN = "让教育走在时尚前沿，让德育与智育并重。（杭电计算机科学导引）\n第一次关注请输入学号姓名进行绑定,如:12345678某某";
    String SUBSCRIBE = "/呲牙欢迎关注图灵徒孙微信公众号，第一次关注请输入学号姓名进行绑定,如:12345678张三";
    String PUNCH = "punch";
    String ANSWER = "answer";
    String STUDENT = "student";
    String FEEDBACK = "feedback";
    String CHECK_IN = "checkin";
    String QUIZ = "quiz";
    String START_PUNCH = "start_punch";
    String TASK = "task";
    String FEEDBACK_CONTENT = "feedback_content";
    String SUMMARY = "summary";

    String subscribe();
}
