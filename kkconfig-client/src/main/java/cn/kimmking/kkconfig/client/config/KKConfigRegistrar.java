package cn.kimmking.kkconfig.client.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Optional;

/**
 * register kk config bean.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/2 21:11
 */
public class KKConfigRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        //ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);
        System.out.println("register PropertySourcesProcessor");

        Optional<String> first = Arrays.stream(registry.getBeanDefinitionNames())
                .filter(x -> PropertySourcesProcessor.class.getName().equals(x)).findFirst();

        if (first.isPresent()) {
            System.out.println("PropertySourcesProcessor already registered");
            return;
        }

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(PropertySourcesProcessor.class).getBeanDefinition();
        registry.registerBeanDefinition(PropertySourcesProcessor.class.getName(), beanDefinition);
    }
}
