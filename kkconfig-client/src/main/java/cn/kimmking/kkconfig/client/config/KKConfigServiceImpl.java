package cn.kimmking.kkconfig.client.config;

import cn.kimmking.kkconfig.client.repository.KKRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * kk config service impl.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/2 20:48
 */
@Slf4j
public class KKConfigServiceImpl implements KKConfigService {

    Map<String, String> config;
    ApplicationContext applicationContext;

    public KKConfigServiceImpl(ApplicationContext applicationContext, Map<String, String> config) {
        this.applicationContext = applicationContext;
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

    @Override
    public void onChange(ChangeEvent event) {
        Set<String> keys = calcChangedKeys(this.config, event.config());
        if(keys.isEmpty()) {
            log.info("[KKCONFIG] calcChangedKeys return empty, ignore update.");
            return;
        }
        this.config = event.config();
        if(!config.isEmpty()) {
            log.info("[KKCONFIG] fire an EnvironmentChangeEvent with keys: {}", keys);
            applicationContext.publishEvent(new EnvironmentChangeEvent(keys));
        }
    }

    private Set<String> calcChangedKeys(Map<String, String> oldConfigs, Map<String, String> newConfigs) {
        if(oldConfigs.isEmpty()) return newConfigs.keySet();
        if(newConfigs.isEmpty()) return oldConfigs.keySet();
        Set<String> news = newConfigs.keySet().stream()
                .filter(key -> !newConfigs.get(key).equals(oldConfigs.get(key)))
                .collect(Collectors.toSet());
        oldConfigs.keySet().stream().filter(key -> !newConfigs.containsKey(key)).forEach(news::add);
        return news;
    }
}
