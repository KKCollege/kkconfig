package cn.kimmking.kkconfig.client.config;

import java.util.Map;

/**
 * kk config service impl.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/2 20:48
 */
public class KKConfigServiceImpl implements KKConfigService {

    Map<String, String> config;

    public KKConfigServiceImpl(Map<String, String> config) {
        this.config = config;
    }

    @Override
    public String[] getPropertyNames() {
        return this.config.keySet().toArray(new String[0]);
    }

    @Override
    public String getProperty(String name) {
        return this.config.get(name);
    }
}
