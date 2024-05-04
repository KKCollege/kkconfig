package cn.kimmking.kkconfig.client.repository;

import cn.kimmking.kkconfig.client.config.ConfigMeta;

import java.util.Map;

/**
 * interface to get config from remote.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/4 19:10
 */
public interface KKRepository {

    static KKRepository getDefault(ConfigMeta meta) {
        return new KKRepositoryImpl(meta);
    }

    Map<String, String> getConfig();

}
