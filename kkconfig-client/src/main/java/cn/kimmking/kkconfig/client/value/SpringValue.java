package cn.kimmking.kkconfig.client.value;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * spring value.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/8 下午8:37
 */
@Data
@AllArgsConstructor
public class SpringValue {
    private Object bean;
    private String beanName;
    private String key;
    private String placeholder;
    private Field  field;
}
