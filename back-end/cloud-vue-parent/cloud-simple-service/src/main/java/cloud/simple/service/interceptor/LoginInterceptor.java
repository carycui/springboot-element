package cloud.simple.service.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cloud.simple.service.contants.Constant;
import cloud.simple.service.domain.SysAdminUserService;
import cloud.simple.service.model.SysAdminUser;
import cloud.simple.service.util.EncryptUtil;
import cloud.simple.service.util.FastJsonUtils;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SysAdminUserService sysAdminUserService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info("请求url:{}", request.getRequestURI());
        String authKey = request.getHeader(Constant.AUTH_KEY);
        logger.info("header auth key:{}", authKey);
        String sessionId = request.getHeader(Constant.SESSION_ID);
        HttpSession session = request.getSession();
        // 校验sessionid和authKey
        if (StringUtils.isEmpty(authKey) || StringUtils.isEmpty(sessionId)) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            String msg = FastJsonUtils.resultError(-100, "authKey或sessionId不能为空！", null);
            logger.info(msg);
            writer.write(msg);
            writer.flush();
            return false;
        }

        //检查账号有效性
       SysAdminUser sessionAdminUser = (SysAdminUser) session.getAttribute(Constant.LOGIN_ADMIN_USER);
        if (sessionAdminUser == null) {
            String decryptAuthKey = EncryptUtil.decryptBase64(authKey, Constant.SECRET_KEY);
            String[] auths = decryptAuthKey.split("\\|");
            String username = auths[0];
            String password = auths[1];
            SysAdminUser record = new SysAdminUser();
            record.setUsername(username);
            record.setPassword(password);
            sessionAdminUser = sysAdminUserService.selectOne(record);
            //设置登录用户id
            session.setAttribute(Constant.LOGIN_ADMIN_USER, sessionAdminUser);
        }

        if (sessionAdminUser == null || sessionAdminUser.getStatus().equals(0)) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(FastJsonUtils.resultError(-101, "账号已被删除或禁用", null));
            writer.flush();
            return false;
        }

        return true;
    }
}
