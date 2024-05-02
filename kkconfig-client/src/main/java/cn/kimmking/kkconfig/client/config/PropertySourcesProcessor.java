package cn.kimmking.kkconfig.client.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * kk property sources processor.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/2 20:52
 */
@Data
public class PropertySourcesProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {

    private final static String KK_PROPERTY_SOURCES = "KKPropertySources";
    private final static String KK_PROPERTY_SOURCE  = "KKPropertySource";
    Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
        if(env.getPropertySources().contains(KK_PROPERTY_SOURCES)) {
            return;
        }
        // 通过 http 请求，去kkconfig-sever获取配置 TODO
        Map<String, String> config = new HashMap<>();
        config.put("kk.a", "dev500");
        config.put("kk.b", "b600");
        config.put("kk.c", "c700");
        KKConfigService configService = new KKConfigServiceImpl(config);
        KKPropertySource propertySource = new KKPropertySource(KK_PROPERTY_SOURCE, configService);
        CompositePropertySource composite = new CompositePropertySource(KK_PROPERTY_SOURCES);
        composite.addPropertySource(propertySource);
        env.getPropertySources().addFirst(composite);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
