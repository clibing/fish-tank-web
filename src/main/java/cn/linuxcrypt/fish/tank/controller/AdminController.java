package cn.linuxcrypt.fish.tank.controller;

import cn.linuxcrypt.common.result.Result;
import cn.linuxcrypt.common.service.IMessageHandler;
import cn.linuxcrypt.common.service.MqttSendService;
import cn.linuxcrypt.fish.tank.service.AuthService;
import cn.linuxcrypt.fish.tank.support.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author clibing
 */
@Slf4j
@Controller
public class AdminController {
    @Resource
    private AuthService authService;

    @Resource
    private IMessageHandler messageHandler;

    @Resource
    private MqttSendService mqttSendService;

    @GetMapping(value = {"/health"})
    public ResponseEntity health() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = {"/", "/index", "index.html"})
    public String running(Model model, Boolean ignore) {
        return "index";
    }

    @PostMapping(value = {"/login"})
    public String login(HttpServletResponse response, String username, String password, Model model) {
        boolean login = authService.login(username, password);
        if (!login) {
            model.addAttribute("message", "账号或者密码错误");
            return "index";
        }
        String token = UUID.randomUUID().toString();
        CookieUtil.setCookie(response, "token", token, false);
        authService.update(token);
        info(model);
        return "admin";
    }

    @PostMapping("/sendMessage")
    @ResponseBody
    public Result sendMessage(String topic, String data){
        if(StringUtils.isBlank(topic)){
            return Result.fail("话题为空");
        }
        if(StringUtils.isBlank(data)){
            return Result.fail("指令为空");
        }

        mqttSendService.sendToMqtt(topic, data);
        return Result.success("指令已经下发");
    }
    private void info(Model model){
        model.addAttribute("waterPump", true);
        model.addAttribute("o2Pump", true);
        model.addAttribute("led", true);
        model.addAttribute("temperature", 30);
        model.addAttribute("humidity", 12);

    }
}
