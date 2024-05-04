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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties({KKDemoConfig.class})
@EnableKKConfig
@RestController
public class KkconfigDemoApplication {

    @Value("${kk.a}")
    private String a;

    @Value("${kk.b}")
    private String b;

    @Autowired
    private KKDemoConfig kkDemoConfig;

    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(KkconfigDemoApplication.class, args);
    }

    @GetMapping("/demo")
    public String demo() {
        return "kk.a = " + a + "\n"
                + "kk.b = " + b + "\n"
                + "demo.a = " + kkDemoConfig.getA() + "\n"
                + "demo.b = " + kkDemoConfig.getB() + "\n";
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
