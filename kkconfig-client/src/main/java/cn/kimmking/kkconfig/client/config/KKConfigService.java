package cn.kimmking.kkconfig.client.config;

/**
 * kk config service.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/2 20:44
 */
public interface KKConfigService {

    String[] getPropertyNames();

    String getProperty(String name);

}
