package cn.kimmking.kkconfig.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import cn.kimmking.kkconfig.client.annotation.EnableKKConfig;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties({KKDemoConfig.class})
@EnableKKConfig
public class KkconfigDemoApplication {

    @Value("${kk.a}")
    private String a;

    @Autowired
    private KKDemoConfig kkDemoConfig;

    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(KkconfigDemoApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        System.out.println(Arrays.toString(environment.getActiveProfiles()));
        return args -> {
            System.out.println(a);
            System.out.println(kkDemoConfig.getA());
        };
    }

}
