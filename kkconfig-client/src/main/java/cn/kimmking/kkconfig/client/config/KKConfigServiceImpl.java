package cn.kimmking.kkconfig.client.config;

import cn.kimmking.kkconfig.client.repository.KKRepository;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * kk config service impl.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/2 20:48
 */
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
        this.config = event.config();
        if(!config.isEmpty()) {
            System.out.println("[KKCONFIG] fire an EnvironmentChangeEvent with keys:" + config.keySet());
            applicationContext.publishEvent(new EnvironmentChangeEvent(config.keySet()));
        }
    }
}
