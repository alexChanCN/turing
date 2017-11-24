package cn.edu.hdu.lab505.tlts.common;

import java.io.InputStream;

public interface IWeChatService {
    public String getReturnValue();

    public void init(InputStream inputStream) throws Exception;

    public String process();
}
