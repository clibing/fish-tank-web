package cn.linuxcrypt.fish.tank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * @author clibing
 * 01: 协议版本
 * 11: 正常， 01: 充电宝异常, 02: 太阳板异常, 04: 温湿度读取错误, 05: sntp时间错误, 06 其他错误
 * 110: 1代表类型为1的设置，暂定为供养泵, 1代表长度， 0代表关闭还是开启
 * 210: 2代表类型为2的设备, 暂定为排污泵, 1代表长度， 0代表关闭还是开启
 * 310: 3代表类型为3的设备, 暂定为LED灯, 1代表长度， 0代表关闭还是开启
 * 441222: 4代表类型为4的设备, 暂定为温湿度检测, 4代表长度 12代表温度 22代表湿度
 */
@Data
public class DescribeProtocol {
    @Getter
    @AllArgsConstructor
    public enum Protocol implements Result {

        /**
         * 获取发送的协议
         */
        version(0, 2) {
            @Override
            public String handler(String item) {
                String v = item.substring(0, 2);
                if (v.startsWith("0")) {
                    return v;
                }
                throw new RuntimeException("协议错误");
            }
        },

        /**
         * 解析水泵协议
         */
        watch_pump(1, 3) {
            @Override
            public Integer handler(String item) {
                return Integer.parseInt(item.substring(1));
            }
        },
        o2_pump(2, 3) {
            @Override
            public Integer handler(String item) {
                return Integer.parseInt(item.substring(1));
            }
        },
        led(3, 3) {
            @Override
            public Integer handler(String item) {
                return Integer.parseInt(item.substring(1));
            }
        },
        dht(4, 6) {
            @Override
            public Pair<Integer, Integer> handler(String item) {
                Integer value = Integer.parseInt(item.substring(1));
                return ImmutablePair.of(value / 100, value % 100);
            }
        },
        ;
        private int type;
        private int len;

        @Override
        public boolean match(String item) {
            return item.startsWith(getType() + "");
        }
    }

    interface Result {
        <T> T handler(String item);

        boolean match(String item);
    }

    private String version;

    private String message;

    private Map<Protocol, Object> resolving(String message) {
        Map<Protocol, Object> map = new HashMap<>();
        String[] split = message.split("\\|");
        int len = split.length;
        if (len <= 2) {
            return map;
        }
        if (!split[0].equals(split[len - 1])) {
            return map;
        }

        for (int i = 0; i < len - 1; i++) {
            String item = split[i];
            for (Protocol p : Protocol.values()) {
                if (p.match(item)) {
                    Object handler = p.handler(item);
                    map.put(p, handler);
                    break;
                }
            }
        }

        return map;
    }


    public static void main(String[] args) {
        String value = "01|10|20|30|41222|51|01";
        DescribeProtocol describeProtocol = new DescribeProtocol();
        describeProtocol.resolving(value);
    }
}
