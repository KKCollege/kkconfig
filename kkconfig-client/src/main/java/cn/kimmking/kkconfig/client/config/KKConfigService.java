package cn.kimmking.kkconfig.client.config;

import cn.kimmking.kkconfig.client.repository.KKRepository;
import cn.kimmking.kkconfig.client.repository.KKRepositoryChangeListener;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * kk config service.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/2 20:44
 */
public interface KKConfigService extends KKRepositoryChangeListener {

    static KKConfigService getDefault(ApplicationContext applicationContext, ConfigMeta meta) {
        KKRepository repository = KKRepository.getDefault(applicationContext, meta);
        Map<String, String> config = repository.getConfig();
        KKConfigService configService = new KKConfigServiceImpl(applicationContext, config);
        repository.addListener(configService);
        return configService;
    }

    String[] getPropertyNames();

    String getProperty(String name);



}
