package cn.kimmking.kkconfig.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Test demo config.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/2 20:30
 */
@Data
@ConfigurationProperties(prefix = "kk")
public class KKDemoConfig {
    String a;
    String b;
}
