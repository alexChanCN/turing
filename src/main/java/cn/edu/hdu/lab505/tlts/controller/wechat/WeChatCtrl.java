package cn.edu.hdu.lab505.tlts.controller.wechat;

import cn.edu.hdu.lab505.tlts.service.IWeChatCoreService;
import cn.edu.hdu.lab505.tlts.util.SignUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;

/**
 * 后台与微信服务器交互控制器
 *
 * @author hhx
 */
@Path("/")
public class WeChatCtrl {

    @Autowired
    private IWeChatCoreService weChatCoreService;

    public IWeChatCoreService getWeChatCoreService() {
        return weChatCoreService;
    }

    private final static Logger LOGGER = Logger.getLogger(WeChatCtrl.class);

    /**
     * 接入
     *
     * @param signature
     * @param echostr
     * @param timestamp
     * @param nonce
     * @return
     */
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public String login(@QueryParam("signature") String signature, @QueryParam("echostr") String echostr,
                        @QueryParam("timestamp") String timestamp, @QueryParam("nonce") String nonce) {
        /**
         * 签名校验
         */
        boolean isLegal = SignUtil.checkSignature(signature, timestamp, nonce);
        if (!isLegal) {
            LOGGER.info("check signature fail");
            LOGGER.info("receive signature=" + signature);

            return String.valueOf(null);
        }

        return String.valueOf(echostr);

    }

    /**
     * @param request
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     * @throws UnsupportedEncodingException
     */
    @Produces(MediaType.APPLICATION_XML)
    @POST
    public String port(@Context HttpServletRequest request, @QueryParam("signature") String signature,
                       @QueryParam("timestamp") String timestamp, @QueryParam("nonce") String nonce)
            throws UnsupportedEncodingException {
        /**
         * 签名校验
         */

        boolean isLegal = SignUtil.checkSignature(signature, timestamp, nonce);
        if (!isLegal) {
            LOGGER.info("check signature fail");
            return String.valueOf(null);
        }

        try {
            getWeChatCoreService().init(request.getInputStream());
        } catch (Exception e) {
            LOGGER.error(e);
        }
        String result = getWeChatCoreService().getReturnValue();
        return String.valueOf(result);
    }
}
