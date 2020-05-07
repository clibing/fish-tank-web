package cn.linuxcrypt.fish.tank.support;

import cn.linuxcrypt.common.result.Result;
import cn.linuxcrypt.common.util.JsonUtil;
import cn.linuxcrypt.common.util.NetworkUtils;
import cn.linuxcrypt.fish.tank.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author clibing
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = CookieUtil.getCookie(request, "token");
        if (StringUtils.isBlank(token)) {
            json(response, Result.fail("非法用户"));
            return false;
        }

        Long expire = authService.expire(token);
        if (expire <= 0) {
            json(response, Result.fail("非法用户"));
            return false;
        }
        log.info("current token not expire, token: {}, expire: {}, from ip: {}", token, expire,
                NetworkUtils.getIpAddress(request));
        return true;
    }

    private void json(HttpServletResponse response, Result result){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer=null;
        try {
            writer=response.getWriter();
            writer.write(JsonUtil.toJson(result));
            writer.flush();
        } catch (Exception ex) {
        }finally {
            if(writer!=null) {
                writer.close();
            }
        }
    }
}
