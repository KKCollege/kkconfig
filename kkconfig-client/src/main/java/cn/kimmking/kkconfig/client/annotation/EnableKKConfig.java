package cn.kimmking.kkconfig.client.annotation;

import cn.kimmking.kkconfig.client.config.KKConfigRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * KK config client entrypoint.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/2 20:20
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Import({KKConfigRegistrar.class})
public @interface EnableKKConfig {
}
