package cn.kimmking.kkconfig.client.config;

import cn.kimmking.kkconfig.client.repository.KKRepository;

/**
 * kk config service.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/2 20:44
 */
public interface KKConfigService {

    static KKConfigService getDefault(ConfigMeta meta) {
        KKRepository repository = KKRepository.getDefault(meta);
        return new KKConfigServiceImpl(repository.getConfig());
    }

    String[] getPropertyNames();

    String getProperty(String name);

}
