package cn.kimmking.kkconfig.client.config;

import org.springframework.core.env.EnumerablePropertySource;

/**
 * KK property source.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/2 20:36
 */
public class KKPropertySource extends EnumerablePropertySource<KKConfigService> {

    public KKPropertySource(String name, KKConfigService source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        return source.getPropertyNames();
    }

    @Override
    public Object getProperty(String name) {
        return source.getProperty(name);
    }
}
