package cn.linuxcrypt.fish.tank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author clibing
 */

@Getter
@AllArgsConstructor
public enum DeviceStatus {
    NORMAL("11", "正常"),
    PORTABLE_BATTERY_EXCEPTION("01", "充电宝异常"),
    SOLAR_PANEL_EXCEPTION("02", "太阳板异常"),
    other_exception("03", "其他异常");

    private String code;

    private String title;
}
