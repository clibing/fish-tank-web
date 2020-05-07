package cn.linuxcrypt.fish.tank.support;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static String getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static Cookie setCookie(HttpServletResponse response, String name, String value, Boolean secure) {
        return setCookie(response, name, value, 1800, null );
    }

    public static Cookie setCookie(HttpServletResponse response, String name, String value, Integer ageSeconds, Boolean secure) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(ageSeconds);
        // 开启后， 只允许通过https获取cookie。
        if (Boolean.TRUE.equals(secure)) {
            cookie.setSecure(true);
        }
        cookie.setPath("/");
        response.addCookie(cookie);
        return cookie;
    }

    public static void removeCookie(HttpServletResponse response, String name, Boolean secure) {
        setCookie(response, name, "0", 0, secure);
    }
}
