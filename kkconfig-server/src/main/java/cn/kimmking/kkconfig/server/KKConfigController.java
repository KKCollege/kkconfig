package cn.kimmking.kkconfig.server;

import cn.kimmking.kkconfig.server.dal.ConfigsMapper;
import cn.kimmking.kkconfig.server.model.Configs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * config server endpoint.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/4/27 下午8:18
 */
@RestController
public class KKConfigController {

    @Autowired
    ConfigsMapper mapper;
    Map<String, Long> VERSIONS = new HashMap<>();

    @GetMapping("/list")
    public List<Configs> list(String app, String env, String ns) {
        return mapper.list(app, env, ns);
    }

    @RequestMapping("/update")
    public List<Configs> update(@RequestParam("app") String app,
                                @RequestParam("env") String env,
                                @RequestParam("ns") String ns,
                                @RequestBody Map<String, String> params) {
        params.forEach((k, v) -> {
            insertOrUpdate(new Configs(app, env, ns, k, v));
        });
        VERSIONS.put(app + "-" + env + "-" + ns, System.currentTimeMillis());
        return mapper.list(app, env, ns);
    }

    private void insertOrUpdate(Configs configs) {
        Configs conf = mapper.select(configs.getApp(), configs.getEnv(),
                                     configs.getNs(), configs.getPkey());
        if(conf == null) {
            mapper.insert(configs);
        } else {
            mapper.update(configs);
        }
    }

    @GetMapping("/version")
    public long version(String app, String env, String ns) {
        return VERSIONS.getOrDefault(app + "-" + env + "-" + ns, -1L);
    }
}
