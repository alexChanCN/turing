package cn.edu.hdu.lab505.tlts.common;

import cn.edu.hdu.lab505.tlts.bean.message.resp.Article;
import cn.edu.hdu.lab505.tlts.bean.message.resp.NewsMessage;
import cn.edu.hdu.lab505.tlts.bean.message.resp.TextMessage;
import cn.edu.hdu.lab505.tlts.util.MessageUtil;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by hhx on 2017/1/16.
 */
public abstract class AbstractWeChatService implements IWeChatService {
    protected String fromUserName;
    protected String toUserName;
    protected long createTime;
    protected String msgType;
    protected long msgId;
    protected String content;
    protected String eventKey;
    protected String eventType;

    @Override
    public String getReturnValue() {
        return process();
    }

    @Override
    public void init(InputStream inputStream) throws Exception {
        Map<String, String> map = MessageUtil.parseXML(inputStream);
        fromUserName = map.get("FromUserName");
        toUserName = map.get("ToUserName");
        msgType = map.get("MsgType");
        eventKey = map.get("EventKey");
        eventType = map.get("Event");
        content = map.get("Content");
        msgId = Long.valueOf(map.get("MsgId"));
        createTime = Long.valueOf(map.get("CreateTime"));
        if (inputStream != null) {
            inputStream.close();
        }
    }

    /**
     * 回复图文消息，不超过10条
     *
     * @param articles
     * @param articleCount
     * @return
     */
    protected final String respNewsMessage(List<Article> articles, int articleCount) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setArticleCount(articleCount);
        newsMessage.setArticles(articles);
        newsMessage.setCreateTime(System.currentTimeMillis() / 1000);
        newsMessage.setFromUserName(toUserName);
        // newsMessage.setFuncFlag(0);
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setToUserName(fromUserName);
        return MessageUtil.newsMessageToXml(newsMessage);

    }

    /**
     * 回复文本消息
     *
     * @param
     * @return
     */
    protected final String respTextMessage(String content) {
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(fromUserName);
        textMessage.setContent(content);
        textMessage.setCreateTime(System.currentTimeMillis() / 1000);
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        // textMessage.setFuncFlag(0);
        return MessageUtil.textMessageToXml(textMessage);

    }
}
