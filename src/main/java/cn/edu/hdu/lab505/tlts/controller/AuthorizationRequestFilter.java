package cn.edu.hdu.lab505.tlts.controller;

import cn.edu.hdu.lab505.tlts.domain.Admin;
import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.service.IAdminService;
import cn.edu.hdu.lab505.tlts.service.IStudentService;
import com.microsoft.schemas.office.x2006.encryption.CTKeyEncryptor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.FailedLoginException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by hhx on 2017/1/15.
 */
@Provider
public class AuthorizationRequestFilter implements ContainerRequestFilter {
    private final static String HEADER_TOKEN = "token";
    private final static String ADMIN_PATH = "admin";
    private final static String USER_PATH = "restapi";
    private final static String SUMMARY_PATH = "summary";
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IStudentService studentService;

    protected IAdminService getAdminService() {
        return adminService;
    }

    protected IStudentService getStudentService() {
        return studentService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String token = requestContext.getHeaderString(HEADER_TOKEN);
        UriInfo uriInfo = requestContext.getUriInfo();
        String path = uriInfo.getRequestUri().getPath();
        boolean allow = true;
        if (path.contains(ADMIN_PATH)) {
            if (!StringUtils.isEmpty(token)) {
                Admin admin = getAdminService().getByWeChatId(token);
                if (admin == null) {
                    allow = false;
                }
            }
            if (path.contains(SUMMARY_PATH)) {
                MultivaluedMap<String, String> map = uriInfo.getQueryParameters();
                List<String> s = map.get("openid");
                if (s != null && !s.isEmpty()) {
                    Admin admin = getAdminService().getByWeChatId(s.get(0));
                    if (admin != null) {
                        allow = true;
                    }
                }
            }

        } else if (path.contains(USER_PATH)) {
            if (!StringUtils.isEmpty(token)) {
                Student student = getStudentService().getByWeChatId(token);
                if (student != null) {
                    allow = true;
                }
            }
        }
        if (allow == false) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User cannot access the resource.").build());
        }

    }
}
