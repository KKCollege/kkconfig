package cn.kimmking.kkconfig.client.repository;

import cn.kimmking.kkconfig.client.config.ConfigMeta;

import java.util.Map;

/**
 * repository change listener.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/4 20:48
 */
public interface KKRepositoryChangeListener {

    void onChange(ChangeEvent event);

    record ChangeEvent(ConfigMeta meta, Map<String, String> config) {}

}
