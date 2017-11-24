package cn.edu.hdu.lab505.tlts.bean.message.req;

import cn.edu.hdu.lab505.tlts.bean.message.req.BaseMessage;

public class VoiceMessage extends BaseMessage {
    private String MediaId;
    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}