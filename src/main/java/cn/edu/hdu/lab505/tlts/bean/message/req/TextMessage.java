package cn.edu.hdu.lab505.tlts.bean.message.req;

import cn.edu.hdu.lab505.tlts.bean.message.req.BaseMessage;

public class TextMessage extends BaseMessage {
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}