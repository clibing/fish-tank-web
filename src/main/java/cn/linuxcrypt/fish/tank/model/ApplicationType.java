package cn.linuxcrypt.fish.tank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author clibing
 */

@Getter
@AllArgsConstructor
public enum ApplicationType {
    FishTank("01", "鱼缸");

    private String type;
    private String title;
}
