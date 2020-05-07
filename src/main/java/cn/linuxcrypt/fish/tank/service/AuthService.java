package cn.linuxcrypt.fish.tank.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthService {
    private final int expireMin = 30;

    private final static Map<String, Long> map = new HashMap<>();

    public Long update(String key) {
        long expire = LocalDateTime.now().atZone(ZoneId.of("+8")).plusMinutes(expireMin).toInstant().toEpochMilli();
        map.put(key, expire);
        return expire;
    }

    /**
     * 大于0 代表不过期
     * -1 代表不存在
     *
     * @param key
     * @return
     */
    public Long expire(String key) {
        if (!map.containsKey(key)) {
            return -1L;
        }
        long current = LocalDateTime.now().atZone(ZoneId.of("+8")).toInstant().toEpochMilli();
        long value = map.get(key) - current;
        if (value < 0) {
            map.remove(key);
        }
        return value;
    }

    @Getter
    @Setter
    @Value("${common.auth.username}")
    private String name;

    @Getter
    @Setter
    @Value("${common.auth.password}")
    private String pwd;

    public boolean login(String username, String password) {
        if(name.equals(username) && pwd.equals(password)){
            return true;
        }
        return false;
    }
}
