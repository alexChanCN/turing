package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.bean.message.resp.Article;
import cn.edu.hdu.lab505.tlts.common.AbstractWeChatService;
import cn.edu.hdu.lab505.tlts.common.AppException;
import cn.edu.hdu.lab505.tlts.domain.Admin;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.util.MessageUtil;
import cn.edu.hdu.lab505.tlts.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hhx on 2017/1/16.
 */
@Service
@Scope("prototype")
public class WeChatCoreService extends AbstractWeChatService implements IWeChatCoreService {
    private final static Logger LOGGER = Logger.getLogger(WeChatCoreService.class);
    @Autowired
    private IPunchService punchService;
    @Autowired
    private IStudentService studentService;
    @Autowired
    private IAdminService adminService;

    public IStudentService getStudentService() {
        return studentService;
    }

    public IAdminService getAdminService() {
        return adminService;
    }

    public IPunchService getPunchService() {
        return punchService;
    }


    /**
     * 关注事件
     *
     * @return
     */
    @Override
    public String subscribe() {
        return respTextMessage(SUBSCRIBE);
    }

    private String slogan() {
        return respTextMessage(SLOGAN);
    }

    private String summary() {
        return respTextMessage(additionOpenId(SUMMARY_PATH));
    }

    private String punch() {
        String msg = "";
        try {
            msg = getPunchService().studentPunch(fromUserName);
        } catch (AppException e) {
            msg = e.getMessage();
        }
        return respTextMessage(msg);
    }

    private String studentBind(Long no, String name, String weChatId) {
        String msg = "";
        Student student = new Student(no, name);
        student.setWeChatId(weChatId);
        try {
            msg = getStudentService().bind(student);
        } catch (AppException e) {
            msg = e.getMessage();
        }
        return respTextMessage(msg);
    }

    private String adminBind(Long no, String name, String weChatId) {
        Admin admin = new Admin(name, no, weChatId);
        String msg = "";
        try {
            msg = getAdminService().bind(admin);
        } catch (AppException e) {
            msg = e.getMessage();
        }
        return respTextMessage(msg);
    }

    private String startPunch() {
        String msg = "";
        try {
            msg = getPunchService().letPunch();
        } catch (AppException e) {
            msg = e.getMessage();
        }
        return respTextMessage(msg);
    }

    private String singleArticle(String title, String des, String picPath, String path) {
        List<Article> list = new ArrayList<>();
        Article article = new Article(title, des, picPath, path);
        list.add(article);
        return respNewsMessage(list, 1);
    }

    private String additionOpenId(String path) {
        return String.valueOf(path) + "?openid=" + fromUserName + "&timestamp=" + System.currentTimeMillis();
    }

    /**
     * 课堂提问图文消息
     *
     * @return
     */
    private String quiz() {
        return singleArticle("提问", "对学生进行提问和打分", null, additionOpenId(QUIZ_LIST_PATH));
    }

    private String showCheckIn() {
        return singleArticle("点名", "查看学生最近三周签到情况", null, additionOpenId(CHECK_IN_PATH));
    }

    /**
     * 发布和查看作业
     *
     * @return
     */
    private String task() {
        return singleArticle("作业", "发布和查看作业", null, additionOpenId(TASK_LIST_PATH));
    }

    /**
     * 学生提交作业
     *
     * @return
     */
    private String answer() {
        return singleArticle("提交作业", "", null, additionOpenId(ANSWER_PATH));
    }

    private String manageStudent() {
        return singleArticle("学生管理", "", null, additionOpenId(STUDENT_LIST_PATH));
    }

    private String showFeedback() {
        return singleArticle("反馈查看", "", null, additionOpenId(FEEDBACK_CONTENT_PATH));
    }

    private String feedback() {
        return singleArticle("问题反馈", "", null, additionOpenId(FEEDBACK_EDIT_PATH));
    }

    private Object getEntity(String weChatId) {
        Admin admin = getAdminService().getByWeChatId(weChatId);
        if (admin != null) {
            return admin;
        } else {
            Student student = getStudentService().getByWeChatId(weChatId);
            return (student != null) ? student : null;
        }
    }

    @Override
    public String process() {
        String msg = "";
        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
            if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                // 订阅事件
                msg = subscribe();

            } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                Object entity = getEntity(fromUserName);
                if (entity == null) {
                    msg = slogan();
                } else if (entity instanceof Admin) {
                    if (eventKey.equals(PUNCH) || eventKey.equals(ANSWER)) {
                        msg = respTextMessage("对不起，您无法使用该功能！");
                    } else {
                        if (eventKey.equals(QUIZ)) {
                            msg = quiz();
                        } else if (eventKey.equals(CHECK_IN)) {
                            msg = showCheckIn();
                        } else if (eventKey.equals(START_PUNCH)) {
                            msg = startPunch();
                        } else if (eventKey.equals(STUDENT)) {
                            msg = manageStudent();
                        } else if (eventKey.equals(TASK)) {
                            msg = task();
                        } else if (eventKey.equals(SUMMARY)) {
                            msg = summary();
                        } else if (eventKey.equals(FEEDBACK_CONTENT)) {
                            msg = showFeedback();
                        } else if (entity.equals(FEEDBACK)) {
                            msg = feedback();
                        }
                    }
                } else if (entity instanceof Student) {
                    if (eventKey.equals(PUNCH)) {
                        msg = punch();
                    } else if (eventKey.equals(ANSWER)) {
                        msg = answer();
                    } else if (eventKey.equals(FEEDBACK)) {
                        msg = feedback();
                    } else {
                        msg = respTextMessage("对不起，您无法使用该功能！");
                    }
                }

            }
        } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            boolean isAdminBind = false;
            if (content.startsWith("@")) {
                //管理员绑定标记
                isAdminBind = true;
                content = content.substring(1, content.length());
            }
            String no = StringUtil.getNumbers(content);
            String name = StringUtil.getWords(content);
            if (!StringUtils.isEmpty(no) && !StringUtils.isEmpty(name)) {
                if (isAdminBind) {
                    msg = adminBind(Long.valueOf(no), name, fromUserName);
                } else {
                    msg = studentBind(Long.valueOf(no), name, fromUserName);
                }
            } else {
                msg = slogan();
            }

        }
        return String.valueOf(msg);
    }
}
